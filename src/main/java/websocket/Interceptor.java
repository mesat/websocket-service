package websocket;

import java.awt.FlowLayout;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.core.CvType;

public class Interceptor {

	static JFrame frameImage; 
	static JLabel jLabel;
	static ImageIcon imageIcon;
	private static final Log logger = LogFactory.getLog(Interceptor.class);
	private ConcurrentMap<Long, byte[]> frame;
	private ConcurrentMap<Long, byte[]> frameBuffer;

	public volatile Boolean onBuffer = Boolean.valueOf(false);
	private static Interceptor INSTANCE;

	private Interceptor() {
		frameBuffer = new ConcurrentHashMap();
		frame = new ConcurrentHashMap();
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
		
		frameImage = new JFrame("image");
		frameImage.setVisible(false);
		frameImage.setAlwaysOnTop(false);
		frameImage.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		imageIcon = new ImageIcon();
		jLabel = new JLabel(imageIcon);

		frameImage.getContentPane().add(jLabel);

		//frameImage.getContentPane().setLayout(new FlowLayout());
	}

	public static Interceptor getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new Interceptor();
		}
		return INSTANCE;
	}

	public void ProcessData(byte[][] list) {

		// To load OpenCV core library
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
		List<Mat> imageList = new ArrayList<>();

		String imgPath = "home/mesat/Pictures/home/mesat/Pictures/Screenshot from 2019-03-02 23-16-13.png";
		Mat image = Imgcodecs.imread(imgPath, Imgcodecs.CV_LOAD_IMAGE_COLOR);
		List<Mat> mv = new ArrayList<>();
		Core.split(image, mv);

		// Create an zero pixel image for filling purposes - will become clear later
		// Also create container images for B G R channels as colour images
		Mat empty_image = new Mat().zeros(image.rows(), image.cols(), CvType.CV_8UC1);
		Mat result_blue = new Mat(image.rows(), image.cols(), CvType.CV_8UC3); // notice the 3 channels here!
		Mat result_green = new Mat(image.rows(), image.cols(), CvType.CV_8UC3); // notice the 3 channels here!
		Mat result_red = new Mat(image.rows(), image.cols(), CvType.CV_8UC3); // notice the 3 channels here!

		Mat imageMerged = new Mat();
		Mat imageR = new Mat();
		Mat imageG = new Mat();
		Mat imageB = new Mat();
		imageR.get(400, 400, list[0]);
		imageG.get(400, 400, list[1]);
		imageB.get(400, 400, list[2]);
		imageList.add(imageB);
		imageList.add(imageG);
		imageList.add(imageR);

		Core.merge(imageList, imageMerged);

		// Creating the empty destination matrix
		Mat destination = new Mat();

		// Converting the image to gray scale and
		// saving it in the dst matrix
		Imgproc.cvtColor(imageMerged, destination, Imgproc.COLOR_RGB2YCrCb);

		// Writing the image
		Imgcodecs.imwrite("GeeksforGeeks.jpg", destination);
		System.out.println("The image is successfully to Grayscale");
	}

	public void addData(Long time, byte[] data) {
		if (time == null || data == null || data.length < 1) {
			return;
		}

		if (onBuffer.booleanValue()) {

			byte[] contains = frameBuffer.put(time, data);
			if (contains != null) {
				logger.error("TIME CONFILICTED!!!!");
			}
			Long[] loa = (Long[]) frameBuffer.keySet().toArray(new Long[0]);
			String messageLog = new String();
			for (int i = 0; i < loa.length; i++) {
				messageLog = messageLog.concat(String.valueOf(loa[i])).concat(" ");
			}
			logger.info("frameBuffer: ".concat(messageLog));
			synchronized (onBuffer) {
				if (frameBuffer.size() > 2) {
					onBuffer = Boolean.valueOf(false);
					List<Mat> mv2 = new ArrayList<>();

					for (byte[] bytedata : frameBuffer.values()) {
						Mat matdata = new Mat(720,1280,CvType.CV_8UC1);
						matdata.put(0, 0, bytedata);
						mv2.add(matdata);
					}
					if (mv2.size() == 3) {
						Mat show = new Mat();
						 Core.merge(mv2, show);
						 imshow(show, "image");

					} else
						logger.error("frameBuffer size was not 3");
					mv2.clear();
					frameBuffer.clear();
					return;
				}
			}

			return;
		}
		byte[] contains = frame.put(time, data);
		if (contains != null) {
			logger.error("TIME CONFILICTED!!!!");
		}
		Long[] loa = (Long[]) frame.keySet().toArray(new Long[0]);
		String messageLog = "";
		for (int i = 0; i < loa.length; i++) {
			messageLog = messageLog.concat(String.valueOf(loa[i])).concat(" ");
		}
		logger.info("frame: ".concat(messageLog));
		synchronized (onBuffer) {

			if (frame.size() > 2) {
				onBuffer = Boolean.valueOf(true);
				List<Mat> mv2 = new ArrayList<>();

				for (byte[] bytedata : frame.values()) {
					Mat matdata = new Mat(720,1280,CvType.CV_8UC1);
					matdata.put(0, 0, bytedata);
					mv2.add(matdata);
				}
				if (mv2.size() == 3) {
					Mat show = new Mat();
					 Core.merge(mv2, show);
					 imshow(show, "image");

				} else
					logger.error("frame size was not 3");
				mv2.clear();
				frame.clear();
				return;
			}
		}

	}

	public void imshow(Mat src, String name) {
		BufferedImage bufImage = null;
		try {


			MatOfByte matOfByte = new MatOfByte();
			Imgcodecs.imencode(".jpg", src, matOfByte);
			byte[] byteArray = matOfByte.toArray();
			InputStream inputStream = new ByteArrayInputStream(byteArray);
			bufImage = ImageIO.read(inputStream);
			//frameImage.setVisible(false);
			imageIcon.setImage(bufImage);
			//((JLabel)frameImage.getContentPane().getComponent(0)).setIcon(new ImageIcon(bufImage));
			frameImage.repaint();
			frameImage.pack();

			frameImage.setVisible(true);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
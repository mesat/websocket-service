package websocket;

import static org.junit.Assert.*;

import java.awt.FlowLayout;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

import org.junit.Test;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.core.Scalar;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

public class InterceptorTests {

	@Test
	public void testGetInstance() {
		// fail("Not yet implemented");
	}

	@Test
	public void testProcessData() {

		try {
			// To load OpenCV core library
			// System.load(new
			// File("/home/mesat/git/websocket-example/open").getAbsolutePath());
			System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
			List<Mat> imageList = new ArrayList<>();

			String imgPath = "/home/mesat/Downloads/maxresdefault.jpg";
			Mat image = Imgcodecs.imread(imgPath, Imgcodecs.IMREAD_UNCHANGED);
			image.convertTo(image, CvType.CV_8UC3);
			//Imgproc.cvtColor(image, image, Imgproc.COLOR_RGB2Lab);
			List<Mat> mv = new ArrayList<>();
			List<Mat> mv2 = new ArrayList<>();
			Core.split(image, mv);
			mv2.add(mv.get(1));
			mv2.add(mv.get(2));
			mv2.add(mv.get(0));
			Mat mat = new Mat();
			Core.merge(mv2, mat);

			// Create an zero pixel image for filling purposes - will become clear later
			// Also create container images for B G R channels as colour images
			Mat empty_image = new Mat().zeros(image.rows(), image.cols(), CvType.CV_8UC1);
			Mat result_blue = new Mat(image.rows(), image.cols(), CvType.CV_8UC3); // notice the 3 channels here!
			Mat result_green = new Mat(image.rows(), image.cols(), CvType.CV_8UC3); // notice the 3 channels here!
			Mat result_red = new Mat(image.rows(), image.cols(), CvType.CV_8UC3); // notice the 3 channels here!

			Mat imageMerged = new Mat();
			Mat imageR = mv.get(2);
			Mat imageG = mv.get(1);
			Mat imageB = mv.get(0);
			//Core.inRange(imageB, new Scalar(50), new Scalar(51), imageB);
			//Core.subtract(imageB, new Scalar(150), imageB);
			//Core.inRange(imageR, new Scalar(0, 0, 0),new Scalar(255,0, 0), imageR);
			Imgproc.threshold(imageB, imageB, 100, 101, Imgproc.THRESH_BINARY);
			byte[][] list = new byte[3][1000];
			imageR.get(0, 0,list[0]);
			imshow(image, "image");
			imshow(mat, "imageB");
			imshow(imageG, "imageG");
			imshow(imageR, "imageR");
			imageR.put(image.rows(), image.cols(), list[0]);
			imageG.put(image.rows(), image.cols(), list[1]);
			imageB.put(image.rows(), image.cols(), list[2]);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	/**
	 * Segment an image based on color ranges.
	 * @param input The image on which to perform the RGB threshold.
	 * @param red The min and max red.
	 * @param green The min and max green.
	 * @param blue The min and max blue.
	 * @param output The image in which to store the output.
	 */
	private void rgbThreshold(Mat input, double[] red, double[] green, double[] blue,
		Mat out) {
		Imgproc.cvtColor(input, out, Imgproc.COLOR_BGR2RGB);
		Core.inRange(out, new Scalar(red[0], green[0], blue[0]),
			new Scalar(red[1], green[1], blue[1]), out);
	}

	public void imshow(Mat src, String name) {
		BufferedImage bufImage = null;
		try {
			MatOfByte matOfByte = new MatOfByte();
			Imgcodecs.imencode(".jpg", src, matOfByte);
			byte[] byteArray = matOfByte.toArray();
			InputStream in = new ByteArrayInputStream(byteArray);
			bufImage = ImageIO.read(in);

			JFrame frame = new JFrame(name);
			frame.getContentPane().setLayout(new FlowLayout());
			frame.getContentPane().add(new JLabel(new ImageIcon(bufImage)));
			frame.pack();
			frame.setVisible(true);
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testAddData() {
		// fail("Not yet implemented");
	}

}

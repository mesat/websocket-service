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
import org.opencv.imgcodecs.Imgcodecs;

public class InterceptorTests {

	@Test
	public void testGetInstance() {
		// fail("Not yet implemented");
	}

	@Test
	public void testProcessData() {

		try {
			// To load OpenCV core library
			//System.load(new File("/home/mesat/git/websocket-example/open").getAbsolutePath());
			System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
			List<Mat> imageList = new ArrayList<>();

			String imgPath = "/home/mesat/Downloads/3SM5q_1546759681_4928.jpg";
			Mat image = Imgcodecs.imread(imgPath, Imgcodecs.CV_LOAD_IMAGE_ANYCOLOR);
			image.convertTo(image, CvType.CV_32F);
			List<Mat> mv = new ArrayList<>();
			Core.split(image, mv);

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
			byte[][] list = new byte[3][10];
			imshow(image);
			imshow(imageB);
			imshow(imageG);
			imshow(imageR);
			imageR.put(image.rows(), image.cols(), list[0]);
			imageG.put(image.rows(), image.cols(), list[1]);
			imageB.put(image.rows(), image.cols(), list[2]);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	public void imshow(Mat src){
	    BufferedImage bufImage = null;
	    try {
	        MatOfByte matOfByte = new MatOfByte();
	        Imgcodecs.imencode(".jpg", src, matOfByte); 
	        byte[] byteArray = matOfByte.toArray();
	        InputStream in = new ByteArrayInputStream(byteArray);
	        bufImage = ImageIO.read(in);

	        JFrame frame = new JFrame("Image");
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

package websocket;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.*;
import org.opencv.imgproc.Imgproc;








public class Interceptor
{
  private static final Log logger = LogFactory.getLog(Interceptor.class);
  private ConcurrentMap<Long, byte[]> frame;
  private ConcurrentMap<Long, byte[]> frameBuffer;
  public volatile Boolean onBuffer = Boolean.valueOf(false);
  private static Interceptor INSTANCE;
  
  private Interceptor()
  {
    frameBuffer = new ConcurrentHashMap();
    frame = new ConcurrentHashMap();
  }
  
  public static Interceptor getInstance()
  {
    if (INSTANCE == null) {
      INSTANCE = new Interceptor();
    }
    return INSTANCE;
  }
  public void ProcessData(byte[][] list) {
	  // To load  OpenCV core library 
      System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
	  List<Mat> imageList = new ArrayList<>();
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
  
  public void addData(Long time, byte[] data)
  {
	
    if (onBuffer.booleanValue()) {
      synchronized (onBuffer) {
        if (frameBuffer.size() > 2) {
          onBuffer = Boolean.valueOf(false);
        }
      }
      frameBuffer.put(time, data);
      Long[] loa = (Long[])frameBuffer.keySet().toArray(new Long[0]);
      String messageLog = new String();
      for (int i = 0; i < loa.length; i++) {
        messageLog.concat(String.valueOf(loa[i])).concat(" ");
      }
      logger.info("frameBuffer: ".concat(messageLog));
      frameBuffer.clear();
      
      return;
    }
    synchronized (onBuffer) {
      if (frame.size() > 2) {
        onBuffer = Boolean.valueOf(true);
      }
    }
    frame.put(time, data);
    Long[] loa = (Long[])frame.keySet().toArray(new Long[0]);
    String messageLog = "";
    for (int i = 0; i < loa.length; i++) {
      messageLog = messageLog.concat(String.valueOf(loa[i])).concat(" ");
    }
    logger.info("frame: ".concat(messageLog));
    frame.clear();
  }
}
package websocket;

import java.util.Calendar;

import org.apache.tomcat.util.buf.ByteBufferUtils;

import com.fasterxml.jackson.core.util.ByteArrayBuilder;

import ch.qos.logback.core.encoder.ByteArrayUtil;

public class ByteArrayModel
{
  private long time;
  private byte[] data = new byte[1024*1024*4];
  
  private ByteArrayModel() {}
  
  public ByteArrayModel(byte[] data)
  {
    setTime(Calendar.getInstance().getTimeInMillis());
    this.data = new byte[data.length];
    ByteArrayBuilder b =new ByteArrayBuilder();
    b.write(data);
    this.data =b.toByteArray();
    
  }
  
  public byte[] getData() {
    return data;
  }
  
  private void setData(byte[] data) {
    this.data = data;
  }
  
  public long getTime() {
    return time;
  }
  
  public void setTime(long time) {
    this.time = time;
  }
}
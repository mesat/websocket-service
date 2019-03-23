package websocket;

import java.util.Calendar;

public class ByteArrayModel
{
  private long time;
  private byte[] data;
  
  public ByteArrayModel() {}
  
  public ByteArrayModel(byte[] data)
  {
    setTime(Calendar.getInstance().getTimeInMillis());
    this.data = data;
  }
  
  public byte[] getData() {
    return data;
  }
  
  public void setData(byte[] data) {
    this.data = data;
  }
  
  public long getTime() {
    return time;
  }
  
  public void setTime(long time) {
    this.time = time;
  }
}
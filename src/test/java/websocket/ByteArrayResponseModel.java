package websocket;

public class ByteArrayResponseModel
{
  private String content;
  
  public ByteArrayResponseModel() {}
  
  public ByteArrayResponseModel(String content)
  {
    this.content = content;
  }
  
  public String getContent() {
    return content;
  }
}
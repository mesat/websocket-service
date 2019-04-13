package websocket;

import ch.qos.logback.core.encoder.ByteArrayUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.util.HtmlUtils;


@Controller
public class ServerResponseController
{
  private static final Log logger = LogFactory.getLog(ServerResponseController.class);
  
  public ServerResponseController() {}
  
  @MessageMapping({"/hello"})
  @SendTo({"/topic/response"})
  public ByteArrayResponseModel getData(ByteArrayModel message) throws Exception { logger.info(String.valueOf(message.getData()));
    Interceptor.getInstance().addData(Long.valueOf(message.getTime()), message.getData());
    return new ByteArrayResponseModel(HtmlUtils.htmlEscape(" "/*String.valueOf(ByteArrayUtil.toHexString(message.getData()))*/));
  }
}
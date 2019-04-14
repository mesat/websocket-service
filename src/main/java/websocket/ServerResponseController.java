package websocket;

import ch.qos.logback.core.encoder.ByteArrayUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.util.HtmlUtils;

@Controller
public class ServerResponseController {
	private static final Log logger = LogFactory.getLog(ServerResponseController.class);

	public ServerResponseController() {
	}

	@MessageMapping({ "/rgbdata" })
	@SendTo({ "/topic/response" })
	public ByteArrayResponseModel getData(ByteArrayModel message) throws Exception {
		logger.info("Data Size: " + message.getData().length + " Height: "+ message.getHeight()+" Width: " +message.getWidth());
		Interceptor.getInstance().addData(message);
		return new ByteArrayResponseModel(
				HtmlUtils.htmlEscape(" "/* String.valueOf(ByteArrayUtil.toHexString(message.getData())) */));
	}
}
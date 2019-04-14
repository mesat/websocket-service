package websocket;

import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.StompWebSocketEndpointRegistration;
import org.springframework.web.socket.config.annotation.WebSocketTransportRegistration;

@org.springframework.context.annotation.Configuration
@org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker
public class WebSocketConfig implements org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer
{
  public WebSocketConfig() {}
  
  public void configureMessageBroker(MessageBrokerRegistry config)
  {
    config.enableSimpleBroker(new String[] { "/topic" });
    config.setApplicationDestinationPrefixes(new String[] { "/app" });
  }
  
  public  void configureWebSocketTransport(WebSocketTransportRegistration registration) {
      registration.setSendTimeLimit(15 * 1000*100).setSendBufferSizeLimit(512 * 1024*4).setMessageSizeLimit(1024*1024*64);
  }


  public void registerStompEndpoints(StompEndpointRegistry registry)
  {
    registry.addEndpoint(new String[] { "/rgbdata" }).setAllowedOrigins(new String[] { "*" }).withSockJS();
  }
}
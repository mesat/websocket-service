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
      registration.setSendTimeLimit(1000).setSendBufferSizeLimit(1229000).setMessageSizeLimit(1229000);
  }


  public void registerStompEndpoints(StompEndpointRegistry registry)
  {
    registry.addEndpoint(new String[] { "/rgbdata" }).setAllowedOrigins(new String[] { "*" }).withSockJS();
  }
}
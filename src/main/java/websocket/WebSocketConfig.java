package websocket;

import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.StompWebSocketEndpointRegistration;

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
  


  public void registerStompEndpoints(StompEndpointRegistry registry)
  {
    registry.addEndpoint(new String[] { "/hello" }).setAllowedOrigins(new String[] { "*" }).withSockJS();
  }
}
package websocket;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class Application
  extends SpringBootServletInitializer
{
  public Application() {}
  
  protected SpringApplicationBuilder configure(SpringApplicationBuilder application)
  {
    return application.sources(new Class[] { Application.class });
  }
  
  public static void main(String[] args) throws Exception {
    SpringApplication.run(Application.class, args);
  }
}
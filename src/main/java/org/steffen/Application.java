package org.steffen;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.SpringServletContainerInitializer;

@SpringBootApplication
public class Application extends SpringServletContainerInitializer
{
    private static final Class<Application> applicationClass = Application.class;

    public static void main(String[] args)
    {
        SpringApplication.run(applicationClass, args);
    }
}

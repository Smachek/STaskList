package com.smachek.stasklist.web_app;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.smachek.*")
public class Application extends SpringBootServletInitializer {
    public static void main(String[] args) {
        new SpringApplicationBuilder(Application.class)
            .web(WebApplicationType.SERVLET)
            .run(args);
    }
}

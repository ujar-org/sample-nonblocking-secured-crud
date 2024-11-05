package com.iqkv.incubator.sample.webfluxcrudsecured;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.metrics.buffering.BufferingApplicationStartup;

@SpringBootApplication
public class WebfluxCrudSecuredApplication {
  public static void main(String[] args) {
    SpringApplication springApplication = new SpringApplication(WebfluxCrudSecuredApplication.class);
    springApplication.setApplicationStartup(new BufferingApplicationStartup(2048));
    springApplication.run(args);
  }
}

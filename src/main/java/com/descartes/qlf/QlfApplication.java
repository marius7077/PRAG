package com.descartes.qlf;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

// Ceci est une application Spring
// Un deuxième commentaire
@SpringBootApplication
@EnableConfigurationProperties
@EntityScan(basePackages = {"com.descartes.qlf.model"})
public class QlfApplication {

  public static void main(String[] args) {
    SpringApplication.run(QlfApplication.class, args);
  }

}
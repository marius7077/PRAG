package com.descartes.qlf;

import com.descartes.qlf.properties.StorageProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(StorageProperties.class)
public class QlfApplication {

  public static void main(String[] args) {
    SpringApplication.run(QlfApplication.class, args);
  }
}

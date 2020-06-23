package com.descartes.qlf.configuration;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class QlfConfiguration {

  @Value("${spring.datasource.hikari.jdbc-url}")
  private String url;

  @Value("${spring.datasource.hikari.username}")
  private String username;

  @Value("${spring.datasource.hikari.password}")
  private String password;

  @Value("${spring.datasource.hikari.driver-class-name}")
  private String driver;

  @Value("${spring.datasource.hikari.maximum-pool-size}")
  private int poolSize;

  @Bean
  public DataSource dataSource() {
    HikariConfig config = new HikariConfig();
    config.setJdbcUrl(url);
    config.setUsername(username);
    config.setPassword(password);
    config.setDriverClassName(driver);
    config.setMaximumPoolSize(poolSize);
    return new HikariDataSource(config);
  }
}

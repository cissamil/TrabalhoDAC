package br.ufpr;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients(basePackages = "br.ufpr")
public class MsClienteApplication {
  public static void main(String[] args){

    SpringApplication.run(MsClienteApplication.class, args);
  }
}

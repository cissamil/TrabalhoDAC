package br.ufpr;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.openfeign.FeignClient;

@SpringBootApplication
@EnableFeignClients(basePackages = "br.ufpr")
public class MsSagaApplication {
  public static void main(String[] args){
    SpringApplication.run(MsSagaApplication.class, args);
  }
}

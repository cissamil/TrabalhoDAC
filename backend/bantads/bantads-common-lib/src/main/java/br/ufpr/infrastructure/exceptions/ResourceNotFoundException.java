package br.ufpr.infrastructure.exceptions;

import org.springframework.stereotype.Component;

public class ResourceNotFoundException extends RuntimeException{
  public ResourceNotFoundException(String message){
    super(message);
  }
}

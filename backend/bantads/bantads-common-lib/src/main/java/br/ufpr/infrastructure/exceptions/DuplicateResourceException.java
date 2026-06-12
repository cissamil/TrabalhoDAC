package br.ufpr.infrastructure.exceptions;

import org.springframework.stereotype.Component;

public class DuplicateResourceException extends RuntimeException{
  public DuplicateResourceException(String message){
    super(message);
  }


}

package br.ufpr.infrastructure.exceptions;

public class UnavailableServiceException extends RuntimeException{

  public UnavailableServiceException(String message){
    super(message);
  }
}

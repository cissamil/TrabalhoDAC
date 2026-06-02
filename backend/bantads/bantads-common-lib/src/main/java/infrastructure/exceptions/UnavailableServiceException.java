package infrastructure.exceptions;

import org.springframework.stereotype.Component;

@Component
public class UnavailableServiceException extends RuntimeException{

  public UnavailableServiceException(String message){
    super(message);
  }
}

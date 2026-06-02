package infrastructure.exceptions;

import org.springframework.stereotype.Component;

@Component
public class DuplicateResourceException extends RuntimeException{
  public DuplicateResourceException(String message){
    super(message);
  }


}

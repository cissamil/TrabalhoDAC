package infrastructure.exceptions;

import org.springframework.stereotype.Component;

@Component
public class ResourceNotFoundException extends RuntimeException{
  public ResourceNotFoundException(String message){
    super(message);
  }
}

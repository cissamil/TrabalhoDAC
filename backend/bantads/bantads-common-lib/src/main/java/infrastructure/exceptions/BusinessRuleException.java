package infrastructure.exceptions;

import org.springframework.stereotype.Component;

@Component
public class BusinessRuleException extends RuntimeException{

  public BusinessRuleException(String message){
    super(message);
  }
}

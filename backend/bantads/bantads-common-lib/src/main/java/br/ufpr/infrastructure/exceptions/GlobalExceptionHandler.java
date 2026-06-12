package br.ufpr.infrastructure.exceptions;

import br.ufpr.model.response.StandardErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

public abstract class GlobalExceptionHandler {

  @ExceptionHandler(ResourceNotFoundException.class)
  public ResponseEntity<StandardErrorResponse> handleNotFound(ResourceNotFoundException e, HttpServletRequest request) {
    StandardErrorResponse err = new StandardErrorResponse(
      LocalDateTime.now(),
      HttpStatus.NOT_FOUND.value(),
      "Recurso Não Encontrado",
      e.getMessage(),
      request.getRequestURI()
    );
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(err);
  }

  @ExceptionHandler(BusinessRuleException.class)
  public ResponseEntity<StandardErrorResponse> handleBusinessRule(BusinessRuleException e, HttpServletRequest request) {
    StandardErrorResponse err = new StandardErrorResponse(
      LocalDateTime.now(),
      HttpStatus.BAD_REQUEST.value(),
      "Violação de Regra de Negócio",
      e.getMessage(),
      request.getRequestURI()
    );
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);
  }

  @ExceptionHandler(DuplicateResourceException.class)
  public ResponseEntity<StandardErrorResponse> handleDuplicate(DuplicateResourceException e, HttpServletRequest request) {
    StandardErrorResponse err = new StandardErrorResponse(
      LocalDateTime.now(),
      HttpStatus.CONFLICT.value(),
      "Conflito de Dados",
      e.getMessage(),
      request.getRequestURI()
    );
    return ResponseEntity.status(HttpStatus.CONFLICT).body(err);
  }

  @ExceptionHandler(ForbiddenResourceException.class)
  public ResponseEntity<StandardErrorResponse> handleForbidden(ForbiddenResourceException e, HttpServletRequest request) {
    StandardErrorResponse err = new StandardErrorResponse(
      LocalDateTime.now(),
      HttpStatus.FORBIDDEN.value(),
      "Ação não permitida",
      e.getMessage(),
      request.getRequestURI()
    );
    return ResponseEntity.status(HttpStatus.FORBIDDEN).body(err);
  }

  @ExceptionHandler(UnavailableServiceException.class)
  public ResponseEntity<StandardErrorResponse> handleUnavailable(UnavailableServiceException e, HttpServletRequest request) {
    StandardErrorResponse err = new StandardErrorResponse(
      LocalDateTime.now(),
      HttpStatus.SERVICE_UNAVAILABLE.value(),
      "Serviço Indisponível",
      e.getMessage(),
      request.getRequestURI()
    );
    return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(err);
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<StandardErrorResponse> handleGeneric(Exception e, HttpServletRequest request) {
    e.printStackTrace();
    StandardErrorResponse err = new StandardErrorResponse(
      LocalDateTime.now(),
      HttpStatus.INTERNAL_SERVER_ERROR.value(),
      "Erro Interno",
      "Falha inesperada no servidor.",
      request.getRequestURI()
    );
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(err);
  }
}

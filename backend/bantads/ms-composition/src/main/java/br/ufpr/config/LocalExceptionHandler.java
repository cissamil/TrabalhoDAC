package br.ufpr.config;

import infrastructure.exceptions.GlobalExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class LocalExceptionHandler extends GlobalExceptionHandler {
}

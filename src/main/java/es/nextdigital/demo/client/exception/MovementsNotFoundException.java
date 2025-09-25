package es.nextdigital.demo.client.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class MovementsNotFoundException extends RuntimeException {
  public MovementsNotFoundException(Long bankAccountId) {
    super("Movements for account " + bankAccountId + " not found");
  }
}

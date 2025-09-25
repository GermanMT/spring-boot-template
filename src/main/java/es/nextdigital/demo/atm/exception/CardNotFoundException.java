package es.nextdigital.demo.atm.exception;

public class CardNotFoundException extends RuntimeException {
  public CardNotFoundException(String message) {
    super(message);
  }
}

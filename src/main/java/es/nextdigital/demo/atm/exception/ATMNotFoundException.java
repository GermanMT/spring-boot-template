package es.nextdigital.demo.atm.exception;

public class ATMNotFoundException extends RuntimeException {
  public ATMNotFoundException(String message) {
    super(message);
  }
}

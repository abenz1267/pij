package entities;

public class ValidationException extends Exception {
  public ValidationException(String error) {
    super(error);
  }
}

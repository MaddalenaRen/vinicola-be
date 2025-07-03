package it.epicode.vinicola_be.exception;

public class ValidationException extends Exception{
    public ValidationException(String message) {
        super(message);
    }
}

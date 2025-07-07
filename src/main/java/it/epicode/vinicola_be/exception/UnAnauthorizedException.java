package it.epicode.vinicola_be.exception;

public class UnAnauthorizedException extends RuntimeException{
    public UnAnauthorizedException(String message) {
        super(message);
    }
}

package it.epicode.vinicola_be.exception;


import it.epicode.vinicola_be.model.ApiError;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class PostExceptionHandler {



    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)

    public ApiError NotFoundExceptionHandler(NotFoundException e){
        ApiError apiError = new ApiError();
        apiError.setMessage(e.getMessage());
        apiError.setDataErrore(LocalDateTime.now());

        return apiError;
    }


    @ExceptionHandler(ValidationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiError handleValidationException(ValidationException ex) {
        ApiError error = new ApiError();
        error.setMessage(ex.getMessage());
        error.setDataErrore(LocalDateTime.now());
        return error;
    }

    @ExceptionHandler(UnAnauthorizedException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ApiError handleUnAuthorizedException(UnAnauthorizedException e) {
        ApiError apiError = new ApiError();
        apiError.setMessage(e.getMessage());
        apiError.setDataErrore(LocalDateTime.now());
        return apiError;
    }

}

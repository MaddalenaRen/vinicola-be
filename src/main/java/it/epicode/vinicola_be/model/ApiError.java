package it.epicode.vinicola_be.model;

import lombok.Data;

import java.time.LocalDateTime;


@Data
public class ApiError {

    private String message;
    private LocalDateTime dataErrore;
}

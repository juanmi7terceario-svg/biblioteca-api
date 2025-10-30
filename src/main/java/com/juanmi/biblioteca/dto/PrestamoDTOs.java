package com.juanmi.biblioteca.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

public class PrestamoDTOs {

    @Data
    public static class Create {
        @NotBlank(message = "El usuario es obligatorio")
        private String usuario;

        @NotNull(message = "El libroId es obligatorio")
        private Long libroId;

        @NotNull(message = "La fecha de préstamo es obligatoria")
        private LocalDate fechaPrestamo;

        // La fecha de devolución puede venir vacía (se puede establecer al devolver).
        private LocalDate fechaDevolucion;
    }

    @Data
    public static class Response {
        private Long id;
        private String usuario;
        private Long libroId;
        private String libroTitulo;
        private LocalDate fechaPrestamo;
        private LocalDate fechaDevolucion;
        private Boolean devuelto;
    }
}

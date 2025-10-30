package com.juanmi.biblioteca.dto;

import com.juanmi.biblioteca.domain.enums.Genero;
import jakarta.validation.constraints.*;

import lombok.Data;

public class LibroDTOs {

    @Data
    public static class Create {
        @NotBlank(message = "El título es obligatorio")
        private String titulo;

        @NotBlank(message = "El ISBN es obligatorio")
        private String isbn;

        @NotNull(message = "El año de publicación es obligatorio")
        @Min(value = 1400, message = "El año debe ser razonable")
        @Max(value = 2100, message = "El año debe ser razonable")
        private Integer anioPublicacion;

        @NotNull(message = "El género es obligatorio")
        private Genero genero;

        @NotNull(message = "El autorId es obligatorio")
        private Long autorId;
    }

    @Data
    public static class Update {
        @NotBlank private String titulo;
        @NotNull @Min(1400) @Max(2100) private Integer anioPublicacion;
        @NotNull private Genero genero;
        @NotNull private Long autorId;
    }

    @Data
    public static class Response {
        private Long id;
        private String titulo;
        private String isbn;
        private Integer anioPublicacion;
        private Genero genero;
        private Long autorId;
        private String autorNombre;
    }
}

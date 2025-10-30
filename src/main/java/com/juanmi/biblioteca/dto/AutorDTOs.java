package com.juanmi.biblioteca.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

public class AutorDTOs {

    @Data
    public static class Create {
        @NotBlank(message = "El nombre es obligatorio")
        private String nombre;

        @NotBlank(message = "La nacionalidad es obligatoria")
        private String nacionalidad;

        @Past(message = "La fecha de nacimiento debe ser en el pasado")
        private LocalDate fechaNacimiento;
    }

    @Data
    public static class Response {
        private Long id;
        private String nombre;
        private String nacionalidad;
        private LocalDate fechaNacimiento;
        private List<LibroDTOs.Response> libros;
    }
}

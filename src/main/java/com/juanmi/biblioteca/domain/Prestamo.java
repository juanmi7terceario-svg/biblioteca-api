package com.juanmi.biblioteca.domain;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Entity
@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class Prestamo {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String usuario;
    private LocalDate fechaPrestamo;
    private LocalDate fechaDevolucion;
    private Boolean devuelto = false;

    @ManyToOne
    @JoinColumn(name = "libro_id", nullable = false)
    private Libro libro;
}

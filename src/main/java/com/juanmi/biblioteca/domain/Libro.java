package com.juanmi.biblioteca.domain;

import com.juanmi.biblioteca.domain.enums.Genero;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class Libro {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titulo;
    private String isbn;
    private Integer anioPublicacion;

    @Enumerated(EnumType.STRING)
    private Genero genero;

    @ManyToOne
    @JoinColumn(name = "autor_id", nullable = false)
    private Autor autor;
}

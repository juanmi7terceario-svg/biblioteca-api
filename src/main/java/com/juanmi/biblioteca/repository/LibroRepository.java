package com.juanmi.biblioteca.repository;

import com.juanmi.biblioteca.domain.Autor;
import com.juanmi.biblioteca.domain.Libro;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface LibroRepository extends JpaRepository<Libro, Long> {
    Optional<Libro> findByIsbn(String isbn);
    boolean existsByIsbn(String isbn);
    List<Libro> findByAutor(Autor autor);
}

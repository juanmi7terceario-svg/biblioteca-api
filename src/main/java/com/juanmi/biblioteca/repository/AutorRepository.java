package com.juanmi.biblioteca.repository;

import com.juanmi.biblioteca.domain.Autor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AutorRepository extends JpaRepository<Autor, Long> {
    boolean existsByNombreIgnoreCase(String nombre);
}

package com.juanmi.biblioteca.repository;

import com.juanmi.biblioteca.domain.Prestamo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PrestamoRepository extends JpaRepository<Prestamo, Long> {
    List<Prestamo> findByUsuarioIgnoreCase(String usuario);
}

package com.juanmi.biblioteca.service.impl;

import com.juanmi.biblioteca.domain.Libro;
import com.juanmi.biblioteca.domain.Prestamo;
import com.juanmi.biblioteca.dto.PrestamoDTOs;
import com.juanmi.biblioteca.exception.BusinessException;
import com.juanmi.biblioteca.exception.NotFoundException;
import com.juanmi.biblioteca.repository.LibroRepository;
import com.juanmi.biblioteca.repository.PrestamoRepository;
import com.juanmi.biblioteca.service.ServicioPrestamo;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ServicioPrestamoImpl implements ServicioPrestamo {

    private static final Logger log = LoggerFactory.getLogger(ServicioPrestamoImpl.class);

    private final PrestamoRepository prestamoRepository;
    private final LibroRepository libroRepository;

    @Override
    public PrestamoDTOs.Response registrar(PrestamoDTOs.Create dto) {
        Libro libro = libroRepository.findById(dto.getLibroId())
                .orElseThrow(() -> new NotFoundException("Libro no encontrado"));

        if (dto.getFechaDevolucion() != null &&
                dto.getFechaDevolucion().isBefore(dto.getFechaPrestamo())) {
            throw new BusinessException("La fecha de devolución no puede ser anterior al préstamo");
        }

        Prestamo p = Prestamo.builder()
                .usuario(dto.getUsuario())
                .libro(libro)
                .fechaPrestamo(dto.getFechaPrestamo())
                .fechaDevolucion(dto.getFechaDevolucion())
                .devuelto(false)
                .build();

        Prestamo guardado = prestamoRepository.save(p);
        log.info("Préstamo creado id={} usuario={}", guardado.getId(), guardado.getUsuario());
        return toResponse(guardado);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PrestamoDTOs.Response> obtenerTodos() {
        return prestamoRepository.findAll().stream()
                .map(this::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<PrestamoDTOs.Response> obtenerPorUsuario(String usuario) {
        return prestamoRepository.findByUsuarioIgnoreCase(usuario).stream()
                .map(this::toResponse)
                .toList();
    }

    @Override
    public PrestamoDTOs.Response marcarDevuelto(Long prestamoId) {
        Prestamo p = prestamoRepository.findById(prestamoId)
                .orElseThrow(() -> new NotFoundException("Préstamo no encontrado"));
        if (Boolean.TRUE.equals(p.getDevuelto())) {
            throw new BusinessException("El préstamo ya está marcado como devuelto");
        }
        p.setDevuelto(true);
        if (p.getFechaDevolucion() == null) {
            p.setFechaDevolucion(LocalDate.now());
        }
        Prestamo actualizado = prestamoRepository.save(p);
        log.info("Préstamo devuelto id={}", prestamoId);
        return toResponse(actualizado);
    }

    private PrestamoDTOs.Response toResponse(Prestamo p) {
        PrestamoDTOs.Response r = new PrestamoDTOs.Response();
        r.setId(p.getId());
        r.setUsuario(p.getUsuario());
        r.setLibroId(p.getLibro().getId());
        r.setLibroTitulo(p.getLibro().getTitulo());
        r.setFechaPrestamo(p.getFechaPrestamo());
        r.setFechaDevolucion(p.getFechaDevolucion());
        r.setDevuelto(p.getDevuelto());
        return r;
    }
}

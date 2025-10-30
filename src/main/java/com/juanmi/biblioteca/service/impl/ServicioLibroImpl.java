package com.juanmi.biblioteca.service.impl;

import com.juanmi.biblioteca.domain.Autor;
import com.juanmi.biblioteca.domain.Libro;
import com.juanmi.biblioteca.dto.LibroDTOs;
import com.juanmi.biblioteca.exception.BusinessException;
import com.juanmi.biblioteca.exception.NotFoundException;
import com.juanmi.biblioteca.repository.AutorRepository;
import com.juanmi.biblioteca.repository.LibroRepository;
import com.juanmi.biblioteca.service.ServicioLibro;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ServicioLibroImpl implements ServicioLibro {

    private static final Logger log = LoggerFactory.getLogger(ServicioLibroImpl.class);

    private final LibroRepository libroRepository;
    private final AutorRepository autorRepository;

    @Override
    public LibroDTOs.Response crear(LibroDTOs.Create dto) {
        if (libroRepository.existsByIsbn(dto.getIsbn())) {
            throw new BusinessException("El ISBN ya existe");
        }
        Autor autor = autorRepository.findById(dto.getAutorId())
                .orElseThrow(() -> new NotFoundException("Autor no encontrado"));

        Libro libro = Libro.builder()
                .titulo(dto.getTitulo())
                .isbn(dto.getIsbn())
                .anioPublicacion(dto.getAnioPublicacion())
                .genero(dto.getGenero())
                .autor(autor)
                .build();

        Libro guardado = libroRepository.save(libro);
        log.info("Libro creado id={} isbn={}", guardado.getId(), guardado.getIsbn());
        return toResponse(guardado);
    }

    @Override
    @Transactional(readOnly = true)
    public List<LibroDTOs.Response> obtenerTodos() {
        return libroRepository.findAll().stream()
                .map(this::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public LibroDTOs.Response obtenerPorId(Long id) {
        Libro l = libroRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Libro no encontrado"));
        return toResponse(l);
    }

    @Override
    public LibroDTOs.Response actualizar(Long id, LibroDTOs.Update dto) {
        Libro l = libroRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Libro no encontrado"));
        Autor autor = autorRepository.findById(dto.getAutorId())
                .orElseThrow(() -> new NotFoundException("Autor no encontrado"));
        l.setTitulo(dto.getTitulo());
        l.setAnioPublicacion(dto.getAnioPublicacion());
        l.setGenero(dto.getGenero());
        l.setAutor(autor);
        Libro actualizado = libroRepository.save(l);
        log.info("Libro actualizado id={}", id);
        return toResponse(actualizado);
    }

    @Override
    public void eliminar(Long id) {
        Libro l = libroRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Libro no encontrado"));
        libroRepository.delete(l);
        log.info("Libro eliminado id={}", id);
    }

    private LibroDTOs.Response toResponse(Libro l) {
        LibroDTOs.Response r = new LibroDTOs.Response();
        r.setId(l.getId());
        r.setTitulo(l.getTitulo());
        r.setIsbn(l.getIsbn());
        r.setAnioPublicacion(l.getAnioPublicacion());
        r.setGenero(l.getGenero());
        r.setAutorId(l.getAutor().getId());
        r.setAutorNombre(l.getAutor().getNombre());
        return r;
    }
}

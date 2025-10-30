package com.juanmi.biblioteca.service.impl;

import com.juanmi.biblioteca.domain.Autor;
import com.juanmi.biblioteca.domain.Libro;
import com.juanmi.biblioteca.dto.AutorDTOs;
import com.juanmi.biblioteca.dto.LibroDTOs;
import com.juanmi.biblioteca.exception.BusinessException;
import com.juanmi.biblioteca.exception.NotFoundException;
import com.juanmi.biblioteca.repository.AutorRepository;
import com.juanmi.biblioteca.repository.LibroRepository;
import com.juanmi.biblioteca.service.ServicioAutor;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ServicioAutorImpl implements ServicioAutor {

    private static final Logger log = LoggerFactory.getLogger(ServicioAutorImpl.class);

    private final AutorRepository autorRepository;
    private final LibroRepository libroRepository;

    @Override
    public AutorDTOs.Response crear(AutorDTOs.Create dto) {
        if (autorRepository.existsByNombreIgnoreCase(dto.getNombre())) {
            throw new BusinessException("Ya existe un autor con ese nombre");
        }
        Autor autor = Autor.builder()
                .nombre(dto.getNombre())
                .nacionalidad(dto.getNacionalidad())
                .fechaNacimiento(dto.getFechaNacimiento())
                .build();
        Autor guardado = autorRepository.save(autor);
        log.info("Autor creado id={}", guardado.getId());
        return toResponse(guardado, List.of());
    }

    @Override
    @Transactional(readOnly = true)
    public List<AutorDTOs.Response> obtenerTodos() {
        return autorRepository.findAll().stream()
                .map(a -> toResponse(a, List.of()))
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public AutorDTOs.Response obtenerPorId(Long id) {
        Autor a = autorRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Autor no encontrado"));
        return toResponse(a, List.of());
    }

    @Override
    @Transactional(readOnly = true)
    public List<LibroDTOs.Response> obtenerLibrosDeAutor(Long autorId) {
        Autor autor = autorRepository.findById(autorId)
                .orElseThrow(() -> new NotFoundException("Autor no encontrado"));
        List<Libro> libros = libroRepository.findByAutor(autor);
        return libros.stream().map(this::toLibroResponse).toList();
    }

    private AutorDTOs.Response toResponse(Autor a, List<LibroDTOs.Response> libros) {
        AutorDTOs.Response r = new AutorDTOs.Response();
        r.setId(a.getId());
        r.setNombre(a.getNombre());
        r.setNacionalidad(a.getNacionalidad());
        r.setFechaNacimiento(a.getFechaNacimiento());
        r.setLibros(libros);
        return r;
    }

    private LibroDTOs.Response toLibroResponse(Libro l) {
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

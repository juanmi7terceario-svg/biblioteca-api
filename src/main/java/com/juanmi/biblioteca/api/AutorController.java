package com.juanmi.biblioteca.api;

import com.juanmi.biblioteca.dto.AutorDTOs;
import com.juanmi.biblioteca.dto.LibroDTOs;
import com.juanmi.biblioteca.service.ServicioAutor;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/autores")
public class AutorController {

    private final ServicioAutor servicioAutor;

    @PostMapping
    public ResponseEntity<AutorDTOs.Response> crear(@Valid @RequestBody AutorDTOs.Create dto) {
        AutorDTOs.Response r = servicioAutor.crear(dto);
        return ResponseEntity.created(URI.create("/autores/" + r.getId())).body(r);
    }

    @GetMapping
    public ResponseEntity<List<AutorDTOs.Response>> obtenerTodos() {
        return ResponseEntity.ok(servicioAutor.obtenerTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<AutorDTOs.Response> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(servicioAutor.obtenerPorId(id));
    }

    @GetMapping("/{id}/libros")
    public ResponseEntity<List<LibroDTOs.Response>> obtenerLibros(@PathVariable Long id) {
        return ResponseEntity.ok(servicioAutor.obtenerLibrosDeAutor(id));
    }
}

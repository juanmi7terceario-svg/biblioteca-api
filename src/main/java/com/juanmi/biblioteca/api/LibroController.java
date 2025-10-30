package com.juanmi.biblioteca.api;

import com.juanmi.biblioteca.dto.LibroDTOs;
import com.juanmi.biblioteca.service.ServicioLibro;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/libros")
public class LibroController {

    private final ServicioLibro servicioLibro;

    @PostMapping
    public ResponseEntity<LibroDTOs.Response> crear(@Valid @RequestBody LibroDTOs.Create dto) {
        LibroDTOs.Response r = servicioLibro.crear(dto);
        return ResponseEntity.created(URI.create("/libros/" + r.getId())).body(r);
    }

    @GetMapping
    public ResponseEntity<List<LibroDTOs.Response>> obtenerTodos() {
        return ResponseEntity.ok(servicioLibro.obtenerTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<LibroDTOs.Response> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(servicioLibro.obtenerPorId(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<LibroDTOs.Response> actualizar(@PathVariable Long id,
                                                         @Valid @RequestBody LibroDTOs.Update dto) {
        return ResponseEntity.ok(servicioLibro.actualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        servicioLibro.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}

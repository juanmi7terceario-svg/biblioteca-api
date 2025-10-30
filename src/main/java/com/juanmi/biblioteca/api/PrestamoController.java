package com.juanmi.biblioteca.api;

import com.juanmi.biblioteca.dto.PrestamoDTOs;
import com.juanmi.biblioteca.service.ServicioPrestamo;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/prestamos")
public class PrestamoController {

    private final ServicioPrestamo servicioPrestamo;

    @PostMapping
    public ResponseEntity<PrestamoDTOs.Response> registrar(@Valid @RequestBody PrestamoDTOs.Create dto) {
        PrestamoDTOs.Response r = servicioPrestamo.registrar(dto);
        return ResponseEntity.created(URI.create("/prestamos/" + r.getId())).body(r);
    }

    @GetMapping
    public ResponseEntity<List<PrestamoDTOs.Response>> obtenerTodos() {
        return ResponseEntity.ok(servicioPrestamo.obtenerTodos());
    }

    @GetMapping("/usuario/{usuario}")
    public ResponseEntity<List<PrestamoDTOs.Response>> obtenerPorUsuario(@PathVariable String usuario) {
        return ResponseEntity.ok(servicioPrestamo.obtenerPorUsuario(usuario));
    }

    @PutMapping("/{id}/devolver")
    public ResponseEntity<PrestamoDTOs.Response> devolver(@PathVariable Long id) {
        return ResponseEntity.ok(servicioPrestamo.marcarDevuelto(id));
    }
}

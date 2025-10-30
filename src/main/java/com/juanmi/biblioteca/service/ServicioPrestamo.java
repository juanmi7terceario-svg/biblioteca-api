package com.juanmi.biblioteca.service;

import com.juanmi.biblioteca.dto.PrestamoDTOs;

import java.util.List;

public interface ServicioPrestamo {
    PrestamoDTOs.Response registrar(PrestamoDTOs.Create dto);
    List<PrestamoDTOs.Response> obtenerTodos();
    List<PrestamoDTOs.Response> obtenerPorUsuario(String usuario);
    PrestamoDTOs.Response marcarDevuelto(Long prestamoId);
}

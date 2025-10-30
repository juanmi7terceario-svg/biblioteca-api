package com.juanmi.biblioteca.service;

import com.juanmi.biblioteca.dto.LibroDTOs;

import java.util.List;

public interface ServicioLibro {
    LibroDTOs.Response crear(LibroDTOs.Create dto);
    List<LibroDTOs.Response> obtenerTodos();
    LibroDTOs.Response obtenerPorId(Long id);
    LibroDTOs.Response actualizar(Long id, LibroDTOs.Update dto);
    void eliminar(Long id);
}

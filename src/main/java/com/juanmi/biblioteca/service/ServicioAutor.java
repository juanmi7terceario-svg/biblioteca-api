package com.juanmi.biblioteca.service;

import com.juanmi.biblioteca.dto.AutorDTOs;
import com.juanmi.biblioteca.dto.LibroDTOs;

import java.util.List;

public interface ServicioAutor {
    AutorDTOs.Response crear(AutorDTOs.Create dto);
    List<AutorDTOs.Response> obtenerTodos();
    AutorDTOs.Response obtenerPorId(Long id);
    List<LibroDTOs.Response> obtenerLibrosDeAutor(Long autorId);
}

package com.universidad.service;

import com.universidad.model.Estudiante;
import java.util.List;

public interface EstudianteService {
    Estudiante crear(String nombre, String correo, String password, String codigo);
    List<Estudiante> listar();
    Estudiante buscarPorId(Long id);
    Estudiante buscarPorCodigo(String codigo);
    Estudiante actualizar(Long id, String nombre, String correo, String password, String codigo);
    void eliminar(Long id);
    boolean login(String correo, String password);
    String notificar(Long id, String mensaje);
}

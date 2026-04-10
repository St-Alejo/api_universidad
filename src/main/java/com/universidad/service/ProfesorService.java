package com.universidad.service;

import com.universidad.model.Profesor;
import java.util.List;

public interface ProfesorService {
    Profesor crear(String nombre, String correo, String password, String especialidad);
    List<Profesor> listar();
    Profesor buscarPorId(Long id);
    Profesor actualizar(Long id, String nombre, String correo, String password, String especialidad);
    void eliminar(Long id);
    String evaluar(Long profesorId, String codigoEstudiante, double nota);
    String aprobarSolicitud(Long profesorId, String codigoSolicitud);
    String notificar(Long id, String mensaje);
}

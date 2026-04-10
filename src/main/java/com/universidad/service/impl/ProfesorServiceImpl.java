package com.universidad.service.impl;

import com.universidad.exception.DuplicateResourceException;
import com.universidad.exception.ResourceNotFoundException;
import com.universidad.model.Profesor;
import com.universidad.service.ProfesorService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class ProfesorServiceImpl implements ProfesorService {

    private final List<Profesor> almacen = new ArrayList<>();
    private final AtomicLong contadorId = new AtomicLong(1);

    public ProfesorServiceImpl() {
        almacen.add(new Profesor(contadorId.getAndIncrement(), "Dr. Carlos Ruiz",    "carlos.ruiz@universidad.edu",    "prof123", "Matemáticas"));
        almacen.add(new Profesor(contadorId.getAndIncrement(), "Dra. Elena Vargas",  "elena.vargas@universidad.edu",   "prof456", "Programación"));
        almacen.add(new Profesor(contadorId.getAndIncrement(), "Mg. Pedro Sánchez",  "pedro.sanchez@universidad.edu",  "prof789", "Física"));
    }

    @Override
    public Profesor crear(String nombre, String correo, String password, String especialidad) {
        validarCamposObligatorios(nombre, correo, password, especialidad);
        if (existeCorreo(correo)) {
            throw new DuplicateResourceException(
                "Ya existe un profesor con el correo '" + correo + "'.");
        }
        Profesor nuevo = new Profesor(contadorId.getAndIncrement(), nombre, correo, password, especialidad);
        almacen.add(nuevo);
        return nuevo;
    }

    @Override
    public List<Profesor> listar() {
        return new ArrayList<>(almacen);
    }

    @Override
    public Profesor buscarPorId(Long id) {
        return almacen.stream()
                .filter(p -> p.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException(
                    "No se encontró ningún profesor con id " + id + "."));
    }

    @Override
    public Profesor actualizar(Long id, String nombre, String correo, String password, String especialidad) {
        Profesor existente = buscarPorId(id);
        validarCamposObligatorios(nombre, correo, password, especialidad);

        boolean correoUsado = almacen.stream()
                .anyMatch(p -> !p.getId().equals(id) && p.getCorreo().equalsIgnoreCase(correo.trim()));
        if (correoUsado) {
            throw new DuplicateResourceException(
                "El correo '" + correo + "' ya está siendo usado por otro profesor.");
        }

        existente.setNombre(nombre);
        existente.setCorreo(correo);
        existente.setPassword(password);
        existente.setEspecialidad(especialidad);
        return existente;
    }

    @Override
    public void eliminar(Long id) {
        Profesor existente = buscarPorId(id);
        almacen.remove(existente);
    }

    @Override
    public String evaluar(Long profesorId, String codigoEstudiante, double nota) {
        if (codigoEstudiante == null || codigoEstudiante.trim().isEmpty()) {
            throw new IllegalArgumentException("El código del estudiante es obligatorio para evaluar.");
        }
        Profesor profesor = buscarPorId(profesorId);
        return profesor.evaluar(codigoEstudiante.trim(), nota);
    }

    @Override
    public String aprobarSolicitud(Long profesorId, String codigoSolicitud) {
        if (codigoSolicitud == null || codigoSolicitud.trim().isEmpty()) {
            throw new IllegalArgumentException("El código de solicitud no puede estar vacío.");
        }
        Profesor profesor = buscarPorId(profesorId);
        return profesor.aprobarSolicitud(codigoSolicitud.trim());
    }

    @Override
    public String notificar(Long id, String mensaje) {
        if (mensaje == null || mensaje.trim().isEmpty()) {
            throw new IllegalArgumentException("El mensaje de notificación no puede estar vacío.");
        }
        Profesor profesor = buscarPorId(id);
        return profesor.enviarNotificacion(mensaje.trim());
    }

    private void validarCamposObligatorios(String nombre, String correo, String password, String especialidad) {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("El campo 'nombre' es obligatorio.");
        }
        if (correo == null || correo.trim().isEmpty()) {
            throw new IllegalArgumentException("El campo 'correo' es obligatorio.");
        }
        if (password == null || password.isEmpty()) {
            throw new IllegalArgumentException("El campo 'password' es obligatorio.");
        }
        if (especialidad == null || especialidad.trim().isEmpty()) {
            throw new IllegalArgumentException("El campo 'especialidad' es obligatorio.");
        }
    }

    private boolean existeCorreo(String correo) {
        return almacen.stream()
                .anyMatch(p -> p.getCorreo().equalsIgnoreCase(correo.trim()));
    }
}

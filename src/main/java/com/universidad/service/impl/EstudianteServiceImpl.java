package com.universidad.service.impl;

import com.universidad.exception.DuplicateResourceException;
import com.universidad.exception.ResourceNotFoundException;
import com.universidad.model.Estudiante;
import com.universidad.service.EstudianteService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class EstudianteServiceImpl implements EstudianteService {

    private final List<Estudiante> almacen = new ArrayList<>();
    private final AtomicLong contadorId = new AtomicLong(1);

    public EstudianteServiceImpl() {
        // Datos de ejemplo precargados
        almacen.add(new Estudiante(contadorId.getAndIncrement(), "Ana García",    "ana.garcia@universidad.edu",    "pass123", "EST001"));
        almacen.add(new Estudiante(contadorId.getAndIncrement(), "Luis Martínez", "luis.martinez@universidad.edu", "pass456", "EST002"));
        almacen.add(new Estudiante(contadorId.getAndIncrement(), "Sara López",    "sara.lopez@universidad.edu",    "pass789", "EST003"));
    }

    @Override
    public Estudiante crear(String nombre, String correo, String password, String codigo) {
        validarCamposObligatorios(nombre, correo, password, codigo);
        if (existeCorreo(correo)) {
            throw new DuplicateResourceException(
                "Ya existe un estudiante con el correo '" + correo + "'.");
        }
        if (existeCodigo(codigo)) {
            throw new DuplicateResourceException(
                "Ya existe un estudiante con el código '" + codigo + "'.");
        }
        Estudiante nuevo = new Estudiante(contadorId.getAndIncrement(), nombre, correo, password, codigo);
        almacen.add(nuevo);
        return nuevo;
    }

    @Override
    public List<Estudiante> listar() {
        return new ArrayList<>(almacen);
    }

    @Override
    public Estudiante buscarPorId(Long id) {
        return almacen.stream()
                .filter(e -> e.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException(
                    "No se encontró ningún estudiante con id " + id + "."));
    }

    @Override
    public Estudiante buscarPorCodigo(String codigo) {
        if (codigo == null || codigo.trim().isEmpty()) {
            throw new IllegalArgumentException("El código de búsqueda no puede estar vacío.");
        }
        return almacen.stream()
                .filter(e -> e.getCodigo().equalsIgnoreCase(codigo.trim()))
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException(
                    "No se encontró ningún estudiante con código '" + codigo + "'."));
    }

    @Override
    public Estudiante actualizar(Long id, String nombre, String correo, String password, String codigo) {
        Estudiante existente = buscarPorId(id);
        validarCamposObligatorios(nombre, correo, password, codigo);

        // Verificar duplicados excluyendo el actual
        boolean correoUsado = almacen.stream()
                .anyMatch(e -> !e.getId().equals(id) && e.getCorreo().equalsIgnoreCase(correo.trim()));
        if (correoUsado) {
            throw new DuplicateResourceException(
                "El correo '" + correo + "' ya está siendo usado por otro estudiante.");
        }

        boolean codigoUsado = almacen.stream()
                .anyMatch(e -> !e.getId().equals(id) && e.getCodigo().equalsIgnoreCase(codigo.trim()));
        if (codigoUsado) {
            throw new DuplicateResourceException(
                "El código '" + codigo + "' ya está siendo usado por otro estudiante.");
        }

        existente.setNombre(nombre);
        existente.setCorreo(correo);
        existente.setPassword(password);
        existente.setCodigo(codigo);
        return existente;
    }

    @Override
    public void eliminar(Long id) {
        Estudiante existente = buscarPorId(id);
        almacen.remove(existente);
    }

    @Override
    public boolean login(String correo, String password) {
        if (correo == null || correo.trim().isEmpty()) {
            throw new IllegalArgumentException("El correo es obligatorio para iniciar sesión.");
        }
        if (password == null || password.isEmpty()) {
            throw new IllegalArgumentException("La contraseña es obligatoria para iniciar sesión.");
        }
        return almacen.stream().anyMatch(e -> e.login(correo.trim(), password));
    }

    @Override
    public String notificar(Long id, String mensaje) {
        if (mensaje == null || mensaje.trim().isEmpty()) {
            throw new IllegalArgumentException("El mensaje de notificación no puede estar vacío.");
        }
        Estudiante estudiante = buscarPorId(id);
        return estudiante.enviarNotificacion(mensaje.trim());
    }

    // ── helpers ──────────────────────────────────────────────────────────────

    private void validarCamposObligatorios(String nombre, String correo, String password, String codigo) {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("El campo 'nombre' es obligatorio.");
        }
        if (correo == null || correo.trim().isEmpty()) {
            throw new IllegalArgumentException("El campo 'correo' es obligatorio.");
        }
        if (password == null || password.isEmpty()) {
            throw new IllegalArgumentException("El campo 'password' es obligatorio.");
        }
        if (codigo == null || codigo.trim().isEmpty()) {
            throw new IllegalArgumentException("El campo 'codigo' es obligatorio.");
        }
    }

    private boolean existeCorreo(String correo) {
        return almacen.stream()
                .anyMatch(e -> e.getCorreo().equalsIgnoreCase(correo.trim()));
    }

    private boolean existeCodigo(String codigo) {
        return almacen.stream()
                .anyMatch(e -> e.getCodigo().equalsIgnoreCase(codigo.trim()));
    }
}

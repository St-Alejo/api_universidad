package com.universidad.service.impl;

import com.universidad.exception.DuplicateResourceException;
import com.universidad.exception.ResourceNotFoundException;
import com.universidad.model.Administrativo;
import com.universidad.service.AdministrativoService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class AdministrativoServiceImpl implements AdministrativoService {

    private final List<Administrativo> almacen = new ArrayList<>();
    private final AtomicLong contadorId = new AtomicLong(1);

    public AdministrativoServiceImpl() {
        almacen.add(new Administrativo(contadorId.getAndIncrement(), "Martha Jiménez", "martha.jimenez@universidad.edu", "adm123", "Registro y Control"));
        almacen.add(new Administrativo(contadorId.getAndIncrement(), "Roberto Díaz",   "roberto.diaz@universidad.edu",   "adm456", "Bienestar Universitario"));
    }

    @Override
    public Administrativo crear(String nombre, String correo, String password, String area) {
        validarCamposObligatorios(nombre, correo, password, area);
        if (existeCorreo(correo)) {
            throw new DuplicateResourceException(
                "Ya existe un administrativo con el correo '" + correo + "'.");
        }
        Administrativo nuevo = new Administrativo(contadorId.getAndIncrement(), nombre, correo, password, area);
        almacen.add(nuevo);
        return nuevo;
    }

    @Override
    public List<Administrativo> listar() {
        return new ArrayList<>(almacen);
    }

    @Override
    public Administrativo buscarPorId(Long id) {
        return almacen.stream()
                .filter(a -> a.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException(
                    "No se encontró ningún administrativo con id " + id + "."));
    }

    @Override
    public Administrativo actualizar(Long id, String nombre, String correo, String password, String area) {
        Administrativo existente = buscarPorId(id);
        validarCamposObligatorios(nombre, correo, password, area);

        boolean correoUsado = almacen.stream()
                .anyMatch(a -> !a.getId().equals(id) && a.getCorreo().equalsIgnoreCase(correo.trim()));
        if (correoUsado) {
            throw new DuplicateResourceException(
                "El correo '" + correo + "' ya está siendo usado por otro administrativo.");
        }

        existente.setNombre(nombre);
        existente.setCorreo(correo);
        existente.setPassword(password);
        existente.setArea(area);
        return existente;
    }

    @Override
    public void eliminar(Long id) {
        Administrativo existente = buscarPorId(id);
        almacen.remove(existente);
    }

    @Override
    public String aprobarSolicitud(Long id, String codigoSolicitud) {
        if (codigoSolicitud == null || codigoSolicitud.trim().isEmpty()) {
            throw new IllegalArgumentException("El código de solicitud no puede estar vacío.");
        }
        Administrativo administrativo = buscarPorId(id);
        return administrativo.aprobarSolicitud(codigoSolicitud.trim());
    }

    @Override
    public String notificar(Long id, String mensaje) {
        if (mensaje == null || mensaje.trim().isEmpty()) {
            throw new IllegalArgumentException("El mensaje de notificación no puede estar vacío.");
        }
        Administrativo administrativo = buscarPorId(id);
        return administrativo.enviarNotificacion(mensaje.trim());
    }

    private void validarCamposObligatorios(String nombre, String correo, String password, String area) {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("El campo 'nombre' es obligatorio.");
        }
        if (correo == null || correo.trim().isEmpty()) {
            throw new IllegalArgumentException("El campo 'correo' es obligatorio.");
        }
        if (password == null || password.isEmpty()) {
            throw new IllegalArgumentException("El campo 'password' es obligatorio.");
        }
        if (area == null || area.trim().isEmpty()) {
            throw new IllegalArgumentException("El campo 'area' es obligatorio.");
        }
    }

    private boolean existeCorreo(String correo) {
        return almacen.stream()
                .anyMatch(a -> a.getCorreo().equalsIgnoreCase(correo.trim()));
    }
}

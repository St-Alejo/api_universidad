package com.universidad.service;

import com.universidad.model.Administrativo;
import java.util.List;

public interface AdministrativoService {
    Administrativo crear(String nombre, String correo, String password, String area);
    List<Administrativo> listar();
    Administrativo buscarPorId(Long id);
    Administrativo actualizar(Long id, String nombre, String correo, String password, String area);
    void eliminar(Long id);
    String aprobarSolicitud(Long id, String codigoSolicitud);
    String notificar(Long id, String mensaje);
}

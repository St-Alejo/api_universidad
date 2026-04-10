package com.universidad.model;

import com.universidad.interfaces.Autenticable;
import com.universidad.interfaces.Notificable;

public class Estudiante extends Persona implements Autenticable, Notificable {

    private String codigo;

    public Estudiante() {}

    public Estudiante(Long id, String nombre, String correo, String password, String codigo) {
        super(id, nombre, correo, password);
        setCodigo(codigo);
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        if (codigo == null || codigo.trim().isEmpty()) {
            throw new IllegalArgumentException("El código del estudiante no puede estar vacío.");
        }
        this.codigo = codigo.trim().toUpperCase();
    }

    @Override
    public boolean login(String usuario, String password) {
        return getCorreo().equalsIgnoreCase(usuario) && getPassword().equals(password);
    }

    @Override
    public String enviarNotificacion(String mensaje) {
        return "[NOTIFICACIÓN → Estudiante " + getNombre() + " <" + getCorreo() + ">] " + mensaje;
    }

    @Override
    public String getTipo() {
        return "ESTUDIANTE";
    }
}

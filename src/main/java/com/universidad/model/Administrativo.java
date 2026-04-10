package com.universidad.model;

import com.universidad.interfaces.Aprobador;
import com.universidad.interfaces.Notificable;

public class Administrativo extends Persona implements Aprobador, Notificable {

    private String area;

    public Administrativo() {}

    public Administrativo(Long id, String nombre, String correo, String password, String area) {
        super(id, nombre, correo, password);
        setArea(area);
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        if (area == null || area.trim().isEmpty()) {
            throw new IllegalArgumentException("El área del administrativo no puede estar vacía.");
        }
        this.area = area.trim();
    }

    @Override
    public String aprobarSolicitud(String codigoSolicitud) {
        return "[APROBACIÓN] Administrativo " + getNombre() +
               " (Área: " + area + ") aprobó la solicitud: " + codigoSolicitud;
    }

    @Override
    public String enviarNotificacion(String mensaje) {
        return "[NOTIFICACIÓN → Administrativo " + getNombre() + " <" + getCorreo() + ">] " + mensaje;
    }

    @Override
    public String getTipo() {
        return "ADMINISTRATIVO";
    }
}

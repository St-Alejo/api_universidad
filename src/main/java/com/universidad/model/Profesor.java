package com.universidad.model;

import com.universidad.interfaces.Aprobador;
import com.universidad.interfaces.Evaluador;
import com.universidad.interfaces.Notificable;

public class Profesor extends Persona implements Evaluador, Aprobador, Notificable {

    private String especialidad;

    public Profesor() {}

    public Profesor(Long id, String nombre, String correo, String password, String especialidad) {
        super(id, nombre, correo, password);
        setEspecialidad(especialidad);
    }

    public String getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(String especialidad) {
        if (especialidad == null || especialidad.trim().isEmpty()) {
            throw new IllegalArgumentException("La especialidad del profesor no puede estar vacía.");
        }
        this.especialidad = especialidad.trim();
    }

    @Override
    public String evaluar(String codigoEstudiante, double nota) {
        if (nota < 0 || nota > 5) {
            throw new IllegalArgumentException("La nota debe estar entre 0.0 y 5.0.");
        }
        String estado = nota >= 3.0 ? "APROBADO" : "REPROBADO";
        return "[EVALUACIÓN] Profesor " + getNombre() + " evaluó al estudiante " +
               codigoEstudiante + " con nota " + nota + " → " + estado;
    }

    @Override
    public String aprobarSolicitud(String codigoSolicitud) {
        return "[APROBACIÓN] Profesor " + getNombre() + " aprobó la solicitud: " + codigoSolicitud;
    }

    @Override
    public String enviarNotificacion(String mensaje) {
        return "[NOTIFICACIÓN → Profesor " + getNombre() + " <" + getCorreo() + ">] " + mensaje;
    }

    @Override
    public String getTipo() {
        return "PROFESOR";
    }
}

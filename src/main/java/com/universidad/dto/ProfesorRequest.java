package com.universidad.dto;

public class ProfesorRequest extends PersonaRequest {
    private String especialidad;

    public ProfesorRequest() {}

    public String getEspecialidad() { return especialidad; }
    public void setEspecialidad(String especialidad) { this.especialidad = especialidad; }
}

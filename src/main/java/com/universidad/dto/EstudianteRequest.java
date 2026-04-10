package com.universidad.dto;

public class EstudianteRequest extends PersonaRequest {
    private String codigo;

    public EstudianteRequest() {}

    public String getCodigo() { return codigo; }
    public void setCodigo(String codigo) { this.codigo = codigo; }
}

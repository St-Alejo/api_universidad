package com.universidad.dto;

public class EvaluarRequest {
    private Long profesorId;
    private String codigoEstudiante;
    private double nota;

    public EvaluarRequest() {}

    public Long getProfesorId() { return profesorId; }
    public void setProfesorId(Long profesorId) { this.profesorId = profesorId; }

    public String getCodigoEstudiante() { return codigoEstudiante; }
    public void setCodigoEstudiante(String codigoEstudiante) { this.codigoEstudiante = codigoEstudiante; }

    public double getNota() { return nota; }
    public void setNota(double nota) { this.nota = nota; }
}

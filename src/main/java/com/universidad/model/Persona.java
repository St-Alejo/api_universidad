package com.universidad.model;

import java.util.regex.Pattern;

public abstract class Persona {

    private static final Pattern EMAIL_PATTERN =
            Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");

    private Long id;
    private String nombre;
    private String correo;
    private String password;

    public Persona() {}

    public Persona(Long id, String nombre, String correo, String password) {
        this.id = id;
        this.nombre = nombre;
        setCorreo(correo);
        this.password = password;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre no puede estar vacío.");
        }
        this.nombre = nombre.trim();
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        if (correo == null || correo.trim().isEmpty()) {
            throw new IllegalArgumentException("El correo no puede estar vacío.");
        }
        if (!EMAIL_PATTERN.matcher(correo.trim()).matches()) {
            throw new IllegalArgumentException(
                    "El formato del correo '" + correo + "' no es válido. " +
                    "Debe tener el formato: usuario@dominio.com");
        }
        this.correo = correo.trim().toLowerCase();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        if (password == null || password.length() < 6) {
            throw new IllegalArgumentException("La contraseña debe tener al menos 6 caracteres.");
        }
        this.password = password;
    }

    public abstract String getTipo();
}

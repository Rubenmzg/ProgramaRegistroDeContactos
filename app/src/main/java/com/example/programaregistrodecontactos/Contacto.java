package com.example.programaregistrodecontactos;

public class Contacto {
    private int id;
    private String nombre;
    private String telefono;
    private String oficina;
    private String celular;
    private String correo;

    public Contacto() {
    }

    public Contacto(int id, String nombre, String telefono, String oficina, String celular, String correo) {
        this.id = id;
        this.nombre = nombre;
        this.telefono = telefono;
        this.oficina = oficina;
        this.celular = celular;
        this.correo = correo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getOficina() {
        return oficina;
    }

    public void setOficina(String oficina) {
        this.oficina = oficina;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }
}

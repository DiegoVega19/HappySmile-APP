package com.happysmile.myapplication.Model;

public class Expediente {
    private int id;
    private int pasiente_id;
    private String fecha;
    private String nombre;
    private String apellido;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPasiente_id() {
        return pasiente_id;
    }

    public void setPasiente_id(int pasiente_id) {
        this.pasiente_id = pasiente_id;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }
}

package com.happysmile.myapplication.Model;

public class Seguimiento {

    public  int id;
    public int pasiente_id;
    public String fecha;
    public String nombre;
    public String apellido;


    public int getPasiente_id() {
        return pasiente_id;
    }

    public void setPasiente_id(int pasiente_id) {
        this.pasiente_id = pasiente_id;
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }
}

package com.happysmile.myapplication.Model;

public class Municipio {

    public String nombre;
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }


    public Municipio(String nombre) {
        this.nombre = nombre;
    }

    public String toString() {
        return nombre;
    }
}

package com.happysmile.myapplication.Model;

public class Municipio {

    public Integer id;
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

    public Municipio(Integer id) {
        this.id = id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }
}

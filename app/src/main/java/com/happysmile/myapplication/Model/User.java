package com.happysmile.myapplication.Model;

import java.io.Serializable;

public class User {
    public int id;
    public int municipio_id;
    public  String nombre;
    public String apellido;
    public String sexo;
    public String fechaDeNacimiento;
    public int edad;
    public String estadoCivil;
    public String centroDeTrabajo;
    public String ocupacion;
    public String telefono;
    public String celular;
    public  String email;
    public String created_at;
    public String updated_at;


    public User(int id, int municipio_id, String nombre, String apellido, String sexo, String fechaDeNacimiento, int edad, String estadoCivil, String centroDeTrabajo, String ocupacion, String telefono, String celular, String email, String created_at, String updated_at) {
        this.id = id;
        this.municipio_id = municipio_id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.sexo = sexo;
        this.fechaDeNacimiento = fechaDeNacimiento;
        this.edad = edad;
        this.estadoCivil = estadoCivil;
        this.centroDeTrabajo = centroDeTrabajo;
        this.ocupacion = ocupacion;
        this.telefono = telefono;
        this.celular = celular;
        this.email = email;
        this.created_at = created_at;
        this.updated_at = updated_at;
    }

    public int getId() {
        return id;
    }

    public int getMunicipio_id() {
        return municipio_id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public String getSexo() {
        return sexo;
    }

    public String getFechaDeNacimiento() {
        return fechaDeNacimiento;
    }

    public int getEdad() {
        return edad;
    }

    public String getEstadoCivil() {
        return estadoCivil;
    }

    public String getCentroDeTrabajo() {
        return centroDeTrabajo;
    }

    public String getOcupacion() {
        return ocupacion;
    }

    public String getTelefono() {
        return telefono;
    }

    public String getCelular() {
        return celular;
    }

    public String getEmail() {
        return email;
    }

    public String getCreated_at() {
        return created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }
}

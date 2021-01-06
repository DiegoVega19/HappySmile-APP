package com.happysmile.myapplication.Model;

public class DoctorCita {
    private  int id;
    private String nombre;
    private String apellido;
    private String servicio;
    private String fechaPropuesta;
    private String horaPropuesta;
    private int totalCitas;

    public int getTotalCitas() {
        return totalCitas;
    }

    public void setTotalCitas(int totalCitas) {
        this.totalCitas = totalCitas;
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

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getFechaPropuesta() {
        return fechaPropuesta;
    }

    public void setFechaPropuesta(String fechaPropuesta) {
        this.fechaPropuesta = fechaPropuesta;
    }

    public String getHoraPropuesta() {
        return horaPropuesta;
    }

    public void setHoraPropuesta(String horaPropuesta) {
        this.horaPropuesta = horaPropuesta;
    }

    public String getServicio() {
        return servicio;
    }

    public void setServicio(String servicio) {
        this.servicio = servicio;
    }
}

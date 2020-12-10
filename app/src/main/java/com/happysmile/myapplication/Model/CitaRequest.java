package com.happysmile.myapplication.Model;

public class CitaRequest {

    private int pasiente_id;
    private  int doctors_id;
    private int servicios_id;
    private int estado_citas_Id;
    private String fechaSolicitud;
    private String fechaPropuesta;
    private String horaPropuesta;

    public void setEstado_citas_Id(int estado_citas_Id) {
        this.estado_citas_Id = estado_citas_Id;
    }

    public void setPasiente_id(int pasiente_id) {
        this.pasiente_id = pasiente_id;
    }

    public void setDoctors_id(int doctors_id) {
        this.doctors_id = doctors_id;
    }

    public void setServicios_id(int servicios_id) {
        this.servicios_id = servicios_id;
    }

    public void setFechaSolicitud(String fechaSolicitud) {
        this.fechaSolicitud = fechaSolicitud;
    }

    public void setFechaPropuesta(String fechaPropuesta) {
        this.fechaPropuesta = fechaPropuesta;
    }

    public void setHoraPropuesta(String horaPropuesta) {
        this.horaPropuesta = horaPropuesta;
    }
}

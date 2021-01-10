package com.happysmile.myapplication.Model;

public class TotalResponse {
     int totalExpedientes;
    int totalEndodoncias;
   int  totalSeguimientos;
   int disponibilidad;


    public int getDisponibilidad() {
        return disponibilidad;
    }

    public void setDisponibilidad(int disponibilidad) {
        this.disponibilidad = disponibilidad;
    }

    public int getTotalExpedientes() {
        return totalExpedientes;
    }

    public void setTotalExpedientes(int totalExpedientes) {
        this.totalExpedientes = totalExpedientes;
    }

    public int getTotalEndodoncias() {
        return totalEndodoncias;
    }

    public void setTotalEndodoncias(int totalEndodoncias) {
        this.totalEndodoncias = totalEndodoncias;
    }

    public int getTotalSeguimientos() {
        return totalSeguimientos;
    }

    public void setTotalSeguimientos(int totalSeguimientos) {
        this.totalSeguimientos = totalSeguimientos;
    }
}

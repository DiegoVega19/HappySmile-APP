package com.happysmile.myapplication.Model;

public class ExpedienteDetalle {

    private int id;
    private String fecha;
    private double presionArterial_Max;
    private double presionArterial_Min;
    private double frecuenciaPulso;
    private double frecuenciaPRespiratoria;
    private double temperaturaBucal;
    private double peso;
    private double talla;
    private String grupoSanguineo;
    private String factor;
    private String created_at;
    private String updated_at;

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

    public double getPresionArterial_Max() {
        return presionArterial_Max;
    }

    public void setPresionArterial_Max(double presionArterial_Max) {
        this.presionArterial_Max = presionArterial_Max;
    }

    public double getPresionArterial_Min() {
        return presionArterial_Min;
    }

    public void setPresionArterial_Min(double presionArterial_Min) {
        this.presionArterial_Min = presionArterial_Min;
    }

    public double getFrecuenciaPulso() {
        return frecuenciaPulso;
    }

    public void setFrecuenciaPulso(double frecuenciaPulso) {
        this.frecuenciaPulso = frecuenciaPulso;
    }

    public double getFrecuenciaPRespiratoria() {
        return frecuenciaPRespiratoria;
    }

    public void setFrecuenciaPRespiratoria(double frecuenciaPRespiratoria) {
        this.frecuenciaPRespiratoria = frecuenciaPRespiratoria;
    }

    public double getTemperaturaBucal() {
        return temperaturaBucal;
    }

    public void setTemperaturaBucal(double temperaturaBucal) {
        this.temperaturaBucal = temperaturaBucal;
    }

    public double getPeso() {
        return peso;
    }

    public void setPeso(double peso) {
        this.peso = peso;
    }

    public double getTalla() {
        return talla;
    }

    public void setTalla(double talla) {
        this.talla = talla;
    }

    public String getGrupoSanguineo() {
        return grupoSanguineo;
    }

    public void setGrupoSanguineo(String grupoSanguineo) {
        this.grupoSanguineo = grupoSanguineo;
    }

    public String getFactor() {
        return factor;
    }

    public void setFactor(String factor) {
        this.factor = factor;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }
}

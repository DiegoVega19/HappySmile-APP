package com.happysmile.myapplication.Model;

public class CancelRequest {
    private int estado_citas_Id;


    public CancelRequest(int estado_citas_Id) {
        this.estado_citas_Id = estado_citas_Id;
    }

    public int getEstado_citas_Id() {
        return estado_citas_Id;
    }
}

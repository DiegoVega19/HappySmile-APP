package com.happysmile.myapplication.Model;

import java.io.Serializable;

public class LoginResponse {
    private String access_token;
    private  Paciente paciente;


    public LoginResponse(String access_token, User user) {
        this.access_token = access_token;
        this.paciente = paciente;
    }


    public String getAccess_token() {
        return access_token;
    }

    public Paciente getPaciente() {
        return paciente;
    }
}

package com.happysmile.myapplication.Api;


import com.google.gson.JsonArray;
import com.happysmile.myapplication.Model.CancelRequest;
import com.happysmile.myapplication.Model.Cita;
import com.happysmile.myapplication.Model.CitaRequest;
import com.happysmile.myapplication.Model.CitaResponse;
import com.happysmile.myapplication.Model.LoginRequest;
import com.happysmile.myapplication.Model.LoginResponse;
import com.happysmile.myapplication.Model.Municipio;
import com.happysmile.myapplication.Model.Paciente;
import com.happysmile.myapplication.Model.RegisterRequest;
import com.happysmile.myapplication.Model.RegisterResponse;
import com.happysmile.myapplication.Model.Servicio;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ApiService {

    @GET("getMuni")
    Call<List<Municipio>> getMuni();

    @GET("getServicios")
    Call<List<Servicio>> getServ();

    //Para el login
    @Headers({
            "Content-Type: application/json",
            "X-Requested-With: XMLHttpRequest",
    })
    @POST("login")
    Call<LoginResponse> login(@Body LoginRequest loginRequest);


    @GET("getid/{nombre}")
    Call<List<Municipio>> getid(@Path("nombre") String nombreMun);

    //Para el registro
    @Headers({
            "Content-Type: application/json",
            "X-Requested-With: XMLHttpRequest",
    })
    @POST("registrar")
    Call<RegisterResponse> registrarse(@Body RegisterRequest registerRequest);

    @GET("getEmail/{email}")
    Call<List<Paciente>> getDatosbyEmail(@Path("email") String nombreCorreo);

    //Para agregar nueva cita
    @Headers({
            "Content-Type: application/json",
            "X-Requested-With: XMLHttpRequest",
    })
    @POST("agregarCita")
    Call<CitaResponse> solicitarCita(@Body CitaRequest citaRequest);

    @GET("getidserv/{nombre}")
    Call<List<Servicio>> getidserv(@Path("nombre") String nombreServ);

    @GET("getCita/{id}")
    Call<Cita> getcitaEstat(@Path("id") int IdPaciente);

    @GET("getCantidadCita/{pasiente_id}")
    Call<Cita> getCantidadCita(@Path("pasiente_id") int IdPaciente);

    @GET("getEstadoCita/{pasiente_id}")
    Call<Cita> getEstadoUltimaCita(@Path("pasiente_id") int IdPaciente);

    @GET("getpaciente/{id}")
    Call<Paciente> getPacData(@Path("id") int IdPaciente);

    @Headers({
            "Content-Type: application/json"
    })
    @PATCH("cancelarCita/{id}")
    Call<CancelRequest> cancCita(@Path("id") int idCita, @Body CancelRequest cancelRequest);
}

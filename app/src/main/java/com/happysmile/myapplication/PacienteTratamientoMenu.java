package com.happysmile.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.happysmile.myapplication.Api.ApiClient;
import com.happysmile.myapplication.Api.ApiService;
import com.happysmile.myapplication.Model.Cita;
import com.happysmile.myapplication.Model.TotalResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PacienteTratamientoMenu extends AppCompatActivity {

    int idPacienteCita;
    View v;
    int SegCount, ExpCount, EndoCount;
    CardView cardExpediente, cardSeguimiento, cardEndo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paciente_tratamiento_menu);
        //Desde el menu validar cuantos expedientes, seguimientos y endodoncias tiene el paciente
        v =  findViewById(android.R.id.content);
        cardExpediente = findViewById(R.id.cardTratExpediente);
        cardSeguimiento = findViewById(R.id.CardTratSeguimiento);
        cardEndo = findViewById(R.id.CardTratEndodoncia);
        //Ahora obtengo datos en shared
        SharedPreferences pref = getApplicationContext().getSharedPreferences("myPrefs", MODE_PRIVATE);
        idPacienteCita = pref.getInt("idPaciente", 0);

        getExpedientes();
        getSeguimientos();
        getEndodoncia();

    }

    private void getExpedientes() {
        Call<TotalResponse> totalResponseCallExp = ApiClient.getService().getExpCount(idPacienteCita);
        totalResponseCallExp.enqueue(new Callback<TotalResponse>() {
            @Override
            public void onResponse(Call<TotalResponse> call, Response<TotalResponse> response) {
                if (response.isSuccessful())
                {
                    TotalResponse totalResponse = response.body();
                    ExpCount = response.body().getTotalExpedientes();
                    //Toast.makeText(PacienteTratamientoMenu.this, "Mi total de expedientes es"+ExpCount, Toast.LENGTH_SHORT).show();
                    irExpediente();
                }
            }

            @Override
            public void onFailure(Call<TotalResponse> call, Throwable t) {
                String message = t.getLocalizedMessage();
                Toast.makeText(PacienteTratamientoMenu.this, message, Toast.LENGTH_LONG).show();
            }
        });
    }

    private void getSeguimientos() {
        Call<TotalResponse> totalResponseCallSeg = ApiClient.getService().getSegCount(idPacienteCita);
        totalResponseCallSeg.enqueue(new Callback<TotalResponse>() {
            @Override
            public void onResponse(Call<TotalResponse> call, Response<TotalResponse> response) {
                if (response.isSuccessful())
                {
                    TotalResponse totalResponse = response.body();
                    SegCount = response.body().getTotalSeguimientos();
                 //   Toast.makeText(PacienteTratamientoMenu.this, "Mi total de seguimietos es"+SegCount, Toast.LENGTH_SHORT).show();
                    irSeguimiento();
                }
            }

            @Override
            public void onFailure(Call<TotalResponse> call, Throwable t) {
                String message = t.getLocalizedMessage();
                Toast.makeText(PacienteTratamientoMenu.this, message, Toast.LENGTH_LONG).show();
            }
        });
    }

    private void getEndodoncia() {
        Call<TotalResponse> totalResponseCallEndo = ApiClient.getService().getEndoCount(idPacienteCita);
        totalResponseCallEndo.enqueue(new Callback<TotalResponse>() {
            @Override
            public void onResponse(Call<TotalResponse> call, Response<TotalResponse> response) {
                if (response.isSuccessful())
                {
                    TotalResponse totalResponse = response.body();
                    EndoCount = response.body().getTotalEndodoncias();
                   // Toast.makeText(PacienteTratamientoMenu.this, "Mi total de endodoncias es"+EndoCount, Toast.LENGTH_SHORT).show();
                    irEndodoncia();
                }
            }

            @Override
            public void onFailure(Call<TotalResponse> call, Throwable t) {
                String message = t.getLocalizedMessage();
                Toast.makeText(PacienteTratamientoMenu.this, message, Toast.LENGTH_LONG).show();
            }
        });
    }

    private void irSeguimiento()
    {
        cardSeguimiento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

              if(SegCount==0)
              {
                String dato = "Seguimientos";
                MostrarSnackbarEstado(dato);
              }
              else
              {
                  Intent i = new Intent(PacienteTratamientoMenu.this,PacienteTratamientoSeguimientoActivity.class);
                  startActivity(i);
              }
            }
        });
    }
    private void irExpediente()
    {
        cardExpediente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ExpCount==0)
                {
                    String dato = "Expedientes";
                    MostrarSnackbarEstado(dato);
                }
                else {
                    Intent i = new Intent(PacienteTratamientoMenu.this, PacienteTratamientoExpediente.class);
                    startActivity(i);
                }
            }
        });
    }
    private  void irEndodoncia()
    {
        cardEndo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (EndoCount==0)
                {
                    String dato = "Endodoncias";
                    MostrarSnackbarEstado(dato);
                }
                else {
                    Intent i = new Intent(PacienteTratamientoMenu.this, PacienteEndoTratActivity.class);
                    startActivity(i);
                }
            }
        });
    }
    public  void MostrarSnackbarEstado(String dato)
    {
        Snackbar snackbar = Snackbar.make(v,"No Posee Registros de "+dato +"!!!",Snackbar.LENGTH_LONG);
        snackbar.show();
    }

}
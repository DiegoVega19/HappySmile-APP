package com.happysmile.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.happysmile.myapplication.Api.ApiClient;
import com.happysmile.myapplication.Model.Doctor;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainMenu extends AppCompatActivity {

    String CorreoDoctor;
    TextView doctorNombre;
    CardView cardCitas, cardExpediente, cardPerfil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        doctorNombre = findViewById(R.id.DocNombreText);
        cardCitas = findViewById(R.id.DocCitascard);
        cardExpediente = findViewById(R.id.DocExpCard);
        cardPerfil = findViewById(R.id.DocPerfilCard);
        cargarDatos();
        cardCitas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainMenu.this,DoctorCitasMenuActivity.class);
                startActivity(i);
            }
        });

        cardExpediente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainMenu.this,ExpedienteActivity.class);
                startActivity(i);
            }
        });

        cardPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainMenu.this,PerfilActivity.class);
                startActivity(i);
            }
        });
    }

    private void cargarDatos() {
        SharedPreferences pref = getApplicationContext().getSharedPreferences("myPrefs",MODE_PRIVATE);
        String datos = pref.getString("DIEGO","no hay nada");
        String correo = pref.getString("Correo","no hay nada");
        doctorNombre.setText(datos);
        CorreoDoctor = correo;
        obtenerDatosDoctor();
    }

    private void obtenerDatosDoctor() {
        //impelementar ya
        Call<Doctor> doctorCall = ApiClient.getService().getDoctorData(CorreoDoctor);
        doctorCall.enqueue(new Callback<Doctor>() {
            @Override
            public void onResponse(Call<Doctor> call, Response<Doctor> response) {
                if (response.isSuccessful())
                {
                   int DocId;
                   DocId = response.body().getId();
                    Toast.makeText(MainMenu.this, "Mi id de doctor es"+DocId, Toast.LENGTH_SHORT).show();
                    SharedPreferences pref = getApplicationContext().getSharedPreferences("myPrefs",MODE_PRIVATE);
                    SharedPreferences.Editor editor = pref.edit();
                    editor.putInt("idDoctor",DocId);
                    editor.commit();
                }
            }

            @Override
            public void onFailure(Call<Doctor> call, Throwable t) {
                String message = t.getLocalizedMessage();
                Toast.makeText(MainMenu.this,message, Toast.LENGTH_LONG).show();
            }
        });
    }


}
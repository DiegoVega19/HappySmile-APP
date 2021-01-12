package com.happysmile.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import com.happysmile.myapplication.Api.ApiClient;
import com.happysmile.myapplication.Model.Paciente;
import com.happysmile.myapplication.Model.TratamientoExpediente;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PacienteTratamientoExpediente extends AppCompatActivity {

    int idPacienteCita;
    EditText fechaIniText, fechaUltText, descripcionText, observacionText;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paciente_tratamiento_expediente);
        fechaIniText = findViewById(R.id.TratFechaText);
        fechaUltText = findViewById(R.id.TratFechaProxima);
        descripcionText = findViewById(R.id.mensajeDescrip);
        observacionText = findViewById(R.id.mensajeObservacion);
        //Ahora obtengo datos en shared
        SharedPreferences pref = getApplicationContext().getSharedPreferences("myPrefs", MODE_PRIVATE);
        idPacienteCita = pref.getInt("idPaciente", 0);
        obtenerdatos();
    }

    private void obtenerdatos() {
        Call<TratamientoExpediente> tratamientoExpedienteCall = ApiClient.getService().getTratExp(idPacienteCita);
        tratamientoExpedienteCall.enqueue(new Callback<TratamientoExpediente>() {
            @Override
            public void onResponse(Call<TratamientoExpediente> call, Response<TratamientoExpediente> response) {
                if (response.isSuccessful()) {
                    String fecha, fechaprox, descripcion, observacion;
                    TratamientoExpediente tratamientoExpediente = response.body();
                    fecha = response.body().getFechaInicio();
                    fechaprox = response.body().getFechaFinal();
                    descripcion = response.body().getDescripcion();
                    observacion = response.body().getObservacion();

                    fechaIniText.setText(fecha);
                    fechaUltText.setText(fechaprox);
                    descripcionText.setText(descripcion);
                    observacionText.setText(observacion);
                }
            }

            @Override
            public void onFailure(Call<TratamientoExpediente> call, Throwable t) {
                String message = t.getLocalizedMessage();
                Toast.makeText(PacienteTratamientoExpediente.this, message, Toast.LENGTH_LONG).show();
            }
        });
    }
}
package com.happysmile.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import com.happysmile.myapplication.Api.ApiClient;
import com.happysmile.myapplication.Api.ApiService;
import com.happysmile.myapplication.Model.TratamientoEndodoncia;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PacienteEndoTratDetalleActivity extends AppCompatActivity {
    int IdRecibido;
    EditText fechaIniText, fechaUltText, descripcionText, observacionText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paciente_endo_trat_detalle);
        fechaIniText = findViewById(R.id.TratEFechaText);
        fechaUltText = findViewById(R.id.TratEFechaProxima);
        descripcionText = findViewById(R.id.EndomensajeDescrip);
        observacionText = findViewById(R.id.EndomensajeObservacion);
        IdRecibido = getIntent().getExtras().getInt("id");
        Toast.makeText(this, "Mi id es: " + IdRecibido, Toast.LENGTH_SHORT).show();
        obtenerTratamiento();
    }

    private void obtenerTratamiento() {
        Call<TratamientoEndodoncia> tratamientoEndodonciaCall = ApiClient.getService().getTratEndo(IdRecibido);
        tratamientoEndodonciaCall.enqueue(new Callback<TratamientoEndodoncia>() {
            @Override
            public void onResponse(Call<TratamientoEndodoncia> call, Response<TratamientoEndodoncia> response) {
                if (response.isSuccessful()) ;
                {
                    String fecha, fechaprox, descripcion, observacion;
                    TratamientoEndodoncia tratamientoEndodoncia = response.body();
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
            public void onFailure(Call<TratamientoEndodoncia> call, Throwable t) {
                String message = t.getLocalizedMessage();
                Toast.makeText(PacienteEndoTratDetalleActivity.this, message, Toast.LENGTH_LONG).show();
            }
        });
    }
}
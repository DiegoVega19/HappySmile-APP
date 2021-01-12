package com.happysmile.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import com.happysmile.myapplication.Api.ApiClient;
import com.happysmile.myapplication.Api.ApiService;
import com.happysmile.myapplication.Model.TratamientoSeguimiento;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PacienteTraSeguiDetalleActivity extends AppCompatActivity {
    int IdRecibido;
    EditText fechaIniText, fechaUltText, descripcionText, observacionText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paciente_tra_segui_detalle);
        fechaIniText = findViewById(R.id.TratSFechaText);
        fechaUltText = findViewById(R.id.TratsFechaProxima);
        descripcionText = findViewById(R.id.SegmensajeDescrip);
        observacionText = findViewById(R.id.SegmensajeObservacion);

        IdRecibido = getIntent().getExtras().getInt("id");
        //  Toast.makeText(this, "Mi id es: "+IdRecibido, Toast.LENGTH_SHORT).show();
        obtenerTratamiento();
    }

    private void obtenerTratamiento() {
        Call<TratamientoSeguimiento> tratamientoSeguimientoCall = ApiClient.getService().getTratSeg(IdRecibido);
        tratamientoSeguimientoCall.enqueue(new Callback<TratamientoSeguimiento>() {
            @Override
            public void onResponse(Call<TratamientoSeguimiento> call, Response<TratamientoSeguimiento> response) {
                if (response.isSuccessful()) {
                    String fecha, fechaprox, descripcion, observacion;
                    TratamientoSeguimiento tratamientoSeguimiento = response.body();
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
            public void onFailure(Call<TratamientoSeguimiento> call, Throwable t) {
                String message = t.getLocalizedMessage();
                Toast.makeText(PacienteTraSeguiDetalleActivity.this, message, Toast.LENGTH_LONG).show();
            }
        });
    }
}
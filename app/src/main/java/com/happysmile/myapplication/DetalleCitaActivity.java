package com.happysmile.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import com.happysmile.myapplication.Api.ApiClient;
import com.happysmile.myapplication.Model.DoctorCita;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetalleCitaActivity extends AppCompatActivity {

    int IdRecibido;
    EditText nombre, servicio, fecha, hora;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_cita);
        nombre = findViewById(R.id.CitanombreText);
        servicio = findViewById(R.id.CitaServiceEditText);
        fecha = findViewById(R.id.CitaFechaEditText);
        hora = findViewById(R.id.CitaHoraEditText);

        IdRecibido = getIntent().getExtras().getInt("id");
        Toast.makeText(this, "Mi id es: "+IdRecibido, Toast.LENGTH_SHORT).show();
        obtenerCita();
    }

    private void obtenerCita() {
        Call<DoctorCita> doctorCitaCall = ApiClient.getService().getUserCitas(IdRecibido);
        doctorCitaCall.enqueue(new Callback<DoctorCita>() {
            @Override
            public void onResponse(Call<DoctorCita> call, Response<DoctorCita> response) {
                if (response.isSuccessful())
                {
                    String citaNombre, citaApellid, citaServicio, citaFecha, citaHora;
                    citaNombre = response.body().getNombre();
                    citaApellid = response.body().getApellido();
                    citaServicio = response.body().getServicio();
                    citaFecha = response.body().getFechaPropuesta();
                    citaHora = response.body().getHoraPropuesta();
                    nombre.setText(citaNombre+" "+citaApellid);
                    servicio.setText(citaServicio);
                    fecha.setText(citaFecha);
                    hora.setText(citaHora);

                }
            }

            @Override
            public void onFailure(Call<DoctorCita> call, Throwable t) {
                String message = t.getLocalizedMessage();
                Toast.makeText(DetalleCitaActivity.this, message, Toast.LENGTH_LONG).show();
            }
        });
    }
}
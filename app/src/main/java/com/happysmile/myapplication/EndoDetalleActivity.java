package com.happysmile.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import com.happysmile.myapplication.Api.ApiClient;
import com.happysmile.myapplication.Model.EndodonciaDetalle;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EndoDetalleActivity extends AppCompatActivity {

    int IdRecibido;
    EditText fecha, diente;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_endo_detalle);
        fecha = findViewById(R.id.EndoDetailFechaText);
        diente = findViewById(R.id.EndoDetailDiente);
        IdRecibido = getIntent().getExtras().getInt("idEndodoncia");
        //   Toast.makeText(this, "Mi id es: "+IdRecibido, Toast.LENGTH_SHORT).show();
        getDetalle();
    }

    private void getDetalle() {
        Call<EndodonciaDetalle> endodonciaDetalleCall = ApiClient.getService().getEndoDetalle(IdRecibido);
        endodonciaDetalleCall.enqueue(new Callback<EndodonciaDetalle>() {
            @Override
            public void onResponse(Call<EndodonciaDetalle> call, Response<EndodonciaDetalle> response) {
                if (response.isSuccessful()) {
                    String endFecha, endDiente;
                    endFecha = response.body().getFecha();
                    endDiente = response.body().getDiente_a_tratar();
                    fecha.setText(endFecha);
                    diente.setText(endDiente);
                }
            }

            @Override
            public void onFailure(Call<EndodonciaDetalle> call, Throwable t) {
                String message = t.getLocalizedMessage();
                Toast.makeText(EndoDetalleActivity.this, message, Toast.LENGTH_LONG).show();
            }
        });
    }
}
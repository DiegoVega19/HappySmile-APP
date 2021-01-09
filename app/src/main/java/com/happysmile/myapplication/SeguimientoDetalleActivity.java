package com.happysmile.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import com.happysmile.myapplication.Api.ApiClient;
import com.happysmile.myapplication.Model.EndodonciaDetalle;
import com.happysmile.myapplication.Model.SeguimientoDetalle;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SeguimientoDetalleActivity extends AppCompatActivity {

    int IdRecibido;
   EditText fecha, fechaUlt, motivo, proxCita;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seguimiento_detalle);
        fecha = findViewById(R.id.SegDetailFechaText);
        fechaUlt = findViewById(R.id.SegDetailUltFecha);
        motivo = findViewById(R.id.segDetailMotivoConsulta);
        proxCita = findViewById(R.id.SegDetailProximaCita);

        IdRecibido = getIntent().getExtras().getInt("idSeg");
        Toast.makeText(this, "Mi id es: "+IdRecibido, Toast.LENGTH_SHORT).show();
        getDetalle();
    }

    private void getDetalle() {
        Call<SeguimientoDetalle> seguimientoDetalleCall = ApiClient.getService().getSegDetalle(IdRecibido);
        seguimientoDetalleCall.enqueue(new Callback<SeguimientoDetalle>() {
            @Override
            public void onResponse(Call<SeguimientoDetalle> call, Response<SeguimientoDetalle> response) {
                if (response.isSuccessful())
                {
                    String sFecha, sFechaUlt, sMotivo, sProx;
                    sFecha = response.body().getFecha();
                    sFechaUlt = response.body().getFechaUltimaConsulta();
                    sMotivo = response.body().getMotivoConsulta();
                    sProx = response.body().getProximaCita();
                    fecha.setText(sFecha);
                    fechaUlt.setText(sFechaUlt);
                    motivo.setText(sMotivo);
                    proxCita.setText(sProx);
                }
            }

            @Override
            public void onFailure(Call<SeguimientoDetalle> call, Throwable t) {
                String message = t.getLocalizedMessage();
                Toast.makeText(SeguimientoDetalleActivity.this, message, Toast.LENGTH_LONG).show();
            }
        });
    }
}
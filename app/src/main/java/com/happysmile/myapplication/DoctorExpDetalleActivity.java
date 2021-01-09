package com.happysmile.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.happysmile.myapplication.Api.ApiClient;
import com.happysmile.myapplication.Model.ExpedienteDetalle;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DoctorExpDetalleActivity extends AppCompatActivity {

   EditText id, fecha, presionMax, presionMin, frecPulso, FrecRespiratorio, TempBucal, peso, talla, grupSang, factor;
     TextView titulo;
   int Exp_ID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_exp_detalle);
        id = findViewById(R.id.IdExpText);
        fecha = findViewById(R.id.FechaExpText);
        presionMax = findViewById(R.id.pMaxText);
        presionMin = findViewById(R.id.pMinText);
        frecPulso = findViewById(R.id.pulsoText);
        FrecRespiratorio = findViewById(R.id.frecuenciaResText);
        TempBucal = findViewById(R.id.tempBucalText);
        peso = findViewById(R.id.pesoText);
        talla = findViewById(R.id.tallaText);
        grupSang = findViewById(R.id.grupoSangText);
        factor = findViewById(R.id.factorText);
        titulo = findViewById(R.id.tituloText);

        //Ahora recupero los datos desde intent
        String nombre = getIntent().getExtras().getString("nombre");
        titulo.setText("Expediente de "+nombre);
        Exp_ID = getIntent().getExtras().getInt("idExp");
        Toast.makeText(this, "Mi id recibido es"+Exp_ID, Toast.LENGTH_SHORT).show();
        obtenerDetalle();
    }

    private void obtenerDetalle() {
        Call<ExpedienteDetalle> expedienteDetalleCall = ApiClient.getService().getExpDetail(Exp_ID);
        expedienteDetalleCall.enqueue(new Callback<ExpedienteDetalle>() {
            @Override
            public void onResponse(Call<ExpedienteDetalle> call, Response<ExpedienteDetalle> response) {
                if (response.isSuccessful())
                {
                    int ExpId;
                    String  ExpFecha, ExpGs, ExpFact;
                    Double ExpPMax, ExpPMin, ExpFrecP, ExpFrecR, ExpTempB, ExpPes, ExpTalla;
                    ExpId = response.body().getId();
                    ExpFecha = response.body().getFecha();
                    ExpGs = response.body().getGrupoSanguineo();
                    ExpFact = response.body().getFactor();
                    ExpPMax = response.body().getPresionArterial_Max();
                    ExpPMin = response.body().getPresionArterial_Min();
                    ExpFrecP = response.body().getFrecuenciaPulso();
                    ExpFrecR = response.body().getFrecuenciaPRespiratoria();
                    ExpTempB = response.body().getTemperaturaBucal();
                    ExpPes = response.body().getPeso();
                    ExpTalla = response.body().getTalla();
                    String idText = String.valueOf(ExpId);

                   id.setText(idText);
                    fecha.setText(ExpFecha);
                    presionMax.setText(ExpPMax.toString());
                    presionMin.setText(ExpPMin.toString());
                    frecPulso.setText(ExpFrecP.toString());
                    FrecRespiratorio.setText(ExpFrecR.toString());
                    TempBucal.setText(ExpTempB.toString());
                    peso.setText(ExpPes.toString());
                    talla.setText(ExpTalla.toString());
                    grupSang.setText(ExpGs);
                    factor.setText(ExpFact);
                }
            }

            @Override
            public void onFailure(Call<ExpedienteDetalle> call, Throwable t) {
                String message = t.getLocalizedMessage();
                Toast.makeText(DoctorExpDetalleActivity.this, message, Toast.LENGTH_LONG).show();
            }
        });
    }
}
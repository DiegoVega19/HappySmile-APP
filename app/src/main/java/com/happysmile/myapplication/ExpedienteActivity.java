package com.happysmile.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.happysmile.myapplication.Api.ApiClient;
import com.happysmile.myapplication.Model.TotalResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ExpedienteActivity extends AppCompatActivity {

    View v;
    int SegCount, ExpCount, EndoCount;
    CardView cardExP, cardSeg, cardEndo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expediente);
       v  =  findViewById(android.R.id.content);
        cardExP = findViewById(R.id.cardExpPrin);
        cardSeg = findViewById(R.id.cardExpSeg);
        cardEndo = findViewById(R.id.cardExpEnd);
        getSeguimientos();
        getExp();
        getEndo();
    }

    private void getSeguimientos() {
        Call<TotalResponse> totalResponseCall = ApiClient.getService().getTotalSeguimientos();
        totalResponseCall.enqueue(new Callback<TotalResponse>() {
            @Override
            public void onResponse(Call<TotalResponse> call, Response<TotalResponse> response) {
                if (response.isSuccessful())
                {
                    SegCount = response.body().getTotalSeguimientos();
                    Toast.makeText(ExpedienteActivity.this, "Mi total de seguimientos son: "+SegCount, Toast.LENGTH_SHORT).show();
                    irSeguimientos();
                }
            }

            @Override
            public void onFailure(Call<TotalResponse> call, Throwable t) {
                String message = t.getLocalizedMessage();
                Toast.makeText(ExpedienteActivity.this, message, Toast.LENGTH_LONG).show();
            }
        });
    }

    private void getExp() {
        Call<TotalResponse> totalResponseCall = ApiClient.getService().getTotalExpedientes();
        totalResponseCall.enqueue(new Callback<TotalResponse>() {
            @Override
            public void onResponse(Call<TotalResponse> call, Response<TotalResponse> response) {
                ExpCount = response.body().getTotalExpedientes();
                Toast.makeText(ExpedienteActivity.this, "Mi total de expedientes es: "+ExpCount, Toast.LENGTH_SHORT).show();
                irExpedientes();
            }

            @Override
            public void onFailure(Call<TotalResponse> call, Throwable t) {
                String message = t.getLocalizedMessage();
                Toast.makeText(ExpedienteActivity.this, message, Toast.LENGTH_LONG).show();
            }
        });
    }

    private void getEndo() {
        Call<TotalResponse> totalResponseCall = ApiClient.getService().gettotalEndodoncias();
        totalResponseCall.enqueue(new Callback<TotalResponse>() {
            @Override
            public void onResponse(Call<TotalResponse> call, Response<TotalResponse> response) {
                EndoCount = response.body().getTotalEndodoncias();
                Toast.makeText(ExpedienteActivity.this, "Mi total de endodoncias es: "+EndoCount, Toast.LENGTH_SHORT).show();
                IrEndodonciass();

            }

            @Override
            public void onFailure(Call<TotalResponse> call, Throwable t) {
                String message = t.getLocalizedMessage();
                Toast.makeText(ExpedienteActivity.this, message, Toast.LENGTH_LONG).show();
            }
        });
    }

    private void irSeguimientos()
    {
        cardSeg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (SegCount==0)
                {
                    String dato = "Seguimientos";
                    MostrarSnackbarEstado(dato);
                }
                else
                {
                    Intent i = new Intent(ExpedienteActivity.this,ExpedienteSeguimientoActivity.class);
                    startActivity(i);
                }

            }
        });
    }
    private void irExpedientes()
    {
        cardExP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ExpCount==0)
                {
                    String dato = "Expedientes";
                    MostrarSnackbarEstado(dato);
                }
                else
                {
                    Intent i = new Intent(ExpedienteActivity.this, ExpedientePrincipalActivity.class);
                    startActivity(i);
                }
            }
        });

    }
    private void IrEndodonciass()
    {
        cardEndo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (EndoCount==0)
                {
                    String dato = "Endodoncias";
                    MostrarSnackbarEstado(dato);
                }
                else
                {
                    Intent i = new Intent(ExpedienteActivity.this, ExpedienteEndodonciaActivity.class);
                    startActivity(i);
                }

            }
        });

    }
    public  void MostrarSnackbarEstado(String dato)
    {
        Snackbar snackbar = Snackbar.make(v,"No Posee  Registros de "+dato +"!!!",Snackbar.LENGTH_LONG);
        snackbar.show();
    }
}
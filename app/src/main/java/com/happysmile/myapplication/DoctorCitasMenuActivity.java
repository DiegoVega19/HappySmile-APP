package com.happysmile.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.happysmile.myapplication.Api.ApiClient;
import com.happysmile.myapplication.Model.DoctorCita;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DoctorCitasMenuActivity extends AppCompatActivity {

   int IdDoctor;
    View v;
    int CitasCount;

    CardView cardCalendar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_citas_menu);
        v =  findViewById(android.R.id.content);
        cardCalendar = findViewById(R.id.CardCalendar);
        //Ahora obtengo datos en shared
        SharedPreferences pref = getApplicationContext().getSharedPreferences("myPrefs", MODE_PRIVATE);
        IdDoctor = pref.getInt("idDoctor", 0);

        getCitas();

    }

    private void getCitas() {
        Call<DoctorCita> doctorCitaCall = ApiClient.getService().getCountDoctorC(IdDoctor);
        doctorCitaCall.enqueue(new Callback<DoctorCita>() {
            @Override
            public void onResponse(Call<DoctorCita> call, Response<DoctorCita> response) {
               if (response.isSuccessful())
               {
                   CitasCount = response.body().getTotalCitas();
                   Toast.makeText(DoctorCitasMenuActivity.this, "Mi total de citas como Doctor es:"+CitasCount, Toast.LENGTH_SHORT).show();
                   irCitas();
               }
            }

            @Override
            public void onFailure(Call<DoctorCita> call, Throwable t) {
                String message = t.getLocalizedMessage();
                Toast.makeText(DoctorCitasMenuActivity.this, message, Toast.LENGTH_LONG).show();
            }
        });
    }
    private  void irCitas(){
        cardCalendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (CitasCount==0)
                {
                    MostrarSnackbarEstado();
                }
                else
                {
                    Intent i = new Intent(DoctorCitasMenuActivity.this,CitasActivity.class);
                    startActivity(i);
                }

            }
        });
    }
    public  void MostrarSnackbarEstado()
    {
        Snackbar snackbar = Snackbar.make(v,"No Posee Registros de Citas",Snackbar.LENGTH_LONG);
        snackbar.show();
    }
}
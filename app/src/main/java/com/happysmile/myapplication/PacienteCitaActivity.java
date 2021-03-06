package com.happysmile.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.happysmile.myapplication.Api.ApiClient;
import com.happysmile.myapplication.Model.Cita;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PacienteCitaActivity extends AppCompatActivity {


    CardView cardAddCita, cardEstadoCita;
    View v;
    int idPacienteCita;
    int IdEstado;
    Boolean TieneCita;
    int CantidadCitas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paciente_cita);
        cardAddCita = findViewById(R.id.cardSolicitarCita);
        v = findViewById(android.R.id.content);
        cardEstadoCita = findViewById(R.id.CardCitaEstado);
        //Ahora obtengo datos en shared
        SharedPreferences pref = getApplicationContext().getSharedPreferences("myPrefs", MODE_PRIVATE);
        TieneCita = pref.getBoolean("CitaAgregada", false);
        idPacienteCita = pref.getInt("idPaciente", 0);
        obtenerTotalCita();
        obtenerUltimoestadoCita();

        cardAddCita.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(PacienteCitaActivity.this, SolicitarCitaActivity.class);
                startActivity(i);
            }
        });

    }

    private void obtenerUltimoestadoCita() {
        Call<Cita> citaCall = ApiClient.getService().getEstadoUltimaCita(idPacienteCita);
        citaCall.enqueue(new Callback<Cita>() {
            @Override
            public void onResponse(Call<Cita> call, Response<Cita> response) {
                Cita cita = response.body();
                IdEstado = response.body().getEstado_citas_id();
                SharedPreferences pref = getApplicationContext().getSharedPreferences("myPrefs", MODE_PRIVATE);
                SharedPreferences.Editor editor = pref.edit();
                editor.putInt("Estado", IdEstado);
                editor.commit();
                //   Toast.makeText(PacienteCitaActivity.this, "Mi estado es"+IdEstado, Toast.LENGTH_SHORT).show();
                IrCita();
            }

            @Override
            public void onFailure(Call<Cita> call, Throwable t) {
                String message = t.getLocalizedMessage();
                Toast.makeText(PacienteCitaActivity.this, message, Toast.LENGTH_LONG).show();
            }
        });
    }

    private void IrCita() {
        cardAddCita.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences pref = getApplicationContext().getSharedPreferences("myPrefs", MODE_PRIVATE);
                int Estado = pref.getInt("Estado", 0);
                if (Estado == 1 || Estado == 2 || Estado == 3 || Estado == 4) {
                    MostrarSnackbarError();
                } else if (Estado == 0 || Estado == 5 || Estado == 6) {
                    Intent i = new Intent(PacienteCitaActivity.this, SolicitarCitaActivity.class);
                    startActivity(i);
                }
            }
        });
    }

    private void obtenerTotalCita() {
        Call<Cita> citaCall = ApiClient.getService().getCantidadCita(idPacienteCita);
        citaCall.enqueue(new Callback<Cita>() {
            @Override
            public void onResponse(Call<Cita> call, Response<Cita> response) {
                if (response.isSuccessful()) {
                    Cita cita = response.body();
                    CantidadCitas = response.body().getTotalCitas();
                    SharedPreferences pref = getApplicationContext().getSharedPreferences("myPrefs", MODE_PRIVATE);
                    SharedPreferences.Editor editor = pref.edit();
                    editor.putInt("TotalCita", CantidadCitas);
                    editor.commit();
                  //  Toast.makeText(PacienteCitaActivity.this, "Mi total de citas es" + CantidadCitas, Toast.LENGTH_SHORT).show();
                    IrEstado();

                }
            }

            @Override
            public void onFailure(Call<Cita> call, Throwable t) {
                String message = t.getLocalizedMessage();
                Toast.makeText(PacienteCitaActivity.this, message, Toast.LENGTH_LONG).show();
            }
        });
    }

    public void IrEstado() {
        cardEstadoCita.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SharedPreferences pref = getApplicationContext().getSharedPreferences("myPrefs", MODE_PRIVATE);
                int TotalCita = pref.getInt("TotalCita", 0);
                if (TotalCita == 0) {
                    MostrarSnackbarEstado();
                } else {
                    Intent i = new Intent(PacienteCitaActivity.this, PacienteEstadoCitaActivity.class);
                    startActivity(i);
                }

            }
        });
    }

    public void MostrarSnackbarError() {
        Snackbar snackbar = Snackbar.make(v, "Tu ultima Cita Aun no ha sido Finalizada!!!", Snackbar.LENGTH_LONG)
                .setAction("Ver estado Cita", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent i = new Intent(PacienteCitaActivity.this, PacienteEstadoCitaActivity.class);
                        startActivity(i);
                    }
                });
        snackbar.show();
    }

    public void MostrarSnackbarEstado() {
        Snackbar snackbar = Snackbar.make(v, "Aun no tienes Citas Creadas!!!", Snackbar.LENGTH_LONG)
                .setAction("Agregar Cita", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent i = new Intent(PacienteCitaActivity.this, SolicitarCitaActivity.class);
                        startActivity(i);
                    }
                });
        snackbar.show();
    }
}
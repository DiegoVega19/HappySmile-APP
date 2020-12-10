package com.happysmile.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class PacienteCitaActivity extends AppCompatActivity {


    CardView cardAddCita, cardEstadoCita;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paciente_cita);
        cardAddCita = findViewById(R.id.cardSolicitarCita);
        cardEstadoCita = findViewById(R.id.CardCitaEstado);

        cardAddCita.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(PacienteCitaActivity.this,SolicitarCitaActivity.class);
                startActivity(i);
            }
        });

        cardEstadoCita.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(PacienteCitaActivity.this, "En creacion...", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
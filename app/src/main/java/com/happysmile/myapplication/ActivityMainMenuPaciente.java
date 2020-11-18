package com.happysmile.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.happysmile.myapplication.Model.LoginResponse;

public class ActivityMainMenuPaciente extends AppCompatActivity {
    private static final String TAG = "Nombre";
    LoginResponse loginResponse;
    TextView username, bienvenida;
    CardView cardPerfil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu_paciente);
//        nombre = findViewById(R.id.nombreText);
        username = findViewById(R.id.letra);
        bienvenida = findViewById(R.id.bienvenida);
        cardPerfil = findViewById(R.id.PerfilCard);
//        Intent intent = getIntent();
//        String message = intent.getStringExtra(LoginActivity.EXTRA_MESSAGE);
//        nombre.setText(message);
        cargardatos();
        cardPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(ActivityMainMenuPaciente.this, "Tocado", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(ActivityMainMenuPaciente.this,EditarPerfilActivity.class);
                startActivity(i);
            }
        });

        }

    private void cargardatos() {
        SharedPreferences pref = getApplicationContext().getSharedPreferences("myPrefs",MODE_PRIVATE);
        String datos = pref.getString("DIEGO","no hay nada");
        Log.i(TAG,"SU NOMBRE ES:"+datos);
        username.setText(datos);
    }

}


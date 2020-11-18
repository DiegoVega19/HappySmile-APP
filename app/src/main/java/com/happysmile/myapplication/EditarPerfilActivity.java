package com.happysmile.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class EditarPerfilActivity extends AppCompatActivity {

    Button btnSalirSesion;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_perfil);
        btnSalirSesion = findViewById(R.id.buttonCerrar);
        cerrarSesion();
    }

    private void cerrarSesion() {

        btnSalirSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences pref = getApplicationContext().getSharedPreferences("myPrefs",MODE_PRIVATE);
                SharedPreferences.Editor editor = pref.edit();
                editor.clear();
                editor.commit();
                Toast.makeText(EditarPerfilActivity.this, "Hasta Pronto!", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(EditarPerfilActivity.this,LoginActivity.class);
                startActivity(i);
            }
        });

    }
}
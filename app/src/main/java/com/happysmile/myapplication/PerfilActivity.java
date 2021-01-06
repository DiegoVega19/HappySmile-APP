package com.happysmile.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.happysmile.myapplication.Api.ApiClient;
import com.happysmile.myapplication.Model.Doctor;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PerfilActivity extends AppCompatActivity {

   String CorreoDoc;
    EditText nombres, inss, email, telefono, celular;
    Button btnSalirSesion;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);
        btnSalirSesion = findViewById(R.id.DocbuttonCerrar);
        nombres = findViewById(R.id.DocnombreText);
        inss = findViewById(R.id.DocINSSText);
        email = findViewById(R.id.DoccorreoEditText);
        telefono = findViewById(R.id.DoctelefonoEditText);
        celular = findViewById(R.id.DoccelularEditText);
        //Obtengo datos en shared
        SharedPreferences pref = getApplicationContext().getSharedPreferences("myPrefs",MODE_PRIVATE);
        String correo = pref.getString("Correo","no hay nada");
        CorreoDoc = correo;
        obtenerDatosDoctor();
        CerrarSesion();
    }

    private void obtenerDatosDoctor() {
        Call<Doctor> doctorCall = ApiClient.getService().getDoctorData(CorreoDoc);
        doctorCall.enqueue(new Callback<Doctor>() {
            @Override
            public void onResponse(Call<Doctor> call, Response<Doctor> response) {
                if (response.isSuccessful())
                {
                    String DocNombre, DocApellido, emailDoc, telefonoDOC, celularDoc, inssDoc;
                    Doctor doctor = response.body();
                    DocNombre = response.body().getNombre();
                    DocApellido = response.body().getApellido();
                    telefonoDOC = response.body().getTelefono();
                    celularDoc = response.body().getCelular();
                    emailDoc = response.body().getEmail();
                    inssDoc = response.body().getInss();

                    nombres.setText(DocNombre+" "+DocApellido);
                    email.setText(emailDoc);
                    telefono.setText(telefonoDOC);
                    celular.setText(celularDoc);
                    inss.setText(inssDoc);
                    Toast.makeText(PerfilActivity.this, "Mi nombre es"+DocNombre, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Doctor> call, Throwable t) {
                String message = t.getLocalizedMessage();
                Toast.makeText(PerfilActivity.this, message, Toast.LENGTH_LONG).show();
            }
        });
    }

    private void CerrarSesion() {
        btnSalirSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences pref = getApplicationContext().getSharedPreferences("myPrefs",MODE_PRIVATE);
                SharedPreferences.Editor editor = pref.edit();
                editor.clear();
                editor.commit();
                Toast.makeText(PerfilActivity.this, "Hasta Pronto!", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(PerfilActivity.this,LoginActivity.class);
                startActivity(i);
            }
        });
    }
}
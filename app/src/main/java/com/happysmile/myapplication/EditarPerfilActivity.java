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
import com.happysmile.myapplication.Api.ApiService;
import com.happysmile.myapplication.Model.Paciente;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditarPerfilActivity extends AppCompatActivity {
    int idPacienteCita;
    EditText nombres, edad, email, telefono, celular;
    Button btnSalirSesion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_perfil);
        btnSalirSesion = findViewById(R.id.buttonCerrar);
        nombres = findViewById(R.id.nombreText);
        edad = findViewById(R.id.edadEditText);
        email = findViewById(R.id.correoEditText);
        telefono = findViewById(R.id.telefonoEditText);
        celular = findViewById(R.id.celularEditText);
        //Ahora obtengo datos en shared
        SharedPreferences pref = getApplicationContext().getSharedPreferences("myPrefs", MODE_PRIVATE);
        idPacienteCita = pref.getInt("idPaciente", 0);
        obtenerDatosPaciente();
        cerrarSesion();
    }

    private void obtenerDatosPaciente() {
        Call<Paciente> pacienteCall = ApiClient.getService().getPacData(idPacienteCita);
        pacienteCall.enqueue(new Callback<Paciente>() {
            @Override
            public void onResponse(Call<Paciente> call, Response<Paciente> response) {
                if (response.isSuccessful()) {
                    String pacNombre, pacApellido, emailPac, telefonoPac, celularPac;
                    int pacEdad;
                    Paciente paciente = response.body();
                    pacNombre = response.body().getNombre();
                    pacApellido = response.body().getApellido();
                    pacEdad = response.body().getEdad();
                    emailPac = response.body().getEmail();
                    telefonoPac = response.body().getTelefono();
                    celularPac = response.body().getCelular();

                    nombres.setText(pacNombre + " " + pacApellido);
                    edad.setText(pacEdad + " Años");
                    email.setText(emailPac);
                    telefono.setText(telefonoPac);
                    celular.setText(celularPac);
                    //  Toast.makeText(EditarPerfilActivity.this, "Mi nombre es"+pacNombre, Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<Paciente> call, Throwable t) {
                String message = t.getLocalizedMessage();
                Toast.makeText(EditarPerfilActivity.this, message, Toast.LENGTH_LONG).show();
            }
        });

    }

    private void cerrarSesion() {

        btnSalirSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences pref = getApplicationContext().getSharedPreferences("myPrefs", MODE_PRIVATE);
                SharedPreferences.Editor editor = pref.edit();
                editor.clear();
                editor.commit();
                Toast.makeText(EditarPerfilActivity.this, "Hasta Pronto!", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(EditarPerfilActivity.this, LoginActivity.class);
                startActivity(i);
            }
        });

    }
}
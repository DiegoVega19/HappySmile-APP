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

import com.happysmile.myapplication.Api.ApiClient;
import com.happysmile.myapplication.Api.ApiService;
import com.happysmile.myapplication.Model.LoginResponse;
import com.happysmile.myapplication.Model.Municipio;
import com.happysmile.myapplication.Model.Paciente;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActivityMainMenuPaciente extends AppCompatActivity {
    private static final String TAG = "Datos";
    String CorrePaciente;
    LoginResponse loginResponse;
    TextView username, bienvenida;
    CardView cardPerfil, cardCitas;
    private ApiService service = ApiClient.getRetrofit().create(ApiService.class);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu_paciente);
//        nombre = findViewById(R.id.nombreText);
        username = findViewById(R.id.letra);
        bienvenida = findViewById(R.id.bienvenida);
        cardPerfil = findViewById(R.id.PerfilCard);
        cardCitas = findViewById(R.id.Citascard);
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

        cardCitas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ActivityMainMenuPaciente.this,PacienteCitaActivity.class);
                startActivity(i);
            }
        });
        }

    private void cargardatos() {
        SharedPreferences pref = getApplicationContext().getSharedPreferences("myPrefs",MODE_PRIVATE);
        String datos = pref.getString("DIEGO","no hay nada");
        String correo = pref.getString("Correo","no hay nada");
        Log.i(TAG,"SU Correo es:"+correo);
        username.setText(datos);
        CorrePaciente = correo;

        obtenerDatosPaciente();
    }

    private void obtenerDatosPaciente() {
        Call<List<Paciente>> listCall = service.getDatosbyEmail(CorrePaciente);
        listCall.enqueue(new Callback<List<Paciente>>() {
            @Override
            public void onResponse(Call<List<Paciente>> call, Response<List<Paciente>> response) {
                    if (response.isSuccessful())
                    {
                        List<Paciente> pacientes = response.body();
                        for (Paciente paciente: pacientes)
                        {

                            int id = paciente.getId();
                            Log.i(TAG,"Su id como paciente es:"+id);
                          //  Toast.makeText(RegistrarActivity.this, "Mi id de municipio es:"+id, Toast.LENGTH_SHORT).show();
                            SharedPreferences pref = getApplicationContext().getSharedPreferences("myPrefs",MODE_PRIVATE);
                            SharedPreferences.Editor editor = pref.edit();
                            editor.putInt("idPaciente",id);
                            editor.commit();

                        }
                    }
                    else
                    {
                        String message = "Un error a ocurrido";
                        Toast.makeText(ActivityMainMenuPaciente.this,message, Toast.LENGTH_LONG).show();
                    }
            }

            @Override
            public void onFailure(Call<List<Paciente>> call, Throwable t) {
                String message = t.getLocalizedMessage();
                Toast.makeText(ActivityMainMenuPaciente.this,message, Toast.LENGTH_LONG).show();
            }
        });

    }

}


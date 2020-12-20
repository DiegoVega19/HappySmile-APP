package com.happysmile.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.happysmile.myapplication.Api.ApiClient;
import com.happysmile.myapplication.Model.LoginRequest;
import com.happysmile.myapplication.Model.LoginResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    public static final String EXTRA_MESSAGE = "data";
    TextView textcuenta;
    View v;
    EditText textemail,textpass;
    Button btnentrar;
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences pref = getApplicationContext().getSharedPreferences("myPrefs",MODE_PRIVATE);
        String datos = pref.getString("DIEGO",null);
        if(datos!=null)
        {
            Intent I = new Intent(getApplicationContext(),ActivityMainMenuPaciente.class );
            startActivity(I);
        }
        setContentView(R.layout.activity_login);
        v =  findViewById(android.R.id.content);
        textcuenta = findViewById(R.id.TextCrearCuenta);
        textemail = findViewById(R.id.nameText);
        textpass = findViewById(R.id.PassText);
        btnentrar = findViewById(R.id.BtnIniciarSesion);

        textcuenta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(getApplicationContext(),RegistrarActivity.class);
                startActivity(i);

            }
        });

        btnentrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(TextUtils.isEmpty(textemail.getText().toString()) || TextUtils.isEmpty(textpass.getText()))
                {
                    Toast.makeText(LoginActivity.this, "El Email y La Contraseña Son Requeridos", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    LoginRequest loginRequest = new LoginRequest();
                    loginRequest.setEmail(textemail.getText().toString());
                    loginRequest.setPassword(textpass.getText().toString());
                    loginUser(loginRequest);
                }
            }
        });



    }
    public void loginUser(LoginRequest loginRequest)
    {
        Call<LoginResponse>loginResponseCall = ApiClient.getService().login(loginRequest);
        loginResponseCall.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {

                if(response.isSuccessful()){

                    LoginResponse loginResponse = response.body();
                    String nombre, correo;
                    nombre = response.body().getUser().getName();
                    correo = response.body().getUser().getEmail();
                   SharedPreferences pref = getApplicationContext().getSharedPreferences("myPrefs",MODE_PRIVATE);
                   SharedPreferences.Editor editor = pref.edit();
                    editor.putString("DIEGO",nombre);
                    editor.putString("Correo",correo);
                    editor.commit();
                    Intent intent = new Intent(LoginActivity.this, ActivityMainMenuPaciente.class);
                    //intent.putExtra(EXTRA_MESSAGE,nombre);
                    startActivity(intent);
                    //startActivity(new Intent(LoginActivity.this,ActivityMainMenuPaciente.class).putExtra("datos",loginResponse));
                    finish();
                   // Toast.makeText(LoginActivity.this, "Hola"+nombre, Toast.LENGTH_SHORT).show();
                    //  RECORDAR ALMACENAR EN SHARED PREFERENCES
                }
                else
                {
                    mostrarSnackbarInCorrect();
                }
            }


            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                String message = t.getLocalizedMessage();
                Toast.makeText(LoginActivity.this,message, Toast.LENGTH_LONG).show();
            }
        });
    }

    public  void mostrarSnackbarInCorrect()
    {
        Snackbar snackbar = Snackbar.make(v,"La Contraseña o el Email Son Incorrectos!!!",Snackbar.LENGTH_LONG);
        snackbar.show();
    }
}
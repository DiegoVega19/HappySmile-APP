package com.happysmile.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.happysmile.myapplication.Api.ApiClient;
import com.happysmile.myapplication.Model.LoginRequest;
import com.happysmile.myapplication.Model.LoginResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    TextView textcuenta;
    EditText textemail,textpass;
    Button btnentrar;
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
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
                LoginRequest loginRequest = new LoginRequest();
                loginRequest.setEmail(textemail.getText().toString());
                loginRequest.setPassword(textpass.getText().toString());
                loginUser(loginRequest);
            }
        });



    }
    public void loginUser(LoginRequest loginRequest)
    {
        Call<LoginResponse>loginResponseCall = ApiClient. getService().login(loginRequest);
        loginResponseCall.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {

                if(response.isSuccessful()){

                    LoginResponse loginResponse = response.body();
                    Toast.makeText(LoginActivity.this, "Hola"+response.body().getPaciente().getNombre(), Toast.LENGTH_SHORT).show();

                }
                else
                {
                    String message = "Un error a ocurrido";
                    Toast.makeText(LoginActivity.this,message, Toast.LENGTH_LONG).show();
                }
            }


            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                String message = t.getLocalizedMessage();
                Toast.makeText(LoginActivity.this,message, Toast.LENGTH_LONG).show();
            }
        });
    }
}
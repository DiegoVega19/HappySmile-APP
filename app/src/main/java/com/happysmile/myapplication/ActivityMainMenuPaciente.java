package com.happysmile.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.happysmile.myapplication.Model.LoginResponse;

public class ActivityMainMenuPaciente extends AppCompatActivity {

    LoginResponse loginResponse;
    TextView nombre;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu_paciente);
//        nombre = findViewById(R.id.nombreText);
//        Intent intent = getIntent();
//        if (intent.getExtras() != null) {
//            loginResponse = (LoginResponse) intent.getSerializableExtra("data");
          //  nombre.setText(loginResponse.getUser().nombre);
//            Log.e("TAG","====>"+loginResponse.getUser().getNombre());
     //   }
    }
}

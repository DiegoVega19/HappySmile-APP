package com.happysmile.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.happysmile.myapplication.Api.ApiClient;
import com.happysmile.myapplication.Api.ApiService;
import com.happysmile.myapplication.Model.Municipio;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegistrarActivity extends AppCompatActivity {

    Spinner spinnersexo, spinnerestadocivil, spinnnermuni;
    private ApiService service = ApiClient.getRetrofit().create(ApiService.class);
    List<String> integerList;
    List<Municipio> municipios;
    ArrayAdapter<Municipio> stringArrayAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar);
        spinnersexo = findViewById(R.id.sexoSpinner);
        spinnerestadocivil = findViewById(R.id.estadoCivilSpinner);
        spinnnermuni = findViewById(R.id.muniSpinner);
        integerList  = new ArrayList<>();
       municipios = new ArrayList<>();
       stringArrayAdapter = new ArrayAdapter(
                this,android.R.layout.simple_spinner_item,municipios);
        InicilizarSpinnersexo();
        InicilizarSpinnerEstadoCivil();
        InicializarSpinnerMuni();

    }

    public void InicilizarSpinnersexo()
    {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.sexo, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnersexo.setAdapter(adapter);
    }

    public void InicilizarSpinnerEstadoCivil()
    {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.estadocivil, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerestadocivil.setAdapter(adapter);

    }

    public void InicializarSpinnerMuni()
    {

        Call<List<Municipio>> listCall = service.getMuni();
        listCall.enqueue(new Callback<List<Municipio>>() {
            @Override
            public void onResponse(Call<List<Municipio>> call, Response<List<Municipio>> response) {
                if (response.isSuccessful())
                {
                    for (Municipio muni : response.body())
                    {
                        String name = muni.getNombre();
                        Municipio municipio = new Municipio(name);
                        municipios.add(municipio);
                        stringArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spinnnermuni.setAdapter(stringArrayAdapter);



                    }
                }
            }

            @Override
            public void onFailure(Call<List<Municipio>> call, Throwable t) {
                String message = t.getLocalizedMessage();
                Toast.makeText(RegistrarActivity.this,message, Toast.LENGTH_LONG).show();
            }
        });




    }
}
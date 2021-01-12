package com.happysmile.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.happysmile.myapplication.Api.ApiClient;
import com.happysmile.myapplication.Api.ApiService;
import com.happysmile.myapplication.Model.LoginResponse;
import com.happysmile.myapplication.Model.Municipio;
import com.happysmile.myapplication.Model.RegisterRequest;
import com.happysmile.myapplication.Model.RegisterResponse;

import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegistrarActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    Spinner spinnersexo, spinnerestadocivil, spinnnermuni;
    View v;
    Calendar calendar;
    DatePickerDialog datePickerDialog;
    EditText username, email, pass, nombres, apellidos, fechanac, edad, centroTrabajo, ocupacion, telefono, celular;
    Button btncrear;
    String Muni;
    String Sexo;
    int IdMuni;
    String EstadoCivil;
    private ApiService service = ApiClient.getRetrofit().create(ApiService.class);
    List<String> integerList;
    List<Municipio> municipios;
    ArrayAdapter<Municipio> stringArrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar);
        username = findViewById(R.id.nameText);
        v = findViewById(android.R.id.content);
        email = findViewById(R.id.emailText);
        pass = findViewById(R.id.passwordText);
        nombres = findViewById(R.id.nombreText);
        apellidos = findViewById(R.id.apellidoText);
        fechanac = findViewById(R.id.nacimientoText);
        edad = findViewById(R.id.edadText);
        centroTrabajo = findViewById(R.id.trabajoText);
        ocupacion = findViewById(R.id.ocupacionText);
        ocupacion = findViewById(R.id.ocupacionText);
        telefono = findViewById(R.id.telefonoText);
        celular = findViewById(R.id.celularText);
        btncrear = findViewById(R.id.buttonCreateAccount);
        spinnersexo = findViewById(R.id.sexoSpinner);
        spinnerestadocivil = findViewById(R.id.estadoCivilSpinner);
        spinnnermuni = findViewById(R.id.muniSpinner);
        integerList = new ArrayList<>();
        municipios = new ArrayList<>();
        stringArrayAdapter = new ArrayAdapter(
                this, android.R.layout.simple_spinner_item, municipios);
        InicilizarSpinnersexo();
        InicilizarSpinnerEstadoCivil();
        InicializarSpinnerMuni();
        spinnnermuni.setOnItemSelectedListener(this);
        spinnerestadocivil.setOnItemSelectedListener(this);
        spinnersexo.setOnItemSelectedListener(this);
        fechanac.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calendar = Calendar.getInstance();
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                int month = calendar.get(Calendar.MONTH);
                int year = calendar.get(Calendar.YEAR);

                datePickerDialog = new DatePickerDialog(RegistrarActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int mYear, int mMonth, int Mday) {
                        fechanac.setText(mYear + "/" + (mMonth + 1) + "/" + Mday);
                    }
                }, year, month, day);
                datePickerDialog.show();
            }
        });


    }

    private void Obteneridmunicipio() {


        Call<List<Municipio>> lstid = service.getid(Muni);
        lstid.enqueue(new Callback<List<Municipio>>() {
            @Override
            public void onResponse(Call<List<Municipio>> call, Response<List<Municipio>> response) {
                if (response.isSuccessful()) {

                    List<Municipio> municipios = response.body();
                    for (Municipio municipio : municipios) {

                        int id = municipio.getId();
                        //  Toast.makeText(RegistrarActivity.this, "Mi id de municipio es:"+id, Toast.LENGTH_SHORT).show();
                        IdMuni = id;
                    }

                }
            }

            @Override
            public void onFailure(Call<List<Municipio>> call, Throwable t) {
                String message = t.getLocalizedMessage();
                Toast.makeText(RegistrarActivity.this, message, Toast.LENGTH_LONG).show();
            }
        });
    }

    public void InicilizarSpinnersexo() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.sexo, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnersexo.setAdapter(adapter);
    }

    public void InicilizarSpinnerEstadoCivil() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.estadocivil, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerestadocivil.setAdapter(adapter);

    }

    public void InicializarSpinnerMuni() {

        Call<List<Municipio>> listCall = service.getMuni();
        listCall.enqueue(new Callback<List<Municipio>>() {
            @Override
            public void onResponse(Call<List<Municipio>> call, Response<List<Municipio>> response) {
                if (response.isSuccessful()) {
                    for (Municipio muni : response.body()) {
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
                Toast.makeText(RegistrarActivity.this, message, Toast.LENGTH_LONG).show();
            }
        });


    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if (parent.getId() == R.id.muniSpinner) {
            String textmuni = parent.getItemAtPosition(position).toString();
            //  Toast.makeText(parent.getContext(), "Mi municipio es:"+textmuni, Toast.LENGTH_SHORT).show();
            Muni = textmuni;
            Obteneridmunicipio();
        } else if (parent.getId() == R.id.sexoSpinner) {
            String textsexo = parent.getItemAtPosition(position).toString();
            Sexo = textsexo;
        } else if (parent.getId() == R.id.estadoCivilSpinner) {
            String textcivil = parent.getItemAtPosition(position).toString();
            //   Toast.makeText(parent.getContext(), "Mi Estado civil es:"+textcivil, Toast.LENGTH_SHORT).show();
            EstadoCivil = textcivil;
        }

        Guardardatos();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    private void Guardardatos() {
        btncrear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //    Toast.makeText(RegistrarActivity.this, "Mi Id De municipo es: "+IdMuni + "Mi Sexo es: "+Sexo + "Mi estado civil es: "+EstadoCivil, Toast.LENGTH_SHORT).show();

                if (TextUtils.isEmpty(username.getText().toString()) || TextUtils.isEmpty(email.getText().toString()) || TextUtils.isEmpty(pass.getText().toString())
                        || TextUtils.isEmpty(nombres.getText().toString()) || TextUtils.isEmpty(apellidos.getText().toString()) || TextUtils.isEmpty(fechanac.getText().toString())
                        || TextUtils.isEmpty(edad.getText().toString()) || TextUtils.isEmpty(centroTrabajo.getText().toString()) || TextUtils.isEmpty(ocupacion.getText().toString())
                        || TextUtils.isEmpty(telefono.getText().toString()) || TextUtils.isEmpty(celular.getText().toString())) {
                    String Mensaje = "Todos los Campos Son Requeridos";
                    Toast.makeText(RegistrarActivity.this, Mensaje, Toast.LENGTH_SHORT).show();
                } else {
                    RegisterRequest registerRequest = new RegisterRequest();
                    registerRequest.setName(username.getText().toString());
                    registerRequest.setEmail(email.getText().toString());
                    registerRequest.setPassword(pass.getText().toString());
                    registerRequest.setMunicipio_id(IdMuni);
                    registerRequest.setNombre(nombres.getText().toString());
                    registerRequest.setApellido(apellidos.getText().toString());
                    registerRequest.setSexo(Sexo);
                    String fecha = fechanac.getText().toString();
                    //    Date fechafinal = Date.valueOf(fecha);
                    registerRequest.setFechaDeNacimiento(fecha);
                    int num;
                    num = Integer.parseInt(edad.getText().toString());
                    registerRequest.setEdad(num);
                    registerRequest.setEstadoCivil(EstadoCivil);
                    registerRequest.setCentroDeTrabajo(centroTrabajo.getText().toString());
                    registerRequest.setOcupacion(ocupacion.getText().toString());
                    registerRequest.setTelefono(telefono.getText().toString());
                    registerRequest.setCelular(celular.getText().toString());
                    RegisterUser(registerRequest);

                }
            }
        });
    }

    public void RegisterUser(RegisterRequest registerRequest) {
        Call<RegisterResponse> registerResponseCall = ApiClient.getService().registrarse(registerRequest);
        registerResponseCall.enqueue(new Callback<RegisterResponse>() {
            @Override
            public void onResponse(Call<RegisterResponse> call, Response<RegisterResponse> response) {
                if (response.isSuccessful()) {
                    mostrarSnackbarCorrect();
                    Intent i = new Intent(RegistrarActivity.this, LoginActivity.class);
                    finish();
                } else {
                    mostrarSnackbarCorrect();
                }
            }

            @Override
            public void onFailure(Call<RegisterResponse> call, Throwable t) {
                String message = t.getLocalizedMessage();
                Toast.makeText(RegistrarActivity.this, message, Toast.LENGTH_LONG).show();
            }
        });
    }

    public void mostrarSnackbarAlgosaliomal() {
        Snackbar snackbar = Snackbar.make(v, "Registro no guardado, Algo salio mal!!!", Snackbar.LENGTH_LONG);
        snackbar.show();
    }

    public void mostrarSnackbarCorrect() {
        Snackbar snackbar = Snackbar.make(v, "Usuario Registrado Con Exito!!!", Snackbar.LENGTH_LONG);
        snackbar.show();
    }

}

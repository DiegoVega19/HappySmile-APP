package com.happysmile.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.happysmile.myapplication.Api.ApiClient;
import com.happysmile.myapplication.Api.ApiService;
import com.happysmile.myapplication.Model.CitaRequest;
import com.happysmile.myapplication.Model.CitaResponse;
import com.happysmile.myapplication.Model.Municipio;
import com.happysmile.myapplication.Model.RegisterRequest;
import com.happysmile.myapplication.Model.Servicio;
import com.happysmile.myapplication.Model.TotalResponse;

import org.w3c.dom.Text;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SolicitarCitaActivity extends AppCompatActivity implements TimePickerFragment.TimePickerListener, AdapterView.OnItemSelectedListener {

    Spinner spinnerserv;
    View v;
    public  int IDSERV;
    private static final String TAG = "Datos";
    Boolean NuevaCita = false;
    String servicioTexto;
    String FECHAGLOBAL;
    Button btnguardarcita;
    Calendar calendar;
    DatePickerDialog datePickerDialog;
    private ApiService service = ApiClient.getRetrofit().create(ApiService.class);
    List<String> integerList;
    List<Servicio> serviciosList;
    ArrayAdapter<Servicio> stringArrayAdapter;
    EditText fechapicker, horapicker;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_solicitar_cita);
        btnguardarcita = findViewById(R.id.buttonguardarCita);
        v =  findViewById(android.R.id.content);
        spinnerserv = findViewById(R.id.spinnerServicio);
        fechapicker = findViewById(R.id.selectorFecha);
        horapicker = findViewById(R.id.selectorHora);
        integerList  = new ArrayList<>();
        serviciosList = new ArrayList<>();
        stringArrayAdapter = new ArrayAdapter(
                this,android.R.layout.simple_spinner_item,serviciosList);
        inicializarSpinerServicio();
        fechapicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calendar = Calendar.getInstance();
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                int month = calendar.get(Calendar.MONTH);
                int year = calendar.get(Calendar.YEAR);

                datePickerDialog = new DatePickerDialog(SolicitarCitaActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int mYear, int mMonth, int Mday) {
                        fechapicker.setText(mYear+"/"+(mMonth+1)+"/"+Mday);
                        FECHAGLOBAL=mYear+"-"+(mMonth+1)+"-"+Mday;
                    }
                },year,month,day);
                datePickerDialog.show();
            }
        });

        horapicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment timepickerFragment = new TimePickerFragment();
                timepickerFragment.setCancelable(false);
                timepickerFragment.show(getSupportFragmentManager(),"Time Picker");
            }
        });

        btnguardarcita.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                verificarDisponibilidad();

            }
        });

        spinnerserv.setOnItemSelectedListener(this);
    }

    private void verificarDisponibilidad() {
        if (TextUtils.isEmpty(horapicker.getText().toString())|| TextUtils.isEmpty(fechapicker.getText().toString()))
        {
            String Mensaje = "Todos los Campos Son Requeridos";
            Toast.makeText(SolicitarCitaActivity.this, Mensaje, Toast.LENGTH_SHORT).show();
        }
        else
        {

            String fecha, hora;
            fecha = fechapicker.getText().toString();

            Toast.makeText(this, "fercha "+FECHAGLOBAL, Toast.LENGTH_SHORT).show();
            hora = horapicker.getText().toString();
            Call<TotalResponse> totalResponseCall = ApiClient.getService().getDisponibilidad(FECHAGLOBAL,hora);
            totalResponseCall.enqueue(new Callback<TotalResponse>() {
                @Override
                public void onResponse(Call<TotalResponse> call, Response<TotalResponse> response) {
                    int disponible;
                    disponible = response.body().getDisponibilidad();
                    Toast.makeText(SolicitarCitaActivity.this, "Mi disponibilidad es: "+disponible, Toast.LENGTH_SHORT).show();
                    if (disponible == 0)
                    {
                        mostrardialog();
                    }
                    else
                    {
                        //Una fecha se esta ocupando
                        mostrarDialogError();
                    }
                }

                @Override
                public void onFailure(Call<TotalResponse> call, Throwable t) {

                }
            });

        }

    }

    private void mostrarDialogError() {
        AlertDialog.Builder builder = new AlertDialog.Builder(SolicitarCitaActivity.this);
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_one_option,null);
        builder.setView(view);
        final AlertDialog dialog  = builder.create();
        dialog.show();
        TextView txt = view.findViewById(R.id.text_dialogOne);
        txt.setText("La fecha y Hora seleccionadas no se encuentran disponibles, por Favor seleccione otra");
        Button btnOk = view.findViewById(R.id.btnOk);
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
    }

    private void mostrardialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(SolicitarCitaActivity.this);
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_twoop,null);
        builder.setView(view);
        final AlertDialog dialog = builder.create();
        dialog.show();
        TextView txt = view.findViewById(R.id.text_dialog);
        txt.setText("¿Estas seguro que desea Agendar una nueva cita?");
        Button btnReintentar = view.findViewById(R.id.btnReintentar);
        btnReintentar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //aqui viene la logica de agregado
               // Toast.makeText(getApplicationContext(),"Mi servicio tocado es..."+servicioTexto,Toast.LENGTH_SHORT).show();
                if (TextUtils.isEmpty(horapicker.getText().toString())|| TextUtils.isEmpty(fechapicker.getText().toString()))
                {
                    String Mensaje = "Todos los Campos Son Requeridos";
                    Toast.makeText(SolicitarCitaActivity.this, Mensaje, Toast.LENGTH_SHORT).show();
                }
                else
                {
                    //Primero Obtengo los datos que tengo en shared preferences
                    SharedPreferences pref = getApplicationContext().getSharedPreferences("myPrefs",MODE_PRIVATE);
                    int idPacienteCita = pref.getInt("idPaciente",0);

                    //Luego obtengo los datos de la fecha actual
                    String currentDate = new SimpleDateFormat("yyyy/MM/dd", Locale.getDefault()).format(new Date());
                    Toast.makeText(SolicitarCitaActivity.this, "Mi fecha actual es"+currentDate, Toast.LENGTH_SHORT).show();


                    //Primero obtengo mi fecha
                    //Luego de obtener datos importantes posee a guardarlos en su clare correspondiente
                    CitaRequest citaRequest = new CitaRequest();
                    citaRequest.setPasiente_id(idPacienteCita);
                    citaRequest.setDoctors_id(1); //Se envia 1 siempre por que es el doctor por defecto
                    citaRequest.setServicios_id(IDSERV);
                    citaRequest.setEstado_citas_Id(1); //Se envia 1 siempre porque 1 es el estado por defecto (No aprobado)
                    citaRequest.setFechaSolicitud(currentDate);
                    citaRequest.setFechaPropuesta(fechapicker.getText().toString());
                    citaRequest.setHoraPropuesta(horapicker.getText().toString());
                    RegistrarCita(citaRequest); //LLamamos al metodo que hacxe el post al servidor
                }
                dialog.dismiss();
            }
        });
        Button btncancelar = view.findViewById(R.id.btnCancel);
        btncancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                //Toast.makeText(SolicitarCitaActivity.this, "Tocado cancelar", Toast.LENGTH_SHORT).show();
                //Recordar Borrar Despues
            }
        });
    }
    public void RegistrarCita(CitaRequest citaRequest)
    {
        Call<CitaResponse> citaResponseCall = ApiClient.getService().solicitarCita(citaRequest);
        citaResponseCall.enqueue(new Callback<CitaResponse>() {
            @Override
            public void onResponse(Call<CitaResponse> call, Response<CitaResponse> response) {
                if (response.isSuccessful())
                {
                   int cantCita = 1;
                    SharedPreferences pref = getApplicationContext().getSharedPreferences("myPrefs",MODE_PRIVATE);
                    SharedPreferences.Editor editor = pref.edit();
                    editor.putInt("TotalCita",cantCita);
                    editor.putBoolean("CitaAgregada",NuevaCita);
                    editor.commit();
                    mostrarSnackbarSatisfactorio();
                }
                else
                {
                    mostrarSnackbarAlgosaliomal();
                }

            }
            @Override
            public void onFailure(Call<CitaResponse> call, Throwable t) {
                String message = t.getLocalizedMessage();
                Toast.makeText(SolicitarCitaActivity.this,message, Toast.LENGTH_LONG).show();
            }
        });
    }
    public void mostrarSnackbarSatisfactorio()
    {
        Snackbar snackbar = Snackbar.make(v,"Cita Guardada Con Exito!!!",Snackbar.LENGTH_LONG)
                .setAction("Ver", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent i = new Intent(SolicitarCitaActivity.this,PacienteEstadoCitaActivity.class);
                        startActivity(i);
                    }
                });
        snackbar.show();
    }
    public  void mostrarSnackbarAlgosaliomal()
    {
        Snackbar snackbar = Snackbar.make(v,"Cita no guardada, Algo salio mal!!!",Snackbar.LENGTH_LONG);
        snackbar.show();
    }

    public void inicializarSpinerServicio() {
        Call<List<Servicio>> listCall = service.getServ();
        listCall.enqueue(new Callback<List<Servicio>>() {
            @Override
            public void onResponse(Call<List<Servicio>> call, Response<List<Servicio>> response) {
                if (response.isSuccessful())
                {

                    for (Servicio serv : response.body())
                    {
                        String name = serv.getNombre();
                        Servicio servicio = new Servicio(name);
                        serviciosList.add(servicio);
                        stringArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spinnerserv.setAdapter(stringArrayAdapter);
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Servicio>> call, Throwable t) {
                String message = t.getLocalizedMessage();
                Toast.makeText(SolicitarCitaActivity.this,message, Toast.LENGTH_LONG).show();
            }
        });

    }

    @Override
    public void onTimeSet(TimePicker timePicker, int hour, int minute) {
        horapicker.setText(hour+":"+minute);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if (parent.getId()==R.id.spinnerServicio)
        {
            String textServicio = parent.getItemAtPosition(position).toString();
            Toast.makeText(parent.getContext(), "Mi Servicio es:"+textServicio, Toast.LENGTH_SHORT).show();
            servicioTexto = textServicio;
          Obteneridservicio();
        }

       // Guardardatos();
    }
    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    private void Obteneridservicio() {

        Call<List<Servicio>> listCall = service.getidserv(servicioTexto);
        listCall.enqueue(new Callback<List<Servicio>>() {
            @Override
            public void onResponse(Call<List<Servicio>> call, Response<List<Servicio>> response) {
                if (response.isSuccessful())
                {
                    List<Servicio> servicios = response.body();
                    for (Servicio servicio: servicios)
                    {

                        int id = servicio.getId();
                          Toast.makeText(SolicitarCitaActivity.this, "Mi id de servicio es:"+id, Toast.LENGTH_SHORT).show();
                        IDSERV = id;
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Servicio>> call, Throwable t) {
                String message = t.getLocalizedMessage();
                Toast.makeText(SolicitarCitaActivity.this,message, Toast.LENGTH_LONG).show();
            }
        });

    }


}
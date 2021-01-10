package com.happysmile.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.work.Data;
import androidx.work.WorkManager;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.happysmile.myapplication.Api.ApiClient;
import com.happysmile.myapplication.Model.DoctorCita;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.UUID;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetalleCitaActivity extends AppCompatActivity {

   Button cancelarNotificacion, activarNotificacion;
   String FECHA_GLOBAL, HORA_GLOBAL;
    int IdRecibido;
    EditText nombre, servicio, fecha, hora;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_cita);
        nombre = findViewById(R.id.CitanombreText);
        servicio = findViewById(R.id.CitaServiceEditText);
        fecha = findViewById(R.id.CitaFechaEditText);
        hora = findViewById(R.id.CitaHoraEditText);
        cancelarNotificacion = findViewById(R.id.btnDocCancelNoti);
        activarNotificacion = findViewById(R.id.btnDocNoti);

        IdRecibido = getIntent().getExtras().getInt("id");
        Toast.makeText(this, "Mi id es: "+IdRecibido, Toast.LENGTH_SHORT).show();
        obtenerCita();
        cancelarNotificacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EliminarNoti("DetalleCitaTag");
            }
        });

        activarNotificacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String tag = generateKey();
                String dateTimeUser =FECHA_GLOBAL+" "+HORA_GLOBAL;
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
                try {
                    Calendar cal = Calendar.getInstance();
                    cal.setTime(sdf.parse(dateTimeUser));
                  //  Toast.makeText(PacienteEstadoCitaActivity.this, "mi fecha y hORA  es:"+cal.getTimeInMillis(), Toast.LENGTH_SHORT).show();

                    Long  Alertime =  cal.getTimeInMillis() - System.currentTimeMillis();
                    //Toast.makeText(PacienteEstadoCitaActivity.this, "mi fecha y hORA  es:"+Alertime, Toast.LENGTH_LONG).show();

                    int random =(int) (Math.random()*50+1);
                    Data data = guardarData("Happy Smile","Tienes una Cita a las: "+dateTimeUser,random);
                    WorkManagerNoti.guardarNoti(Alertime,data,"DetalleCitaTag");
                   // Toast.makeText(PacienteEstadoCitaActivity.this, "Notificacion Guardada con exito", Toast.LENGTH_SHORT).show();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void obtenerCita() {
        Call<DoctorCita> doctorCitaCall = ApiClient.getService().getUserCitas(IdRecibido);
        doctorCitaCall.enqueue(new Callback<DoctorCita>() {
            @Override
            public void onResponse(Call<DoctorCita> call, Response<DoctorCita> response) {
                if (response.isSuccessful())
                {
                    String citaNombre, citaApellid, citaServicio, citaFecha, citaHora;
                    citaNombre = response.body().getNombre();
                    citaApellid = response.body().getApellido();
                    citaServicio = response.body().getServicio();
                    citaFecha = response.body().getFechaPropuesta();
                    citaHora = response.body().getHoraPropuesta();
                    nombre.setText(citaNombre+" "+citaApellid);
                    servicio.setText(citaServicio);
                    fecha.setText(citaFecha);
                    hora.setText(citaHora);
                    FECHA_GLOBAL = citaFecha;
                    HORA_GLOBAL = citaHora;

                }
            }

            @Override
            public void onFailure(Call<DoctorCita> call, Throwable t) {
                String message = t.getLocalizedMessage();
                Toast.makeText(DetalleCitaActivity.this, message, Toast.LENGTH_LONG).show();
            }
        });
    }

    private void EliminarNoti(String tag)
    {
        WorkManager.getInstance(this).cancelAllWorkByTag(tag);
        Toast.makeText(this, "Ha cancelado la notificacion de su Cita", Toast.LENGTH_SHORT).show();
        activarNotificacion.setVisibility(View.VISIBLE);
        cancelarNotificacion.setVisibility(View.INVISIBLE);
    }

    private String generateKey()
    {
        return UUID.randomUUID().toString();
    }
    private Data guardarData(String titulo, String detalle, int id_noti)
    {
        return new Data.Builder()
                .putString("titulo",titulo)
                .putString("detalle",detalle)
                .putInt("Idnoti",id_noti)
                .build();
    }
}
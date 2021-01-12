package com.happysmile.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.work.Data;
import androidx.work.WorkManager;

import android.app.AlertDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.happysmile.myapplication.Api.ApiClient;
import com.happysmile.myapplication.Model.CancelRequest;
import com.happysmile.myapplication.Model.Cita;
import com.happysmile.myapplication.Model.CitaRequest;
import com.happysmile.myapplication.Model.Municipio;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PacienteEstadoCitaActivity extends AppCompatActivity {


    String Fecha, Hora_Estado;
    EditText nombres, tiempo, service, datosDoctor, message;
    int CitaID;
    View v;
    TextView estadoCita;
    SwipeRefreshLayout refreshLayout;
    Button cancelarButton, notificar, noNotificar;
    int idPacienteCita;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paciente_estado_cita);
        nombres = findViewById(R.id.nombreApellidoText);
        tiempo = findViewById(R.id.tiempoText);
      refreshLayout = findViewById(R.id.refresh);
        service = findViewById(R.id.servicioText);
        v =  findViewById(android.R.id.content);
        datosDoctor = findViewById(R.id.doctorNombreText);
        message = findViewById(R.id.mensajeText);
        estadoCita = findViewById(R.id.estadoCitaText);
        cancelarButton = findViewById(R.id.btnCancelar);
        notificar = findViewById(R.id.btnNotificar);
        noNotificar = findViewById(R.id.btnCancelarNotificacion);
        //Ahora obtengo datos en shared
        SharedPreferences pref = getApplicationContext().getSharedPreferences("myPrefs", MODE_PRIVATE);
        idPacienteCita = pref.getInt("idPaciente", 0);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
           public void onRefresh() {
               // Toast.makeText(PacienteEstadoCitaActivity.this, "Sirvio!!!", Toast.LENGTH_SHORT).show();
                getCita();
                refreshLayout.setRefreshing(false);

           }
        });
       getCita();
       cancelarButton.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {

               mostrarDialogo();
           }
       });
       noNotificar.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               EliminarNoti("tag1");
           }
       });
       notificar.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               String tag = generateKey();

               //Toast.makeText(PacienteEstadoCitaActivity.this, "Mi fecha desde la notificacion es: "+Fecha, Toast.LENGTH_SHORT).show();
               String dateTimeUser = Fecha + " " + Hora_Estado;
             //  Toast.makeText(PacienteEstadoCitaActivity.this, "MI HORA Y FECH: "+dateTimeUser, Toast.LENGTH_SHORT).show();
             //  LocalDateTime localDateTime = LocalDateTime.parse(dateTimeUser, DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss"));
               //long millis = localDateTime
                 //      .atZone(ZoneId.systemDefault())
                   //    .toInstant().toEpochMilli();

               SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
               try {
                   Calendar cal = Calendar.getInstance();
                   cal.setTime(sdf.parse(dateTimeUser));
                //   Toast.makeText(PacienteEstadoCitaActivity.this, "mi fecha y hORA  es:"+cal.getTimeInMillis(), Toast.LENGTH_SHORT).show();

                   Long  Alertime =  cal.getTimeInMillis() - System.currentTimeMillis();
               //    Toast.makeText(PacienteEstadoCitaActivity.this, "mi fecha y hORA  es:"+Alertime, Toast.LENGTH_LONG).show();

               int random =(int) (Math.random()*50+1);
                   Data data = guardarData("Happy Smile","Tienes una Cita Aprobada para el dia de hoy",random);
                   WorkManagerNoti.guardarNoti(Alertime,data,"tag1");
                   Toast.makeText(PacienteEstadoCitaActivity.this, "Notificacion Guardada con exito", Toast.LENGTH_SHORT).show();
               } catch (ParseException e) {
                   e.printStackTrace();
               }

                notificar.setVisibility(View.INVISIBLE);
                noNotificar.setVisibility(View.VISIBLE);
           }
       });
    }

    public void mostrarDialogo()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(PacienteEstadoCitaActivity.this);
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_twoop,null);
        builder.setView(view);
        final AlertDialog dialog = builder.create();
        dialog.show();
        TextView txt = view.findViewById(R.id.text_dialog);
        txt.setText("¿Estas seguro que deseas Cancelar la Cita?");
        Button btnReintentar = view.findViewById(R.id.btnReintentar);
        btnReintentar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cancelarCita();
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
    public void getCita()
    {
        Call<Cita> citaCall = ApiClient.getService().getcitaEstat(idPacienteCita);
        citaCall.enqueue(new Callback<Cita>() {
            @Override
            public void onResponse(Call<Cita> call, Response<Cita> response) {
                if (response.isSuccessful())
                {
                    String fechapropuesta, hora, pacienteA, pacienteN, doctorN, servicio, estado;
                        int estadoid;
                    Cita cita = response.body();
                    fechapropuesta = response.body().getFechaPropuesta();
                    CitaID = response.body().getId();
                    hora = response.body().getHoraPropuesta();
                    pacienteN = response.body().getNombrePaciente();
                   pacienteA = response.body().getApellidoPaciente();
                        doctorN = cita.getDoctorNombre();
                        servicio = response.body().getServicio();
                        estado = response.body().getEstadoCita();
                       estadoid = response.body().getEstado_citas_id();

                        hora = cita.getHoraPropuesta();
                        pacienteN = cita.getNombrePaciente();
                        pacienteA = cita.getApellidoPaciente();
                        doctorN = cita.getDoctorNombre();
                        servicio = cita.getServicio();
                        estado = cita.getEstadoCita();
                        estadoid = cita.getEstado_citas_id();
                        //Guardamos el estado en las shared
                    SharedPreferences pref = getApplicationContext().getSharedPreferences("myPrefs",MODE_PRIVATE);
                    SharedPreferences.Editor editor = pref.edit();
                    editor.putInt("Estado",estadoid);
                    editor.commit();

                        nombres.setText(pacienteN +" "+ pacienteA);
                        tiempo.setText(fechapropuesta + "-" + hora);
                        service.setText(servicio);
                        datosDoctor.setText(doctorN);
                        estadoCita.setText(estado);
                        Fecha = fechapropuesta;
                        Hora_Estado = hora;

                        if (estadoid == 1) {
                            estadoCita.setTextColor(getResources().getColor(R.color.NA_color,getResources().newTheme()));
                        } else if (estadoid == 2) {
                            estadoCita.setTextColor(getResources().getColor(R.color.NF_color,getResources().newTheme()));
                            datosDoctor.setVisibility(View.VISIBLE);
                            message.setVisibility(View.VISIBLE);
                            message.setText("Hola, Le Agende esta fecha, puesto que los otros dias, no se posee disponibilidad de horario, esperamos su comprension, Gracias.");
                        } else if (estadoid == 3) {
                            estadoCita.setTextColor(getResources().getColor(R.color.Rev_color,getResources().newTheme()));
                            message.setVisibility(View.VISIBLE);
                            message.setText("Hola, Actualmente su cita se encuentra en revision, esperamos su comprension, Gracias.");

                        } else if (estadoid == 4) {
                            estadoCita.setTextColor(getResources().getColor(R.color.Ap_color,getResources().newTheme()));
                            datosDoctor.setVisibility(View.VISIBLE);
                            message.setVisibility(View.VISIBLE);
                            message.setText("Hola, Su cita, a sido aprobada con exito!!!.");
                            notificar.setVisibility(View.VISIBLE);
                        }
                        else if (estadoid== 5)
                        {
                            estadoCita.setTextColor(getResources().getColor(R.color.Canc_color,getResources().newTheme()));
                            datosDoctor.setVisibility(View.VISIBLE);
                            message.setVisibility(View.VISIBLE);
                            message.setText("Ha cancelado su cita!!!.");
                        }
                        else if (estadoid==6)
                        {
                            estadoCita.setTextColor(getResources().getColor(R.color.Ap_color,getResources().newTheme()));
                            datosDoctor.setVisibility(View.VISIBLE);
                            message.setVisibility(View.VISIBLE);
                            message.setText("La cita se ha finalizado con exito, Gracias por confiar en nuestro servicio!!!.");
                        }
                }
            }

            @Override
            public void onFailure(Call<Cita> call, Throwable t) {
                String message = t.getLocalizedMessage();
                Toast.makeText(PacienteEstadoCitaActivity.this, message, Toast.LENGTH_LONG).show();
            }
        });
    }
        private void cancelarCita()
        {
            Toast.makeText(PacienteEstadoCitaActivity.this, "Mi id de cita es "+CitaID, Toast.LENGTH_SHORT).show();
            CancelRequest cancelRequest = new CancelRequest(5); //siempre va ir 5 por defecto, porque es el id de cancelacion
            Call<CancelRequest> cancelRequestCall = ApiClient.getService().cancCita(CitaID,cancelRequest);
            cancelRequestCall.enqueue(new Callback<CancelRequest>() {
                @Override
                public void onResponse(Call<CancelRequest> call, Response<CancelRequest> response) {
                    if (response.isSuccessful())
                    {
                        mostrarSnackbarCorrect();
                    }
                    else
                    {
                        mostrarSnackbarAlgosaliomal();
                    }
                }

                @Override
                public void onFailure(Call<CancelRequest> call, Throwable t) {
                    String message = t.getLocalizedMessage();
                    Toast.makeText(PacienteEstadoCitaActivity.this,message, Toast.LENGTH_LONG).show();
                }
            });


        }

    public  void mostrarSnackbarAlgosaliomal()
    {
        Snackbar snackbar = Snackbar.make(v,"Cita no guardada, Algo salio mal!!!",Snackbar.LENGTH_LONG);
        snackbar.show();
    }

    public  void mostrarSnackbarCorrect()
    {
        Snackbar snackbar = Snackbar.make(v,"Cita Cancelada con Exito, Recarga La Pagina Para ver mas informacion!!!",Snackbar.LENGTH_LONG);
        snackbar.show();
    }

    private void EliminarNoti(String tag)
    {
        WorkManager.getInstance(this).cancelAllWorkByTag(tag);
        Toast.makeText(this, "Ha cancelado la notificacion de su Cita", Toast.LENGTH_SHORT).show();
        notificar.setVisibility(View.VISIBLE);
        noNotificar.setVisibility(View.INVISIBLE);
    }

    private String generateKey()
    {
        return UUID.randomUUID().toString();
    }
    private Data  guardarData(String titulo, String detalle, int id_noti)
    {
        return new Data.Builder()
                .putString("titulo",titulo)
                .putString("detalle",detalle)
                .putInt("Idnoti",id_noti)
                .build();
    }

}
package com.happysmile.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

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

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PacienteEstadoCitaActivity extends AppCompatActivity {

    EditText nombres, tiempo, service, datosDoctor, message;
    int CitaID;
    View v;
    TextView estadoCita;
    SwipeRefreshLayout refreshLayout;
    Button cancelarButton;
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

}
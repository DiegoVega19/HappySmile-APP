package com.happysmile.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

import com.happysmile.myapplication.Adapters.SeguimientoAdapter;
import com.happysmile.myapplication.Api.ApiClient;
import com.happysmile.myapplication.Model.Seguimiento;
import com.happysmile.myapplication.Model.SeguimientoResponse;


import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PacienteTratamientoSeguimientoActivity extends AppCompatActivity {

    SwipeRefreshLayout refresh;
    int idPacienteCita;
    SeguimientoAdapter adapter;
    private RecyclerView recyclerView;
    private Seguimiento seguimiento;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paciente_tratamiento_seguimiento);
        refresh = findViewById(R.id.refreshL);
        inicializarVistas();
        refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Toast.makeText(PacienteTratamientoSeguimientoActivity.this, "Actualizado!!", Toast.LENGTH_SHORT).show();
                getSeguimientos();
                refresh.setRefreshing(false);
            }
        });

    }

    private void inicializarVistas() {
        recyclerView = findViewById(R.id.RecyclerSeguimientos);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.smoothScrollToPosition(0);
        getSeguimientos();
    }

    private  void  getSeguimientos()
    {
        //Ahora obtengo datos en shared
        SharedPreferences pref = getApplicationContext().getSharedPreferences("myPrefs", MODE_PRIVATE);
        idPacienteCita = pref.getInt("idPaciente", 0);
        Call<SeguimientoResponse> seguimientoCall = ApiClient.getService().getSeg(idPacienteCita);
        seguimientoCall.enqueue(new Callback<SeguimientoResponse>() {
            @Override
            public void onResponse(Call<SeguimientoResponse> call, Response<SeguimientoResponse> response) {
               if (response.isSuccessful())
               {
                   List<Seguimiento> seguimientos = response.body().getSeguimiento();
                 adapter = new SeguimientoAdapter(getApplicationContext(),seguimientos);
                   recyclerView.setAdapter(adapter);
                  recyclerView.smoothScrollToPosition(0);
                  adapter.notifyDataSetChanged();
               }

            }

            @Override
            public void onFailure(Call<SeguimientoResponse> call, Throwable t) {
                String message = t.getLocalizedMessage();
                Toast.makeText(PacienteTratamientoSeguimientoActivity.this, message, Toast.LENGTH_LONG).show();
            }
        });
    }
}
package com.happysmile.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.SearchView;
import android.widget.Toast;

import com.happysmile.myapplication.Adapters.CitaDoctorAdapter;
import com.happysmile.myapplication.Api.ApiClient;
import com.happysmile.myapplication.Api.ApiService;
import com.happysmile.myapplication.Model.DoctorCita;
import com.happysmile.myapplication.Model.DoctorCitaResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CitasActivity extends AppCompatActivity {

    SwipeRefreshLayout refresh;
    int idDoctor;
    SearchView searchView;
    CitaDoctorAdapter adapter;
    private RecyclerView recyclerView;
    private DoctorCita doctorCita;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_citas);
        refresh = findViewById(R.id.DrefreshE);
        inicializarVistas();
        refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Toast.makeText(CitasActivity.this, "Actualizado!!", Toast.LENGTH_SHORT).show();
                getCitas();
                refresh.setRefreshing(false);
            }
        });
    }

    private void inicializarVistas() {
        recyclerView = findViewById(R.id.RecyclerCitasDoc);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.smoothScrollToPosition(0);
        searchView = findViewById(R.id.searchViewCitasDoc);
        getCitas();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                adapter.getFilter().filter(s);
                return true;
            }
        });
    }

    private void getCitas() {
        //Ahora obtengo datos en shared
        SharedPreferences pref = getApplicationContext().getSharedPreferences("myPrefs", MODE_PRIVATE);
        idDoctor = pref.getInt("idDoctor", 0);
        Call<DoctorCitaResponse> doctorCitaResponseCall = ApiClient.getService().getDoctorCitas(idDoctor);
        doctorCitaResponseCall.enqueue(new Callback<DoctorCitaResponse>() {
            @Override
            public void onResponse(Call<DoctorCitaResponse> call, Response<DoctorCitaResponse> response) {
                List<DoctorCita> doctorCitas = response.body().getCita();
                adapter = new CitaDoctorAdapter(getApplicationContext(), doctorCitas);
                recyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
                recyclerView.smoothScrollToPosition(0);
            }

            @Override
            public void onFailure(Call<DoctorCitaResponse> call, Throwable t) {
                String message = t.getLocalizedMessage();
                Toast.makeText(CitasActivity.this, message, Toast.LENGTH_LONG).show();
            }
        });
    }
}
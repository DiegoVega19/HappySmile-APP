package com.happysmile.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Bundle;
import android.widget.SearchView;
import android.widget.Toast;

import com.happysmile.myapplication.Adapters.CitaDoctorAdapter;
import com.happysmile.myapplication.Adapters.ExpedientePrincipalAdapter;
import com.happysmile.myapplication.Api.ApiClient;
import com.happysmile.myapplication.Model.DoctorCita;
import com.happysmile.myapplication.Model.Expediente;
import com.happysmile.myapplication.Model.ExpedienteResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ExpedientePrincipalActivity extends AppCompatActivity {

    SwipeRefreshLayout refresh;
    SearchView searchView;
    ExpedientePrincipalAdapter adapter;
    private RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expediente_principal);
        refresh = findViewById(R.id.expRefresh);
            inicializarVistas();
        refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Toast.makeText(ExpedientePrincipalActivity.this, "Actualizado!!", Toast.LENGTH_SHORT).show();
                getExpedientes();
                refresh.setRefreshing(false);
            }
        });
    }
    private void inicializarVistas() {
        recyclerView = findViewById(R.id.RecyclerDocExp);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.smoothScrollToPosition(0);
        searchView = findViewById(R.id.searchViewEp);
        getExpedientes();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                adapter.getFilter().filter(s);
                return  true;
            }
        });
    }

    private void getExpedientes(){
    Call<ExpedienteResponse> expedienteResponseCall = ApiClient.getService().getExp();
    expedienteResponseCall.enqueue(new Callback<ExpedienteResponse>() {
        @Override
        public void onResponse(Call<ExpedienteResponse> call, Response<ExpedienteResponse> response) {
            if (response.isSuccessful())
            {
                List<Expediente> expedientes = response.body().getExpediente();
                adapter = new ExpedientePrincipalAdapter(getApplicationContext(),expedientes);
                recyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
                recyclerView.smoothScrollToPosition(0);
            }
        }

        @Override
        public void onFailure(Call<ExpedienteResponse> call, Throwable t) {
            String message = t.getLocalizedMessage();
            Toast.makeText(ExpedientePrincipalActivity.this, message, Toast.LENGTH_LONG).show();
        }
    });

    }
}
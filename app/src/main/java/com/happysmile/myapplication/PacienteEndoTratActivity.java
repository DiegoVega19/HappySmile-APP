package com.happysmile.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.SearchView;
import android.widget.Toast;

import com.happysmile.myapplication.Adapters.EndodonciaAdapter;
import com.happysmile.myapplication.Adapters.SeguimientoAdapter;
import com.happysmile.myapplication.Api.ApiClient;
import com.happysmile.myapplication.Api.ApiService;
import com.happysmile.myapplication.Model.Endodoncia;
import com.happysmile.myapplication.Model.EndodonciaResponse;
import com.happysmile.myapplication.Model.Seguimiento;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PacienteEndoTratActivity extends AppCompatActivity {

    SwipeRefreshLayout refresh;
    int idPacienteCita;
    SearchView searchView;
    List<Endodoncia> endodoncias;
    EndodonciaAdapter adapter;

    private RecyclerView recyclerView;
    private Endodoncia endodoncia;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paciente_endo_trat);
        refresh = findViewById(R.id.refreshE);
        inicializarVistas();
        refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Toast.makeText(PacienteEndoTratActivity.this, "Actualizado!!", Toast.LENGTH_SHORT).show();
                getEndodoncias();
                refresh.setRefreshing(false);
            }
        });
    }

    private void inicializarVistas() {
        recyclerView = findViewById(R.id.RecyclerEndodoncias);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.smoothScrollToPosition(0);
        searchView = findViewById(R.id.searchViewEndoTrat);
        getEndodoncias();
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

    private void getEndodoncias() {
        //Ahora obtengo datos en shared
        SharedPreferences pref = getApplicationContext().getSharedPreferences("myPrefs", MODE_PRIVATE);
        idPacienteCita = pref.getInt("idPaciente", 0);
        Call<EndodonciaResponse> endodonciaResponseCall = ApiClient.getService().getEndo(idPacienteCita);
        endodonciaResponseCall.enqueue(new Callback<EndodonciaResponse>() {
            @Override
            public void onResponse(Call<EndodonciaResponse> call, Response<EndodonciaResponse> response) {
                if (response.isSuccessful())
                {
                   endodoncias = response.body().getEndodoncia();
                    adapter = new EndodonciaAdapter(getApplicationContext(),endodoncias);
                  //  recyclerView.setAdapter(new EndodonciaAdapter(getApplicationContext(),endodoncias));
                    recyclerView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                    recyclerView.smoothScrollToPosition(0);
                }
            }

            @Override
            public void onFailure(Call<EndodonciaResponse> call, Throwable t) {
                String message = t.getLocalizedMessage();
                Toast.makeText(PacienteEndoTratActivity.this, message, Toast.LENGTH_LONG).show();
            }
        });
    }
}
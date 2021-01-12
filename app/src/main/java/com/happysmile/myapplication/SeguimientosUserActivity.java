package com.happysmile.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Bundle;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.happysmile.myapplication.Adapters.ExpedientePrincipalAdapter;
import com.happysmile.myapplication.Adapters.SegByUserAdapter;
import com.happysmile.myapplication.Api.ApiClient;
import com.happysmile.myapplication.Model.Seguimiento;
import com.happysmile.myapplication.Model.SeguimientoResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SeguimientosUserActivity extends AppCompatActivity {

    SwipeRefreshLayout refresh;
    SearchView searchView;
    int IdRecibido;
    SegByUserAdapter adapter;
    private RecyclerView recyclerView;
    TextView titulo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seguimientos_user);
        refresh = findViewById(R.id.userSegRefresh);
        titulo = findViewById(R.id.tituloUserSeg);
        String nombreRecibido = getIntent().getExtras().getString("nombre");
        titulo.setText("Seguimientos de "+nombreRecibido);
        inicializarVistas();
        refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Toast.makeText(SeguimientosUserActivity.this, "Actualizado!!", Toast.LENGTH_SHORT).show();
                getSeguimientosUser();
                refresh.setRefreshing(false);
            }
        });
    }

    private void inicializarVistas() {
        recyclerView = findViewById(R.id.RecyclerUserSeg);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.smoothScrollToPosition(0);
        searchView = findViewById(R.id.searchViewBySeg);
        IdRecibido = getIntent().getExtras().getInt("idPaciente");
       // Toast.makeText(this, "Mi id recibido es: "+IdRecibido, Toast.LENGTH_SHORT).show();
        getSeguimientosUser();
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

    private void getSeguimientosUser()
    {
        Call<SeguimientoResponse> seguimientoResponseCall = ApiClient.getService().getSegByUser(IdRecibido);
        seguimientoResponseCall.enqueue(new Callback<SeguimientoResponse>() {
            @Override
            public void onResponse(Call<SeguimientoResponse> call, Response<SeguimientoResponse> response) {
                if (response.isSuccessful())
                {
                    List<Seguimiento> seguimientos = response.body().getSeguimiento();
                    adapter = new SegByUserAdapter(getApplicationContext(),seguimientos);
                    recyclerView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                    recyclerView.smoothScrollToPosition(0);

                }
            }

            @Override
            public void onFailure(Call<SeguimientoResponse> call, Throwable t) {
                String message = t.getLocalizedMessage();
                Toast.makeText(SeguimientosUserActivity.this, message, Toast.LENGTH_LONG).show();
            }
        });

    }
}
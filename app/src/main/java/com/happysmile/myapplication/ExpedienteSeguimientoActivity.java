package com.happysmile.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Bundle;
import android.widget.SearchView;
import android.widget.Toast;

import com.happysmile.myapplication.Adapters.ExpedientePrincipalAdapter;
import com.happysmile.myapplication.Adapters.UserSeguimientoAdapter;
import com.happysmile.myapplication.Api.ApiClient;
import com.happysmile.myapplication.Model.Seguimiento;
import com.happysmile.myapplication.Model.SeguimientoResponse;
import com.happysmile.myapplication.Model.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ExpedienteSeguimientoActivity extends AppCompatActivity {

    SwipeRefreshLayout refresh;
    SearchView searchView;
    UserSeguimientoAdapter adapter;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expediente_seguimiento);
        refresh = findViewById(R.id.segRefresh);
        inicializarVistas();
        refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Toast.makeText(ExpedienteSeguimientoActivity.this, "Actualizado!!", Toast.LENGTH_SHORT).show();
                getSeguimientos();
                refresh.setRefreshing(false);
            }
        });
    }

    private void inicializarVistas() {
        recyclerView = findViewById(R.id.RecyclerDocSeg);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.smoothScrollToPosition(0);
        searchView = findViewById(R.id.searchViewDocSeg);
        getSeguimientos();
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

    private void getSeguimientos() {
        Call<SeguimientoResponse> seguimientoResponseCall = ApiClient.getService().getDocSeg();
        seguimientoResponseCall.enqueue(new Callback<SeguimientoResponse>() {
            @Override
            public void onResponse(Call<SeguimientoResponse> call, Response<SeguimientoResponse> response) {
                if (response.isSuccessful()) {
                    List<Seguimiento> seguimientos = response.body().getSeguimiento();
                    adapter = new UserSeguimientoAdapter(getApplicationContext(), seguimientos);
                    recyclerView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                    recyclerView.smoothScrollToPosition(0);
                }
            }

            @Override
            public void onFailure(Call<SeguimientoResponse> call, Throwable t) {
                String message = t.getLocalizedMessage();
                Toast.makeText(ExpedienteSeguimientoActivity.this, message, Toast.LENGTH_LONG).show();
            }
        });

    }
}
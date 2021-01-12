package com.happysmile.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Bundle;
import android.widget.SearchView;
import android.widget.Toast;

import com.happysmile.myapplication.Adapters.UserEndoAdapter;
import com.happysmile.myapplication.Adapters.UserSeguimientoAdapter;
import com.happysmile.myapplication.Api.ApiClient;
import com.happysmile.myapplication.Model.Endodoncia;
import com.happysmile.myapplication.Model.EndodonciaResponse;
import com.happysmile.myapplication.Model.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ExpedienteEndodonciaActivity extends AppCompatActivity {

    SwipeRefreshLayout refresh;
    UserEndoAdapter adapter;
    SearchView searchView;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expediente_endodoncia);
        refresh = findViewById(R.id.endoRefresh);
        inicializarVistas();
        refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Toast.makeText(ExpedienteEndodonciaActivity.this, "Actualizado!!", Toast.LENGTH_SHORT).show();
                getEndo();
                refresh.setRefreshing(false);
            }
        });
    }

    private void inicializarVistas() {
        recyclerView = findViewById(R.id.RecyclerDocEndo);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.smoothScrollToPosition(0);
        searchView = findViewById(R.id.searchViewUserEndo);
        getEndo();
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

    private void getEndo() {
        Call<EndodonciaResponse> endodonciaResponseCall = ApiClient.getService().getDocEndo();
        endodonciaResponseCall.enqueue(new Callback<EndodonciaResponse>() {
            @Override
            public void onResponse(Call<EndodonciaResponse> call, Response<EndodonciaResponse> response) {
                List<Endodoncia> endodoncias = response.body().getEndodoncia();
                adapter = new UserEndoAdapter(getApplicationContext(), endodoncias);
                recyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
                recyclerView.smoothScrollToPosition(0);
            }

            @Override
            public void onFailure(Call<EndodonciaResponse> call, Throwable t) {
                String message = t.getLocalizedMessage();
                Toast.makeText(ExpedienteEndodonciaActivity.this, message, Toast.LENGTH_LONG).show();
            }
        });
    }
}
package com.happysmile.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.happysmile.myapplication.Adapters.EndoByUserAdapter;
import com.happysmile.myapplication.Adapters.SegByUserAdapter;
import com.happysmile.myapplication.Api.ApiClient;
import com.happysmile.myapplication.Model.Endodoncia;
import com.happysmile.myapplication.Model.EndodonciaResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EndodonciasUserActivity extends AppCompatActivity {

    SwipeRefreshLayout refresh;
    int IdRecibido;
    EndoByUserAdapter adapter;
    private RecyclerView recyclerView;
    TextView titulo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_endodoncias_user);
        refresh = findViewById(R.id.userByEndoRefresh);
        titulo = findViewById(R.id.TituloUserByEndo);
        String nombreRecibido = getIntent().getExtras().getString("Endonombre");
        titulo.setText("Endodoncias de "+nombreRecibido);
        inicializarVistas();
        refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Toast.makeText(EndodonciasUserActivity.this, "Actualizado!!", Toast.LENGTH_SHORT).show();
                getEndodonciasUser();
                refresh.setRefreshing(false);
            }
        });
    }

    private void inicializarVistas() {
        recyclerView = findViewById(R.id.RecycleruserByEndo);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.smoothScrollToPosition(0);
        IdRecibido = getIntent().getExtras().getInt("EndoidPaciente");
        Toast.makeText(this, "Mi id recibido es: "+IdRecibido, Toast.LENGTH_SHORT).show();
        getEndodonciasUser();
    }

    public void getEndodonciasUser()
    {
        Call<EndodonciaResponse> endodonciaResponseCall = ApiClient.getService().getEndoByUser(IdRecibido);
        endodonciaResponseCall.enqueue(new Callback<EndodonciaResponse>() {
            @Override
            public void onResponse(Call<EndodonciaResponse> call, Response<EndodonciaResponse> response) {
                    if (response.isSuccessful())
                    {
                        List<Endodoncia> endodoncias = response.body().getEndodoncia();
                        adapter = new EndoByUserAdapter(getApplicationContext(), endodoncias);
                        recyclerView.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
                        recyclerView.smoothScrollToPosition(0);
                    }
            }

            @Override
            public void onFailure(Call<EndodonciaResponse> call, Throwable t) {
                String message = t.getLocalizedMessage();
                Toast.makeText(EndodonciasUserActivity.this, message, Toast.LENGTH_LONG).show();
            }
        });
    }
}
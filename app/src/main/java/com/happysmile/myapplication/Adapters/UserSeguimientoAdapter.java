package com.happysmile.myapplication.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.happysmile.myapplication.Model.Seguimiento;
import com.happysmile.myapplication.Model.User;
import com.happysmile.myapplication.PacienteTraSeguiDetalleActivity;
import com.happysmile.myapplication.R;
import com.happysmile.myapplication.SeguimientosUserActivity;

import java.util.List;

public class UserSeguimientoAdapter extends RecyclerView.Adapter<UserSeguimientoAdapter.ViewHolder> {
    private List<Seguimiento> seguimientoList;
    private Context context;

    public UserSeguimientoAdapter(Context ApplicationContext, List<Seguimiento> seguimientos) {
        this.context = ApplicationContext;
        this.seguimientoList = seguimientos;
    }

    @NonNull
    @Override
    public UserSeguimientoAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_citas, parent, false);
        return new UserSeguimientoAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserSeguimientoAdapter.ViewHolder holder, int position) {
        holder.nombreText.setText(seguimientoList.get(position).getNombre() + " " + seguimientoList.get(position).getApellido());
        holder.codigoText.setText("Codigo de Paciente: " + seguimientoList.get(position).getPasiente_id());
        holder.imagenPredefinida.setImageResource(R.drawable.card);
    }

    @Override
    public int getItemCount() {
        return seguimientoList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView nombreText, codigoText;
        private ImageView imagenPredefinida;

        public ViewHolder(View view) {
            super(view);
            nombreText = (TextView) view.findViewById(R.id.tituloCard);
            codigoText = (TextView) view.findViewById(R.id.descripcionCard);
            imagenPredefinida = (ImageView) view.findViewById(R.id.imagenCard);
            //Evento onclick
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int pos = getAdapterPosition();
                    if (pos!= RecyclerView.NO_POSITION)
                    {
                        Seguimiento seguimientoClickedItem = seguimientoList.get(pos);
                        Intent i = new Intent(context, SeguimientosUserActivity.class);
                        i.putExtra("idPaciente", seguimientoList.get(pos).getPasiente_id());
                        i.putExtra("nombre",seguimientoList.get(pos).getNombre());
                        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(i);
                    }
                }
            });
        }

    }
}

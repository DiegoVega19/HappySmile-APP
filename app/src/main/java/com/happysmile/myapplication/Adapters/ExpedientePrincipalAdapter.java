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

import com.happysmile.myapplication.DoctorExpDetalleActivity;
import com.happysmile.myapplication.Model.Endodoncia;
import com.happysmile.myapplication.Model.Expediente;
import com.happysmile.myapplication.PacienteTraSeguiDetalleActivity;
import com.happysmile.myapplication.R;

import java.util.List;

public class ExpedientePrincipalAdapter extends RecyclerView.Adapter<ExpedientePrincipalAdapter.ViewHolder> {
    private List<Expediente> expedientes;
    private Context context;

    public ExpedientePrincipalAdapter(Context applicacionContext, List<Expediente> expedienteList1)
    {
        this.context = applicacionContext;
        this.expedientes = expedienteList1;
    }

    @NonNull
    @Override
    public ExpedientePrincipalAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_citas,parent,false);
        return new ExpedientePrincipalAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ExpedientePrincipalAdapter.ViewHolder holder, int position) {
        holder.nombreText.setText(expedientes.get(position).getNombre()+" "+expedientes.get(position).getApellido());
        holder.fechaText.setText(expedientes.get(position).getFecha());
        holder.imagenPredefinida.setImageResource(R.drawable.card);
    }

    @Override
    public int getItemCount() {
        return expedientes.size();
    }

    public  class ViewHolder extends  RecyclerView.ViewHolder{
        private TextView nombreText, fechaText;
        private ImageView imagenPredefinida;
        public ViewHolder(View view) {
            super(view);
            nombreText = (TextView) view.findViewById(R.id.tituloCard);
            fechaText = (TextView) view.findViewById(R.id.descripcionCard);
            imagenPredefinida = (ImageView) view.findViewById(R.id.imagenCard);
            //Evento onclick
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int pos = getAdapterPosition();
                    if (pos!= RecyclerView.NO_POSITION)
                    {
                        Expediente expedienteclickedItem = expedientes.get(pos);
                        Intent i = new Intent(context, DoctorExpDetalleActivity.class);
                        i.putExtra("idExp", expedientes.get(pos).getId());
                        i.putExtra("nombre",expedientes.get(pos).getNombre());
                        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(i);
                    }
                    }
            });
        }
    }
}

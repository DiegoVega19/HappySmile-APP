package com.happysmile.myapplication.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.happysmile.myapplication.DetalleCitaActivity;
import com.happysmile.myapplication.Model.DoctorCita;
import com.happysmile.myapplication.Model.Endodoncia;
import com.happysmile.myapplication.PacienteEndoTratDetalleActivity;
import com.happysmile.myapplication.R;

import java.util.List;

public class CitaDoctorAdapter extends RecyclerView.Adapter<CitaDoctorAdapter.ViewHolder> {
    private List<DoctorCita> doctorCitas;
    private Context context;

    public CitaDoctorAdapter(Context applicacionContext, List<DoctorCita> doctorCitaList)
    {
        this.context = applicacionContext;
        this.doctorCitas = doctorCitaList;
    }

    @NonNull
    @Override
    public CitaDoctorAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_citas,parent,false);
        return new CitaDoctorAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CitaDoctorAdapter.ViewHolder holder, int position) {
        holder.nombreText.setText(doctorCitas.get(position).getNombre());
        holder.datosTiempo.setText(doctorCitas.get(position).getFechaPropuesta()+"-"+doctorCitas.get(position).getHoraPropuesta());
        holder.imagenPredefinida.setImageResource(R.drawable.card);
    }

    @Override
    public int getItemCount() {
        return doctorCitas.size();
    }

    public  class ViewHolder extends  RecyclerView.ViewHolder{
        private TextView nombreText, datosTiempo;
        private ImageView imagenPredefinida;
        public ViewHolder(View view){
            super(view);
            nombreText = (TextView) view.findViewById(R.id.tituloCard);
            datosTiempo = (TextView) view.findViewById(R.id.descripcionCard);
            imagenPredefinida = (ImageView) view.findViewById(R.id.imagenCard);
            //Cuando se le da click al item
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int pos = getAdapterPosition();
                    if (pos!= RecyclerView.NO_POSITION);
                    {
                        DoctorCita doctorCitaClickedItem = doctorCitas.get(pos);
                        Intent i = new Intent(context, DetalleCitaActivity.class);
                        i.putExtra("id", doctorCitas.get(pos).getId());
                        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(i);
                    }
                }
            });
        }
    }
}

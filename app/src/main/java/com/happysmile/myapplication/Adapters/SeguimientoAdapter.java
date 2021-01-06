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

import com.happysmile.myapplication.Model.Seguimiento;
import com.happysmile.myapplication.PacienteTraSeguiDetalleActivity;
import com.happysmile.myapplication.R;

import java.util.List;

public class SeguimientoAdapter extends RecyclerView.Adapter<SeguimientoAdapter.ViewHolder> {

    private List<Seguimiento> seguimientos;
    private Context context;

    public SeguimientoAdapter(Context aplicationContext, List<Seguimiento> seguimientoArrayList) {
        this.context = aplicationContext;
        this.seguimientos = seguimientoArrayList;
    }

    @NonNull
    @Override
    public SeguimientoAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_citas, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SeguimientoAdapter.ViewHolder holder, int position) {

        holder.idText.setText("Codigo de Seguimiento: "+seguimientos.get(position).getId());
        holder.FechaText.setText(seguimientos.get(position).getFecha());
        holder.imagenPredefinida.setImageResource(R.drawable.card_seg);
    }

    @Override
    public int getItemCount() {
        return seguimientos.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView idText, FechaText;
        private ImageView imagenPredefinida;

        public ViewHolder(View view) {
            super(view);
            FechaText = (TextView) view.findViewById(R.id.tituloCard);
            idText = (TextView) view.findViewById(R.id.descripcionCard);
            imagenPredefinida = (ImageView) view.findViewById(R.id.imagenCard);

            //Cuando el item se de click
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int pos = getAdapterPosition();
                    if (pos!= RecyclerView.NO_POSITION)
                    {
                        Seguimiento seguimientoClickedItem = seguimientos.get(pos);
                        Intent i = new Intent(context, PacienteTraSeguiDetalleActivity.class);
                        i.putExtra("id", seguimientos.get(pos).getId());
                        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(i);
                    }
                }
            });
        }
    }
}

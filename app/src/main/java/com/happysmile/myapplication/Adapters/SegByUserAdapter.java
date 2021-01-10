package com.happysmile.myapplication.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.happysmile.myapplication.DetalleCitaActivity;
import com.happysmile.myapplication.Model.DoctorCita;
import com.happysmile.myapplication.Model.Seguimiento;
import com.happysmile.myapplication.R;
import com.happysmile.myapplication.SeguimientoDetalleActivity;

import java.util.ArrayList;
import java.util.List;

public class SegByUserAdapter extends RecyclerView.Adapter<SegByUserAdapter.ViewHolder> implements Filterable {
   private List<Seguimiento> filterList;
    private List<Seguimiento> seguimientos;
    private Context context;

    public SegByUserAdapter (Context applicationContext, List<Seguimiento> seguimientoList)
    {
        this.context = applicationContext;
        this.seguimientos = seguimientoList;
        this.filterList = seguimientoList;
    }

    @NonNull
    @Override
    public SegByUserAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_citas,parent,false);
        return new SegByUserAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SegByUserAdapter.ViewHolder holder, int position) {
        holder.codigoText.setText("Codigo de seguimiento: "+filterList.get(position).getId());
        holder.fechaText.setText(filterList.get(position).getFecha());
        holder.imagenPredefinida.setImageResource(R.drawable.card);
    }

    @Override
    public int getItemCount() {
        return filterList.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String searchString = charSequence.toString();
                if (searchString.isEmpty()) {

                    filterList = seguimientos;
                }
                else
                {
                    List<Seguimiento> tempFilteredList = new ArrayList<>();
                    for(Seguimiento seguimiento : seguimientos)
                    {
                        //Buscar Por id
                        if(Integer.toString(seguimiento.getId()).toLowerCase().contains(searchString))
                        {
                            tempFilteredList.add(seguimiento);
                        }
                    }
                    filterList = tempFilteredList;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = filterList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                filterList = (List<Seguimiento>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    public  class ViewHolder extends  RecyclerView.ViewHolder{
        private TextView codigoText, fechaText;
        private ImageView imagenPredefinida;
        public ViewHolder(View view){
            super(view);
            codigoText = (TextView) view.findViewById(R.id.tituloCard);
            fechaText = (TextView) view.findViewById(R.id.descripcionCard);
            imagenPredefinida = (ImageView) view.findViewById(R.id.imagenCard);

            //Cuando se le da click al item
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int pos = getAdapterPosition();
                    if (pos!= RecyclerView.NO_POSITION);
                    {
                        Seguimiento seguimientoClickedItem = seguimientos.get(pos);
                        Intent i = new Intent(context, SeguimientoDetalleActivity.class);
                        i.putExtra("idSeg", seguimientos.get(pos).getId());
                        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(i);

                    }
                }
            });
        }
    }
}

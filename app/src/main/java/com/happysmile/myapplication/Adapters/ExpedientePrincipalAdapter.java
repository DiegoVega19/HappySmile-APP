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

import com.happysmile.myapplication.DoctorExpDetalleActivity;
import com.happysmile.myapplication.Model.Endodoncia;
import com.happysmile.myapplication.Model.Expediente;
import com.happysmile.myapplication.PacienteTraSeguiDetalleActivity;
import com.happysmile.myapplication.R;

import java.util.ArrayList;
import java.util.List;

public class ExpedientePrincipalAdapter extends RecyclerView.Adapter<ExpedientePrincipalAdapter.ViewHolder> implements Filterable {
    private List<Expediente> expedientes;
    private List<Expediente> filterList;
    private Context context;

    public ExpedientePrincipalAdapter(Context applicacionContext, List<Expediente> expedienteList1)
    {
        this.context = applicacionContext;
        this.expedientes = expedienteList1;
        this.filterList = expedienteList1;
    }

    @NonNull
    @Override
    public ExpedientePrincipalAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_citas,parent,false);
        return new ExpedientePrincipalAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ExpedientePrincipalAdapter.ViewHolder holder, int position) {
        holder.nombreText.setText(filterList.get(position).getNombre()+" "+filterList.get(position).getApellido());
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

                    filterList = expedientes;
                }
                else
                {
                    List<Expediente> tempFilteredList = new ArrayList<>();
                    for (Expediente expediente :expedientes)
                    {
                        //Buscar Por id
                        if(expediente.getNombre().toLowerCase().contains(searchString))
                        {
                            tempFilteredList.add(expediente);
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
                filterList = (List<Expediente>) filterResults.values;
                notifyDataSetChanged();
            }
        };
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

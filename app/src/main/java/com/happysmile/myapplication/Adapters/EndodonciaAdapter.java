package com.happysmile.myapplication.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.view.menu.MenuView;
import androidx.recyclerview.widget.RecyclerView;

import com.happysmile.myapplication.Model.Endodoncia;
import com.happysmile.myapplication.PacienteEndoTratDetalleActivity;
import com.happysmile.myapplication.R;

import java.util.List;

public class EndodonciaAdapter extends RecyclerView.Adapter<EndodonciaAdapter.ViewHolder>{
    private List<Endodoncia> endodoncias;
    private Context context;

    public EndodonciaAdapter(Context aplicationContext, List<Endodoncia> endodonciaList)
    {
        this.context = aplicationContext;
        this.endodoncias = endodonciaList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_citas,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.idText.setText("Codigo de Endodoncia: "+endodoncias.get(position).getId());
        holder.FechaText.setText(endodoncias.get(position).getFecha());
        holder.imagenPredefinida.setImageResource(R.drawable.card_endo);
    }

    @Override
    public int getItemCount() {
        return endodoncias.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView idText, FechaText;
        private ImageView imagenPredefinida;
        public ViewHolder(View view) {
            super(view);
            FechaText = (TextView) view.findViewById(R.id.tituloCard);
            idText = (TextView) view.findViewById(R.id.descripcionCard);
            imagenPredefinida = (ImageView) view.findViewById(R.id.imagenCard);

            //Cuando se le da click al item
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int pos = getAdapterPosition();
                    if (pos!= RecyclerView.NO_POSITION)
                    {
                        Endodoncia endodonciaClickedItem = endodoncias.get(pos);
                        Intent i = new Intent(context, PacienteEndoTratDetalleActivity.class);
                        i.putExtra("id", endodoncias.get(pos).getId());
                        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(i);
                    }
                }
            });
        }
    }
}
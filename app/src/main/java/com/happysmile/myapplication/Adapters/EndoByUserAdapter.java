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

import com.happysmile.myapplication.EndoDetalleActivity;
import com.happysmile.myapplication.Model.Endodoncia;
import com.happysmile.myapplication.R;

import java.util.ArrayList;
import java.util.List;

public class EndoByUserAdapter extends RecyclerView.Adapter<EndoByUserAdapter.ViewHolder> implements Filterable {

   private List<Endodoncia> filterList;
    private List<Endodoncia> endodoncias;
    private Context context;

    public  EndoByUserAdapter(Context applicacionContext, List<Endodoncia>endodonciaList)
    {
        this.context = applicacionContext;
        this.endodoncias = endodonciaList;
        this.filterList = endodonciaList;
    }

    @NonNull
    @Override
    public EndoByUserAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_citas,parent,false);
        return new EndoByUserAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EndoByUserAdapter.ViewHolder holder, int position) {
        holder.codigoText.setText("Codigo de endodoncia: "+filterList.get(position).getId());
        holder.fechaText.setText(filterList.get(position).getFecha());
        holder.imagenPredefinida.setImageResource(R.drawable.card);
    }

    @Override
    public int getItemCount() {
        return filterList.size();
    }

    @Override
    public Filter getFilter() {
        return  new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String searchString = charSequence.toString();
                if (searchString.isEmpty()) {

                    filterList = endodoncias;
                }
                else
                {
                    List<Endodoncia> tempFilteredList = new ArrayList<>();
                    for (Endodoncia endodoncia :endodoncias)
                    {
                        //Buscar Por id
                        if(Integer.toString(endodoncia.getId()).toLowerCase().contains(searchString))
                        {
                            tempFilteredList.add(endodoncia);
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
                filterList = (List<Endodoncia>) filterResults.values;
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
                        Endodoncia endodonciaClickedItem = endodoncias.get(pos);
                        Intent i = new Intent(context, EndoDetalleActivity.class);
                        i.putExtra("idEndodoncia", endodoncias.get(pos).getId());
                        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(i);
                    }
                }
            });
        }
    }
}

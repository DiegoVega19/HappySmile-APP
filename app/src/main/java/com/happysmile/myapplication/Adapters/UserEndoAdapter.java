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

import com.happysmile.myapplication.EndodonciasUserActivity;
import com.happysmile.myapplication.Model.Endodoncia;
import com.happysmile.myapplication.Model.Seguimiento;
import com.happysmile.myapplication.Model.User;
import com.happysmile.myapplication.R;
import com.happysmile.myapplication.SeguimientosUserActivity;

import java.util.List;

public class UserEndoAdapter extends RecyclerView.Adapter<UserEndoAdapter.ViewHolder> {
    private List<Endodoncia> endodoncias;
    private Context context;

    public UserEndoAdapter(Context ApplicationContext, List<Endodoncia> endodonciaList) {
        this.context = ApplicationContext;
        this.endodoncias = endodonciaList;
    }


    @NonNull
    @Override
    public UserEndoAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_citas, parent, false);
        return new UserEndoAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserEndoAdapter.ViewHolder holder, int position) {
        holder.nombreText.setText(endodoncias.get(position).getNombre() + " " + endodoncias.get(position).getApellido());
        holder.codigoText.setText("Codigo de Paciente: " + endodoncias.get(position).getPasiente_id());
        holder.imagenPredefinida.setImageResource(R.drawable.card);
    }

    @Override
    public int getItemCount() {
        return endodoncias.size();
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
                        Endodoncia endodonciaClickedItem = endodoncias.get(pos);
                        Intent i = new Intent(context, EndodonciasUserActivity.class);
                        i.putExtra("EndoidPaciente", endodoncias.get(pos).getPasiente_id());
                        i.putExtra("Endonombre",endodoncias.get(pos).getNombre());
                        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(i);
                    }
                }
            });
        }
    }
}

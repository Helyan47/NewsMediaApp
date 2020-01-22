package com.example.periodicos;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class AdaptadorPeriodico extends RecyclerView.Adapter<AdaptadorPeriodico.ViewHolder> {

    ArrayList<Periodico> datos;
    IOnLongClick iOnLongClick;

    public void setiOnLongClick(IOnLongClick iOnLongClick) {
        this.iOnLongClick = iOnLongClick;
    }

    public AdaptadorPeriodico(ArrayList<Periodico> datos) {
        this.datos = datos;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.periodico_element, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        String nombre = datos.get(position).getNombre();
        holder.nombre.setText(nombre);
        String tematica = datos.get(position).getTematica();
        holder.tematica.setText(tematica);

        switch (tematica) {
            case "Generalista" :
                Picasso.get().load(R.drawable.news_96px).resize(150, 150).transform(new ImageRoundCorners()).into(holder.imagen);
                break;
            case "Deportes" :
                Picasso.get().load(R.drawable.soccer_ball_96px).resize(150, 150).transform(new ImageRoundCorners()).into(holder.imagen);
                break;
            case "Politica" :
                Picasso.get().load(R.drawable.elections_96px).resize(150, 150).transform(new ImageRoundCorners()).into(holder.imagen);
                break;
        }
    }

    @Override
    public int getItemCount() {
        return datos.size() ;
    }

    public void remove(int position) {

        datos.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, datos.size());

    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnLongClickListener {

        ImageView imagen;
        TextView nombre;
        TextView tematica;

        public ViewHolder(View view) {


            super(view);
            imagen = (ImageView) view.findViewById(R.id.imageView);
            nombre = (TextView) view.findViewById(R.id.tv_nombre);
            tematica = (TextView) view.findViewById(R.id.tv_tematica);
            view.setOnLongClickListener(this);
        }

        @Override
        public boolean onLongClick(View view) {
            if (iOnLongClick != null) {
                iOnLongClick.showMenu(getAdapterPosition(), view);}
            return true;
        }
    }

    public interface IOnLongClick{

        void showMenu(int position, View view);

    }

}

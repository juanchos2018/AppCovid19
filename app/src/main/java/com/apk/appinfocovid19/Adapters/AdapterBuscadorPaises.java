package com.apk.appinfocovid19.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.apk.appinfocovid19.Model.Pais;
import com.apk.appinfocovid19.R;

import java.util.ArrayList;
import java.util.List;

//public class AdapterBuscadorPaises {
public class AdapterBuscadorPaises extends RecyclerView.Adapter<AdapterBuscadorPaises.ViewHolder> {
    private Context context;
    private List<Pais> listPais;

    private EscuchaEventosClick escucha;

    public interface EscuchaEventosClick {
        void onItemClick(ViewHolder holder, int posicion);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView nameText;

        ViewHolder(View itemView) {
            super(itemView);
            nameText = itemView.findViewById(R.id.nameText);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            escucha.onItemClick(this, getAdapterPosition());
        }
    }

    public AdapterBuscadorPaises(List<Pais> listPaises, Context context, EscuchaEventosClick escucha) {
        this.listPais = listPaises;
        this.context = context;
        this.escucha = escucha;
    }

    @Override
    public int getItemCount() {
        return listPais.size();
    }

    @Override
    public AdapterBuscadorPaises.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_search, parent, false);
        return new AdapterBuscadorPaises.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(AdapterBuscadorPaises.ViewHolder holder, int position) {
        Pais obj = listPais.get(position);
        holder.nameText.setText(obj.getNombrePais());
    }

    public void filtrar(ArrayList<Pais> filtroPais) {
        this.listPais = filtroPais;
        notifyDataSetChanged();
    }

}

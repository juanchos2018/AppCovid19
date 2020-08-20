package com.apk.appinfocovid19.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.apk.appinfocovid19.Model.Pais;
import com.apk.appinfocovid19.R;

import java.util.List;

public class AdapterPaises extends RecyclerView.Adapter<AdapterPaises.ViewHolder>   implements View.OnClickListener {

    private Context context;
    private List<Pais> listPais;

    public AdapterPaises(List<Pais> listPaises, Context context) {
        this.listPais = listPaises;
        this.context = context;
    }


    private View.OnClickListener listener;
    public  void setOnClickListener(View.OnClickListener listener)
    {
        this.listener=listener;
    }

    @Override
    public void onClick(View v) {
        if (listener!=null){
            listener.onClick(v);
        }
    }
//    private EscuchaEventosClick escucha;

//    public interface EscuchaEventosClick {
//        void onItemClick(ViewHolder holder, int posicion);
//    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView numberText,confirmCountText,deathCountText,recoveredCountText;
        AppCompatTextView countryName;

        ViewHolder(View itemView) {
            super(itemView);
            numberText = itemView.findViewById(R.id.numberText);
            countryName = itemView.findViewById(R.id.countryName);
            confirmCountText = itemView.findViewById(R.id.confirmCountText);
            deathCountText = itemView.findViewById(R.id.deathCountText);
            recoveredCountText = itemView.findViewById(R.id.recoveredCountText);
        }

//        @Override
//        public void onClick(View v) {
////            escucha.onItemClick(this, getAdapterPosition());
//        }
    }



    @Override
    public int getItemCount() {
        return listPais.size();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_global_case, parent, false);
        v.setOnClickListener(this);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Pais obj = listPais.get(position);
        holder.numberText.setText(String.valueOf(obj.getIdPais()));
        holder.countryName.setText(obj.getNombrePais());
        holder.confirmCountText.setText(String.valueOf(obj.getCasosConfirmados()));
        holder.deathCountText.setText(String.valueOf(obj.getBajasTotales()));
        holder.recoveredCountText.setText(String.valueOf(obj.getCasosRecuperadosTotales()));
    }

}


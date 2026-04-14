package br.edu.unifaj.cc.mobile.logincomnavegacao.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import br.edu.unifaj.cc.mobile.logincomnavegacao.R;
import br.edu.unifaj.cc.mobile.logincomnavegacao.model.entity.BolsaSangue;

public class BolsaSangueAdapter extends RecyclerView.Adapter<BolsaSangueAdapter.BolsaViewHolder> {
    
    private List<BolsaSangue> listaBolsas;
    
    public BolsaSangueAdapter(List<BolsaSangue> listaBolsas) {
        this.listaBolsas = listaBolsas;
    }
    
    @NonNull
    @Override
    public BolsaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
            .inflate(R.layout.item_bolsa_sangue, parent, false);
        return new BolsaViewHolder(view);
    }
    
    @Override
    public void onBindViewHolder(@NonNull BolsaViewHolder holder, int position) {
        BolsaSangue bolsa = listaBolsas.get(position);
        holder.txtCodigo.setText("Código: " + bolsa.getCodigo());
        holder.txtTipoSanguineo.setText("Tipo: " + bolsa.getTipoCompleto());
        holder.txtVolume.setText("Volume: " + bolsa.getVolumeMl() + "ml");
        holder.txtDataColeta.setText("Coleta: " + bolsa.getDataColeta());
        holder.txtValidade.setText("Validade: " + bolsa.getDataValidade());
        holder.txtStatus.setText("Status: " + bolsa.getStatus());
    }
    
    @Override
    public int getItemCount() {
        return listaBolsas.size();
    }
    
    static class BolsaViewHolder extends RecyclerView.ViewHolder {
        TextView txtCodigo;
        TextView txtTipoSanguineo;
        TextView txtVolume;
        TextView txtDataColeta;
        TextView txtValidade;
        TextView txtStatus;
        
        BolsaViewHolder(@NonNull View itemView) {
            super(itemView);
            txtCodigo = itemView.findViewById(R.id.txtCodigo);
            txtTipoSanguineo = itemView.findViewById(R.id.txtTipoSanguineo);
            txtVolume = itemView.findViewById(R.id.txtVolume);
            txtDataColeta = itemView.findViewById(R.id.txtDataColeta);
            txtValidade = itemView.findViewById(R.id.txtValidade);
            txtStatus = itemView.findViewById(R.id.txtStatus);
        }
    }
}
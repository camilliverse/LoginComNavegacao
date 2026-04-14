package br.edu.unifaj.cc.mobile.logincomnavegacao.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import br.edu.unifaj.cc.mobile.logincomnavegacao.R;
import br.edu.unifaj.cc.mobile.logincomnavegacao.model.entity.Agendamento;
import br.edu.unifaj.cc.mobile.logincomnavegacao.model.enums.StatusAgendamento;

public class AgendamentoAdapter extends RecyclerView.Adapter<AgendamentoAdapter.ViewHolder> {
    
    private final List<Agendamento> agendamentos;
    private final OnAgendamentoClickListener listener;
    
    public interface OnAgendamentoClickListener {
        void onCancelarClick(Agendamento agendamento, int position);
    }
    
    public AgendamentoAdapter(List<Agendamento> agendamentos, OnAgendamentoClickListener listener) {
        this.agendamentos = agendamentos;
        this.listener = listener;
    }
    
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_agendamento, parent, false);
        return new ViewHolder(view);
    }
    
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Agendamento agendamento = agendamentos.get(position);
        
        holder.txtHemocentro.setText(agendamento.getHemocentro().getNome());
        holder.txtEndereco.setText(agendamento.getHemocentro().getEndereco().getEnderecoCompleto());
        holder.txtData.setText(agendamento.getData());
        holder.txtHora.setText(agendamento.getHora());
        holder.txtStatus.setText(agendamento.getStatus().getDescricao());
        
        int statusColor;
        if (agendamento.isPendente()) {
            statusColor = 0xFFFF9800;
        } else if (agendamento.isConfirmado()) {
            statusColor = 0xFF4CAF50;
        } else if (agendamento.isCancelado()) {
            statusColor = 0xFFF44336;
        } else if (agendamento.isRealizado()) {
            statusColor = 0xFF2196F3;
        } else {
            statusColor = 0xFF757575;
        }
        holder.txtStatus.setTextColor(statusColor);
        
        holder.btnCancelar.setVisibility(
            agendamento.isPendente() ? View.VISIBLE : View.GONE
        );
        
        holder.btnCancelar.setOnClickListener(v -> {
            if (listener != null) {
                listener.onCancelarClick(agendamento, position);
            }
        });
    }
    
    @Override
    public int getItemCount() {
        return agendamentos.size();
    }
    
    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtHemocentro;
        TextView txtEndereco;
        TextView txtData;
        TextView txtHora;
        TextView txtStatus;
        Button btnCancelar;
        
        ViewHolder(View itemView) {
            super(itemView);
            txtHemocentro = itemView.findViewById(R.id.txtHemocentro);
            txtEndereco = itemView.findViewById(R.id.txtEndereco);
            txtData = itemView.findViewById(R.id.txtData);
            txtHora = itemView.findViewById(R.id.txtHora);
            txtStatus = itemView.findViewById(R.id.txtStatus);
            btnCancelar = itemView.findViewById(R.id.btnCancelar);
        }
    }
}

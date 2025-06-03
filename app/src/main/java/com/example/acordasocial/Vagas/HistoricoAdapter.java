package com.example.acordasocial.Vagas;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.acordasocial.R;

import java.util.List;

public class HistoricoAdapter extends RecyclerView.Adapter<HistoricoAdapter.HistoricoViewHolder> {

    private List<Vaga> listaVagas;

    public HistoricoAdapter(List<Vaga> listaVagas) {
        this.listaVagas = listaVagas;
    }

    public static class HistoricoViewHolder extends RecyclerView.ViewHolder {
        TextView nomeOng, descricao, local, horario;

        public HistoricoViewHolder(@NonNull View itemView) {
            super(itemView);
            nomeOng = itemView.findViewById(R.id.tvNomeOng);
            descricao = itemView.findViewById(R.id.tvDescricao);
            local = itemView.findViewById(R.id.tvLocal);
            horario = itemView.findViewById(R.id.tvHorario);
        }
    }

    @NonNull
    @Override
    public HistoricoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_historico, parent, false);
        return new HistoricoViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull HistoricoViewHolder holder, int position) {
        Vaga vaga = listaVagas.get(position);
        holder.nomeOng.setText(vaga.getNomeOng());
        holder.descricao.setText(vaga.getDescricao());
        holder.local.setText(vaga.getLocal());
        holder.horario.setText(vaga.getHorario());
    }

    @Override
    public int getItemCount() {
        return listaVagas.size();
    }
}

package com.example.acordasocial.Vagas;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.acordasocial.R;

import java.util.List;

public class VagaAdapter extends RecyclerView.Adapter<VagaAdapter.VagaViewHolder> {

    private List<Vaga> listaVagas;

    public VagaAdapter(List<Vaga> listaVagas) {
        this.listaVagas = listaVagas;
    }

    public static class VagaViewHolder extends RecyclerView.ViewHolder {
        TextView nomeOng, descricao, local, horario;
        Button btnParticipar;

        public VagaViewHolder(@NonNull View itemView) {
            super(itemView);
            nomeOng = itemView.findViewById(R.id.tvNomeOng);
            descricao = itemView.findViewById(R.id.tvDescricao);
            local = itemView.findViewById(R.id.tvLocal);
            horario = itemView.findViewById(R.id.etHorario );
            btnParticipar = itemView.findViewById(R.id.btnParticipar);
        }
    }

    @NonNull
    @Override
    public VagaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_feed, parent, false);
        return new VagaViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull VagaViewHolder holder, int position) {
        Vaga vaga = listaVagas.get(position);
        holder.nomeOng.setText(vaga.getNomeOng());
        holder.descricao.setText(vaga.getDescricao());
        holder.local.setText(vaga.getLocal());


        holder.btnParticipar.setOnClickListener(v -> {
            Intent intent = new Intent(v.getContext(), service_description.class);
            intent.putExtra("id", vaga.getId());
            intent.putExtra("nomeOng", vaga.getNomeOng());
            intent.putExtra("descricao", vaga.getDescricao());
            intent.putExtra("local", vaga.getLocal());
            intent.putExtra("horario", vaga.getHorario());
            v.getContext().startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return listaVagas.size();
    }
}
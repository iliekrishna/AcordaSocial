package com.example.acordasocial;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class VagaAdapter extends RecyclerView.Adapter<VagaAdapter.VagaViewHolder> {

    private List<Vaga> listaVagas;

    public VagaAdapter(List<Vaga> listaVagas) {
        this.listaVagas = listaVagas;
    }

    public static class VagaViewHolder extends RecyclerView.ViewHolder {
        TextView nomeOng;
        TextView descricao;
        TextView local;
        Button btnParticipar;

        public VagaViewHolder(@NonNull View itemView) {
            super(itemView);
            nomeOng = itemView.findViewById(R.id.tvNomeOng);
            descricao = itemView.findViewById(R.id.tvDescricao);
            local = itemView.findViewById(R.id.tvLocal);
            btnParticipar = itemView.findViewById(R.id.btnParticipar);


        }
    }

    @NonNull
    @Override
    public VagaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_feed, parent, false);
        return new VagaViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull VagaViewHolder holder, int position) {
        Vaga vaga = listaVagas.get(position);
        holder.nomeOng.setText(vaga.getNomeOng());
        holder.descricao.setText(vaga.getDescricao());
        holder.local.setText(vaga.getLocal());

        // Opcional: clique no botão "Participar"
        holder.btnParticipar.setOnClickListener(v -> {
            // Aqui você pode colocar uma ação, tipo Toast ou navegar pra outra tela.
            // Exemplo: Toast.makeText(v.getContext(), "Participou!", Toast.LENGTH_SHORT).show();

        });
    }


    @Override
    public int getItemCount() {
        return listaVagas.size();
    }
}

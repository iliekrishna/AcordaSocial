package com.example.acordasocial;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Voluntarios extends AppCompatActivity {

    private ListView listViewVoluntarios;
    private ArrayList<String> listaVoluntarios;
    private ArrayAdapter<String> adapter;

    private String nomeEvento; // 🏷️ Nome do evento recebido da tela anterior

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voluntarios);

        listViewVoluntarios = findViewById(R.id.listViewVoluntarios);
        listaVoluntarios = new ArrayList<>();

        adapter = new ArrayAdapter<>(
                this,
                R.layout.list_item_voluntario,
                R.id.textViewItem,
                listaVoluntarios
        );

        listViewVoluntarios.setAdapter(adapter);

        // 🏷️ Pegando nome do evento vindo da tela anterior
        nomeEvento = getIntent().getStringExtra("nomeOng"); // Corrigir aqui, pegar o nome da ONG
        if (nomeEvento == null) {
            Toast.makeText(this, "Evento não selecionado.", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        recuperarVoluntarios(nomeEvento);
    }

    // ✅ Método para buscar voluntários de um evento específico
    private void recuperarVoluntarios(String nomeEvento) {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("participacoes");

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                listaVoluntarios.clear();

                for (DataSnapshot usuarioSnapshot : snapshot.getChildren()) {
                    String uidUsuario = usuarioSnapshot.getKey();

                    if (usuarioSnapshot.hasChild(nomeEvento)) {
                        // Existe participação neste evento
                        buscarDadosUsuario(uidUsuario);
                    }
                }

                adapter.notifyDataSetChanged();

                if (listaVoluntarios.isEmpty()) {
                    //Toast.makeText(Voluntarios.this, "Nenhum voluntário encontrado.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(Voluntarios.this, "Erro ao buscar dados: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    // ✅ Buscar os dados (nome e email) de um usuário pelo UID
    private void buscarDadosUsuario(String userId) {
        DatabaseReference usuarioRef = FirebaseDatabase.getInstance().getReference("usuarios").child(userId);

        usuarioRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String nome = snapshot.child("nome").getValue(String.class);
                String email = snapshot.child("email").getValue(String.class);

                if (nome != null && email != null) {
                    listaVoluntarios.add("Nome: " + nome + "\nEmail: " + email);
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(Voluntarios.this, "Erro ao buscar usuário: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    // 🔄 Botão de refresh
    public void setRefresh(View view) {
        recuperarVoluntarios(nomeEvento);
    }

    // ⬅️ Botão de voltar
    public void setVoltar(View view) {
        finish();
    }
}

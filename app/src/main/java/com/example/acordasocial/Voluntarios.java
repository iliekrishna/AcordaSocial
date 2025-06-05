package com.example.acordasocial;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.acordasocial.Usuario.historico_servicos;
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

    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voluntarios);

        listViewVoluntarios = findViewById(R.id.listViewVoluntarios);
        listaVoluntarios = new ArrayList<>();

        adapter = new ArrayAdapter<>(
                this,
                R.layout.list_item_voluntario,  // Layout personalizado
                R.id.textViewItem,              // ID do TextView dentro do layout
                listaVoluntarios
        );

        listViewVoluntarios.setAdapter(adapter);

        databaseReference = FirebaseDatabase.getInstance().getReference("usuarios");

    }

    @Override
    protected void onResume() {
        super.onResume();
        recuperarVoluntarios();
    }
    private void recuperarVoluntarios() {
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                listaVoluntarios.clear();
                if (snapshot.exists()) {
                    for (DataSnapshot usuarioSnapshot : snapshot.getChildren()) {
                        String nome = usuarioSnapshot.child("nome").getValue(String.class);
                        String email = usuarioSnapshot.child("email").getValue(String.class);

                        if (nome != null && email != null) {
                            listaVoluntarios.add(nome + " - " + email);
                        }
                    }
                    adapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(Voluntarios.this, "Nenhum voluntário encontrado.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(Voluntarios.this, "Erro ao carregar dados: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void setVoltar(View view){
        finish();
    }
}
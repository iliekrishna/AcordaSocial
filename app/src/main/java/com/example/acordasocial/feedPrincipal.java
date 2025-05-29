package com.example.acordasocial;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class feedPrincipal extends AppCompatActivity {

    private RecyclerView recyclerView;
    private VagaAdapter vagaAdapter;
    private List<Vaga> listaVagas;

    private ImageButton btnperfil;
    private Button btnCriarVaga;
    private DatabaseReference databaseReference;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_feed_principal);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        recyclerView = findViewById(R.id.recyclerViewFeed);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        databaseReference = FirebaseDatabase.getInstance().getReference("vagas");

        listaVagas = new ArrayList<>();
        vagaAdapter = new VagaAdapter(listaVagas);
        recyclerView.setAdapter(vagaAdapter);

        btnCriarVaga = findViewById(R.id.btnCriarVaga);
        btnCriarVaga.setOnClickListener(v -> {
            startActivity(new Intent(feedPrincipal.this, CriarVagaActivity.class));
        });

        btnperfil = findViewById(R.id.btnPerfil);
        btnperfil.setOnClickListener(v -> {
            startActivity(new Intent(feedPrincipal.this, perfilUsuario.class));
        });

        // Carregar vagas em tempo real
        carregarVagasFirebase();
    }

    private void carregarVagasFirebase() {
        databaseReference.addValueEventListener(new com.google.firebase.database.ValueEventListener() {
            @Override
            public void onDataChange(com.google.firebase.database.DataSnapshot snapshot) {
                if (!snapshot.exists()) {
                    Toast.makeText(feedPrincipal.this, "Nenhuma vaga encontrada.", Toast.LENGTH_SHORT).show();
                    return;
                }

                listaVagas.clear();

                for (com.google.firebase.database.DataSnapshot childSnapshot : snapshot.getChildren()) {
                    Vaga vaga = childSnapshot.getValue(Vaga.class);
                    if (vaga != null) {
                        vaga.setId(childSnapshot.getKey());
                        listaVagas.add(vaga);
                    }
                }

                vagaAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(com.google.firebase.database.DatabaseError error) {
                Toast.makeText(feedPrincipal.this, "Erro ao carregar vagas.", Toast.LENGTH_SHORT).show();
                Log.e("FeedPrincipal", "Erro ao buscar vagas", error.toException());
            }
        });
    }
}
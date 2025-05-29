package com.example.acordasocial;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class feedPrincipal extends AppCompatActivity {

    private RecyclerView recyclerView;
    private VagaAdapter vagaAdapter;
    private List<Vaga> listaVagas;
    private ImageButton btnperfil;



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

        listaVagas = new ArrayList<>();
        listaVagas.add(new Vaga("ONG Esperança", "Ajude na distribuição de alimentos.", "São Paulo - SP"));
        listaVagas.add(new Vaga("ONG Vida", "Ofereça apoio educacional.", "Rio de Janeiro - RJ"));
        listaVagas.add(new Vaga("ONG Solidariedade", "Apoio psicológico para jovens.", "Belo Horizonte - MG"));

        vagaAdapter = new VagaAdapter(listaVagas);
        recyclerView.setAdapter(vagaAdapter);

        btnperfil = findViewById(R.id.btnPerfil);
        btnperfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(feedPrincipal.this, perfilUsuario.class);
                startActivity(intent);
            }
        });


    }
}
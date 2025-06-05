package com.example.acordasocial;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.acordasocial.Usuario.historico_servicos;
import com.example.acordasocial.Usuario.perfilUsuario;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class detalhesActivity extends AppCompatActivity {
    private Button btnVoltar, btnParticipantes;
    TextView nomeOng, descricao, local, horario;

    private static final String CHANNEL_ID = "canal_id";
    private String nomeEvento = "", desc = "", loc = "", hora = "";

    private FirebaseAuth auth;
    private DatabaseReference databaseReference;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_detalhes);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        nomeOng = findViewById(R.id.textServiceName);
        descricao = findViewById(R.id.textOngName);
        local = findViewById(R.id.textLocation);
        horario = findViewById(R.id.textTime);
        btnVoltar = findViewById(R.id.btnVoltar);
        btnParticipantes = findViewById(R.id.btnParticipantes);
        nomeEvento = getIntent().getStringExtra("nomeOng");
        desc = getIntent().getStringExtra("descricao");
        loc = getIntent().getStringExtra("local");
        hora = getIntent().getStringExtra("horario");

        nomeOng.setText(nomeEvento);
        descricao.setText(desc);
        local.setText(loc);
        horario.setText("Horário: " + hora);

        auth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();


    }
    public void setBtnParticipantes(View view){
        Intent intent = new Intent(detalhesActivity.this, Voluntarios.class);
        intent.putExtra("nomeOng", nomeEvento);
        String idEvento = getIntent().getStringExtra("id");
        intent.putExtra("idEvento", idEvento);
        startActivity(intent);
    }
    public void setBtnVoltar(View view){
        Intent intent = new Intent(detalhesActivity.this, historico_servicos.class);
        startActivity(intent);
        finish();
    }
}
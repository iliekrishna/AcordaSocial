package com.example.acordasocial;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class service_description extends AppCompatActivity {


    TextView nomeOng, descricao, local, horario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_description);

        nomeOng = findViewById(R.id.textServiceName);
        descricao = findViewById(R.id.textOngName);
        local = findViewById(R.id.textLocation);
        horario = findViewById(R.id.textTime);

        String nome = getIntent().getStringExtra("nomeOng");
        String desc = getIntent().getStringExtra("descricao");
        String loc = getIntent().getStringExtra("local");
        String hora = getIntent().getStringExtra("horario");

        nomeOng.setText(nome);
        descricao.setText(desc);
        local.setText(loc);
        horario.setText("Horário: " + hora);
    }
}
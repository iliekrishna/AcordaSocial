package com.example.acordasocial;


import static com.example.acordasocial.FirebaseConnection.getDatabaseReference;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;

public class perfilUsuario extends AppCompatActivity {
    private TextView tvNomeUsuario, tvEmailUsuario;
    FirebaseAuth auth;
    private DatabaseReference databaseReference;
    private Button btnSair, btnMeusVoluntariados;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_perfil_usuario);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        auth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference("usuarios");

        tvNomeUsuario = findViewById(R.id.tvNomeUsuario);
        tvEmailUsuario = findViewById(R.id.tvEmailUsuario);
        btnSair = findViewById(R.id.btnSair);
        btnMeusVoluntariados = findViewById(R.id.btnMeusVoluntariados);

       carregarDadosUsuario();

    }

    private void carregarDadosUsuario() {
        FirebaseUser user = auth.getCurrentUser();

        if (user != null) {
            String uid = user.getUid();

            databaseReference.child(uid).get().addOnSuccessListener(snapshot -> {
                if (snapshot.exists()) {
                    String nome = snapshot.child("nome").getValue(String.class);
                    String email = snapshot.child("email").getValue(String.class);



                    tvNomeUsuario.setText(nome != null ? nome : "Nome não encontrado");
                    tvEmailUsuario.setText(email != null ? email : "Email não encontrado");



                } else {
                    Toast.makeText(perfilUsuario.this, "Usuário não encontrado no banco de dados", Toast.LENGTH_SHORT).show();
                }
            }).addOnFailureListener(e -> {
                Toast.makeText(perfilUsuario.this, "Erro ao buscar dados: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            });
        } else {
            Toast.makeText(perfilUsuario.this, "Usuário não autenticado", Toast.LENGTH_SHORT).show();
        }
    }
    public void Sair(View view){
        auth.signOut();  // Desloga o usuário
        Intent intent = new Intent(perfilUsuario.this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK); // Limpa a pilha de atividades
        startActivity(intent);
        finish();  // Finaliza a activity atual
    }

    public void MeusEventos(View view){
        Intent intent = new Intent(perfilUsuario.this, historico_servicos.class);
        startActivity(intent);

    }


}
package com.example.acordasocial;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

public class perfilUsuario extends AppCompatActivity {
    private TextView tvNomeUsuario, tvEmailUsuario;
    FirebaseAuth auth;

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

        tvNomeUsuario = findViewById(R.id.tvNomeUsuario);
        tvEmailUsuario = findViewById(R.id.tvEmailUsuario);

        exibirDadosUsuario();

    }

    private void exibirDadosUsuario() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        FirebaseAuth auth = FirebaseAuth.getInstance();

        FirebaseUser user = auth.getCurrentUser();

        if (user != null) {
            String userId = user.getUid();

            db.collection("usuarios").document(userId)
                    .get()
                    .addOnSuccessListener(documentSnapshot -> {
                        if (documentSnapshot.exists()) {
                            String nome = documentSnapshot.getString("nome");
                            String email = documentSnapshot.getString("email");

                            tvNomeUsuario.setText(nome);
                            tvEmailUsuario.setText(email);
                        }
                    })
                    .addOnFailureListener(e -> {
                        Toast.makeText(this, "Erro ao carregar dados", Toast.LENGTH_SHORT).show();
                    });
        }
    }
}
package com.example.acordasocial.Usuario;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.acordasocial.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class telaCadastro extends AppCompatActivity {

    private EditText etNome, etEmail, etSenha;
    private Button btnCadastrar;

    private FirebaseAuth auth;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_tela_cadastro);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        etNome = findViewById(R.id.etNome);
        etEmail = findViewById(R.id.etEmail);
        etSenha = findViewById(R.id.etSenha);
        btnCadastrar = findViewById(R.id.btnCadastrar);
        auth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference("usuarios");

        btnCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nome = etNome.getText().toString().trim();
                String email = etEmail.getText().toString().trim();
                String senha = etSenha.getText().toString().trim();

                if (nome.isEmpty() || email.isEmpty() || senha.isEmpty()) {
                    Toast.makeText(telaCadastro.this, "Preencha todos os campos", Toast.LENGTH_SHORT).show();
                } else {
                    salvarUsuario(nome, email, senha);
                    Intent intent = new Intent(telaCadastro.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        });


    }
    private void salvarUsuario(String nome, String email, String senha) {
        auth.createUserWithEmailAndPassword(email, senha)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        String userId = auth.getCurrentUser().getUid();
                        Usuario usuario = new Usuario(userId, nome, email, senha);

                        databaseReference.child(userId).setValue(usuario)
                                .addOnCompleteListener(task1 -> {
                                    if (task1.isSuccessful()) {
                                        // Enviar e-mail de verificação
                                        auth.getCurrentUser().sendEmailVerification()
                                                .addOnCompleteListener(task2 -> {
                                                    if (task2.isSuccessful()) {
                                                        Toast.makeText(telaCadastro.this, "Cadastro realizado com sucesso! Verifique seu e-mail.", Toast.LENGTH_SHORT).show();
                                                    } else {
                                                        Toast.makeText(telaCadastro.this, "Erro ao enviar e-mail de verificação: " + task2.getException().getMessage(), Toast.LENGTH_LONG).show();
                                                    }
                                                });
                                        limparCampos();
                                    } else {
                                        Toast.makeText(telaCadastro.this, "Erro ao salvar dados: " + task1.getException().getMessage(), Toast.LENGTH_LONG).show();
                                    }
                                });
                    } else {
                        Toast.makeText(telaCadastro.this, "Erro no cadastro: " + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
    }

    private void limparCampos() {
        etNome.setText("");
        etEmail.setText("");
        etSenha.setText("");
    }

}
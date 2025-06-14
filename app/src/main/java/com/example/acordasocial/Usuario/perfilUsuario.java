package com.example.acordasocial.Usuario;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.acordasocial.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class perfilUsuario extends AppCompatActivity {
    private static final int PICK_IMAGE_REQUEST = 1;
    private ActivityResultLauncher<Intent> activityResultLauncher;
    private Uri imageUri;

    private ImageView imgPerfil;

    private TextView tvNomeUsuario, tvEmailUsuario;
    FirebaseAuth auth;
    private DatabaseReference databaseReference;
    private Button btnSair, btnMeusVoluntariados, btnEditarPerfil;

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

        imgPerfil = findViewById(R.id.imgPerfil);
        tvNomeUsuario = findViewById(R.id.tvNomeUsuario);
        tvEmailUsuario = findViewById(R.id.tvEmailUsuario);
        btnSair = findViewById(R.id.btnSair);
        btnMeusVoluntariados = findViewById(R.id.btnMeusVoluntariados);
        btnEditarPerfil = findViewById(R.id.btnEditarPerfil);


       carregarDadosUsuario();
        btnEditarPerfil.setOnClickListener(v -> abrirGaleria());

        activityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                        imageUri = result.getData().getData();
                        Toast.makeText(this, "Imagem selecionada com sucesso!", Toast.LENGTH_SHORT).show();
                        // Aqui pode exibir num ImageView ou fazer upload
                    }
                }
        );


    }

    private void abrirGaleria() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        activityResultLauncher.launch(Intent.createChooser(intent, "Selecione uma imagem"));
    }


//    private void abrirGaleria() {
//        Intent intent = new Intent();
//        intent.setType("image/*");
//        intent.setAction(Intent.ACTION_GET_CONTENT);
//        startActivityForResult(Intent.createChooser(intent, "Selecione uma imagem"), PICK_IMAGE_REQUEST);
//    }

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
        Toast.makeText(perfilUsuario.this, "Usuário Desconectado", Toast.LENGTH_SHORT).show();
        finish();  // Finaliza a activity atual
    }

    public void MeusEventos(View view){
        Intent intent = new Intent(perfilUsuario.this, historico_servicos.class);
        startActivity(intent);
        finish();
    }


}
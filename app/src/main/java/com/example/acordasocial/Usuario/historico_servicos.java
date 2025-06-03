package com.example.acordasocial.Usuario;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.acordasocial.R;
import com.example.acordasocial.Vagas.HistoricoAdapter;
import com.example.acordasocial.Vagas.Vaga;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.*;

import java.util.ArrayList;
import java.util.List;

public class historico_servicos extends AppCompatActivity {

    private FirebaseAuth auth;
    private DatabaseReference databaseReferenceUsuarios;
    private DatabaseReference databaseReferenceVagas;

    private RecyclerView rvHistorico;
    private List<Vaga> listaVagas = new ArrayList<>();
    private HistoricoAdapter historicoAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historico_servicos);

        rvHistorico = findViewById(R.id.rvHistorico);

        // ESSENCIAL: definir o LayoutManager
        rvHistorico.setLayoutManager(new LinearLayoutManager(this));

        historicoAdapter = new HistoricoAdapter(listaVagas);
        rvHistorico.setAdapter(historicoAdapter);

        auth = FirebaseAuth.getInstance();
        databaseReferenceUsuarios = FirebaseDatabase.getInstance().getReference("usuarios");
        databaseReferenceVagas = FirebaseDatabase.getInstance().getReference("vagas");

        carregarEventosDoUsuario();
    }

    private void carregarEventosDoUsuario() {
        String uid = auth.getCurrentUser().getUid();

        databaseReferenceUsuarios.child(uid).child("participacoes").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    listaVagas.clear();
                    for (DataSnapshot vagaSnapshot : snapshot.getChildren()) {
                        String vagaId = vagaSnapshot.getKey();

                        databaseReferenceVagas.child(vagaId).addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot vagaData) {
                                Vaga vaga = vagaData.getValue(Vaga.class);
                                if (vaga != null) {
                                    listaVagas.add(vaga);
                                    historicoAdapter.notifyDataSetChanged();
                                }
                            }

                            @Override
                            public void onCancelled(DatabaseError error) {
                                Toast.makeText(historico_servicos.this, "Erro ao carregar vaga", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                } else {
                    Toast.makeText(historico_servicos.this, "Você não participou de nenhum evento.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Toast.makeText(historico_servicos.this, "Erro ao carregar eventos", Toast.LENGTH_SHORT).show();
            }
        });
    }
}

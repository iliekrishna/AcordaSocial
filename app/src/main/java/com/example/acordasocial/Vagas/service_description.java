package com.example.acordasocial.Vagas;

import android.Manifest;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.acordasocial.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class service_description extends AppCompatActivity {

    private Button btnParticipar1;
    TextView nomeOng, descricao, local, horario;

    private static final String CHANNEL_ID = "canal_id";
    private String nomeEvento = "", desc = "", loc = "", hora = "";

    private FirebaseAuth auth;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_description);

        btnParticipar1 = findViewById(R.id.btnParticipar1);

        nomeOng = findViewById(R.id.textServiceName);
        descricao = findViewById(R.id.textOngName);
        local = findViewById(R.id.textLocation);
        horario = findViewById(R.id.textTime);

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

        // Criar canal de notificação
        createNotificationChannel();

        btnParticipar1.setOnClickListener(v -> {
            registrarParticipacao();
        });
    }

    private void registrarParticipacao() {
        FirebaseUser user = auth.getCurrentUser();

        if (user != null) {
            String uid = user.getUid();

            // Estrutura: participacoes/{uid}/{evento_id}
            DatabaseReference participacaoRef = databaseReference.child("participacoes").child(uid);

            // Criar objeto com os dados da vaga
            Participacao participacao = new Participacao(nomeEvento, desc, loc, hora);

            // Gerar chave única
            participacaoRef.push().setValue(participacao)
                    .addOnSuccessListener(aVoid -> {
                        Toast.makeText(service_description.this, "Participação registrada com sucesso!", Toast.LENGTH_SHORT).show();
                        enviarNotificacao();
                    })
                    .addOnFailureListener(e -> {
                        Toast.makeText(service_description.this, "Erro ao registrar participação: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    });

        } else {
            Toast.makeText(this, "Usuário não autenticado", Toast.LENGTH_SHORT).show();
        }
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "Canal Padrão";
            String description = "Descrição do canal";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);

            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    private void enviarNotificacao() {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_notification)
                .setContentTitle("Inscrição confirmada: " + nomeEvento)
                .setContentText("Parabéns! Você está participando do evento: " + nomeEvento + ".")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) == PackageManager.PERMISSION_GRANTED) {
            notificationManager.notify(1, builder.build());
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.POST_NOTIFICATIONS}, 1);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        // Opcional: Pode disparar novamente a notificação se a permissão for concedida
    }

    // Classe modelo de participação
    public static class Participacao {
        public String nomeEvento;
        public String descricao;
        public String local;
        public String horario;

        public Participacao() {
            // Construtor vazio requerido pelo Firebase
        }

        public Participacao(String nomeEvento, String descricao, String local, String horario) {
            this.nomeEvento = nomeEvento;
            this.descricao = descricao;
            this.local = local;
            this.horario = horario;
        }
    }
}

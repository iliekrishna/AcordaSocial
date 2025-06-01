package com.example.acordasocial.Vagas;

import android.Manifest;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.acordasocial.R;

public class service_description extends AppCompatActivity {

    private Button btnParticipar1;
    TextView nomeOng, descricao, local, horario;

    private static final String CHANNEL_ID = "canal_id";
    private String nomeEvento = "";

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
        String desc = getIntent().getStringExtra("descricao");
        String loc = getIntent().getStringExtra("local");
        String hora = getIntent().getStringExtra("horario");

        nomeOng.setText(nomeEvento);
        descricao.setText(desc);
        local.setText(loc);
        horario.setText("Horário: " + hora);

        // Criar canal de notificação (só uma vez)
        createNotificationChannel();

        btnParticipar1.setOnClickListener(v -> {
            enviarNotificacao();
        });
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
                .setSmallIcon(R.drawable.ic_notification)  // Ícone obrigatório!
                .setContentTitle("Inscrição confirmada: " + nomeEvento)
                .setContentText("Parabéns! Você está participando do evento: " + nomeEvento + ".")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) ==
                PackageManager.PERMISSION_GRANTED) {
            notificationManager.notify(1, builder.build());
        } else {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.POST_NOTIFICATIONS}, 1);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
      //Dispara notificação após confirmar
    }
}

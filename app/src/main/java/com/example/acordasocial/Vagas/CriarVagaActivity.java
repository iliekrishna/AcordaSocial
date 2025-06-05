package com.example.acordasocial.Vagas;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.acordasocial.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class CriarVagaActivity extends AppCompatActivity {

    private EditText etNomeOng, etDescricao, etLocal, etHorario, etData;
    private Button btnSalvar;

    private DatabaseReference databaseReference;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_criar_vaga);

        // Inicializando Realtime Database
        databaseReference = FirebaseDatabase.getInstance().getReference("vagas");

        etData = findViewById(R.id.etData);
        etNomeOng = findViewById(R.id.etNomeOng);
        etDescricao = findViewById(R.id.etDescricao);
        etLocal = findViewById(R.id.etLocal);
        etHorario = findViewById(R.id.etHorario);
        btnSalvar = findViewById(R.id.btnSalvar);

        // Adiciona as máscaras
        addMaskToHorario();
        addMaskToData();

        btnSalvar.setOnClickListener(view -> salvarVaga());
    }

    private void salvarVaga() {
        String nomeOng = etNomeOng.getText().toString().trim();
        String descricao = etDescricao.getText().toString().trim();
        String local = etLocal.getText().toString().trim();
        String horario = etHorario.getText().toString().trim();
        String data = etData.getText().toString().trim();

        if (nomeOng.isEmpty() || descricao.isEmpty() || local.isEmpty() || horario.isEmpty() || data.isEmpty()) {
            Toast.makeText(this, "Preencha todos os campos!", Toast.LENGTH_SHORT).show();
            return;
        }

        String vagaId = databaseReference.push().getKey();

        Map<String, Object> vaga = new HashMap<>();
        vaga.put("id", vagaId);
        vaga.put("nomeOng", nomeOng);
        vaga.put("descricao", descricao);
        vaga.put("local", local);
        vaga.put("horario", horario);
        vaga.put("data", data);

        if (vagaId != null) {
            databaseReference.child(vagaId).setValue(vaga)
                    .addOnSuccessListener(aVoid -> {
                        Toast.makeText(this, "Vaga criada com sucesso!", Toast.LENGTH_SHORT).show();
                        finish();
                    })
                    .addOnFailureListener(e -> {
                        Toast.makeText(this, "Erro ao criar vaga: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    });
        }
    }

    private void addMaskToHorario() {
        etHorario.addTextChangedListener(new TextWatcher() {
            private boolean isUpdating;
            private final String mask = "##:##";
            private final String regex = "[^0-9]";

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (isUpdating) {
                    isUpdating = false;
                    return;
                }

                String str = s.toString().replaceAll(regex, "");

                StringBuilder formatted = new StringBuilder();

                int i = 0;
                for (char m : mask.toCharArray()) {
                    if (m != '#' && str.length() > i) {
                        formatted.append(m);
                    } else {
                        try {
                            formatted.append(str.charAt(i));
                        } catch (Exception e) {
                            break;
                        }
                        i++;
                    }
                }

                isUpdating = true;
                etHorario.setText(formatted.toString());
                etHorario.setSelection(formatted.length());
            }

            @Override
            public void afterTextChanged(Editable editable) { }
        });
    }

    private void addMaskToData() {
        etData.addTextChangedListener(new TextWatcher() {
            private boolean isUpdating;
            private final String mask = "##/##/####";
            private final String regex = "[^0-9]";

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (isUpdating) {
                    isUpdating = false;
                    return;
                }

                String str = s.toString().replaceAll(regex, "");

                StringBuilder formatted = new StringBuilder();

                int i = 0;
                for (char m : mask.toCharArray()) {
                    if (m != '#' && str.length() > i) {
                        formatted.append(m);
                    } else {
                        try {
                            formatted.append(str.charAt(i));
                        } catch (Exception e) {
                            break;
                        }
                        i++;
                    }
                }

                isUpdating = true;
                etData.setText(formatted.toString());
                etData.setSelection(formatted.length());
            }

            @Override
            public void afterTextChanged(Editable editable) { }
        });
    }
}

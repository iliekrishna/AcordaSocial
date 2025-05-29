package dao;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import com.google.firebase.database.ValueEventListener;
import com.example.acordasocial.Usuario;


import java.util.ArrayList;
import java.util.List;

public class usuarioDAO {

    private DatabaseReference databaseReference;

    public void UsuarioDAO() {
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        databaseReference = db.getReference("usuarios");
    }

    public void salvar(Usuario usuario) {
        if (usuario.getId() == null) {
            usuario.setId(databaseReference.push().getKey());
        }
        databaseReference.child(usuario.getId()).setValue(usuario);
    }

    public void buscarPorEmail(String email, final UsuarioCallback callback) {
        databaseReference.orderByChild("email").equalTo(email)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        Usuario usuario = null;
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            usuario = snapshot.getValue(Usuario.class);
                            break; // Apenas o primeiro resultado
                        }
                        callback.onCallback(usuario);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        callback.onCallback(null);
                    }
                });
    }

    public void listarTodos(final UsuariosCallback callback) {
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<Usuario> usuarios = new ArrayList<>();
                for (DataSnapshot ds : snapshot.getChildren()) {
                    Usuario usuario = ds.getValue(Usuario.class);
                    usuarios.add(usuario);
                }
                callback.onCallback(usuarios);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                callback.onCallback(new ArrayList<Usuario>());
            }
        });
    }

    public void atualizar(Usuario usuario) {
        if (usuario.getId() != null) {
            databaseReference.child(usuario.getId()).setValue(usuario);
        }
    }

    public void deletar(String id) {
        databaseReference.child(id).removeValue();
    }

    // Interfaces de callback
    public interface UsuarioCallback {
        void onCallback(Usuario usuario);
    }

    public interface UsuariosCallback {
        void onCallback(List<Usuario> usuarios);
    }
}
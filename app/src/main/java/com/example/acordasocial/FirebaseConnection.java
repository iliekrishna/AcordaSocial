package com.example.acordasocial;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;

public class FirebaseConnection {

    private static FirebaseFirestore firestoreInstance;
    private static DatabaseReference databaseReference;
    private static FirebaseAuth authInstance;

    public static FirebaseFirestore getFirestore() {
        if (firestoreInstance == null) {
            firestoreInstance = FirebaseFirestore.getInstance();
        }
        return firestoreInstance;
    }

    public static DatabaseReference getDatabaseReference() {
        if (databaseReference == null) {
            databaseReference = FirebaseDatabase.getInstance().getReference();
        }
        return databaseReference;
    }

    public static FirebaseAuth getAuth() {
        if (authInstance == null) {
            authInstance = FirebaseAuth.getInstance();
        }
        return authInstance;
    }
}
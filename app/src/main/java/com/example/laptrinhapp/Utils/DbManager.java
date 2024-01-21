package com.example.laptrinhapp.Utils;

import com.google.firebase.firestore.FirebaseFirestore;

public class DbManager {
    private static FirebaseFirestore firestoreInstance;

    private DbManager() {
        // Private constructor to prevent instantiation
    }

    public static synchronized FirebaseFirestore getInstance() {
        if (firestoreInstance == null) {
            firestoreInstance = FirebaseFirestore.getInstance();
        }
        return firestoreInstance;
    }
}

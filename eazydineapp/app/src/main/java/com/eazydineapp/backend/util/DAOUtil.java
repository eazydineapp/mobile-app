package com.eazydineapp.backend.util;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


/**
 * Created by ravisha on 10/9/16.
 */
public class DAOUtil {
    private static DatabaseReference databaseReference;

    static{

        databaseReference = FirebaseDatabase.getInstance().getReference();

    }

    public static DatabaseReference getDatabaseReference() {
        return databaseReference;
    }



}

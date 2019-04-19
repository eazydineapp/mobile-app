package com.eazydineapp.backend.dao.impl;

import android.support.annotation.NonNull;
import android.util.Log;

import com.eazydineapp.backend.dao.api.UserDAO;
import com.eazydineapp.backend.exception.ItemException;
import com.eazydineapp.backend.ui.api.UIUserService;
import com.eazydineapp.backend.util.DAOUtil;
import com.eazydineapp.backend.util.PathUtil;
import com.eazydineapp.backend.vo.Order;
import com.eazydineapp.backend.vo.OrderStatus;
import com.eazydineapp.backend.vo.User;
import com.eazydineapp.backend.vo.UserStatus;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class UserDAOImpl implements UserDAO {

    private static final String TAG = "UserDAOImpl";

    @Override
    public void getUserStatus(String userId, final UIUserService uiUserService)  throws ItemException {
        try {
            final String userPath = PathUtil.getUserDetailPath() + userId;
            DatabaseReference orderReference = DAOUtil.getDatabaseReference().child(userPath);
           // Query query = orderReference.orderByChild("status").equalTo(UserStatus.OUT.toString());
            orderReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    User user = null;
                    if (dataSnapshot.getValue() != null) {
                      user = dataSnapshot.getValue(User.class);
                    }
                    uiUserService.displayUserInfo(user);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                }
            });
        } catch (Exception exception) {
            Log.e(TAG, "Error getting order", exception);
            throw new ItemException("Error", exception);
        }
    }

    @Override
    public void updateUser(User user) throws ItemException {
        try {
            final String userPath = PathUtil.getUserDetailPath() + user.getPhoneNumber();
            DAOUtil.getDatabaseReference().child(userPath).setValue(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Log.d(TAG, "Success updating order");
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    Log.e(TAG, "Error adding order", exception);
                }
            });
        } catch (Exception exception) {
            Log.e(TAG, "Error updating order", exception);
            throw new ItemException("Error", exception);
        }
    }
}

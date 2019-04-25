package com.eazydineapp.backend.dao.impl;

import android.util.Log;

import androidx.annotation.NonNull;

import com.eazydineapp.backend.dao.api.WaitlistDAO;
import com.eazydineapp.backend.exception.ItemException;
import com.eazydineapp.backend.ui.api.UIWaitlistService;
import com.eazydineapp.backend.util.DAOUtil;
import com.eazydineapp.backend.util.PathUtil;
import com.eazydineapp.backend.vo.Waitlist;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class WaitlistDAOImpl implements WaitlistDAO {

    String TAG = "WaitlistDAOImpl";

    @Override
    public void addUserToWaitList(Waitlist waitlist) {
        try {
            final String waitListPath = PathUtil.getWaitListPath()+waitlist.getRestaurantId();
            System.out.println("Ading user to waitlist");
            DAOUtil.getDatabaseReference().child(waitListPath).child(waitlist.getUserId()).setValue(waitlist).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Log.d(TAG, "Success adding user to waitlist");
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    Log.e(TAG, "Error adding user to wait", exception);
                }
            });
        } catch (Exception exception) {
            Log.e(TAG, "Error adding user to wait", exception);
        }
    }

    @Override
    public void getUsers(String restaurantId, String userId, final UIWaitlistService UIWaitlistService) throws ItemException {
        try {
            System.out.println("Get User status in Wait list");
            final String waitPath = PathUtil.getWaitListPath()+ restaurantId + PathUtil.getRootPath() + userId;
            DAOUtil.getDatabaseReference().child(waitPath).addListenerForSingleValueEvent(
                    new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            Waitlist waitUser = null;
                            if(dataSnapshot.getValue() != null){
                                waitUser = dataSnapshot.getValue(Waitlist.class);
                            }
                            UIWaitlistService.displayWaitStatus(waitUser);
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                        }
                    });
        } catch (Exception exception) {
            Log.e(TAG, "Error getting item", exception);
            throw new ItemException("Error", exception);

        }
    }
}

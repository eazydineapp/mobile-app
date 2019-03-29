package com.eazydineapp.backend.dao.impl;

import android.support.annotation.NonNull;
import android.util.Log;


import com.eazydineapp.backend.dao.api.OrderDAO;
import com.eazydineapp.backend.exception.ItemException;
import com.eazydineapp.backend.ui.api.UIOrderService;
import com.eazydineapp.backend.util.DAOUtil;
import com.eazydineapp.backend.util.PathUtil;
import com.eazydineapp.backend.vo.Order;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class OrderDAOImpl implements OrderDAO {

    String TAG = "OrderDAOImpl";

    String orderPath = PathUtil.getOrderPath();


    @Override
    public void add(Order order) throws ItemException {
        try {
            String orderId = DAOUtil.getDatabaseReference().push().getKey();
            order.setOrderId(orderId);
            System.out.println("Ading the order..");
            DAOUtil.getDatabaseReference().child(orderPath).child(orderId).setValue(order).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Log.d(TAG, "Success adding order");
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    Log.e(TAG, "Error adding order", exception);
                }
            });
        } catch (Exception exception) {
            Log.e(TAG, "Error adding order", exception);
            throw new ItemException("Error", exception);
        }

    }

    @Override
    public void readByCook(String cookId, final UIOrderService uiOrderService) throws ItemException {
        try {
            System.out.println("List of all orders for given cook..");

            DAOUtil.getDatabaseReference().child(PathUtil.getCookOrderPath(cookId)).addValueEventListener(
                    new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            GenericTypeIndicator<Map<String, Order>> genericTypeIndicator = new GenericTypeIndicator<Map<String, Order>>() {
                            };
                            Map<String, Order> orderMap = dataSnapshot.getValue(genericTypeIndicator);
                            List<Order> orderList = new ArrayList<>();
                           /* for(String key:orderMap.keySet()){
                                Order order = orderMap.get(key);

                                System.out.println("Lis of all item in daoimpl.."+order.getUserName());
                                orderList.add(order);
                            }
                            uiOrderService.displayAllOrders(orderList); */
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

    @Override
    public void readByUser(String userID, final UIOrderService UIOrderService) throws ItemException {
        try {
            System.out.println("List of all orders for given restaurant..");

            DAOUtil.getDatabaseReference().child(PathUtil.getOrderPath()).addValueEventListener(
                    new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            List<Order> orders = new ArrayList<>();
                            for(DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                Order order = snapshot.getValue(Order.class);
                                orders.add(order);
                            }
                            UIOrderService.displayAllOrders(orders);
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

    @Override
    public void delete(String id) throws Exception {

    }

    @Override
    public void update(com.eazydineapp.backend.vo.Order order) throws ItemException {
        try {
            DAOUtil.getDatabaseReference().child(PathUtil.getOrderIdPath(order.getOrderId())).setValue(order);
            // DAOUtil.getDatabaseReference().child(PathUtil.getCookOrderIdPath(order.getItem().getCookId(),order.getOrderId())).setValue(order);
        } catch (Exception exception) {
            Log.e(TAG, "Error updating order", exception);
            throw new ItemException("Error", exception);
        }
    }
}

package com.eazydineapp.backend.dao.impl;

import android.support.annotation.NonNull;
import android.util.Log;


import com.eazydineapp.backend.dao.api.OrderDAO;
import com.eazydineapp.backend.exception.ItemException;
import com.eazydineapp.backend.ui.api.UIOrderService;
import com.eazydineapp.backend.util.AppUtil;
import com.eazydineapp.backend.util.DAOUtil;
import com.eazydineapp.backend.util.PathUtil;
import com.eazydineapp.backend.vo.CartItem;
import com.eazydineapp.backend.vo.Order;
import com.eazydineapp.backend.vo.OrderStatus;
import com.eazydineapp.backend.vo.Waitlist;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.lang.Math.round;

public class OrderDAOImpl implements OrderDAO {

    String TAG = "OrderDAOImpl";

    @Override
    public void addToCart(final Order order) throws ItemException {
        try {
            final String orderPath = PathUtil.getUserPath() + order.getUserId() + PathUtil.getOrderPath();
            DatabaseReference orderReference = DAOUtil.getDatabaseReference().child(orderPath);
            Query query = orderReference.orderByChild("orderStatus").equalTo(OrderStatus.Cart.toString());
            query.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.getValue() != null) {
                        Order dbOrder = null;
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            dbOrder = snapshot.getValue(Order.class);
                            if (dbOrder.getRestaurantId().equals(order.getRestaurantId())){
                                updateCartItems(dbOrder, order.getItemList());
                                break;
                            }
                        }
                    } else {
                        createCart(order);

                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                }
            });
        } catch (Exception exception) {
            Log.e(TAG, "Error adding order", exception);
            throw new ItemException("Error", exception);
        }

    }

    protected void createCart(Order order) {
        try {
            final String orderPath = PathUtil.getUserPath() + order.getUserId() + PathUtil.getOrderPath();
            final String orderId = DAOUtil.getDatabaseReference().push().getKey();
            order.setOrderId(orderId);
            System.out.println("Ading the order..");
            DAOUtil.getDatabaseReference().child(orderPath).child(orderId).setValue(order).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    //DAOUtil.getDatabaseReference().child(cartPath).child(orderId).removeValue();
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
        }
    }

    protected void updateCartItems(Order order, List<CartItem> cartItems) {
        try {
            Map<String, CartItem> cartItemMap = new HashMap<>();
            cartItems.addAll(order.getItemList());
            float totalPrice = 0.0f;
            for (CartItem item : cartItems) {
                totalPrice += item.getPriceTotal();
                if (cartItemMap.containsKey(item.getItemId())) {
                    CartItem cartItem = cartItemMap.get(item.getItemId());
                    cartItem.setQuantity(cartItem.getQuantity() + item.getQuantity());
                    cartItemMap.put(item.getItemId(), cartItem);
                } else {
                    cartItemMap.put(item.getItemId(), item);
                }
            }

            ArrayList<CartItem> cartItemToStore = new ArrayList<>();
            for (String key : cartItemMap.keySet()) {
                cartItemToStore.add(cartItemMap.get(key));
            }

            totalPrice = AppUtil.round(totalPrice);
            order.setItemList(cartItemToStore);
            order.setTotalPrice(totalPrice);

            final String orderPath = PathUtil.getUserPath() + order.getUserId() + PathUtil.getOrderPath() + order.getOrderId();
            DAOUtil.getDatabaseReference().child(orderPath).setValue(order).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    //DAOUtil.getDatabaseReference().child(cartPath).child(orderId).removeValue();
                    Log.d(TAG, "Success updating order to cart");
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    Log.e(TAG, "Error adding order to cart", exception);
                }
            });
        } catch (Exception exception) {
            Log.e(TAG, "Error getting item", exception);
        }
    }

    @Override
    public void getOrderByUser(String userId, final UIOrderService UIOrderService) throws ItemException {
        try {
            final String orderPath = PathUtil.getUserPath() + userId + PathUtil.getOrderPath();
            DatabaseReference orderReference = DAOUtil.getDatabaseReference().child(orderPath);
            Query query = orderReference.orderByChild("orderStatus").equalTo(OrderStatus.Paid.toString());
            query.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.getValue() != null) {
                        List<Order> dbOrders = new ArrayList<>();
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            dbOrders.add(snapshot.getValue(Order.class));
                        }
                        UIOrderService.displayAllOrders(dbOrders);
                    }
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
    public void getOrderByUserAndRestaurantId(String userId, final String restaurantId, final UIOrderService UIOrderService) throws ItemException {
        try {
            System.out.println("List of all orders for given restaurant and user");
            final String orderPath = PathUtil.getUserPath() + userId + PathUtil.getOrderPath();
            DAOUtil.getDatabaseReference().child(orderPath).addValueEventListener(
                    new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            List<Order> orders = new ArrayList<>();
                            for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                Order order = snapshot.getValue(Order.class);
                                if(restaurantId.equals(order.getRestaurantId()) &&
                                        (!OrderStatus.Cart.equals(order.getOrderStatus()) && !OrderStatus.Paid.equals(order.getOrderStatus()))){
                                    orders.add(order);
                                }
                            }
                            UIOrderService.displayAllOrders(orders);
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                        }
                    });
        } catch (Exception exception) {
            Log.e(TAG, "Error getting order by restaurant and user", exception);
            throw new ItemException("Error", exception);

        }
    }

    @Override
    public void getCartByUser(String userId, final UIOrderService UIOrderService) throws ItemException {
        try {
            System.out.println("List of all orders for given restaurant..");
            final String orderPath = PathUtil.getUserPath() + userId + PathUtil.getOrderPath();
            Query query = DAOUtil.getDatabaseReference().child(orderPath).orderByChild("orderStatus").equalTo(OrderStatus.Cart.toString());
            query.addValueEventListener(
                    new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            Order order = null;
                            for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                order = snapshot.getValue(Order.class);
                            }
                            UIOrderService.displayOrder(order);
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
    public void updateOrder(Order order) throws ItemException {
        try {
            final String orderPath = PathUtil.getUserPath() + order.getUserId() + PathUtil.getOrderPath() + order.getOrderId();
            DAOUtil.getDatabaseReference().child(orderPath).setValue(order).addOnSuccessListener(new OnSuccessListener<Void>() {
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

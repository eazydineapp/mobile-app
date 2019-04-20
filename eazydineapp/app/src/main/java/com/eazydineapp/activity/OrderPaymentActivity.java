package com.eazydineapp.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.eazydineapp.R;
import com.eazydineapp.backend.service.api.OrderService;
import com.eazydineapp.backend.service.api.UserService;
import com.eazydineapp.backend.service.impl.OrderServiceImpl;
import com.eazydineapp.backend.service.impl.UserServiceImpl;
import com.eazydineapp.backend.ui.api.UIOrderService;
import com.eazydineapp.backend.util.AndroidStoragePrefUtil;
import com.eazydineapp.backend.util.AppUtil;
import com.eazydineapp.backend.vo.CartItem;
import com.eazydineapp.backend.vo.Order;
import com.eazydineapp.backend.vo.OrderPayment;
import com.eazydineapp.backend.vo.OrderStatus;
import com.eazydineapp.backend.vo.User;
import com.eazydineapp.backend.vo.UserStatus;
import com.paypal.android.sdk.payments.PayPalConfiguration;
import com.paypal.android.sdk.payments.PayPalPayment;
import com.paypal.android.sdk.payments.PayPalService;
import com.paypal.android.sdk.payments.PaymentActivity;
import com.paypal.android.sdk.payments.PaymentConfirmation;

import org.json.JSONException;

import java.math.BigDecimal;
import java.text.DateFormatSymbols;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static java.lang.Math.round;

public class OrderPaymentActivity extends AppCompatActivity {

    private String userId, restaurantId, payOption;
    private OrderPayment orderPayment;
    private AndroidStoragePrefUtil storagePrefUtil;
    private static PayPalConfiguration payPalConfig;
    private static final String PAYPAL_CLIENT_ID = "ARqZyB4y3IADi_vifcxelthHpEAk31ZzNUcysxcPMztAbDcaQGAQn3fgbrJcA6leQOu9SfBRTf8fU-Ah";
    public static final int PAYPAL_REQUEST_CODE = 123;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        Toolbar toolbar = findViewById(R.id.toolbarPayment);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationIcon(R.drawable.ic_keyboard_arrow_left_white_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        storagePrefUtil = new AndroidStoragePrefUtil();
        userId = storagePrefUtil.getRegisteredUser(this);
        restaurantId = storagePrefUtil.getValue(this, "RESTAURANT_ID");

        orderPayment = (OrderPayment)getIntent().getSerializableExtra("eazydineapp-payment");
        payPalConfig = new PayPalConfiguration().environment(PayPalConfiguration.ENVIRONMENT_SANDBOX).clientId(PAYPAL_CLIENT_ID);
        initUI();
    }

    private void initUI() {
        TextView serviceCharge  = findViewById(R.id.serviceCharge);
        serviceCharge.setText("$"+String.valueOf(orderPayment.getServiceCharge()));

        TextView tax = findViewById(R.id.tax);
        tax.setText("$"+String.valueOf(orderPayment.getTax()));

        TextView subTotal  = findViewById(R.id.subTotal);
        subTotal.setText("$"+String.valueOf(orderPayment.getSubTotal()));

        TextView orderTotal  = findViewById(R.id.orderTotal);
        orderTotal.setText("$"+String.valueOf(orderPayment.getTotal()));

        final RadioGroup rg = (RadioGroup) findViewById(R.id.paymentRadioGroup);
        payOption = ((RadioButton)findViewById(rg.getCheckedRadioButtonId())).getText().toString();
        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                payOption = ((RadioButton)findViewById(rg.getCheckedRadioButtonId())).getText().toString();
            }
        });

        TextView payButtonText = findViewById(R.id.payButtonText);
        payButtonText.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                if("Pay at Counter".equalsIgnoreCase(payOption)) {
                    //navigate to order summary screen
                    onBackPressed();
                }else {
                    makePayment();
                }
            }
        });
    }

    private void makePayment() {
        //Creating a paypalpayment
        PayPalPayment payment = new PayPalPayment(new BigDecimal(String.valueOf(orderPayment.getTotal())), "USD", "EazyDine Order Payment",
                PayPalPayment.PAYMENT_INTENT_SALE);

        //Creating Paypal Payment activity intent
        Intent intent = new Intent(this, PaymentActivity.class);

        //putting the paypal configuration to the intent
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, payPalConfig);

        //Puting paypal payment to the intent
        intent.putExtra(PaymentActivity.EXTRA_PAYMENT, payment);

        //Starting the intent activity for result
        //the request code will be used on the method onActivityResult
        startActivityForResult(intent, PAYPAL_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //If the result is from paypal
        if (requestCode == PAYPAL_REQUEST_CODE) {

            //If the result is OK i.e. user has not canceled the payment
            if (resultCode == Activity.RESULT_OK) {
                //change order status
                updateOrderStatus();
                //Getting the payment confirmation
                PaymentConfirmation confirm = data.getParcelableExtra(PaymentActivity.EXTRA_RESULT_CONFIRMATION);

                //if confirmation is not null
                if (confirm != null) {
                    try {
                        //Getting the payment details
                        String paymentDetails = confirm.toJSONObject().toString(4);
                        Log.i("paymentExample", paymentDetails);

                    } catch (JSONException e) {
                        Log.e("paymentExample", "an extremely unlikely failure occurred: ", e);
                    }
                }
            } else if (resultCode == Activity.RESULT_CANCELED) {
                Log.i("paymentExample", "The user canceled.");
            } else if (resultCode == PaymentActivity.RESULT_EXTRAS_INVALID) {
                Log.i("paymentExample", "An invalid Payment or PayPalConfiguration was submitted. Please see the docs.");
            }
        }
    }

    private void updateOrderStatus() {
        UserService userService = new UserServiceImpl();
        userService.updateUser(new User(userId, UserStatus.OUT, ""));

        final OrderService orderService = new OrderServiceImpl();
        orderService.updateOrderByUserAndRestaurant(userId, restaurantId);

        startActivity(new Intent(this, MainActivity.class));
    }
}

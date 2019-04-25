package com.eazydineapp.checkout;

import android.content.Intent;
import android.os.Handler;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.eazydineapp.R;
import com.eazydineapp.backend.vo.CartItem;

import java.util.ArrayList;

public class CheckoutActivity extends AppCompatActivity {

    private final String FRAG_TAG_ADDRESS = "Shipping detail";
    private final String FRAG_TAG_PAYMENT_MODE = "Payment detail";
    private final String FRAG_TAG_CONFIRM = "Confirm order";

    private Handler mHandler;

    private TextView checkoutActionText, checkoutStageHeading1, checkoutStageHeading2, checkoutStageHeading3, statusTxt;
    private int checkoutStage;
    private ArrayList<CartItem> cartItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);

        loadCartItems();
        initUi();

        loadFragment(FRAG_TAG_ADDRESS);


    }

    private void loadCartItems() {
        Intent intent = getIntent();
        this.cartItems = (ArrayList<CartItem>) intent.getSerializableExtra("cartItems");
    }

    private void initUi() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationIcon(R.drawable.ic_keyboard_arrow_left_white_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        checkoutActionText = findViewById(R.id.checkoutActionText);
        checkoutStageHeading1 = findViewById(R.id.checkoutStageHeading1);
        checkoutStageHeading2 = findViewById(R.id.checkoutStageHeading2);
        checkoutStageHeading3 = findViewById(R.id.checkoutStageHeading3);
        statusTxt = findViewById(R.id.statusTxt);


        findViewById(R.id.checkoutAction).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (checkoutStage) {
                    case 1:
                        loadFragment(FRAG_TAG_PAYMENT_MODE);
                        break;
                    case 2:
                        loadFragment(FRAG_TAG_CONFIRM);
                        break;
                    case 3:
                        break;
                }
            }
        });
    }

    private void loadFragment(final String fragTag) {
        Fragment fragment = null;
        switch (fragTag) {
            case FRAG_TAG_ADDRESS:
                checkoutStage = 1;
                fragment = new CheckoutAddressFragment();
                break;
            case FRAG_TAG_PAYMENT_MODE:
                checkoutStage = 2;
                fragment = new CheckoutPaymentModeFragment();
                break;
            case FRAG_TAG_CONFIRM:
                checkoutStage = 3;
                fragment = new CheckoutConfirmFragment();
                break;
        }

        setupViews();

        // Sometimes, when fragment has huge data, screen seems hanging
        // when switching between navigation menus
        // So using runnable, the fragment is loaded with cross fade effect
        // This effect can be seen in GMail app
        final Fragment finalFragment = fragment;
        Runnable mPendingRunnable = new Runnable() {
            @Override
            public void run() {
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);
                fragmentTransaction.add(R.id.checkoutFrame, finalFragment, fragTag);
                fragmentTransaction.addToBackStack(fragTag);
                fragmentTransaction.commitAllowingStateLoss();
            }
        };

        if (mHandler == null)
            mHandler = new Handler();
        if (getSupportFragmentManager().findFragmentByTag(fragTag) == null)
            mHandler.post(mPendingRunnable);

    }

    private void setupViews() {
        getSupportActionBar().setTitle(checkoutStage == 1 ? FRAG_TAG_ADDRESS : checkoutStage == 2 ? FRAG_TAG_CONFIRM : FRAG_TAG_PAYMENT_MODE);
        checkoutActionText.setText(checkoutStage == 1 ? "Proceed to payment" : checkoutStage == 2 ? "Confirm order" : "Confirm & pay");
        checkoutStageHeading1.setTextColor(ContextCompat.getColor(this, checkoutStage == 1 ? R.color.colorAccent : android.R.color.darker_gray));
        checkoutStageHeading2.setTextColor(ContextCompat.getColor(this, checkoutStage == 2 ? R.color.colorAccent : android.R.color.darker_gray));
        checkoutStageHeading3.setTextColor(ContextCompat.getColor(this, checkoutStage == 3 ? R.color.colorAccent : android.R.color.darker_gray));
        checkoutStageHeading1.setCompoundDrawablesWithIntrinsicBounds(null, null, null, ContextCompat.getDrawable(this, checkoutStage == 1 ? R.drawable.ic_local_mall_accent_24dp : R.drawable.ic_local_mall_gray_24dp));
        checkoutStageHeading2.setCompoundDrawablesWithIntrinsicBounds(null, null, null, ContextCompat.getDrawable(this, checkoutStage == 2 ? R.drawable.ic_credit_card_accent_24dp : R.drawable.ic_credit_card_gray_24dp));
        checkoutStageHeading3.setCompoundDrawablesWithIntrinsicBounds(null, null, null, ContextCompat.getDrawable(this, checkoutStage == 3 ? R.drawable.ic_assignment_turned_in_accent_24dp : R.drawable.ic_assignment_turned_in_gray_24dp));
    }

    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() == 1) {
            finish();
        } else {
            super.onBackPressed();
            checkoutStage = getSupportFragmentManager().getBackStackEntryCount();
            setupViews();
        }
    }
}

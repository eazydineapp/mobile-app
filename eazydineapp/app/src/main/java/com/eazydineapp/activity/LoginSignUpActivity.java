package com.eazydineapp.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;


import com.eazydineapp.R;
import com.eazydineapp.fragment.LoginSignUpTabFragment;
import com.eazydineapp.util.IntentKeyConstants;

import butterknife.ButterKnife;

public class LoginSignUpActivity extends AppCompatActivity {
    private int tabPosition;
    private static final String TAG = LoginSignUpActivity.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_sign_up);
        ButterKnife.bind(this);
        tabPosition = getIntent().getIntExtra(IntentKeyConstants.TAB_POSITION, 0);
        openLoginSIgnUpTabFragment();
    }

    private void openLoginSIgnUpTabFragment() {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.splashFrame, LoginSignUpTabFragment.newInstance(tabPosition), TAG);
        fragmentTransaction.addToBackStack(TAG);
        fragmentTransaction.commit();
    }

    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() == 1) {
            finish();
        } else {
            super.onBackPressed();
        }
    }
}
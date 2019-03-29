package com.eazydineapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;

import com.eazydineapp.R;
import com.eazydineapp.util.IntentKeyConstants;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SplashActivity extends AppCompatActivity {

    @BindView(R.id.splash_register_btn)
    Button registerBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);
/*
        ActionBar actionBar =  getSupportActionBar();
        actionBar.hide();
*/
    }



    @OnClick({R.id.splash_register_btn})
    public void onClickRegister() {
        openLoginSignUpActivity(1);
    }

    private void openLoginSignUpActivity(int position) {
        Intent logiSignUpIntent = new Intent(this, LoginSignUpActivity.class);
        logiSignUpIntent.putExtra(IntentKeyConstants.TAB_POSITION, position);
        logiSignUpIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(logiSignUpIntent);
        finish();
    }
}
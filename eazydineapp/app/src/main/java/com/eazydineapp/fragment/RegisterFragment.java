package com.eazydineapp.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.eazydineapp.R;
import com.eazydineapp.backend.util.AndroidStoragePrefUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by user on 1/25/2018.
 */

public class RegisterFragment extends Fragment {


    @BindView(R.id.register_btn)
    Button mRegisterBtn;

    private ViewPager mViewPager;
    private static final String TAG = RegisterFragment.class.getName();

    public RegisterFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_signup_layout, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mViewPager = (ViewPager) getActivity().findViewById(R.id.auth_viewpager);
    }

    @OnClick({R.id.register_btn})
    public void onClickRegister() {

        EditText phoneNumber = getActivity().findViewById(R.id.phoneNumber);
        AndroidStoragePrefUtil storagePrefUtil = new AndroidStoragePrefUtil();
        storagePrefUtil.saveRegisteredUser(this, phoneNumber.getText().toString());

        Fragment verificationCodeFragment = new VerificationCodeFragment();
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.splashFrame, verificationCodeFragment, TAG);
        fragmentTransaction.addToBackStack(TAG);
        fragmentTransaction.commit();
    }



}

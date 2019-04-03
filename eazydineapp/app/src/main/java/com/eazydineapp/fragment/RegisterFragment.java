package com.eazydineapp.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.eazydineapp.R;
import com.eazydineapp.model.User;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by user on 1/25/2018.
 */

public class RegisterFragment extends Fragment {

     EditText email, pswd, confirmpswd, pnumber;
     TextView errorTextView;

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
        email = (EditText)  getActivity().findViewById(R.id.email);
        pswd = (EditText)  getActivity().findViewById(R.id.password);
        pnumber = (EditText)  getActivity().findViewById(R.id.phone);
        confirmpswd = (EditText)  getActivity().findViewById(R.id.confirmpassword);
        errorTextView = (TextView) getActivity().findViewById(R.id.errorTextView);
    }

    @OnClick({R.id.switchSignIn})
    public void onClickSignIn(){
        Fragment signInFragment = new SignInFragment();
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.splashFrame, signInFragment, TAG);
        fragmentTransaction.addToBackStack(TAG);
        fragmentTransaction.commit();
    }

    @OnClick({R.id.register_btn})
    public void onClickRegister() {
        String emailID = email.getText().toString();
        String password = pswd.getText().toString();
        String phonenumber = pnumber.getText().toString();
        String confirmpassword = confirmpswd.getText().toString();

        if(!password.equals(confirmpassword)){
            errorTextView.append("Password is not matching.");
        }

        if(emailID.isEmpty()  || password.isEmpty() || phonenumber.isEmpty() || phonenumber.length() < 10){
            errorTextView.append("Invalid Entry");
            errorTextView.requestFocus();
        }

        else {
            Bundle bundle = new Bundle();
            bundle.putString("email", emailID);
            bundle.putString("password", password);
            bundle.putString("phone", phonenumber);
            Fragment verificationCodeFragment = new VerificationCodeFragment();
            verificationCodeFragment.setArguments(bundle);
            FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.splashFrame, verificationCodeFragment, TAG);
            fragmentTransaction.addToBackStack(TAG);
            fragmentTransaction.commit();
        }

    }

}

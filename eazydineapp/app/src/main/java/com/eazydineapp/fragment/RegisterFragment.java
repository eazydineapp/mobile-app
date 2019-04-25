package com.eazydineapp.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import com.eazydineapp.R;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by user on 1/25/2018.
 */

public class RegisterFragment extends Fragment {

    EditText pnumber;
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
        pnumber = (EditText)  getActivity().findViewById(R.id.phone);
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
        String phonenumber = pnumber.getText().toString();

        if(phonenumber.isEmpty() || (phonenumber.length() < 10 && phonenumber.length() > 15)){
            errorTextView.append("Invalid Entry");
            errorTextView.requestFocus();
        }

        else {
            if(phonenumber.length() == 10) {
                phonenumber = "+1"+phonenumber;
            }

            Bundle bundle = new Bundle();
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
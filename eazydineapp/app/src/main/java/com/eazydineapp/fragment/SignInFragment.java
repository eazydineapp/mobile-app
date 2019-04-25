package com.eazydineapp.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.eazydineapp.R;
import com.eazydineapp.activity.MainActivity;
import com.eazydineapp.backend.util.AndroidStoragePrefUtil;
import com.eazydineapp.interactor.AuthInnerInteractor;

import butterknife.OnClick;

public class SignInFragment extends Fragment {
    private AuthInnerInteractor innerInteractor;
    EditText phoneNumber;
    TextView errorTextView;
    private static final String TAG = SignInFragment.class.getName();

    public SignInFragment() {
        // Required empty public constructor
    }

    public static SignInFragment newInstance(AuthInnerInteractor innerInteractor) {
        SignInFragment fragment = new SignInFragment();
        fragment.innerInteractor = innerInteractor;
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_sign_in, container, false);
        phoneNumber = (EditText)  view.findViewById(R.id.phoneNumber);
        errorTextView = (TextView) view.findViewById(R.id.errorTextView);
        final Fragment thisFragment = this;
        view.findViewById(R.id.signIn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String phoneNumberStr = phoneNumber.getText().toString();

                if(phoneNumberStr.isEmpty() || (phoneNumberStr.length() < 10 && phoneNumberStr.length() > 15)){
                    errorTextView.append("Invalid entry");
                    errorTextView.requestFocus();
                }
                else {
                    if(phoneNumberStr.length() == 10) {
                        phoneNumberStr = "+1"+phoneNumberStr;
                    }
                    AndroidStoragePrefUtil storagePrefUtil = new AndroidStoragePrefUtil();
                    storagePrefUtil.saveRegisteredUser(thisFragment, phoneNumberStr);
                    Intent intent = new Intent(getActivity(), MainActivity.class);
                    getActivity().startActivity(intent);
                    getActivity().finish();
                }
            }
        });
        view.findViewById(R.id.forgetPassword).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment forgotPasswordFragment = new ForgetPasswordFragment();
                FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.splashFrame, forgotPasswordFragment, TAG);
                fragmentTransaction.addToBackStack(TAG);
                fragmentTransaction.commit();
            }
        });
        view.findViewById(R.id.switchSignUp).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment signUpFragment = new RegisterFragment();
                FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.splashFrame, signUpFragment, TAG);
                fragmentTransaction.addToBackStack(TAG);
                fragmentTransaction.commit();
            }
        });
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}
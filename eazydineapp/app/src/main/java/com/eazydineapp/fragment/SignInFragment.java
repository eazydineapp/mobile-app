package com.eazydineapp.fragment;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.eazydineapp.R;
import com.eazydineapp.activity.MainActivity;
import com.eazydineapp.interactor.AuthInnerInteractor;

import butterknife.OnClick;

public class SignInFragment extends Fragment {
    private AuthInnerInteractor innerInteractor;
    EditText email, pswd;
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
        email = (EditText)  view.findViewById(R.id.email);
        pswd = (EditText)  view.findViewById(R.id.password);
        errorTextView = (TextView) view.findViewById(R.id.errorTextView);
        view.findViewById(R.id.signIn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String emailID = email.getText().toString();
                String password = pswd.getText().toString();

                if(emailID.isEmpty() || password.isEmpty()){
                    errorTextView.append("Invalid entry");
                    errorTextView.requestFocus();
                }
                else if(emailID.length() > 0  && password.length() > 0 ){
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

package com.eazydineapp.fragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.eazydineapp.R;
import com.eazydineapp.interactor.AuthInnerInteractor;

public class ForgetPasswordFragment extends Fragment {
    private AuthInnerInteractor innerInteractor;

    public ForgetPasswordFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_forget_password, container, false);
        view.findViewById(R.id.forgot_pass_back_iv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                innerInteractor.popForgetPassword();
            }
        });
        view.findViewById(R.id.forgot_pass_login_tv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                innerInteractor.popForgetPassword();
            }
        });
        return view;
    }

    public static ForgetPasswordFragment newInstance(AuthInnerInteractor innerInteractor) {
        ForgetPasswordFragment fragment = new ForgetPasswordFragment();
        fragment.innerInteractor = innerInteractor;
        return fragment;
    }
}

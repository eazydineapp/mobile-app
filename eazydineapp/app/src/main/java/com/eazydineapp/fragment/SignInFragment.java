package com.eazydineapp.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.eazydineapp.R;
import com.eazydineapp.backend.util.AndroidStoragePrefUtil;
import com.eazydineapp.interactor.AuthInnerInteractor;

public class SignInFragment extends Fragment {
    private AuthInnerInteractor innerInteractor;


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
        final Fragment thisFragment = this;
        View view = inflater.inflate(R.layout.fragment_sign_in, container, false);
        view.findViewById(R.id.signIn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText phoneNumber = getActivity().findViewById(R.id.phoneNumber);
                AndroidStoragePrefUtil storagePrefUtil = new AndroidStoragePrefUtil();
                storagePrefUtil.saveRegisteredUser(thisFragment, phoneNumber.getText().toString());
                innerInteractor.switchToMain();
            }
        });
        view.findViewById(R.id.forgetPassword).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                innerInteractor.switchToForgetPassword();
            }
        });
        return view;
    }

}

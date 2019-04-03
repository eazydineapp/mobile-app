package com.eazydineapp.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.eazydineapp.R;
import com.eazydineapp.activity.MainActivity;
import com.eazydineapp.location.LocationActivity;
import com.eazydineapp.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskExecutors;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by user on 1/28/2018.
 */

public class VerificationCodeFragment extends Fragment {

    String emailID, password, phoneNumber;
    private static final String TAG = VerificationCodeFragment.class.getName();
    EditText verification_code_otp_et;
    TextView errorTextView, dynamicPhoneNumber;

    // Firebase database connection initialization
    final FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference ref = database.getReference();
    DatabaseReference usersRef = ref.child("users");

    @BindView(R.id.verification_code_back_iv)
    ImageView mBackIv;
    @BindView(R.id.verification_code_btn)
    Button mVerifyBtn;
    private Context mContext;
    private Activity mActivity;

    String mVerificationId, code;
    PhoneAuthProvider.ForceResendingToken  mResendToken;
    //firebase auth object
    FirebaseAuth mAuth;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Bundle bundle = getArguments();
        emailID = bundle.getString("email");
        password = bundle.getString("password");
        phoneNumber = bundle.getString("phone");
        View view=inflater.inflate(R.layout.fragment_verification_code_layout,container,false);
        verification_code_otp_et = view.findViewById(R.id.verification_code_otp_et);
        errorTextView = view.findViewById(R.id.errorTextView);
        dynamicPhoneNumber = view.findViewById(R.id.dynamicPhoneNumber);
        Log.d(TAG, "User Data:" + emailID + "  " + password + " " + phoneNumber);
        if(phoneNumber.length() == 10){
            mVerificationId = phoneNumber;
            dynamicPhoneNumber.setText(phoneNumber);
            sendVerificationCode(phoneNumber);
        }
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this,view);
        mContext = getContext();
        mActivity = getActivity();
    }

    @OnClick({R.id.verification_code_back_iv})
    public void onClickBack() {
        getActivity().onBackPressed();
    }

    @OnClick({R.id.verification_code_btn})
    public void onClickVerificationBtn() {
        code = verification_code_otp_et.getText().toString().trim();
//        if (code.isEmpty() || code.length() < 6) {
//            errorTextView.setError("Enter valid code");
//            errorTextView.requestFocus();
//            return;
//        }
//        else {
//             //verifying the code entered manually
//             verifyVerificationCode(code);
//        }
        createUser(emailID, password, phoneNumber);
        //verification successful we will start the Main activity
        Intent intent = new Intent(mActivity, MainActivity.class);
        mActivity.startActivity(intent);
        mActivity.finish();
    }

    private void createUser(String emailID, String password, String phonenumber){
        User user = new User(emailID, password, phonenumber);
        usersRef.setValue(user);
        Log.d(TAG, "Created User:" + user.getEmail() + "  " + user.getPassword() + " " + user.getPhoneNumber() );
    }


    private void sendVerificationCode(String mobile) {
        Log.d(TAG, "Inside sendVerificationCode method" );
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                "+1" + mobile,
                60,
                TimeUnit.SECONDS,
                TaskExecutors.MAIN_THREAD,
                mCallbacks);

    }


    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        @Override
        public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
            Log.d(TAG, "onVerificationCompleted:" + phoneAuthCredential);

            //Getting the code sent by SMS
            code = phoneAuthCredential.getSmsCode();
            Log.d(TAG,  "\n\nOTP is " + code.toString());

            //sometime the code is not detected automatically
            //in this case the code will be null
            //so user has to manually enter the code
            if (code != null) {
                verification_code_otp_et.setText(code);
            }
        }

        @Override
        public void onVerificationFailed(FirebaseException e) {
            Toast.makeText(mActivity, e.getMessage(), Toast.LENGTH_LONG).show();
        }

        @Override
        public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            Log.d(TAG, "Message Sent to " + s.toString());
            mVerificationId = s;
            mResendToken = forceResendingToken;
        }
    };

    private void verifyVerificationCode(String otp) {
        //creating the credential
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(mVerificationId, otp);
        //signing the user
        signInWithPhoneAuthCredential(credential);
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(mActivity, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "signInWithCredential:success");
                            createUser(emailID, password, phoneNumber);
                            //verification successful we will start the Main activity
                            Intent intent = new Intent(mActivity, MainActivity.class);
                            mActivity.startActivity(intent);
                            mActivity.finish();
                        } else {

                            //verification unsuccessful.. display an error message
                            String message = "Something is wrong, we will fix it soon...";
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                message = "Invalid code entered...";
                            }
                            Toast.makeText(mActivity,  message, Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }
}
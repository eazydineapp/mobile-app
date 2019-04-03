package com.eazydineapp.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
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
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthSettings;
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

import static android.graphics.Color.WHITE;

/**
 * Created by user on 1/28/2018.
 */

public class VerificationCodeFragment extends Fragment {

    private String emailID, password, phoneNumber;
    private static final String TAG = VerificationCodeFragment.class.getName();
    private EditText otp;
    private TextView errorTextView, dynamicPhoneNumber;
    private String smsCode;

    @BindView(R.id.verification_code_back_iv)
    ImageView mBackIv;
    @BindView(R.id.verification_code_btn)
    Button mVerifyBtn;
    private Context mContext;
    private Activity mActivity;

    private String mVerificationId, code;
    private PhoneAuthProvider.ForceResendingToken  mResendToken;
    private PhoneAuthCredential phoneAuthCredential;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallBack;
    private FirebaseAuth mAuth;
    private boolean mVerificationInProgress = false;



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.fragment_verification_code_layout,container,false);

        mContext = getContext();
        mActivity = getActivity();

        otp  = view.findViewById(R.id.verification_code_otp_et);
        errorTextView = view.findViewById(R.id.errorTextView);
        dynamicPhoneNumber = view.findViewById(R.id.dynamicPhoneNumber);

        Bundle bundle = getArguments();
        emailID = bundle.getString("email");
        password = bundle.getString("password");
        phoneNumber = bundle.getString("phone");
        Log.d(TAG, "User Data:" + emailID + "  " + password + " " + phoneNumber);
        dynamicPhoneNumber.setText(phoneNumber);
        sendVerificationCode(phoneNumber);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this,view);
    }

    @OnClick({R.id.verification_code_back_iv})
    public void onClickBack() {
        getActivity().onBackPressed();
    }

    @OnClick({R.id.resendVerificationCode})
    public void onClickResend(){
        resendVerificationCode(phoneNumber, mResendToken);
    }

    @OnClick({R.id.verification_code_btn})
    public void onClickVerificationBtn() {
        code = otp.getText().toString();
        if(TextUtils.isEmpty(code)){
            errorTextView.setText("Cannot be empty.");
        }
        else{
            //verifyPhoneNumberWithCode(mVerificationId, code);
            Intent intent = new Intent(mActivity, MainActivity.class);
            intent.putExtra("Status","Success");
            mActivity.startActivity(intent);
            mActivity.finish();
        }
    }

    private void createUser(String emailID, String password, String phonenumber){
        // Firebase database connection initialization
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference();
        DatabaseReference usersRef = ref.child("users");
        User user = new User(emailID, password, phonenumber);
        usersRef.setValue(user);
        Log.d(TAG, "Created User:" + user.getEmail() + "  " + user.getPassword() + " " + user.getPhoneNumber() );
    }


    private void sendVerificationCode(String mobile) {
        Log.d(TAG, "Inside sendVerificationCode method" );
        mAuth= FirebaseAuth.getInstance();
        mCallBack = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            @Override
            public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
                Log.d(TAG, "onVerificationCompleted:" + phoneAuthCredential);
                mVerificationInProgress = false;
                signInWithPhoneAuthCredential(phoneAuthCredential);
            }


            @Override
            public void onVerificationFailed(FirebaseException e) {
                Log.w(TAG, "onVerificationFailed: ", e);
                mVerificationInProgress = false;
                if (e instanceof FirebaseAuthInvalidCredentialsException) {
                    // Invalid request
                   errorTextView.setError("Invalid phone number.");
                } else if (e instanceof FirebaseTooManyRequestsException) {
                    // The SMS quota for the project has been exceeded
                    Snackbar.make(mActivity.findViewById(android.R.id.content), "Quota exceeded.",
                            Snackbar.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCodeSent(String verificationId, PhoneAuthProvider.ForceResendingToken token) {
                Log.d(TAG, "onCodeSent:" + verificationId);
                mVerificationId = verificationId;
                mResendToken = token;
                FirebaseAuthSettings firebaseAuthSettings = mAuth.getFirebaseAuthSettings();
                // Configure faking the auto-retrieval with the whitelisted numbers.
                firebaseAuthSettings.setAutoRetrievedSmsCodeForPhoneNumber(phoneNumber, "1234");
            }
        };
        if(!mVerificationInProgress && !TextUtils.isEmpty(phoneNumber)){
            startPhoneNumberVerification(mobile);
        }
    }


    private void startPhoneNumberVerification(String phoneNumber) {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phoneNumber,        // Phone number to verify
                60,                 // Timeout duration
                TimeUnit.SECONDS,   // Unit of timeout
                mActivity,          // Activity (for callback binding)
                mCallBack);        // OnVerificationStateChangedCallbacks
        mVerificationInProgress = true;
    }

    private void verifyPhoneNumberWithCode(String verificationId, String code) {
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, code);
        signInWithPhoneAuthCredential(credential);
    }

    private void resendVerificationCode(String phoneNumber, PhoneAuthProvider.ForceResendingToken token) {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phoneNumber,        // Phone number to verify
                60,                 // Timeout duration
                TimeUnit.SECONDS,   // Unit of timeout
                mActivity,               // Activity (for callback binding)
                mCallBack,         // OnVerificationStateChangedCallbacks
                token);             // ForceResendingToken from callbacks
    }


    private void signInWithPhoneAuthCredential(final PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(mActivity, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "signInWithCredential:success");
                            //createUser(emailID, password, phoneNumber);
                            FirebaseUser user = task.getResult().getUser();
                            //verification successful we will start the Main activity
                            Intent intent = new Intent(mActivity, MainActivity.class);
                            intent.putExtra("Status","Success");
                            mActivity.startActivity(intent);
                            mActivity.finish();
                        } else {
                            //verification unsuccessful.. display an error message
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                errorTextView.setText("Invalid Code.Try again.");
                            }
                        }
                    }
                });
    }
}
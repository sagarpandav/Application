package com.example.mitul.application;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class PhoneVerificationActivity extends AppCompatActivity {

    @BindView(R.id.editText_phone) EditText phone;
    @BindView(R.id.editText_OTP) EditText otp;

    private FirebaseAuth firebaseAuth;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallback;

    private String verificationCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_verification);

        ButterKnife.bind(this);

        firebaseAuth = FirebaseAuth.getInstance();

        mCallback = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
                signInWithPhone(phoneAuthCredential);
            }

            @Override
            public void onVerificationFailed(FirebaseException e) {
                Log.e("PhoneActivity", "Verification Falied");
            }

            @Override
            public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                super.onCodeSent(s, forceResendingToken);

                verificationCode = s;
                createToast("Code Sent Successfully");
            }
        };
    }

    private void createToast(String str) {
        Toast.makeText(this, str, Toast.LENGTH_SHORT).show();
    }

    @OnClick(R.id.button_sendNumber)
    public void onSendSms(){
        String number = phone.getText().toString();
        PhoneAuthProvider.getInstance().verifyPhoneNumber(number, 60, TimeUnit.SECONDS, this, mCallback);
    }

    public void signInWithPhone(PhoneAuthCredential authCredential){
        firebaseAuth.signInWithCredential(authCredential)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            createToast("Verified");
                        }
                    }
                });
    }

    @OnClick(R.id.button_verify)
    public void onVerify(){
        String inputCode = otp.getText().toString();
        if (!inputCode.equals("")){

            PhoneAuthCredential credential= PhoneAuthProvider.getCredential(verificationCode, inputCode);
            signInWithPhone(credential);
        }
    }
}

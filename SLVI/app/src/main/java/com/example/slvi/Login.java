package com.example.slvi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.slvi.databinding.ActivityLoginBinding;
import com.example.slvi.databinding.ActivitySignUpBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.annotations.NotNull;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class Login extends AppCompatActivity {

    ActivityLoginBinding binding;
    FirebaseAuth firebaseAuth;
    private ProgressDialog loadingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        firebaseAuth = FirebaseAuth.getInstance();
        loadingBar = new ProgressDialog(this);

        binding.comSignuplink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Login.this, SignUp.class);
                startActivity(i);
            }
        });
        binding.forgotComPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (TextUtils.isEmpty(binding.emailInput.getText())) {
                    Toast.makeText(Login.this, "Please enter your email address", Toast.LENGTH_SHORT).show();

                } else {
                    FirebaseAuth auth = FirebaseAuth.getInstance();
                    String emailAddress = binding.emailInput.getText().toString().trim();

                    auth.sendPasswordResetEmail(emailAddress)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(Login.this, "We have sent you an email to reset password to "+emailAddress+"", Toast.LENGTH_LONG).show();
                                    }
                                }
                            });
                }
            }
        });


        binding.ComSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String email = binding.emailInput.getText().toString();
                String password = binding.passwordInput.getText().toString();

                if (Patterns.EMAIL_ADDRESS.matcher(email).matches()){

                    loadingBar.setTitle("Sign In");
                    loadingBar.setMessage("Please wait we are checking your credentials..");
                    loadingBar.setCanceledOnTouchOutside(false);
                    loadingBar.show();

                    firebaseAuth.signInWithEmailAndPassword(email,password)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull @NotNull Task<AuthResult> task) {

                                    if (task.isSuccessful()){
                                        FirebaseUser user = firebaseAuth.getCurrentUser();
                                        String uid = user.getUid();
                                        Toast.makeText(Login.this,"Successfully Logged In",Toast.LENGTH_LONG).show();
                                        Intent i = new Intent(getApplicationContext(), MainActivity.class);
                                        startActivity(i);
                                        loadingBar.dismiss();
                                        finish();

                                    }else {
                                        loadingBar.dismiss();
                                        new SweetAlertDialog(Login.this, SweetAlertDialog.WARNING_TYPE)
                                                .setTitleText("Login failed!")
                                                .setContentText("Please check the login credentials")
                                                .show();

                                    }

                                }
                            });
                }else{
                    binding.emailInput.setError("Enter a valid Email address");
                    binding.emailInput.requestFocus();

                }
            }
        });
    }
}
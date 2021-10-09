package com.example.slvi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.example.slvi.databinding.ActivitySignUpBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;


public class SignUp extends AppCompatActivity {

    FirebaseAuth firebaseAuth;
    ActivitySignUpBinding binding;
    Dialog dialog;
    private ProgressDialog loadingBar;
    public String buttonClick;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignUpBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.userSigninLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(SignUp.this, Login.class);
                buttonClick = "Login";
                Boolean buttonClickValidity = buttonClickValidation(buttonClick);
                if(buttonClickValidity){
                    startActivity(i);
                }
            }
        });

        firebaseAuth = FirebaseAuth.getInstance();
        loadingBar = new ProgressDialog(this);

        binding.userSignupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String name = binding.userNameInput.getText().toString();
                String email = binding.emailInput.getText().toString();
                String password = binding.passwordInput.getText().toString();
                String confirmPassword = binding.confirmPasswordInput.getText().toString();

                if(!name.isEmpty()){
                    if (Patterns.EMAIL_ADDRESS.matcher(email).matches())
                    {
                        if (password.matches(confirmPassword) && password.length() >= 8)
                        {
                            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),0);

                            loadingBar.setTitle("Create Account");
                            loadingBar.setMessage("Please wait, We are creating your account..");
                            loadingBar.setCanceledOnTouchOutside(false);
                            loadingBar.show();

                            firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {

                                    Date date = Calendar.getInstance().getTime();
                                    System.out.println("Current time => " + date);

                                    SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault());
                                    String formattedDate = df.format(date);

                                    if (task.isSuccessful()) {
                                        Users user = new Users(name, email,formattedDate);

                                        FirebaseDatabase.getInstance().getReference("Users")
                                                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                                .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {
                                                    Toast.makeText(SignUp.this, "Successfully created", Toast.LENGTH_SHORT).show();
                                                    FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
                                                    if (firebaseUser != null) {
                                                        Intent i = new Intent(SignUp.this, Login.class);
                                                        startActivity(i);
                                                        finish();
                                                    }
                                                }else {
                                                    Toast.makeText(SignUp.this, "User Registration failed...Please try again", Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        });

                                    }else {
                                        Toast.makeText(SignUp.this, "User Registration failed", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        }
                        else if (password.length() < 8){
                            binding.passwordInput.setError("Password must have at least 8 characters");
                            binding.passwordInput.requestFocus();
                        }else {

                            binding.confirmPasswordInput.setError("Password doesn't match");
                            binding.confirmPasswordInput.requestFocus();
                        }
                    }else {

                        binding.emailInput.setError("Please provide a valid email");
                        binding.emailInput.requestFocus();
                    }
                }else {
                    binding.userNameInput.setError("Please enter a name");
                    binding.userNameInput.requestFocus();
                }
            }
        });
    }

    public static Boolean buttonClickValidation(String clickedButton){
        if(clickedButton.equals("Login")){
            return true;
        }else{
            return false;
        }
    }
}
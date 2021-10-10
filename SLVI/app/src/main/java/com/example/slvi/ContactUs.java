package com.example.slvi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast ;
import cn.pedant.SweetAlert.SweetAlertDialog ;
import android.graphics.Color;

import com.example.slvi.databinding.ActivityContactUsBinding;
import com.example.slvi.databinding.ActivitySignUpBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

public class ContactUs extends AppCompatActivity {

    ActivityContactUsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityContactUsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        binding.contactSubBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String name = binding.nameInput.getText().toString();
                String email = binding.emailinput.getText().toString();
                String message = binding.msgInput.getText().toString();
                String reply ="Pending Reply...";

                if(TextUtils.isEmpty(name) || TextUtils.isEmpty(email) || TextUtils.isEmpty(message)){
                    Toast.makeText(getApplicationContext(), "Please fill all the fields", Toast.LENGTH_SHORT).show();
                }
                else {

                    Inquiry inquiry = new Inquiry(name, email, message,reply);

                    FirebaseDatabase.getInstance().getReference("Inquiry")
                            .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                            .setValue(inquiry).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                new SweetAlertDialog(ContactUs.this, SweetAlertDialog.SUCCESS_TYPE)
                                        .setTitleText("Successfully Submitted")
                                        .setContentText("We will look into your problem as soon as possible.")
                                        .show();
                                resetData();
                            }else {
                                new SweetAlertDialog(ContactUs.this, SweetAlertDialog.WARNING_TYPE)
                                        .setTitleText("Something Went Wrong")
                                        .setContentText("Submission Failed. Please try again later.")
                                        .show();
                            }
                        }
                    });
                }
            }
        });

    }

    private void resetData() {
        binding.nameInput.setText("");
        binding.emailinput.setText("");
        binding.msgInput.setText("");
    }
}
package com.example.slvi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.slvi.databinding.ActivityProfileBinding;
import com.example.slvi.databinding.ActivitySubmittedInquiryBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class SubmittedInquiry extends AppCompatActivity {

    ActivitySubmittedInquiryBinding binding;
    DatabaseReference dbRef;
    String reply = "Pending Reply...";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySubmittedInquiryBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser() ;
        String userId = currentFirebaseUser.getUid();

        dbRef = FirebaseDatabase.getInstance().getReference("Inquiry/"+userId);
        dbRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.hasChildren()) {
                    binding.nameView.setText(snapshot.child("name").getValue().toString());
                    binding.emailView.setText(snapshot.child("email").getValue().toString());
                    binding.inquiryView.setText(snapshot.child("message").getValue().toString());
                    reply = snapshot.child("reply").getValue().toString();
                    binding.replyView.setText(reply);

                    if (reply.equals("Pending Reply...")){
                        SweetAlertDialog alertDialog = new SweetAlertDialog(SubmittedInquiry.this, SweetAlertDialog.CUSTOM_IMAGE_TYPE);
                        alertDialog.setTitleText("Your Inquiry is still Pending");
                        alertDialog.setContentText("We are trying our best to get connected with you.");
                        alertDialog.setCustomImage(R.drawable.pending);
                        alertDialog.show();
                    }
                }
                else{
                    new SweetAlertDialog(SubmittedInquiry.this, SweetAlertDialog.WARNING_TYPE)
                            .setTitleText("No Inquiry Submitted")
                            .setContentText("You have not submitted any inquiries yet.")
                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sweetAlertDialog) {
                                    finish();
                                }
                            })
                            .show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
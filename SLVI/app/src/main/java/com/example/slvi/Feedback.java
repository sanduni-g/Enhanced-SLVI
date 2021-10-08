package com.example.slvi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.slvi.databinding.ActivityContactUsBinding;
import com.example.slvi.databinding.ActivityFeedbackBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class Feedback extends AppCompatActivity {

    ActivityFeedbackBinding binding;
    String experience = "initial";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityFeedbackBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.feedbackWorst.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.feedbackWorst.setBackground(null);
                binding.feedbackBad.setBackground(null);
                binding.feedbackNormal.setBackground(null);
                binding.feedbackGood.setBackground(null);
                binding.feedbackBest.setBackground(null);

                Drawable highlight = getResources().getDrawable(R.drawable.highlight);
                binding.feedbackWorst.setBackground(highlight);
                experience = "Worst";
                Toast.makeText(getApplicationContext(), "Worst User Experience", Toast.LENGTH_SHORT).show();
            }
        });

        binding.feedbackBad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.feedbackWorst.setBackground(null);
                binding.feedbackBad.setBackground(null);
                binding.feedbackNormal.setBackground(null);
                binding.feedbackGood.setBackground(null);
                binding.feedbackBest.setBackground(null);

                Drawable highlight = getResources().getDrawable(R.drawable.highlight);
                binding.feedbackBad.setBackground(highlight);
                experience = "Bad";
                Toast.makeText(getApplicationContext(), "Bad User Experience", Toast.LENGTH_SHORT).show();
            }
        });

        binding.feedbackNormal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.feedbackWorst.setBackground(null);
                binding.feedbackBad.setBackground(null);
                binding.feedbackNormal.setBackground(null);
                binding.feedbackGood.setBackground(null);
                binding.feedbackBest.setBackground(null);

                Drawable highlight = getResources().getDrawable(R.drawable.highlight);
                binding.feedbackNormal.setBackground(highlight);
                experience = "Neutral";
                Toast.makeText(getApplicationContext(), "Normal User Experience", Toast.LENGTH_SHORT).show();
            }
        });

        binding.feedbackGood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.feedbackWorst.setBackground(null);
                binding.feedbackBad.setBackground(null);
                binding.feedbackNormal.setBackground(null);
                binding.feedbackGood.setBackground(null);
                binding.feedbackBest.setBackground(null);

                Drawable highlight = getResources().getDrawable(R.drawable.highlight);
                binding.feedbackGood.setBackground(highlight);
                experience = "Good";
                Toast.makeText(getApplicationContext(), "Good User Experience", Toast.LENGTH_SHORT).show();
            }
        });

        binding.feedbackBest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.feedbackWorst.setBackground(null);
                binding.feedbackBad.setBackground(null);
                binding.feedbackNormal.setBackground(null);
                binding.feedbackGood.setBackground(null);
                binding.feedbackBest.setBackground(null);

                Drawable highlight = getResources().getDrawable(R.drawable.highlight);
                binding.feedbackBest.setBackground(highlight);
                experience = "Best";
                Toast.makeText(getApplicationContext(), "Best User Experience", Toast.LENGTH_SHORT).show();
            }
        });

        binding.imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        binding.feedbackSubBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String message = binding.msgInput.getText().toString();

                if (experience == "initial"){
                    Toast.makeText(getApplicationContext(), "Please rate your user experience before submission.", Toast.LENGTH_SHORT).show();
                } else  if (TextUtils.isEmpty(message)){
                    Toast.makeText(getApplicationContext(), "Please type the feedback message before submission.", Toast.LENGTH_SHORT).show();
                }
                else {
                    UserFeedback userFeedback = new UserFeedback(experience,message);

                    FirebaseDatabase.getInstance().getReference("UserFeedback")
                            .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                            .setValue(userFeedback).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                new SweetAlertDialog(Feedback.this, SweetAlertDialog.SUCCESS_TYPE)
                                        .setTitleText("Successfully Submitted")
                                        .setContentText("Thank you for sharing your valuable feedback.")
                                        .show();
                                resetData();
                            }else {
                                //Toast.makeText(ContactUs.this, "Submission Failed. Please try again", Toast.LENGTH_SHORT).show();
                                new SweetAlertDialog(Feedback.this, SweetAlertDialog.WARNING_TYPE)
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
        binding.feedbackWorst.setBackground(null);
        binding.feedbackBad.setBackground(null);
        binding.feedbackNormal.setBackground(null);
        binding.feedbackGood.setBackground(null);
        binding.feedbackBest.setBackground(null);

        experience = "initial";
        binding.msgInput.setText("");
    }
}
package com.example.slvi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.slvi.databinding.ActivityFeedbackBinding;
import com.example.slvi.databinding.ActivityOngoingVehicleNumberBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class OngoingVehicleNumber extends AppCompatActivity {

    String vehicleType = "not selected";
    ActivityOngoingVehicleNumberBinding binding;
    DatabaseReference dbRef;
    String vehicleImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityOngoingVehicleNumberBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.ambulance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                vehicleType = "Ambulance";
                vehicleImage = "ambulance";

                findNumber(vehicleType, vehicleImage);
            }
        });

        binding.car.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                vehicleType = "Car";
                vehicleImage = "car";

                findNumber(vehicleType, vehicleImage);
            }
        });

        binding.dualPurpose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                vehicleType = "Dual Purpose";
                vehicleImage = "bus";

                findNumber(vehicleType, vehicleImage);
            }
        });

        binding.cab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                vehicleType = "Cab";
                vehicleImage = "cab";

                findNumber(vehicleType, vehicleImage);
            }
        });

        binding.lorry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                vehicleType = "Lorry";
                vehicleImage = "lorry";

                findNumber(vehicleType, vehicleImage);
            }
        });

        binding.bike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                vehicleType = "Bike";
                vehicleImage = "bike";

                findNumber(vehicleType, vehicleImage);
            }
        });

        binding.threeWheeler.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                vehicleType = "Three Wheeler";
                vehicleImage = "three_wheel";

                findNumber(vehicleType, vehicleImage);
            }
        });

        binding.imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void findNumber(String vehicleType, String vehicleImage) {
        dbRef = FirebaseDatabase.getInstance().getReference("OnGoingNumber/"+vehicleType);
        dbRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.hasChildren()){
                    String nextNumber = snapshot.child("nextNumber").getValue().toString();
                    displayNumber(nextNumber, vehicleType, vehicleImage);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void displayNumber(String nextNumber, String vehicleType,String vehicleImage) {
        SweetAlertDialog alertDialog = new SweetAlertDialog(this, SweetAlertDialog.CUSTOM_IMAGE_TYPE);
        alertDialog.setTitleText(nextNumber);
        alertDialog.setContentText("will be the next number for " + vehicleType + " type vehicles.");
        alertDialog.setCustomImage(getResources().getIdentifier(vehicleImage, "drawable", getPackageName()));
        alertDialog.show();
    }
}
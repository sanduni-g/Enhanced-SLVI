package com.example.slvi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.slvi.databinding.ActivityRevenueDetailsBinding;
import com.example.slvi.databinding.ActivityViewVehicleDetailsBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class RevenueDetails extends AppCompatActivity {

    ActivityRevenueDetailsBinding binding;
    DatabaseReference dbRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRevenueDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        binding.searchIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String vehicleNumber = binding.vehicleNumberInput.getText().toString();

                if(TextUtils.isEmpty(vehicleNumber)){
                    Toast.makeText(getApplicationContext(), "Please Enter the Vehicle Number First", Toast.LENGTH_SHORT).show();
                }
                else {
                    int len = vehicleNumber.length();
                    Boolean vehicleNumberValidity = vehicleNumberValidation(len);
                    if(!vehicleNumberValidity){
                        new SweetAlertDialog(RevenueDetails.this, SweetAlertDialog.WARNING_TYPE)
                                .setTitleText("Please check the vehicle number again")
                                .setContentText("EX: CAA-7845")
                                .show();
                        resetData();
                    }else{
                        readData(vehicleNumber);
                    }
                }
            }
        });
    }

    private void readData(String vehicleNumber) {
        dbRef = FirebaseDatabase.getInstance().getReference("Vehicles/"+vehicleNumber);
        dbRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.hasChildren()){
                    binding.issuedDate.setText(snapshot.child("licenseIssuedDate").getValue().toString());
                    binding.expiryDate.setText(snapshot.child("licenseExpiryDate").getValue().toString());
                    binding.licenseNumber.setText(snapshot.child("licenseNumber").getValue().toString());

                }
                else{
                    Toast.makeText(getApplicationContext(), "No Details available for this vehicle number", Toast.LENGTH_SHORT).show();
//                    new SweetAlertDialog(ViewVehicleDetails.this, SweetAlertDialog.WARNING_TYPE)
//                            .setTitleText("Please check the vehicle number again")
//                            .setContentText("EX: CAA-7845")
//                            .show();
//                    resetData();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void resetData() {
        binding.issuedDate.setText("");
        binding.expiryDate.setText("");
        binding.licenseNumber.setText("");
    }

    public static Boolean vehicleNumberValidation(int vehicleNumLength){
        if(vehicleNumLength <= 6 ){
            return false;
        }else{
            return true;
        }
    }
}
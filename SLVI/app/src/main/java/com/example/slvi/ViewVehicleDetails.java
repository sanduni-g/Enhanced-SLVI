package com.example.slvi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.slvi.databinding.ActivityHomeBinding;
import com.example.slvi.databinding.ActivityViewVehicleDetailsBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class ViewVehicleDetails extends AppCompatActivity {

    ActivityViewVehicleDetailsBinding binding;
    DatabaseReference dbRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityViewVehicleDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.srilankaLink.setMovementMethod(LinkMovementMethod.getInstance());
        binding.motorLink.setMovementMethod(LinkMovementMethod.getInstance());

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
                        new SweetAlertDialog(ViewVehicleDetails.this, SweetAlertDialog.WARNING_TYPE)
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
                    binding.abowner.setText(snapshot.child("ABOwner").getValue().toString());
                    binding.engineNumber.setText(snapshot.child("engineNumber").getValue().toString());
                    binding.vehicleClass.setText(snapshot.child("class").getValue().toString());
                    binding.make.setText(snapshot.child("make").getValue().toString());
                    binding.model.setText(snapshot.child("model").getValue().toString());
                    binding.yom.setText(snapshot.child("yom").getValue().toString());
                    binding.chassisNumber.setText(snapshot.child("chassisNumber").getValue().toString());
                }
                else{
                    Toast.makeText(getApplicationContext(), "No Details available for this vehicle number", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private void resetData() {
        binding.abowner.setText("");
        binding.engineNumber.setText("");
        binding.vehicleClass.setText("");
        binding.make.setText("");
        binding.model.setText("");
        binding.yom.setText("");
        binding.chassisNumber.setText("");
    }

    public static Boolean vehicleNumberValidation(int vehicleNumLength){
        if(vehicleNumLength <= 6 ){
            return false;
        }else{
            return true;
        }
    }
}
package com.example.slvi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Menu;

import com.example.slvi.databinding.ActivityHomeBinding;
import com.example.slvi.databinding.ActivityMainBinding;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

import androidx.core.view.GravityCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewStub;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import cn.pedant.SweetAlert.SweetAlertDialog;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    LinearLayout imgVD;
    LinearLayout imgRD;
    LinearLayout imgOGV;
    LinearLayout imgProfile;
    View parent;

    @Override protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        parent = findViewById(R.id.parent_view);
        imgVD = parent.findViewById(R.id.btn_vehicle_details);
        imgVD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ViewVehicleDetails.class);
                startActivity(intent);
            }
        });

        imgRD = parent.findViewById(R.id.btn_revenue_details);
        imgRD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), RevenueDetails.class);
                startActivity(intent);
            }
        });

        imgOGV = parent.findViewById(R.id.btn_ongoing_details);
        imgOGV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), OngoingVehicleNumber.class);
                startActivity(intent);
            }
        });

        imgProfile = parent.findViewById(R.id.btn_profile_details);
        imgProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Profile.class);
                startActivity(intent);
            }
        });

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")

    @Override public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            Toast.makeText(getApplicationContext(), "Home Page", Toast.LENGTH_SHORT).show();

        } else if (id == R.id.nav_vehicle_details) {
            Toast.makeText(getApplicationContext(), "Vehicle Details", Toast.LENGTH_SHORT).show();
            Intent i = new Intent(getApplicationContext(), ViewVehicleDetails.class);
            startActivity(i);

        } else if (id == R.id.nav_revenue_details) {
            Toast.makeText(getApplicationContext(), "Revenue Details", Toast.LENGTH_SHORT).show();
            Intent i = new Intent(getApplicationContext(), RevenueDetails.class);
            startActivity(i);

        } else if (id == R.id.nav_on_going) {
            Toast.makeText(getApplicationContext(), "On Going Number", Toast.LENGTH_SHORT).show();
            Intent i = new Intent(getApplicationContext(), OngoingVehicleNumber.class);
            startActivity(i);

        } else if (id == R.id.nav_profile) {
            Toast.makeText(getApplicationContext(), "Your Profile", Toast.LENGTH_SHORT).show();
            Intent i = new Intent(getApplicationContext(), Profile.class);
            startActivity(i);

        }else if (id == R.id.nav_feedback) {
            Toast.makeText(getApplicationContext(), "Provide Feedback", Toast.LENGTH_SHORT).show();
            Intent i = new Intent(getApplicationContext(), Feedback.class);
            startActivity(i);

        } else if (id == R.id.nav_contact) {
            Toast.makeText(getApplicationContext(), "Contact Us", Toast.LENGTH_SHORT).show();
            Intent i = new Intent(getApplicationContext(), ContactUs.class);
            startActivity(i);

        } else if (id == R.id.nav_logout) {
            new SweetAlertDialog(MainActivity.this, SweetAlertDialog.WARNING_TYPE)
                    .setTitleText("Are you sure?")
                    .setContentText("You are going to exit from the app!")
                    .setConfirmText("Yes")
                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sDialog) {
                            sDialog.dismissWithAnimation();
                            Toast.makeText(getApplicationContext(), "Thank You for Using Our Service", Toast.LENGTH_SHORT).show();
                            Intent i = new Intent(getApplicationContext(), Login.class);
                            startActivity(i);
                            FirebaseAuth.getInstance().signOut();
                        }
                    })
                    .setCancelButton("No", new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sDialog) {
                            sDialog.dismissWithAnimation();
                        }
                    })
                    .show();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}
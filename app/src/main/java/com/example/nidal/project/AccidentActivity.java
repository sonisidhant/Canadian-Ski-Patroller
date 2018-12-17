package com.example.nidal.project;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.NumberPicker;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AccidentActivity extends AppCompatActivity {

    CheckBox bleedingYes = null, bleedingNo = null, bandaidYes = null, bandaidNo = null, criticalYes = null, criticalNo = null, ambulanceYes = null, ambulanceNo = null;
    Button submitButton, emergencyButton;
    NumberPicker numberPicker;
    EditText editText;
    String bleed, band, ambu, crit, emergency;
    Double latitude = 0.0;
    Double longitude = 0.0;
    String vertical, horizon;
    Long time;
    String sTime;
    String people, spinal;

    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    static final int REQUEST_LOCATION = 1;
    LocationManager lm;


    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accident);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_accident);
        toolbar.setTitle(getString(R.string.toolbar_accident));


        databaseReference = FirebaseDatabase.getInstance().getReference().child("accidents");

        numberPicker = (NumberPicker) findViewById(R.id.peopleN);
        numberPicker.setMaxValue(20);
        numberPicker.setMinValue(0);
        numberPicker.setWrapSelectorWheel(false);

        bleedingYes = (CheckBox) findViewById(R.id.bleedingYes);
        //bleedingNo = (CheckBox) findViewById(R.id.bleedingNo);
        bandaidYes = (CheckBox) findViewById(R.id.bandaidYes);
        //bandaidNo = (CheckBox) findViewById(R.id.bandaidNo);
        criticalYes = (CheckBox) findViewById(R.id.criticalYes);
        //criticalNo = (CheckBox) findViewById(R.id.criticalNo);
        ambulanceYes = (CheckBox) findViewById(R.id.ambulanceYes);
        //ambulanceNo = (CheckBox) findViewById(R.id.ambulancenNo);
        submitButton = (Button) findViewById(R.id.submit);
        emergencyButton = (Button) findViewById(R.id.emergency);

        editText = (EditText) findViewById(R.id.spinal);


        lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        getLocation();


        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                getLocation();
                writeUserInfo();
                Log.w("passed", bleed);
                Intent i = new Intent(AccidentActivity.this, MainActivity.class);
                startActivity(i);
            }
        });

        emergencyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getLocation();
                writeUserInfoEmergency();
                Log.w("passed", bleed);
                Intent i = new Intent(AccidentActivity.this, MainActivity.class);
                numberPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
                    @Override
                    public void onValueChange(NumberPicker picker, int oldVal, int newVal) {

                        people = "Number of people" + Integer.toString(newVal);
                    }
                });        startActivity(i);
            }
        });
    }

    void getLocation() {
        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);

        } else {
            Location location = lm.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

            if (location != null){
                latitude = location.getLatitude();
                longitude = location.getLongitude();
                time =  location.getTime();
                sTime = time.toString();


            } else {

                Log.w("Coordinates", "GivingError");
            }
        }

    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,@NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case REQUEST_LOCATION:
                getLocation();
                break;
        }
    }

    public void writeUserInfo() {


        if (bleedingYes.isChecked()) {
            bleed = "Are there any spinal injuries? Yes";
        } else if (!bleedingYes.isChecked()) {
            bleed = "Are there any spinal injuries? No";
        }


        if (bandaidYes.isChecked()) {
            band = "Is the person conscious? Yes";
        } else if (!bandaidYes.isChecked()) {
            band = "Is the person conscious? No";
        }

        if (ambulanceYes.isChecked()) {
            ambu = "Is the person breathing? Yes";
        } else if (!ambulanceYes.isChecked()) {
            ambu = "Is the person breathing? No";
        }

        if (criticalYes.isChecked()) {
            crit = "Is there severe bleeding? Yes";
        } else if (!criticalYes.isChecked()) {
            crit = "Is there severe bleeding? No";
        }

        people = "number of people involved: " + Integer.toString(numberPicker.getValue());

        spinal = String.valueOf(editText.getText());




        emergency = "Not an emergency";

        Log.w("Iwant", bleed);


        String userId = firebaseAuth.getCurrentUser().getUid();

        vertical = Double.toString(longitude);
        horizon = Double.toString(latitude);
        //FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        String Id = firebaseAuth.getCurrentUser().getUid();

        AccidentActivityObject accident = new AccidentActivityObject(userId, bleed, band, crit, ambu, horizon, vertical, sTime, emergency,people,spinal);
        try {
            databaseReference.child(Id).setValue(accident);
            databaseReference = FirebaseDatabase.getInstance().getReference().child("accidentReportedBy");
            databaseReference.child(Id).setValue(Id);
            Log.w("Iwannaknow", bleed);

        } catch (Exception x) {
        }
    }

    public void writeUserInfoEmergency() {

        bleed = "Are there any spinal injuries? Yes";
        band = "Is the person conscious? Yes";
        ambu = "Is the person breathing? Yes";
        crit = "Is there severe bleeding? Yes";
        emergency = "EMERGENCY!";

//        numberPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
//            @Override
//            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
//
//                people = Integer.toString(newVal);
//            }
//        });

        people = "number of people involved: " + Integer.toString(numberPicker.getValue());

        spinal = String.valueOf(editText.getText());

        String userId = firebaseAuth.getCurrentUser().getUid();

        vertical = Double.toString(longitude);
        horizon = Double.toString(latitude);
        //FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        String Id = firebaseAuth.getCurrentUser().getUid();

        AccidentActivityObject accident = new AccidentActivityObject(userId, bleed, band, crit, ambu, horizon, vertical, sTime, emergency,people,spinal);
        try {
            databaseReference.child(Id).setValue(accident);
            databaseReference = FirebaseDatabase.getInstance().getReference().child("accidentReportedBy");
            databaseReference.child(Id).setValue(Id);
            Log.w("Iwannaknow", bleed);

        } catch (Exception x) {
        }
    }
}


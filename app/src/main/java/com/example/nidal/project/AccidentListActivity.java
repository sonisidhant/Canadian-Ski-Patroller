package com.example.nidal.project;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.ArrayList;
import java.util.Map;

public class AccidentListActivity extends AppCompatActivity {
    ListView userListView;
    MainActivity main;
    ArrayList<String> arrayList = new ArrayList<>();
    ArrayAdapter<String> arrayAdapter;
    DatabaseReference db;
    String currentCoords[] = new String[3];
    String latitude, longitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accident_list);

        currentCoords = getIntent().getStringArrayExtra("coords");
        //currentCoords[0] = main.coords[0];
        //  currentCoords[1] = main.coords[1];

        // Log.w("LISTACTIVITY", main.coords[0]);
        //  currentCoords = getIntent().getStringArrayExtra("coords");


        Intent intentExtras = getIntent();
        final String selectedUserId = intentExtras.getStringExtra("itemSelected");
        db = FirebaseDatabase.getInstance().getReference().child("accidents");
        Query queryRef = db.orderByChild("userId");

        //db = FirebaseDatabase.getInstance().getReference().child("accidents").child(selectedUserId);



        userListView = (ListView) findViewById(R.id.userList);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_list);
        toolbar.setTitle("Accidents");
        setSupportActionBar(toolbar);
        arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, arrayList);
        userListView.setAdapter(arrayAdapter);

        queryRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Map<String,Object> value = (Map<String, Object>) dataSnapshot.getValue();

                String name1 = String.valueOf(value.get("userId"));
                //System.out.println(dataSnapshot.getKey() + "is" + value.get("fullName").toString());
                if (name1.equals(selectedUserId)){
                    /*for(DataSnapshot accidents : dataSnapshot.getChildren()){

                        AccidentActivityObject info = accidents.getValue(AccidentActivityObject.class);*/

                    latitude = String.valueOf(value.get("latitude"));
                    longitude = String.valueOf(value.get("longitude"));

                        arrayList.add(String.valueOf(value.get("userId")));
                        arrayList.add(String.valueOf(value.get("bleeding")));
                        arrayList.add(String.valueOf(value.get("needBandaid")));
                        arrayList.add(String.valueOf(value.get("critical")));
                        arrayList.add(String.valueOf(value.get("callAmbulance")));
                        arrayList.add(String.valueOf(value.get("emergency")));
                        arrayList.add(String.valueOf(value.get("latitude")));
                        arrayList.add(String.valueOf(value.get("longitude")));
                        arrayList.add(String.valueOf(value.get("people")));
                        arrayList.add(String.valueOf(value.get("spinal")));

                        arrayAdapter.notifyDataSetChanged();
                    }
                else{
                    System.out.println("its is null");
                }

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        /*db.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                String accidents = dataSnapshot.getValue().toString();


                arrayList.add(accidents);

                arrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });*/

       /* ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_list_item_1,
                //pass arraylist );
        );*/

        //userListView.setAdapter(arrayAdapter);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater mi = getMenuInflater();
        mi.inflate(R.menu.accident_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.show_menu) {
            // Log.w("ListCoords", currentCoords[0]);
            Intent map = new Intent(this, AccidentMapActivity.class);
            map.putExtra("latitude", latitude);
            map.putExtra("longitude", longitude);
            startActivity(map);
        }
        return true;
    }
}


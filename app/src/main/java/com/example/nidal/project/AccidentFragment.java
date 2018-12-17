package com.example.nidal.project;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class AccidentFragment extends Fragment {
    ListView userListView;

    ArrayList<String> arrayList = new ArrayList<>();
    ArrayAdapter<String> arrayAdapter;
    DatabaseReference db;
    FirebaseUser userDb;

    public AccidentFragment(){}
/*    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


       *//* ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_list_item_1,
                //pass arraylist );
        );*//*

        //userListView.setAdapter(arrayAdapter);

    }*/
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle   savedInstanceState) {
        //setContentView(R.layout.activity_accident_list);
        View view = inflater.inflate(R.layout.accident_fragment, container, false);
        db = FirebaseDatabase.getInstance().getReference().child("accidentReportedBy");
        //userDb = FirebaseAuth.getInstance().getCurrentUser();
        userListView = (ListView) view.findViewById(R.id.userList);
        arrayList = new ArrayList<>();
/*        Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar_list);
        toolbar.setTitle("Accidents");*/
        arrayAdapter = new ArrayAdapter<String>(view.getContext(), android.R.layout.simple_list_item_1, arrayList);
        userListView.setAdapter(arrayAdapter);

        db.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                String accidents = (String)dataSnapshot.getValue().toString();
                //Map<String, Object> accidents = (Map<String, Object>) dataSnapshot.getValue();

                arrayList.add(accidents);
                //arrayList = new ArrayList<>();
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
        });

        userListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), AccidentListActivity.class);
                String s = userListView.getItemAtPosition(position).toString();
                intent.putExtra("itemSelected", s);
                startActivityForResult(intent, position);
            }
        });

        return view;
    }

    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater mi = getMenuInflater();
        mi.inflate(R.menu.accident_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==R.id.show_menu)
        {
            Intent map =new Intent(this, AccidentMapActivity.class);
            // map.putExtra("COORDS", new long[] {  , });
            startActivity(map);
        }
        return true;
    }*/
}
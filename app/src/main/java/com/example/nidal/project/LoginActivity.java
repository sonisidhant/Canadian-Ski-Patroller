package com.example.nidal.project;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends Activity {

        protected static final String ACTIVITY_NAME = "LoginActivity";

        EditText editTextID;
        EditText editTextPassword;
        FirebaseAuth firebaseAuth;
        ProgressDialog progressDialog;
        private DatabaseReference databaseReference;
        TextView textView;
        String firstname;
        String lastname;

       //static List<String> userArray = new ArrayList<String>();
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_login);

            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_login);
            toolbar.setTitle(getString(R.string.login_title));

            editTextID = findViewById(R.id.editTextID);
            editTextPassword = findViewById(R.id.editTextPassword);
            textView = (TextView) findViewById(R.id.textView2);

            progressDialog = new ProgressDialog(this);


            firebaseAuth = FirebaseAuth.getInstance();

            Log.i(ACTIVITY_NAME,"IN onCreate(): ");
            /*SharedPreferences prefs = getSharedPreferences(usrname, Context.MODE_PRIVATE);
            final SharedPreferences.Editor edit = prefs.edit(); //edit the file
            final EditText textField = (EditText)findViewById(R.id.editText);
            textField.setText(prefs.getString(loginname,"email@domain.com"));*/

            Button PatrolLeader = (Button)findViewById(R.id.Login);


            PatrolLeader.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    login();

                }
            });

            textView.setOnClickListener(new View.OnClickListener(){
                public void onClick(View v){
                    Intent newMemberIntent = new Intent(LoginActivity.this,
                            NewMemberActivity.class);
                    startActivityForResult(newMemberIntent, 275);
                }
            });
        }

        public void login(){

            String email = editTextID.getText().toString().trim();
            String password = editTextPassword.getText().toString().trim();


            if (TextUtils.isEmpty(email)) {
                Toast.makeText(this, "Please enter email", Toast.LENGTH_SHORT).show();
                return;
            }
            if (TextUtils.isEmpty(password)) {
                Toast.makeText(this, "Please enter password", Toast.LENGTH_SHORT).show();
                return;
            }

            progressDialog.setMessage("Logging in please wait");
            progressDialog.show();

            firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()){



                        String currentUser = firebaseAuth.getCurrentUser().getUid();
                        databaseReference = FirebaseDatabase.getInstance().getReference().child("users").child(currentUser).child("onlineFlag");
                      /* final DatabaseReference databaseReferenceActiveUsers = FirebaseDatabase.getInstance().getReference("activeUsers");
                       //DatabaseReference getDataFromUsers = FirebaseDatabase.getInstance().getReference().child("users");


                        DatabaseReference activeUserfname = FirebaseDatabase.getInstance().getReference().child("users").child(currentUser).child("firstName");
                        DatabaseReference activeUserlname = FirebaseDatabase.getInstance().getReference().child("users").child(currentUser).child("lastName");

                        activeUserfname.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                String lastname = (String) dataSnapshot.getValue();
                                databaseReferenceActiveUsers.setValue(lastname);

                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });

                        activeUserlname.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                lastname.equals(dataSnapshot.getValue());
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                        String activeUserName = firstname + " " + lastname;
                        ActiveUsersObject activeUser = new ActiveUsersObject(activeUserName);
                        databaseReferenceActiveUsers.setValue(activeUser);*/
                      DatabaseReference databaseReferenceActiveUsers = FirebaseDatabase.getInstance().getReference().child("activeUsers");

                      String name = firebaseAuth.getCurrentUser().getDisplayName();
                      databaseReferenceActiveUsers.child(firebaseAuth.getCurrentUser().getUid()).setValue(name);

                        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                databaseReference.setValue(true);
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });


                        Intent secondIntent = new Intent(LoginActivity.this,
                                MainActivity.class);
                        secondIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivityForResult(secondIntent, 275);
                        if (progressDialog != null && progressDialog.isShowing()) {
                            progressDialog.dismiss();
                        }
                        //static array does not work for active users
                        /*if (!userArray.contains(firebaseAuth.getCurrentUser().getDisplayName())){
                            userArray.add(firebaseAuth.getCurrentUser().getDisplayName());
                        }*/
                    }
                 else{
                        Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();if (progressDialog != null && progressDialog.isShowing()) {
                        progressDialog.dismiss();
                    }
                    }
                }
            });

        }



        @Override
        public void onResume()
        {
            super.onResume();
            Log.i(ACTIVITY_NAME, "in onResume() : ");

        }
        @Override
        public void onStart()
        {
            super.onStart();
            Log.i(ACTIVITY_NAME, "In onStart() : ");


        }

        @Override
        public void onPause()
        {
            super.onPause();
            Log.i(ACTIVITY_NAME, "In onPause() : ");


        }
        @Override
        public void onStop()
        {
            super.onStop();
            Log.i(ACTIVITY_NAME, " onStop() : ");


        }
        @Override
        public void onDestroy()
        {
            super.onDestroy();
            Log.i(ACTIVITY_NAME, " onDestroy() : ");


        }
    }


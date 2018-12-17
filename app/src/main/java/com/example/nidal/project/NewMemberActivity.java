package com.example.nidal.project;

/**
 * References:
 * https://www.youtube.com/watch?v=0NFwF7L-YA8
 */

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;

public class NewMemberActivity extends AppCompatActivity {

    private Button registerButton;
    private EditText editEmail;
    private EditText editCEmail;
    private EditText editCPass;
    private EditText editPass;
    private RadioGroup radioGroup;
    //private RadioButton radioButton;

    private ProgressDialog progressDialog;

    private FirebaseAuth firebaseAuth;
    //private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_member);

        firebaseAuth = FirebaseAuth.getInstance();
        //databaseReference = FirebaseDatabase.getInstance().getReference();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_new_member);
        toolbar.setTitle(getString(R.string.new_member_title));

        progressDialog = new ProgressDialog(this);

        registerButton = (Button) findViewById(R.id.Login);
        editEmail = (EditText) findViewById(R.id.editTextEmail);
        editCEmail = (EditText) findViewById(R.id.editTextCEmail);
        editPass = (EditText) findViewById(R.id.editTextPass);
        editCPass = (EditText) findViewById(R.id.editTextCPass);

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser();
                //saveUserToDatabase();

            }
        });
    }

    private void registerUser() {
        String email = editEmail.getText().toString().trim();
        String cEmail = editCEmail.getText().toString().trim();
        String password = editPass.getText().toString().trim();
        String cPassword = editCPass.getText().toString().trim();

        if (TextUtils.isEmpty(password) || (!email.equals(cEmail))) {
            Toast.makeText(this, "Please check the email", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(password) || (!password.equals(cPassword))) {
            Toast.makeText(this, "Please check the password", Toast.LENGTH_SHORT).show();
            return;
        }

        progressDialog.setMessage("Registering new user please wait");
        progressDialog.show();
        firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isSuccessful()) {
                    Toast.makeText(NewMemberActivity.this, "Registration Successful", Toast.LENGTH_SHORT).show();
                    if (progressDialog != null && progressDialog.isShowing()) {
                        progressDialog.dismiss();
                        Intent intent = new Intent(NewMemberActivity.this,
                                UserInformation.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivityForResult(intent, 275);
                    }
                        return;
                } else {
                    if (task.getException() instanceof FirebaseAuthUserCollisionException){
                        Toast.makeText(NewMemberActivity.this, "User already exists", Toast.LENGTH_SHORT).show();
                        if (progressDialog != null && progressDialog.isShowing()) {
                            progressDialog.dismiss();
                        }
                    } else {
                        Toast.makeText(NewMemberActivity.this, "Error registering user. Please try again.", Toast.LENGTH_SHORT).show();
                        if (progressDialog != null && progressDialog.isShowing()) {
                            progressDialog.dismiss();
                        }
                    }
                    return;
                }

            }
        });

        firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    return;
                }

            }
        });
    }

    /*private void saveUserToDatabase() {
        String fname = editFname.getText().toString().trim();
        String lname = editLname.getText().toString().trim();
        String email = editEmail.getText().toString().trim();
        String radiovalue = "";
        if(radioGroup.getCheckedRadioButtonId()!=-1){
            RadioGroup radioGroup = (RadioGroup)findViewById(R.id.radioGroup);
            radiovalue = ((RadioButton)findViewById(radioGroup.getCheckedRadioButtonId())).getText().toString();
        }

        if (!radioButton.isSelected()) {
            Toast.makeText(this, "Please select patroller or leader", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(fname)) {
            Toast.makeText(this, "Please enter first name", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(lname)) {
            Toast.makeText(this, "Please enter last name", Toast.LENGTH_SHORT).show();
            return;
        }

        if (radioGroup.getCheckedRadioButtonId() == -1) {
            Toast.makeText(this, "Please select patroller or leader", Toast.LENGTH_SHORT).show();
            return;
        }

        UserInformation userInformation = new UserInformation(fname, lname, email, radiovalue);
        //FirebaseUser user = firebaseAuth.getCurrentUser();
        databaseReference.child("users").child(firebaseAuth.getUid()).setValue(userInformation);
    }*/
}



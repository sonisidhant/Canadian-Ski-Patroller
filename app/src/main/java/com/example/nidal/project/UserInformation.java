package com.example.nidal.project;
/**
 * References:
 * https://www.youtube.com/watch?v=qVaBY92sOSI&t=62s
 */


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;

import de.hdodenhof.circleimageview.CircleImageView;

public class UserInformation extends Activity {

    private static final int CHOOSE_IMAGE = 101;
    CircleImageView displayPicture;
    EditText fName, lName, phone;
    RadioGroup radioGroup;
   static Uri uriDisplayPicture;
    String displayPictureUrl;
    Button saveButton;
    UserProfileChangeRequest profile;

    FirebaseAuth firebaseAuth;

    DatabaseReference firebaseDatabaseReference;

    private ProgressDialog progressDialog;
    public static final String MY_PREFS_NAME = "MyPrefsFile";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_information);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_user_information);
        toolbar.setTitle(getString(R.string.user_information_title));

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabaseReference = FirebaseDatabase.getInstance().getReference();

        progressDialog = new ProgressDialog(this);

        displayPicture = (CircleImageView) findViewById(R.id.imageView);
        radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
        fName = (EditText) findViewById(R.id.editTextFname);
        lName = (EditText) findViewById(R.id.editTextLname);
        phone = (EditText) findViewById(R.id.editTextPhone);
        saveButton = (Button) findViewById(R.id.save);

        displayPicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showImageChooser();
            }
        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveUserInfo();
                writeUserInfo();
            }
        });
    }

    public void writeUserInfo(){

        int radioButtonID = radioGroup.getCheckedRadioButtonId();
        View radioButton = radioGroup.findViewById(radioButtonID);
        int radioID = radioGroup.indexOfChild(radioButton);
        RadioButton rbtn = (RadioButton) radioGroup.getChildAt(radioID);


        String leaderflag = (String) rbtn.getText();
        String firstName = fName.getText().toString();
        String lastName = lName.getText().toString();
        String phoneNumer = phone.getText().toString();
        String email_this = firebaseAuth.getCurrentUser().getEmail();
        //Boolean activeFlag = true;
        String userId = firebaseAuth.getCurrentUser().getUid();

        UserInformationObject user = new UserInformationObject(firstName, lastName, email_this, phoneNumer, leaderflag);//, activeFlag);
        try {
            firebaseDatabaseReference.child("users").child(userId).setValue(user);
        } finally {
            Intent userInfoIntent = new Intent(UserInformation.this,
                    MainActivity.class);
            startActivityForResult(userInfoIntent, 275);
        }
    }

    public void saveUserInfo(){

/*        int radioButtonID = radioGroup.getCheckedRadioButtonId();
        View radioButton = radioGroup.findViewById(radioButtonID);
        int radioID = radioGroup.indexOfChild(radioButton);
        RadioButton rbtn = (RadioButton) radioGroup.getChildAt(radioID);*/


        //String selectedRadioButton = (String) rbtn.getText();
        String firstName = fName.getText().toString();
        String lastName = lName.getText().toString();
        //String phoneNumer = phone.getText().toString();

        if (TextUtils.isEmpty(firstName)) {
            Toast.makeText(this, "Please enter first name", Toast.LENGTH_SHORT).show();
            fName.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(lastName)) {
            Toast.makeText(this, "Please enter last name", Toast.LENGTH_SHORT).show();
            lName.requestFocus();
            return;
        }

        /*if (TextUtils.isEmpty(phoneNumer)) {
            Toast.makeText(this, "Please enter phone number", Toast.LENGTH_SHORT).show();
            phone.requestFocus();
            return;
        }

        if (radioGroup.getCheckedRadioButtonId() == -1) {
            Toast.makeText(this, "Please select patroller or leader", Toast.LENGTH_SHORT).show();
            return;
        }*/

        FirebaseUser user = firebaseAuth.getCurrentUser();

        if (user != null){
            String displayName = firstName + " " + lastName;
            if (displayPictureUrl != null) {
                profile = new UserProfileChangeRequest.Builder()
                        .setDisplayName(displayName)
                        .setPhotoUri(Uri.parse(displayPictureUrl))
                        .build();
            }
            else {
                profile = new UserProfileChangeRequest.Builder()
                        .setDisplayName(displayName)
                        .build();
            }
            user.updateProfile(profile)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(UserInformation.this, "Profile saved", Toast.LENGTH_SHORT).show();

                               /* Intent userInfoIntent = new Intent(UserInformation.this,
                                        MainActivity.class);
                                startActivityForResult(userInfoIntent, 275);*/
                            }
                        }
                    });
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == CHOOSE_IMAGE && resultCode == RESULT_OK && data != null && data.getData()!= null){

            uriDisplayPicture = data.getData();

            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uriDisplayPicture);
                displayPicture.setImageBitmap(bitmap);

                uploadImageToFirebaseStoreage();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void uploadImageToFirebaseStoreage() {
        StorageReference displayPictureRef =
                FirebaseStorage.getInstance().getReference("displayPictures/" + System.currentTimeMillis() + ".jpg");

        if(uriDisplayPicture != null){

            progressDialog.setMessage("Setting display picture");
            progressDialog.show();

            displayPictureRef.putFile(uriDisplayPicture).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Toast.makeText(UserInformation.this, "Display picture changed", Toast.LENGTH_SHORT).show();
                    if (progressDialog != null && progressDialog.isShowing()) {
                        progressDialog.dismiss();
                    }

                    displayPictureUrl = taskSnapshot.getMetadata().getReference().getDownloadUrl().toString();
                }
            })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(UserInformation.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                            if (progressDialog != null && progressDialog.isShowing()) {
                                progressDialog.dismiss();
                            }
                        }
                    });
            return;
        }
    }

    public void showImageChooser(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select display picture"), CHOOSE_IMAGE);
    }
}

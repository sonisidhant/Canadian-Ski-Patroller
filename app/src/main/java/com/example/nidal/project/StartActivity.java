package com.example.nidal.project;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class StartActivity extends AppCompatActivity {

    protected static final String ACTIVITY_NAME = "StartActivity";
    Button button;
    Button Patroller;
    TextView textView;
    //protected Switch toggle = (Switch) findViewById(R.id.switch1);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        button = (Button) findViewById(R.id.PatrolLeader_Button);
        Patroller = (Button)findViewById(R.id.Patroller_Button);
        textView = (TextView) findViewById(R.id.textView2);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_start);
        toolbar.setTitle(getString(R.string.welcome_message));

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(StartActivity.this,LoginActivity.class);
                startActivityForResult(i, 10);
            }
        });

        Patroller.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent patrollerintent = new Intent(StartActivity.this,
                        LoginActivity.class);
                startActivityForResult(patrollerintent, 275);
            }
        });

        textView.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent newMemberIntent = new Intent(StartActivity.this,
                        NewMemberActivity.class);
                startActivityForResult(newMemberIntent, 275);
            }
        });



    }
    @Override
    public void onResume()
    {
        super.onResume();
  /*      if (toggle.isChecked()) {
            setTheme(R.style.AppThemeLight);
        }*/
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


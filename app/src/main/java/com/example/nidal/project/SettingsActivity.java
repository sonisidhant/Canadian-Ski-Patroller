package com.example.nidal.project;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.CompoundButton;
import android.widget.Switch;

/**
 *References:
 * https://www.chrisblunt.com/android-toggling-your-apps-theme/
 */
public class SettingsActivity extends AppCompatActivity {

    //RadioGroup radioGroup;
    //RadioButton radioButton;
    private static final String PREFS_NAME = "prefs";
    private static final String PREF_LIGHT_THEME = "light_theme";
    //protected Switch toggle = (Switch) findViewById(R.id.switch1);

    @Override
    protected void onCreate(Bundle savedInstanceState) {

       /* radioGroup = findViewById(R.id.radio_group);
        Button buttonApply = findViewById(R.id.button_apply);

        buttonApply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int radioID = radioGroup.getCheckedRadioButtonId();
                radioButton = findViewById(radioID);

                if(radioButton.getText() == "Light"){
                    setTheme(R.style.AppThemeLight);
                }
                if(radioButton.getText() == "Dark"){
                    setTheme(R.style.AppThemeDark);
                }
            }
        });*/
        SharedPreferences preferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        boolean useLightTheme = preferences.getBoolean(PREF_LIGHT_THEME, false);

        if(useLightTheme) {
            setTheme(R.style.AppThemeLight);
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_settings);
        toolbar.setTitle(getString(R.string.title_activity_settings));

        Switch toggle = (Switch) findViewById(R.id.switch1);
        toggle.setChecked(useLightTheme);

        toggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton view, boolean isChecked) {
                toggleTheme(isChecked);
            }
        });
    }

/*    public void checkButton (View v){
            int radioID = radioGroup.getCheckedRadioButtonId();
            radioButton = findViewById(radioID);

        Toast.makeText(this, "Theme selected: " + radioButton.getText(), Toast.LENGTH_SHORT).show();
    }*/

    private void toggleTheme(boolean lightTheme) {
        SharedPreferences.Editor editor = getSharedPreferences(PREFS_NAME, MODE_PRIVATE).edit();
        editor.putBoolean(PREF_LIGHT_THEME, lightTheme);
        editor.apply();

        Intent intent = getIntent();
        finish();

        startActivity(intent);
    }
}


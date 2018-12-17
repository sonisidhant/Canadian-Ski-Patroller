package com.example.nidal.project;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import de.hdodenhof.circleimageview.CircleImageView;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener
{
	private DrawerLayout navDrawer;
	NavigationView navigationView;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;
    NavigationView navMenu;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private String[] pageTitle = {"Accidents", "Weather", "Location"};
    private Button btnAdd;
    //static String name;
    //static User user;

    //String news_Url;

    @Override
    protected void onCreate(Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_main);
        toolbar.setTitle(getString(R.string.accidents_title));
        setSupportActionBar(toolbar);

        //String currentUser = firebaseAuth.getCurrentUser().getUid();
        navMenu = (NavigationView) findViewById(R.id.nav_pane_view);
        firebaseAuth = FirebaseAuth.getInstance();
        navDrawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle navToggle = new ActionBarDrawerToggle(this, navDrawer,
                toolbar, R.string.nav_bar_open, R.string.nav_bar_close);
        navDrawer.addDrawerListener(navToggle);
        navToggle.syncState();


/*        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.floatingActionButton);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               Intent i = new Intent(MainActivity.this, AccidentActivity.class);
               startActivity(i);
            }
        });*/

        viewPager = (ViewPager)findViewById(R.id.view_pager);

        navigationView = (NavigationView)  findViewById(R.id.nav_pane_view);
        navigationView.setNavigationItemSelectedListener(this);

        TextView userEmail = (TextView) navigationView.getHeaderView(0).findViewById(R.id.emailNav);
        userEmail.setText(firebaseAuth.getCurrentUser().getEmail());

        TextView userName = (TextView) navigationView.getHeaderView(0).findViewById(R.id.navName);
        userName.setText(firebaseAuth.getCurrentUser().getDisplayName());

        CircleImageView imageView = (CircleImageView) navigationView.getHeaderView(0).findViewById(R.id.imageView);
        imageView.setImageURI(firebaseAuth.getCurrentUser().getPhotoUrl());

        btnAdd = (Button) findViewById(R.id.btn_add_accident);

        //news_Url = "https://newsapi.org/v2/top-headlines?country=ca&apiKey=11e909f83a6c4cfd915a885056e16924";

        //ImageView test = (ImageView) findViewById(R.id.imageView3);
        //test.setImageURI(firebaseAuth.getCurrentUser().getPhotoUrl());

       FirebaseDatabase.getInstance().getReference().child("users").child(firebaseAuth.getCurrentUser().getUid())
                .child("leaderFlag").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.getValue().equals("Patroller")) {
                    navMenu.getMenu().findItem(R.id.nav_list).setEnabled(false);
                }
                    if(dataSnapshot.getValue().equals("Leader")){
                        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.floatingActionButton);
                        fab.hide();

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

       //new MainActivity.AsyncHttpTask().execute(news_Url);

        //setting Tab layout (number of Tabs = number of ViewPager pages)
        tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        for (int i = 0; i < pageTitle.length; i++) {
            tabLayout.addTab(tabLayout.newTab().setText(pageTitle[i]));
        }

        //set gravity for tab bar
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        //set viewpager adapter
        ViewPagerAdapter pagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(pagerAdapter);

        //change Tab selection when swipe ViewPager
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        //change ViewPager page when tab selected
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent settingsIntent = new Intent(MainActivity.this, AccidentActivity.class);
                startActivity(settingsIntent);
            }
        });
    }

   /* public class AsyncHttpTask extends AsyncTask<String, Void, String>{

        @Override
        protected String doInBackground(String... urls) {
            String result = "";
            URL url;
            HttpURLConnection urlConnection = null;

            try {
                url = new URL (urls[0]);
                urlConnection = (HttpURLConnection) url.openConnection();
                String response = streamToString(urlConnection.getInputStream());
                parseResult(response);

                return result;


            } catch (ConnectException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }
    }

    String streamToString (InputStream stream) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(stream));
        String data;
        String result = "";

        while ((data = bufferedReader.readLine()) != null){
            result += data;
        }
        if (null != stream){
            stream.close();
        }
        return result;
    }

    private void parseResult(String result){

        JSONObject respone = null;
        try {
            respone = new JSONObject(result);
            JSONArray articles = respone.optJSONArray("articles");

            for (int i = 0; i < articles.length(); i++){
                JSONObject article = articles.optJSONObject(i);
                String title = article.optString("title");
                Log.i("Titles", title);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        } catch (Exception e){
            e.printStackTrace();
        }



    }*/


    @Override
    public boolean onNavigationItemSelected(MenuItem item){
        int id = item.getItemId();
    if (id == R.id.nav_logoff) {
    /*{
        firebaseAuth.signOut()
                .addOnCompleteListener(new OnCompleteListener<Void>() {

                    public void onComplete(@NonNull Task<Void> task) {
                        Intent quit = new Intent(MainActivity.this, StartActivity.class);
                        startActivity(quit);
                        finish();

                    }
                });*/
        //if(LoginActivity.userArray.contains(firebaseAuth.getCurrentUser().getDisplayName())){
            //LoginActivity.userArray.remove(firebaseAuth.getCurrentUser().getDisplayName());

/*            databaseReference.child(currentUser).child("onlineFlag").setValue(false);
        //}
        firebaseAuth.signOut();
        Intent quit = new Intent(MainActivity.this, StartActivity.class);
        startActivity(quit);*/
        String currentUser = firebaseAuth.getCurrentUser().getUid();
        //databaseReference = FirebaseDatabase.getInstance().getReference().child("users").child(currentUser).child("onlineFlag");

        databaseReference = FirebaseDatabase.getInstance().getReference().child("activeUsers").child(currentUser);

        databaseReference.removeValue();

        /*databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                databaseReference.setValue(false);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });*/

        firebaseAuth.signOut();
        Intent quit = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(quit);

    }

        if (id == R.id.nav_accident)
        {
            Intent settingsIntent = new Intent(MainActivity.this, AccidentActivity.class);
            startActivity(settingsIntent);
        }

        if (id == R.id.nav_settings)
        {
            Intent settingsIntent = new Intent(MainActivity.this, SettingsActivity.class);
            startActivity(settingsIntent);
        }

        if (id == R.id.nav_list)
        {
            Intent listIntent = new Intent(MainActivity.this, ListViewActivity.class);
            startActivity(listIntent);
        }

        if (id == R.id.nav_update_user)
        {
            Intent userIntent = new Intent(MainActivity.this, UserInformation.class);
            startActivity(userIntent);
        }


    DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
    drawer.closeDrawer(GravityCompat.START);
    return true;
    }


/*    @Override
    public void onBackPressed() {
        if (navDrawer.isDrawerOpen(GravityCompat.START)){
            navDrawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }*/
}

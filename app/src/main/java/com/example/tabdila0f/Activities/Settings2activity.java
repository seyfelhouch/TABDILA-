package com.example.tabdila0f.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.tabdila0f.Fragments.HomeFragment;
import com.example.tabdila0f.R;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

public class Settings2activity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    TextView editt1,editt2,logout, back , name ;
    private FirebaseAuth auth;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    FirebaseDatabase firebaseDatabase;
    FirebaseUser currentUser ;
    FirebaseAuth mAuth;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings2activity);
        editt2=findViewById(R.id.change_password);
        editt1=findViewById(R.id.Edit_Profile);
        logout=findViewById(R.id.log_out);
        name=findViewById(R.id.usernameTextView);
        ImageView profilePic = findViewById(R.id.profileCircleImageView);
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        firebaseDatabase = FirebaseDatabase.getInstance();
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        name.setText(user.getDisplayName());
        auth = FirebaseAuth.getInstance();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        updateNavHeader();

        Glide.with(this).load(user.getPhotoUrl()).placeholder(R.drawable.userphoto).into(profilePic);

        editt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent registerActivity = new Intent(getApplicationContext(),editProfil.class);
                startActivity(registerActivity);
                finish();


            }
        });
        editt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent registerActivity = new Intent(getApplicationContext(),SettingActivity.class);
                startActivity(registerActivity);
                finish();


            }
        });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signOut();
            }
        });

        back = (TextView) findViewById(R.id.retour);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent registerActivity = new Intent(getApplicationContext(),Home.class);
                startActivity(registerActivity);
                finish();


            }
        });
    }
    public void signOut() {
        auth.signOut();
        startActivity(new Intent(Settings2activity.this, LoginActivity.class));
        finish();



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
        getMenuInflater().inflate(R.menu.home, menu);
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
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {

            Intent HomeActivity = new Intent(getApplicationContext(),Home.class);
            startActivity(HomeActivity);
            finish();

        } else if (id == R.id.nav_profile) {
            Intent SettingActivity = new Intent(getApplicationContext(),ProfileActivity.class);
            startActivity(SettingActivity);
            finish();


        } else if (id == R.id.nav_settings) {

            getSupportActionBar().setTitle("Profile");



        }
        else if (id == R.id.nav_signout) {

            FirebaseAuth.getInstance().signOut();
            Intent loginActivity = new Intent(getApplicationContext(),LoginActivity.class);
            startActivity(loginActivity);
            finish();


        }
        else if (id == R.id.a_propos) {


            Intent loginActivity = new Intent(getApplicationContext(),A_propos.class);
            startActivity(loginActivity);
            finish();


        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void updateNavHeader() {

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View headerView = navigationView.getHeaderView(0);
        TextView navUsername = headerView.findViewById(R.id.nav_username);
        TextView navUserMail = headerView.findViewById(R.id.nav_user_mail);
        ImageView navUserPhot = headerView.findViewById(R.id.nav_user_photo);

        navUserMail.setText(currentUser.getEmail());
        navUsername.setText(currentUser.getDisplayName());

        // now we will use Glide to load user image
        // first we need to import the library

        Glide.with(this).load(currentUser.getPhotoUrl()).into(navUserPhot);




    }


}

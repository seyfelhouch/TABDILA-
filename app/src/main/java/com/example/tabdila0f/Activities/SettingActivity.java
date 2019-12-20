package com.example.tabdila0f.Activities;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.app.NotificationManager;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.example.tabdila0f.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

public class SettingActivity extends AppCompatActivity {

    private Button btnChangePassword, btnRemoveUser, changePassword, remove, signOut;
    private TextView email,back,name;

    private EditText oldEmail, password, newPassword;
    private ProgressBar progressBar;
    private FirebaseAuth auth;
    FirebaseUser currentUser ;
    FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        auth = FirebaseAuth.getInstance();

        email = (TextView) findViewById(R.id.useremail);
        ImageView profilePic = findViewById(R.id.settings_profile_image);
        back=findViewById(R.id.Back_to_settings);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SettingActivity.this, Settings2activity.class));
                finish();
            }
        });

        //get current user
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        setDataToView(user);
        name=findViewById(R.id.usernameTextView);

       name.setText(user.getDisplayName());
        auth = FirebaseAuth.getInstance();



        authListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user == null) {
                    // user auth state is changed - user is null
                    // launch login activity
                    startActivity(new Intent(SettingActivity.this, LoginActivity.class));
                    finish();
                }
            }
        };
        if (user.getPhotoUrl() != null)
            Glide.with(this).load(user.getPhotoUrl()).placeholder(R.drawable.userphoto).into(profilePic);


        btnChangePassword = (Button) findViewById(R.id.change_password_button);
        btnRemoveUser = (Button) findViewById(R.id.remove_user_button);
        changePassword = (Button) findViewById(R.id.changePass);
        remove = (Button) findViewById(R.id.remove);
        signOut = (Button) findViewById(R.id.sign_out);

        oldEmail = (EditText) findViewById(R.id.old_email);
        password = (EditText) findViewById(R.id.password);
        newPassword = (EditText) findViewById(R.id.newPassword);

        oldEmail.setVisibility(View.GONE);
        password.setVisibility(View.GONE);
        newPassword.setVisibility(View.GONE);
        changePassword.setVisibility(View.GONE);
        remove.setVisibility(View.GONE);

        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        if (progressBar != null) {
            progressBar.setVisibility(View.GONE);
        }

        btnChangePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                oldEmail.setVisibility(View.GONE);
                password.setVisibility(View.VISIBLE);
                newPassword.setVisibility(View.VISIBLE);
                changePassword.setVisibility(View.VISIBLE);
                remove.setVisibility(View.GONE);
            }
        });

        changePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                if (user != null && !newPassword.getText().toString().trim().equals("")) {
                    if (newPassword.getText().toString().trim().length() < 6) {
                        newPassword.setError("Password too short, enter minimum 6 characters");
                        progressBar.setVisibility(View.GONE);
                    } else {
                        user.updatePassword(newPassword.getText().toString().trim())
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            Toast.makeText(SettingActivity.this, "Password is updated, sign in with new password!", Toast.LENGTH_SHORT).show();
                                            signOut();
                                            progressBar.setVisibility(View.GONE);
                                        } else {
                                            Toast.makeText(SettingActivity.this, "Failed to update password!", Toast.LENGTH_SHORT).show();
                                            progressBar.setVisibility(View.GONE);
                                        }
                                    }
                                });
                    }
                } else if (newPassword.getText().toString().trim().equals("")) {
                    newPassword.setError("Enter password");
                    progressBar.setVisibility(View.GONE);
                }
            }
        });

        btnRemoveUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                if (user != null) {
                    user.delete()
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(SettingActivity.this, "Your profile is deleted:( Create a account now!", Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(SettingActivity.this, LoginActivity.class));
                                        finish();
                                        progressBar.setVisibility(View.GONE);
                                    } else {
                                        Toast.makeText(SettingActivity.this, "Failed to delete your account!", Toast.LENGTH_SHORT).show();
                                        progressBar.setVisibility(View.GONE);
                                    }
                                }
                            });
                }
            }
        });

        signOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signOut();
            }
        });

    }

    @SuppressLint("SetTextI18n")
    private void setDataToView(FirebaseUser user) {

        email.setText( user.getEmail());

    }

    // this listener will be called when there is change in firebase user session
    FirebaseAuth.AuthStateListener authListener = new FirebaseAuth.AuthStateListener() {
        @SuppressLint("SetTextI18n")
        @Override
        public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
            FirebaseUser user = firebaseAuth.getCurrentUser();
            if (user == null) {
                // user auth state is changed - user is null
                // launch login activity
                startActivity(new Intent(SettingActivity.this, LoginActivity.class));
                finish();
            } else {
                setDataToView(user);
            }
        }


    };

    //sign out method
    public void signOut() {

        auth.signOut();

        // this listener will be called when there is change in firebase user session
        FirebaseAuth.AuthStateListener authListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user == null) {
                    // user auth state is changed - user is null
                    // launch login activity

                    startActivity(new Intent(SettingActivity.this, LoginActivity.class));
                    finish();
                }
            }
        };
    }

    @Override
    protected void onResume() {
        super.onResume();
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void onStart() {
        super.onStart();
        auth.addAuthStateListener(authListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (authListener != null) {
            auth.removeAuthStateListener(authListener);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.home, menu);

        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_settings:
                Notifications notifications=new Notifications();
                notifications.notificationFunction(SettingActivity.this , "toast" , "je suis le menu");
                startActivity(new Intent(SettingActivity.this,Home.class));

                return true;

        }

        return super.onOptionsItemSelected(item);
    }
}

// and yesss we have made it. WE HAVE MADE AN APP WITH LOGIN AND REGISTRATION
// PLEASE DO LIKE AND SUBSCRIBE FOR MORE.
//THANK YOU





package com.fitness.fit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.FirebaseDatabase;

public class Newuser extends AppCompatActivity implements View.OnClickListener{

    private TextView banner, registerUser, planview;
    private EditText editTextFullname, editTextAge, editTextEmail,editTextPassword, editTextDplan,editTextEplan;
    private com.airbnb.lottie.LottieAnimationView progressBar;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newuser);

        mAuth = FirebaseAuth.getInstance();

        banner = (TextView) findViewById(R.id.banner);
        banner.setOnClickListener(this);

        registerUser = (Button) findViewById(R.id.registerUser);
        registerUser.setOnClickListener(this);

        planview = (TextView) findViewById(R.id.planview);
        planview.setOnClickListener(this);

        editTextFullname = (EditText) findViewById(R.id.fullName);
        editTextAge = (EditText) findViewById(R.id.age);
        editTextEmail = (EditText) findViewById(R.id.email);
        editTextPassword = (EditText) findViewById(R.id.password);
        editTextDplan = (EditText) findViewById(R.id.dplan);
        editTextEplan = (EditText) findViewById(R.id.eplan);

        progressBar = (com.airbnb.lottie.LottieAnimationView) findViewById(R.id.progressBar);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.banner:
                startActivity(new Intent(this, UserRegister.class));
                break;
            case R.id.registerUser:
                registerUser();
                break;
            case R.id.planview:
                startActivity(new Intent(this, PlanView_r.class));
                break;
        }
    }

    private void registerUser() {
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();
        String fullName = editTextFullname.getText().toString().trim();
        String age = editTextAge.getText().toString().trim();
        String dplan = editTextDplan.getText().toString().trim();
        String eplan = editTextEplan.getText().toString().trim();
        if(fullName.isEmpty())
        {
            editTextFullname.setError("Full name is required!");
            editTextFullname.requestFocus();
            return;
        }
        if(age.isEmpty())
        {
            editTextAge.setError("Age is required!");
            editTextAge.requestFocus();
            return;
        }
        if(Integer.parseInt(age) < 16)
        {
            editTextAge.setError("Min age 16 is required!");
            editTextAge.requestFocus();
            return;
        }
        if(email.isEmpty())
        {
            editTextEmail.setError("Email is required!");
            editTextEmail.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches())
        {
            editTextEmail.setError("Please provide valid email!");
            editTextEmail.requestFocus();
            return;
        }
        if(password.isEmpty())
        {
            editTextPassword.setError("Password is required!");
            editTextPassword.requestFocus();
            return;
        }
        if(password.length() < 8)
        {

            editTextPassword.setError("Min password length should be 8 chars");
            editTextPassword.requestFocus();
            return;
        }
        if(dplan.isEmpty())
        {
            editTextDplan.setError("Dietary plan is required!");
            editTextDplan.requestFocus();
            return;
        }
        if(Integer.parseInt(dplan) >= 11)
        {
            editTextDplan.setError("Invalid entry!");
            editTextDplan.requestFocus();
            return;
        }
        if(Integer.parseInt(dplan) <= 0)
        {
            editTextDplan.setError("Invalid entry!");
            editTextDplan.requestFocus();
            return;
        }
        if(eplan.isEmpty())
        {
            editTextEplan.setError("Exercise plan is required!");
            editTextEplan.requestFocus();
            return;
        }
        if(Integer.parseInt(eplan) >= 11)
        {
            editTextEplan.setError("Invalid entry!");
            editTextEplan.requestFocus();
            return;
        }
        if(Integer.parseInt(eplan) <= 0)
        {
            editTextEplan.setError("Invalid entry!");
            editTextEplan.requestFocus();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            User user = new User(fullName, age, email, dplan, eplan);
                            FirebaseDatabase.getInstance().getReference("Users")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                                        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                                .setDisplayName(fullName)
                                                .build();
                                        user.updateProfile(profileUpdates);
                                        FirebaseAuth.getInstance().signOut();
                                        Toast.makeText(Newuser.this, "User has been registered successfully!", Toast.LENGTH_LONG).show();
                                        progressBar.setVisibility(View.GONE);
                                        startActivity(new Intent(Newuser.this, UserRegister.class));
                                    }
                                    else {
                                        Toast.makeText(Newuser.this, "Failed to register, try again!", Toast.LENGTH_LONG).show();
                                        progressBar.setVisibility(View.GONE);
                                    }
                                }
                            });
                        }
                        else{
                            Toast.makeText(Newuser.this,"Failed to register, try again!", Toast.LENGTH_LONG).show();
                            progressBar.setVisibility(View.GONE);
                        }
                    }
                });
    }
}
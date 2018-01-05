package com.aregev2.personalcommitment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SplashActivity extends AppCompatActivity {

    SharedPreferences sharedPreferences;

    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        sharedPreferences = this.getSharedPreferences("com.aregev2.personalcommitment.STARTUP", Context.MODE_PRIVATE);

        firebaseAuth = FirebaseAuth.getInstance();

        String userEmail = sharedPreferences.getString("USER_EMAIL", null);
        String userPassword = sharedPreferences.getString("USER_PASSWORD", null);
        if(userEmail != null) {
            if (userPassword != null) {
                firebaseAuth.signInWithEmailAndPassword(userEmail, userPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                            startActivity(intent);
                        } else {
                            Toast.makeText(SplashActivity.this, "We have a problem,\ncheck your internet connection" + task.getException(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        }else{
            Intent intent = new Intent(SplashActivity.this, WelcomeActivity.class);
            startActivity(intent);
        }
    }
}

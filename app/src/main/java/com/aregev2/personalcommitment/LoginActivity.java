package com.aregev2.personalcommitment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    FirebaseAuth firebaseAuth;

    TextInputLayout textInputLayoutEmail;
    TextInputLayout textInputLayoutPassword;

    EditText editTextEmail;
    EditText editTextPassword;

    Button buttonLogin;

    SharedPreferences sharedPreferences;

    String email;
    String password;

    int accountType = 0;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        firebaseAuth = FirebaseAuth.getInstance();

        sharedPreferences = this.getSharedPreferences("com.aregev2.personalcommitment.STARTUP", Context.MODE_PRIVATE);

        textInputLayoutEmail = findViewById(R.id.activity_login_layout_email);
        textInputLayoutPassword = findViewById(R.id.activity_login_layout_password);

        editTextEmail = findViewById(R.id.activity_login_editText_email);
        editTextPassword = findViewById(R.id.activity_login_editText_password);

        buttonLogin = findViewById(R.id.activity_login_button_login);
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                email = editTextEmail.getText().toString();
                password = editTextPassword.getText().toString();

                if(!email.isEmpty()){
                    if(!password.isEmpty()){
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("USER_EMAIL", email);
                        editor.putString("USER_PASSWORD", password);
                        Log.i("ACCOUNT","Data saved");
                        editor.commit();

                        accountType = 0;
                        firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(task.isSuccessful()){
                                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                    startActivity(intent);
                                }else{
                                    Toast.makeText(LoginActivity.this, "Account Error" + task.getException(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }else{
                        textInputLayoutPassword.setError("Field is required");
                    }
                }else{
                    textInputLayoutEmail.setError("Field is required");
                }
            }
        });

        editTextEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                textInputLayoutEmail.setError(null);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        editTextPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                textInputLayoutPassword.setError(null);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }


}

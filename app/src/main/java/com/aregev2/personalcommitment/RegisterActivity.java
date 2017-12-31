package com.aregev2.personalcommitment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterActivity extends AppCompatActivity {

    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

    TextInputLayout textInputLayoutUsername;
    TextInputLayout textInputLayoutEmail;
    TextInputLayout textInputLayoutPassword;


    EditText editTextUsername;
    EditText editTextEmail;
    EditText editTextPassword;

    Button buttonRegister;

    String username;
    String email;
    String password;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        textInputLayoutUsername = findViewById(R.id.activity_register_layout_username);
        textInputLayoutEmail = findViewById(R.id.activity_register_layout_email);
        textInputLayoutPassword = findViewById(R.id.activity_register_layout_password);

        editTextUsername = findViewById(R.id.activity_register_editText_username);
        editTextEmail = findViewById(R.id.activity_register_editText_email);
        editTextPassword = findViewById(R.id.activity_register_editText_password);

        buttonRegister = findViewById(R.id.activity_register_button_register);
        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                username = editTextUsername.getText().toString();
                email = editTextEmail.getText().toString();
                password = editTextPassword.getText().toString();

                if(!username.isEmpty()){
                    if(!email.isEmpty()){
                        if(!password.isEmpty()){
                            textInputLayoutUsername.setError(null);
                            textInputLayoutEmail.setError(null);
                            textInputLayoutPassword.setError(null);
                            firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if(task.isSuccessful()){
                                        Toast.makeText(RegisterActivity.this, "Account Created", Toast.LENGTH_SHORT).show();
                                    }else{
                                        Toast.makeText(RegisterActivity.this, "Account Error" + task.getException(), Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        }else{
                            textInputLayoutPassword.setError("Field is required");
                        }
                    }else{
                        textInputLayoutEmail.setError("Field is required");
                    }
                }else{
                    textInputLayoutUsername.setError("Field is required");
                }
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        Toast.makeText(this, "Working", Toast.LENGTH_SHORT).show();
        return true;
    }


}

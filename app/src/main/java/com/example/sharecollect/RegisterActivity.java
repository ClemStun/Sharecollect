package com.example.sharecollect;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class RegisterActivity extends AppCompatActivity {

    private EditText etPseudo;
    private EditText etEmail;
    private EditText etPassword;
    private EditText etPasswordConfirm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        etPseudo = findViewById(R.id.editTextPseudo);
        etEmail = findViewById(R.id.editTextEmail);
        etPassword = findViewById(R.id.editTextPassword);
        etPasswordConfirm = findViewById(R.id.editTextPasswordConfirm);
    }

    public void backToLogin(View view) {
        finish();
    }

    public void onClickRegister(View view) {
        String pseudo = etPseudo.getText().toString();
        String email = etEmail.getText().toString();
        String password = etPassword.getText().toString();
        String passwordConfirm = etPasswordConfirm.getText().toString();

        if(pseudo.isEmpty()){
            etPseudo.setError("Pseudo is required");
            etPseudo.requestFocus();
        } else if(email.isEmpty()){
            etEmail.setError("Email is required");
            etEmail.requestFocus();
        } else if(password.isEmpty()){
            etPassword.setError("Password is required");
            etPassword.requestFocus();
        } else if(passwordConfirm.isEmpty()){
            etPasswordConfirm.setError("Password confirmation is required");
            etPasswordConfirm.requestFocus();
        } else if(!password.equals(passwordConfirm)) {
            etPasswordConfirm.setError("Passwords do not match");
            etPasswordConfirm.requestFocus();
        }
    }
}
package com.example.sharecollect;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class LoginActivity extends AppCompatActivity {

    private EditText etPseudo;
    private EditText etPassword;
    private Button btnLogin;
    private Button btnRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etPseudo = findViewById(R.id.editTextPseudo);
        etPassword = findViewById(R.id.editTextPassword);
        btnLogin = findViewById(R.id.login_btn);
        btnRegister = findViewById(R.id.register_btn);
    }

    public void onClickLogin(View view){
        String pseudo = etPseudo.getText().toString();
        String password = etPassword.getText().toString();

        if(pseudo.isEmpty()){
            etPseudo.setError("Pseudo is required");
            etPseudo.requestFocus();
        } else if(password.isEmpty()){
            etPassword.setError("Password is required");
            etPassword.requestFocus();
        }

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void onClickRegister(View view){
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }
}
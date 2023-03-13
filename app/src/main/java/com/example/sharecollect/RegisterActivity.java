package com.example.sharecollect;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

public class RegisterActivity extends AppCompatActivity {

    private EditText etPseudo;
    private EditText etEmail;
    private EditText etPassword;
    private EditText etPasswordConfirm;
    private TextView tvError;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        etPseudo = findViewById(R.id.editTextPseudo);
        etEmail = findViewById(R.id.editTextEmail);
        etPassword = findViewById(R.id.editTextPassword);
        etPasswordConfirm = findViewById(R.id.editTextPasswordConfirm);
        tvError = findViewById(R.id.textViewErrorMessage);
    }

    /**
     * Allows to hide the keyboard when clicking outside a text field
     * @param ev : event
     * @return : true if the event has been processed, false otherwise
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (getCurrentFocus() != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
        return super.dispatchTouchEvent(ev);
    }

    /**
     * Allow to go back to the login activity
     * @param view : view
     */
    public void backToLogin(View view) {
        finish();
    }

    /**
     * Allow to create a new user
     * If the creation is a success, we redirect to the login activity
     * Otherwise, we display an error message
     * @param view : view
     */
    public void onClickRegister(View view) {
        HttpGetRequest httpGetRequest = new HttpGetRequest();

        if(checkFields()) {

            String returnState = httpGetRequest.createUser(etPseudo.getText().toString().trim(),
                    etEmail.getText().toString().trim(),
                    etPassword.getText().toString().trim());

            if (returnState.equals("User created")) {
                Intent intent = new Intent(this, LoginActivity.class);
                startActivity(intent);
            } else if(returnState.equals("User already exists")) {
                tvError.setText("User already exists");
                tvError.setVisibility(View.VISIBLE);
            } else if(returnState.equals("Network error")){
                tvError.setText("Network error");
                tvError.setVisibility(View.VISIBLE);
            }
        }
    }

    /**
     * Check if all fields are filled
     * If a field is empty, we display an error message
     * If the passwords do not match, we display an error message
     * @return : true if all fields are filled, false otherwise
     */
    private boolean checkFields() {
        String pseudo = etPseudo.getText().toString();
        String email = etEmail.getText().toString();
        String password = etPassword.getText().toString();
        String passwordConfirm = etPasswordConfirm.getText().toString();

        if(pseudo.isEmpty()){
            etPseudo.setError("Pseudo is required");
            etPseudo.requestFocus();
            return false;
        } else if(email.isEmpty()){
            etEmail.setError("Email is required");
            etEmail.requestFocus();
            return false;
        } else if(password.isEmpty()){
            etPassword.setError("Password is required");
            etPassword.requestFocus();
            return false;
        } else if(passwordConfirm.isEmpty()){
            etPasswordConfirm.setError("Password confirmation is required");
            etPasswordConfirm.requestFocus();
            return false;
        } else if(!password.equals(passwordConfirm)) {
            etPasswordConfirm.setError("Passwords do not match");
            etPasswordConfirm.requestFocus();
            return false;
        }

        return true;
    }

    /**
     * Show/Hide password or confirm password when clicking on the eye icon
     * @param view : view
     */
    public void showHidePasswordOnClick(View view) {
        if (view.getId() == R.id.imageButtonPwdEye) {
            if (etPassword.getInputType() == 129) {
                etPassword.setInputType(1);
            } else {
                etPassword.setInputType(129);
            }
        } else if (view.getId() == R.id.imageButtonConfirmPwdEye) {
            if (etPasswordConfirm.getInputType() == 129) {
                etPasswordConfirm.setInputType(1);
            } else {
                etPasswordConfirm.setInputType(129);
            }
        }
    }

}
package com.example.sharecollect;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.sharecollect.controllers.UserController;
import com.example.sharecollect.models.User;

import java.util.HashMap;

/**
 * Activity to login a user
 * @author Hugo C.
 * @version 1.0
 * @since 2023-03-22
 */
public class LoginActivity extends AppCompatActivity {

    private EditText etPseudo;
    private EditText etPassword;
    private Button btnLogin;
    private Button btnRegister;
    private TextView tvError;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);

        etPseudo = findViewById(R.id.editTextPseudo);
        etPassword = findViewById(R.id.editTextPassword);
        btnLogin = findViewById(R.id.login_btn);
        btnRegister = findViewById(R.id.register_btn);
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
     * Allow to connect a user
     * If the connection is a success, we redirect to the main activity
     * Otherwise, we display an error message
     * @param view : view
     */
    public void onClickLogin(View view){

        if(checkFields()){
            HashMap<String, Object> response = HttpGetRequest.connectUser(etPseudo.getText().toString().trim(),
                    etPassword.getText().toString().trim());

            if(response.get("error").equals(getString(R.string.user_not_found))) {

                tvError.setText(getString(R.string.user_not_found));
                tvError.setVisibility(View.VISIBLE);
            } else if(response.get("error").equals(getString(R.string.network_error))) {

                tvError.setText(getString(R.string.network_error));
                tvError.setVisibility(View.VISIBLE);
            } else {
                Intent intent = new Intent(this, MainActivity.class);

                //Sotckage des données de l'utilisateur
                UserController userController = UserController.getInstance();
                Log.println(Log.DEBUG, "LoginActivity", "User connected : " + response.get("username") + " " + response.get("mail") + " " + response.get("token"));
                User user = new User(Integer.parseInt((String) response.get("id")), (String) response.get("username"), (String) response.get("mail"), (String) response.get("token"));
                userController.setUser(user);

                startActivity(intent);
            }
        }
    }

    /**
     * Allow to go to the register activity
     * @param view : view
     */
    public void onClickRegister(View view){
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }

    /**
     * Check if the fields are not empty
     * If they are, we display an error message
     * @return : true if the fields are not empty, false otherwise
     */
    private boolean checkFields(){
        String pseudo = etPseudo.getText().toString();
        String password = etPassword.getText().toString();

        if(pseudo.isEmpty()){
            etPseudo.setError(getString(R.string.pseudo_required));
            etPseudo.requestFocus();
            return false;
        } else if(password.isEmpty()){
            etPassword.setError(getString(R.string.pwd_required));
            etPassword.requestFocus();
            return false;
        }

        return true;
    }

    /**
     * Show/Hide password when clicking on the eye icon
     * @param view : view
     */
    public void showHidePasswordOnClick(View view) {
        if (view.getId() == R.id.imageButtonPwdEye) {
            if (etPassword.getInputType() == 129) {
                etPassword.setInputType(1);
            } else {
                etPassword.setInputType(129);
            }
        }
    }
}
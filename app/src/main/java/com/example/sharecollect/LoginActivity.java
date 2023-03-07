package com.example.sharecollect;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

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
        String id = "";
        String token = "";

        HttpGetRequest httpGetRequest = new HttpGetRequest();

        if(checkFields()){
            String returnState = httpGetRequest.connectUser(etPseudo.getText().toString(), etPassword.getText().toString());

            if(returnState.contains("token")) {

                // Extract id and token from returnState
                String[] splitReturnState = returnState.split(",");
                id = splitReturnState[0].split(":")[1];
                token = splitReturnState[1].split(":")[1].replace("\"", "");
            }
        }
        if(!id.isEmpty() && !token.isEmpty()){
            Intent intent = new Intent(this, MainActivity.class);
            intent.putExtra("id", id);
            intent.putExtra("token", token);
            startActivity(intent);
        } else{
            tvError.setText("Pseudo or password is incorrect");
            tvError.setVisibility(View.VISIBLE);
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
            etPseudo.setError("Pseudo is required");
            etPseudo.requestFocus();
            return false;
        } else if(password.isEmpty()){
            etPassword.setError("Password is required");
            etPassword.requestFocus();
            return false;
        }

        return true;
    }
}
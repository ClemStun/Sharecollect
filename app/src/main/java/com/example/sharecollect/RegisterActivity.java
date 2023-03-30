package com.example.sharecollect;

import android.Manifest;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;

/**
 * Activity to register a new user
 * @author Hugo C.
 * @version 1.0
 * @since 2023-03-22
 */
public class RegisterActivity extends AppCompatActivity {

    private EditText etPseudo;
    private EditText etEmail;
    private EditText etPassword;
    private EditText etPasswordConfirm;
    private TextView tvError;
    private Button btnAddPicture;

    Uri profilePictureURI = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        etPseudo = findViewById(R.id.editTextPseudo);
        etEmail = findViewById(R.id.editTextEmail);
        etPassword = findViewById(R.id.editTextPassword);
        etPasswordConfirm = findViewById(R.id.editTextPasswordConfirm);
        tvError = findViewById(R.id.textViewErrorMessage);
        btnAddPicture = findViewById(R.id.buttonProfilePicture);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    3);
        }

        System.out.println("RegisterActivity");

        // Définir une variable pour stocker le résultat de la sélection d'image
        ActivityResultLauncher<Intent> selectImageLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == Activity.RESULT_OK && result.getData() != null) {
                            // Récupérer l'URI de la photo sélectionnée par l'utilisateur
                            profilePictureURI = result.getData().getData();
                        }
                    }
                });

        btnAddPicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Créer un intent pour ouvrir la galerie de photos
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

                // Démarrer l'activité pour sélectionner une photo
                selectImageLauncher.launch(intent);
            }
        });
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
    public void onClickRegister(View view) throws FileNotFoundException {
        System.out.println("onClickRegister");

        if(checkFields()) {
            System.out.println("Fields checked");
            HashMap<String, Object> response = HttpGetRequest.createUser(etPseudo.getText().toString().trim(),
                    etEmail.getText().toString().trim(),
                    etPassword.getText().toString().trim());

            System.out.println(response);
            if (response.get("User").equals("User created")) {
                System.out.println("User created");
                if(profilePictureURI != null) {
                    System.out.println("Profile picture not null");
                    HashMap<String, Object> hashMapWithId = HttpGetRequest.getIdByUsername(etPseudo.getText().toString().trim());
                    // Retrieve file by URI
                    File profilePicture = getFileFromUri(profilePictureURI);
                    HttpGetRequest.sendProfilePicture(hashMapWithId.get("id").toString(), profilePicture);
                }
                Intent intent = new Intent(this, LoginActivity.class);
                startActivity(intent);
            } else if(response.get("error").equals(getString(R.string.user_already_exists))) {
                tvError.setText(getString(R.string.user_already_exists));
                tvError.setVisibility(View.VISIBLE);
            } else if(response.get("error").equals(getString(R.string.network_error))){
                tvError.setText(getString(R.string.network_error));
                tvError.setVisibility(View.VISIBLE);
            }
        }
    }

    private File getFileFromUri(Uri uri) {
        String path = null;
        if (ContentResolver.SCHEME_CONTENT.equals(uri.getScheme())) {
            ContentResolver contentResolver = getContentResolver();
            Cursor cursor = contentResolver.query(uri, null, null, null, null);
            if (cursor != null && cursor.moveToFirst()) {
                int index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                path = cursor.getString(index);
                cursor.close();
            }
        } else if (ContentResolver.SCHEME_FILE.equals(uri.getScheme())) {
            path = uri.getPath();
        }
        if (path != null) {
            return new File(path);
        }
        return null;
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
            etPseudo.setError(getString(R.string.pseudo_required));
            etPseudo.requestFocus();
            return false;
        } else if(email.isEmpty()){
            etEmail.setError(getString(R.string.email_required));
            etEmail.requestFocus();
            return false;
        } else if(password.isEmpty()){
            etPassword.setError(getString(R.string.pwd_required));
            etPassword.requestFocus();
            return false;
        } else if(passwordConfirm.isEmpty()){
            etPasswordConfirm.setError(getString(R.string.pwd_confirm_required));
            etPasswordConfirm.requestFocus();
            return false;
        } else if(!password.equals(passwordConfirm)) {
            etPasswordConfirm.setError(getString(R.string.pwd_confirm_not_match));
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
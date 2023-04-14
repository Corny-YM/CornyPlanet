package com.example.planetcorny;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.planetcorny.target.Account;
import com.example.planetcorny.utilities.Constants;
import com.example.planetcorny.utilities.PreferenceManager;

public class SignUpActivity extends AppCompatActivity {
    EditText edtName, edtEmail, edtPassword, edtConfirmPassword;
    Button btnSignUp;
    TextView tvSignIn;

    DbHelper dbHelper = new DbHelper(this);
    private PreferenceManager preferenceManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        preferenceManager = new PreferenceManager(this);

        setContentView(R.layout.activity_sign_up);
        map();
        listeners();
    }

    private void handleSignIn(String email, String name) {
        preferenceManager.putBoolean(Constants.KEY_IS_SIGNED_IN, true);
        preferenceManager.putString(Constants.KEY_USER_EMAIL, email);
        preferenceManager.putString(Constants.KEY_USER_NAME, name);
    }

    public void listeners() {
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = edtName.getText().toString();
                String email = edtEmail.getText().toString();
                String password = edtPassword.getText().toString();
                String confirmPassword = edtConfirmPassword.getText().toString();

                Boolean isExisted = dbHelper.checkExistedAccount(email);
                if(isExisted) {
                    edtEmail.setText("This email has been used");
                    Toast.makeText(SignUpActivity.this, "This email has been used", Toast.LENGTH_LONG).show();
                    return;
                }
                if(!password.equals(confirmPassword)) {
                    edtConfirmPassword.setText("Password & Confirm password is not match!");
                    Toast.makeText(SignUpActivity.this, "Password & Confirm password is not match!", Toast.LENGTH_LONG).show();
                    return;
                }

                Account acc = new Account(name, email, password);
                dbHelper.addAccount(acc);

                handleSignIn(email, name);

                Intent intent = new Intent(SignUpActivity.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
            }
        });

        tvSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignUpActivity.this, SignInActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    public void map() {
        tvSignIn = findViewById(R.id.textSignIn);
        btnSignUp = findViewById(R.id.buttonSignUp);
        edtName = findViewById(R.id.inputName);
        edtEmail = findViewById(R.id.inputEmail);
        edtPassword = findViewById(R.id.inputPassword);
        edtConfirmPassword = findViewById(R.id.inputConfirmPassword);
    }
}
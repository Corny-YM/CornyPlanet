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

import java.util.Objects;

public class SignInActivity extends AppCompatActivity {
    TextView createAccount, signInAsGuest;
    EditText edtEmail, edtPassword;
    Button btnSignIn;

    DbHelper dbHelper = new DbHelper(this);
    private PreferenceManager preferenceManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Check if signed
        preferenceManager = new PreferenceManager(getApplicationContext());
        Boolean isSigned = preferenceManager.getBoolean(Constants.KEY_IS_SIGNED_IN);
        String email = preferenceManager.getString(Constants.KEY_USER_EMAIL);
        if (isSigned && !email.equals("")) {
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
            finish();
        }

        setContentView(R.layout.activity_sign_in);
        map();
        listeners();
    }

    private void handleSignIn(String id, String email, String name) {
        preferenceManager.putBoolean(Constants.KEY_IS_SIGNED_IN, true);
        preferenceManager.putString(Constants.KEY_USER_ID, id);
        preferenceManager.putString(Constants.KEY_USER_EMAIL, email);
        preferenceManager.putString(Constants.KEY_USER_NAME, name);
    }

    public void listeners() {
        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = edtEmail.getText().toString();
                String password = edtPassword.getText().toString();

                if (email.trim().equals("") || password.trim().equals("")) {
                    Toast.makeText(SignInActivity.this, "Empty input!", Toast.LENGTH_SHORT).show();
                    return;
                }

                Account acc = dbHelper.getAccount(email);
                // corny@gmail.com - 123123
                // theanh@gmail.com - 123

                if (acc.getEmail().equals("")) {
                    showToast("Account doesn't exist!");
                    clearInput();
                    return;
                }

                if (!acc.getPassword().equals(password)) {
                    showToast("Password not correct!");
                    edtEmail.setText("Password not correct");
                    return;
                }

                String userId = acc.getId()+"";
                String userName = acc.getDisplayName();
                String userEmail = acc.getEmail();
                handleSignIn(userId, userEmail, userName);

                Intent intent = new Intent(SignInActivity.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
            }
        });
        createAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignInActivity.this, SignUpActivity.class);
                startActivity(intent);
                // finish();
            }
        });
        signInAsGuest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleSignIn("", "", "");
                Intent intent = new Intent(SignInActivity.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
            }
        });
    }

    public void map() {
        createAccount = findViewById(R.id.textCreateNewAccount);
        signInAsGuest = findViewById(R.id.textSignInAsGuest);
        edtEmail = findViewById(R.id.inputEmail);
        edtPassword = findViewById(R.id.inputPassword);
        btnSignIn = findViewById(R.id.buttonSignIn);
    }

    public void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }

    public void clearInput() {
        edtEmail.setText("");
        edtPassword.setText("");
    }
}
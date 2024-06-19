package com.example.projekt_quiz;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Login extends AppCompatActivity {

    private EditText email, password;
    private ProgressBar progressbar;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mAuth = FirebaseAuth.getInstance();
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        Button loginBtn = findViewById(R.id.login);
        progressbar = findViewById(R.id.progressBar);
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                loginUserAccount();
            }
        });

    }

    public void intentToRegistration(View view) {
        Intent intent = new Intent(this, Registration.class);
        startActivity(intent);
    }

    private void loginUserAccount()
    {
        progressbar.setVisibility(View.VISIBLE);
        String emailText, passwordText;
        emailText = email.getText().toString();
        passwordText = password.getText().toString();
        if (TextUtils.isEmpty(emailText)) {
            Toast.makeText(getApplicationContext(),
                "Enter email!",
                Toast.LENGTH_SHORT)
                .show();
            return;
        }

        if (TextUtils.isEmpty(passwordText)) {
            Toast.makeText(getApplicationContext(),
                "Enter password!",
                Toast.LENGTH_SHORT)
                .show();
            return;
        }
        mAuth.signInWithEmailAndPassword(emailText, passwordText)
            .addOnCompleteListener(
                new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(
                            @NonNull Task<AuthResult> task)
                    {
                        if (task.isSuccessful()) {
                            Toast.makeText(getApplicationContext(),
                                            "Login successful!",
                                            Toast.LENGTH_SHORT)
                                    .show();
                            progressbar.setVisibility(View.GONE);
                            Intent intent
                                    = new Intent(Login.this,
                                    MainActivity.class);
                            startActivity(intent);
                        }
                        else {
                            Toast.makeText(getApplicationContext(),
                                            "Login failed!",
                                            Toast.LENGTH_SHORT)
                                    .show();
                            progressbar.setVisibility(View.GONE);
                        }
                    }
                });
    }
}

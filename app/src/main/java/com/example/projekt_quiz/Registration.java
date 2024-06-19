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

public class Registration extends AppCompatActivity {

    private EditText email, password;
    private ProgressBar progressBar;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        mAuth = FirebaseAuth.getInstance();
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        Button registerBtn = findViewById(R.id.register);
        progressBar = findViewById(R.id.progressbar);
        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerNewUser();
            }
        });
    }
    public void intentToLogin(View view) {
        Intent intent = new Intent(this, Login.class);
        startActivity(intent);
    }
    private void registerNewUser() {
        progressBar.setVisibility(View.VISIBLE);

        String emailText, passwordText;
        emailText = email.getText().toString();
        passwordText = password.getText().toString();
        if (TextUtils.isEmpty(emailText)) {
            Toast.makeText(getApplicationContext(),
                "Email not entered!",
                Toast.LENGTH_SHORT)
                .show();
            return;
        }

        if (TextUtils.isEmpty(passwordText)) {
            Toast.makeText(getApplicationContext(),
                "Password not entered!",
                Toast.LENGTH_SHORT)
                .show();
            return;
        }
        mAuth.createUserWithEmailAndPassword(emailText, passwordText)
            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    progressBar.setVisibility(View.GONE);
                    if (task.isSuccessful()) {
                        Toast.makeText(getApplicationContext(),
                                "Registration successful!",
                                Toast.LENGTH_SHORT)
                                .show();
                        Intent intent = new Intent(Registration.this, Login.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(
                            getApplicationContext(),
                            "Registration failed!",
                            Toast.LENGTH_SHORT)
                            .show();
                    }
                }
            });
    }
}
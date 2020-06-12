package com.almohamady.firebaseauth;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText txtUsername, txtPassword;
    FirebaseAuth mAuth;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();
        findViewById(R.id.textViewSignup).setOnClickListener(this);
        findViewById(R.id.buttonLogin).setOnClickListener(this);
        txtUsername = findViewById(R.id.editTextEmail);
        txtPassword = findViewById(R.id.editTextPassword);
        progressBar = findViewById(R.id.progressbar);
    }

    private void userLogin() {
        String username = txtUsername.getText().toString();
        String password = txtPassword.getText().toString();
        if (username.isEmpty()) {
            txtUsername.setError("Email is required");
            txtUsername.requestFocus();
        } else if (!Patterns.EMAIL_ADDRESS.matcher(username).matches()) {
            txtUsername.setError("Email is valid");
            txtUsername.requestFocus();
        } else if (password.isEmpty()) {
            txtPassword.setError("Password is required");
            txtPassword.requestFocus();
        } else {
            progressBar.setVisibility(View.VISIBLE);
            mAuth.signInWithEmailAndPassword(username, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        Toast.makeText(getApplicationContext(), "Success", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(MainActivity.this, ProfileActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                    } else {
                        Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                    progressBar.setVisibility(View.GONE);
                }
            });
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.textViewSignup:
                startActivity(new Intent(this, SignUpActivity.class));
                break;
            case R.id.buttonLogin:
                userLogin();
                break;
        }
    }
}

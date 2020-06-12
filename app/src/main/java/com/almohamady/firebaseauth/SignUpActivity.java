package com.almohamady.firebaseauth;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;

public class SignUpActivity  extends AppCompatActivity implements View.OnClickListener {

    ProgressBar progressBar;
    EditText txtUserName, txtPassword;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        mAuth = FirebaseAuth.getInstance();
        findViewById(R.id.buttonSignUp).setOnClickListener(this);
        findViewById(R.id.textViewLogin).setOnClickListener(this);
        progressBar = findViewById(R.id.progressbar);
        txtUserName = findViewById(R.id.editTextEmail);
        txtPassword = findViewById(R.id.editTextPassword);
    }

    private void registerUser() {
          String userName = txtUserName.getText().toString().trim();
          String password = txtPassword.getText().toString().trim();

          if (userName.isEmpty()){
              txtUserName.setError("Email is required");
              txtUserName.requestFocus();
          } else if (!Patterns.EMAIL_ADDRESS.matcher(userName).matches()){
              txtUserName.setError("Email is valid");
              txtUserName.requestFocus();
          } else if  (password.isEmpty()) {
              txtPassword.setError("Password is required");
              txtPassword.requestFocus();
          } else {
              progressBar.setVisibility(View.VISIBLE);
              mAuth.createUserWithEmailAndPassword(userName, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                  @Override
                  public void onComplete(@NonNull Task<AuthResult> task) {
                      if (task.isSuccessful()) {
                          Toast.makeText(getApplicationContext(), "User Registered Successful", Toast.LENGTH_SHORT).show();
                          finish();
                      } else {
                          if (task.getException() instanceof FirebaseAuthUserCollisionException) {
                              Toast.makeText(getApplicationContext(), "Email is Exists ", Toast.LENGTH_SHORT).show();
                          } else {
                              Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                          }
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
           case R.id.buttonSignUp:
               registerUser();
               break;
           case R.id.textViewLogin:
               startActivity(new Intent(this, MainActivity.class));
               break;
       }
    }
}

package com.almohamady.firebaseauth;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class SignUpActivity  extends AppCompatActivity implements View.OnClickListener {

    EditText txtUserName, txtPassword;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        findViewById(R.id.buttonSignUp).setOnClickListener(this);
        findViewById(R.id.textViewLogin).setOnClickListener(this);
        txtUserName = findViewById(R.id.editTextEmail);
        txtPassword = findViewById(R.id.editTextPassword);
    }

    private void registerUser() {
          String userName = txtUserName.getText().toString().trim();
          String password = txtPassword.getText().toString().trim();
          
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

package com.example.chatiniapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.chatiniapp.Services.RegistrationService;

import org.json.JSONException;

public class SignUpActivity extends AppCompatActivity {

    RegistrationService registrationService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        registrationService  = new RegistrationService(SignUpActivity.this);

        EditText username = findViewById(R.id.username);
        EditText email = findViewById(R.id.email);
        EditText password = findViewById(R.id.password);
        EditText phone = findViewById(R.id.phone);
        EditText city = findViewById(R.id.adress);
        Button register = findViewById(R.id.register);
        TextView login = findViewById(R.id.login);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    registrationService.register(username.getText().toString(),
                            email.getText().toString(),
                            phone.getText().toString(),
                            password.getText().toString(),
                            city.getText().toString()  );
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignUpActivity.this, LoginActivity.class));
            }
        });

    }
}
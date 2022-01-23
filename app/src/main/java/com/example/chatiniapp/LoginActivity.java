package com.example.chatiniapp;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.android.volley.RequestQueue;
import com.example.chatiniapp.Services.RegistrationService;
public class LoginActivity extends AppCompatActivity {

    RegistrationService registrationService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        registrationService  = new RegistrationService(LoginActivity.this);

        EditText username = findViewById(R.id.username);
        EditText password = findViewById(R.id.password);
        Button login = findViewById(R.id.login);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                registrationService.login(username.getText().toString(),
                                          password.getText().toString()) ;


//                if (ContextCompat.checkSelfPermission(
//                        LoginActivity.this, Manifest.permission.ACCESS_NETWORK_STATE) ==
//                        PackageManager.PERMISSION_GRANTED) {
//                    registrationService.login(username.getText().toString(),
//                            password.getText().toString());
//
//                } else Toast.makeText(LoginActivity.this, "failed", Toast.LENGTH_SHORT).show();

            }
        });

        TextView register = findViewById(R.id.register);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, SignUpActivity.class));
            }
        });
    }

}
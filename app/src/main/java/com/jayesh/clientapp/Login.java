package com.jayesh.clientapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Login extends AppCompatActivity {
    private Button button;
    private EditText email,password;
    SharedPreferenceConfig preferenceConfig;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        preferenceConfig = new SharedPreferenceConfig(getApplicationContext());
        email=findViewById(R.id.editText);
        password=findViewById(R.id.editText2);
        button=findViewById(R.id.button);


        if(preferenceConfig.readLoginStatus())
        {
            startActivity(new Intent(Login.this,User.class));
            finish();
        }




        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String emailId = email.getText().toString().trim();
                String pass = password.getText().toString().trim();

                if(emailId.isEmpty()){
                    email.setError("Please enter Email Id");
                }
                if(pass.isEmpty()){
                    password.setError("Please enter Password");
                }
                if(emailId.equals("abc@gmail.com")&&pass.equals("12345")){

                    startActivity(new Intent(Login.this,User.class));
                    preferenceConfig.writeLoginStatus(true);
                    finish();
                }
                else{
                    Toast.makeText(Login.this, "Invalid Login Credentials", Toast.LENGTH_SHORT).show();
                    email.setText("");
                    password.setText("");
                }
            }

        });



}}


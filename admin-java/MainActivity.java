package com.admin.spzone;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private Button loginbtn;
    private EditText user_email,user_pass;
    private TextView text_show;

    private String email,password;


    private SharedPreferences sharedpreferences;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sharedpreferences=this.getSharedPreferences("login",MODE_PRIVATE);
        editor=sharedpreferences.edit();

        if(sharedpreferences.getString("isLogin","false").equals("yes")){
            opendash();
        }

        loginbtn = findViewById(R.id.loginbtn);
        user_email = findViewById(R.id.user_email);
        user_pass = findViewById(R.id.user_pass);
        text_show = findViewById(R.id.text_show);



        text_show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(user_pass.getInputType()==144) {
                    user_pass.setInputType(129);
                    text_show.setText("hide");
                }
                else{
                    user_pass.setInputType(144);
                    text_show.setText("show");
                }
                user_pass.setSelection(user_pass.getText().length());
            }
        });

        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validatedata();
            }
        });
    }

    private void validatedata() {
        email=user_email.getText().toString();
        password=user_pass.getText().toString();
        if (TextUtils.isEmpty(email)) {
            user_email.setError("Email cannot be empty");
            user_email.requestFocus();
        }
        else if (TextUtils.isEmpty(password)){
            user_pass.setError("Password cannot be empty");
            user_pass.requestFocus();
        }
        else if (email.equals("admin@gmail.com") && password.equals("admin@spzone123")){
            editor.putString("isLogin","yes");
            editor.commit();
            Toast.makeText(MainActivity.this,"Login Successful", Toast.LENGTH_SHORT).show();
            opendash();
        }
        else{
            Toast.makeText(this, "check email and password again", Toast.LENGTH_SHORT).show();
        }
    }

    private void opendash() {
        startActivity(new Intent(MainActivity.this, Admin_dashboard.class));
        finish();
    }
}
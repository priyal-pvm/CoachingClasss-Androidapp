package com.admin.spzone;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class ViewProfile extends AppCompatActivity {

    private Button logoutbtn;
    private SharedPreferences sharedpreferences;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_profile);

        sharedpreferences=this.getSharedPreferences("login",MODE_PRIVATE);
        editor=sharedpreferences.edit();

        logoutbtn = findViewById(R.id.logoutbtn);
        logoutbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor.putString("isLogin","false");
                editor.commit();
                openlogin();
            }
        });


    }

    private void openlogin() {
        startActivity(new Intent(ViewProfile.this, MainActivity.class));
        finish();
    }
}
package com.admin.spzone;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

public class Admin_dashboard extends AppCompatActivity implements View.OnClickListener
{
    CardView view_profile,upload_notice,manage_student,manage_teacher,manage_fees,manage_salary,permit_leave,add_schedule;

    private SharedPreferences sharedpreferences;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_dashboard);

        sharedpreferences=this.getSharedPreferences("login",MODE_PRIVATE);
        editor=sharedpreferences.edit();

        if(sharedpreferences.getString("isLogin","false").equals("false")){
            openlogin();
        }

        view_profile=findViewById(R.id.view_profile);
        view_profile.setOnClickListener(this);

        manage_salary=findViewById(R.id.manage_salary);
        manage_salary.setOnClickListener(this);

        upload_notice=findViewById(R.id.upload_notice);
        upload_notice.setOnClickListener(this);

        manage_student=findViewById(R.id.manage_student);
        manage_student.setOnClickListener(this);

        manage_teacher=findViewById(R.id.manage_teacher);
        manage_teacher.setOnClickListener(this);

        add_schedule=findViewById(R.id.add_schedule);
        add_schedule.setOnClickListener(this);

    }

    private void openlogin() {
        startActivity(new Intent(Admin_dashboard.this, MainActivity.class));
        finish();
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.upload_notice:
                intent = new Intent(Admin_dashboard.this, UploadNotice.class);
                startActivity(intent);
                break;
            case R.id.view_profile:
                intent = new Intent(Admin_dashboard.this, ViewProfile.class);
                startActivity(intent);
                break;
            case R.id.manage_salary:
                intent = new Intent(Admin_dashboard.this, fees.class);
                startActivity(intent);
                break;
            case R.id.manage_student:
                intent = new Intent(Admin_dashboard.this, ManageStudents.class);
                startActivity(intent);
                break;
            case R.id.manage_teacher:
                intent = new Intent(Admin_dashboard.this, Updatefaculty.class);
                startActivity(intent);
                break;
            case R.id.add_schedule:
                intent = new Intent(Admin_dashboard.this, AddSchedule.class);
                startActivity(intent);
                break;
        }
    }
}
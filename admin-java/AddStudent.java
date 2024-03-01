package com.admin.spzone;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class AddStudent extends AppCompatActivity {

    private EditText addStudentName,addStudentRollno,addStudentPass,addStudentEmail,addStudentPhone;
    private Button addStudentButton;
    private Spinner AddStudentClass,AddStudentBatch;
    private String name,rollno,passtxt,email,phone;

    private String std,batch;

    private ProgressDialog pd;

    private DatabaseReference dbreference,dbref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_student);

        addStudentName=findViewById(R.id.addStudentName);
        addStudentRollno=findViewById(R.id.addStudentRollno);
        addStudentPass=findViewById(R.id.addStudentPass);
        addStudentEmail=findViewById(R.id.addStudentEmail);
        addStudentPhone=findViewById(R.id.addStudentPhone);
        AddStudentClass=findViewById(R.id.AddStudentClass);
        AddStudentBatch=findViewById(R.id.AddStudentBatch);

        addStudentButton=findViewById(R.id.addStudentButton);


        dbreference= FirebaseDatabase.getInstance().getReference().child("Students");

        addStudentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkvalidation();
            }
        });
        //select standard
        String[] standards =new String[]{"Select student standard","6th VI","7th VII","8th VIII","9th IX","10th X","11th XI","12th XII"};
        AddStudentClass.setAdapter(new ArrayAdapter<String>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,standards));
        AddStudentClass.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                std = AddStudentClass.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        //select batch
        String[] batches =new String[]{"Select student batch","F001","F002"};
        AddStudentBatch.setAdapter(new ArrayAdapter<String>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,batches));
        AddStudentBatch.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                batch = AddStudentBatch.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void checkvalidation() {
        name=addStudentName.getText().toString();
        rollno=addStudentRollno.getText().toString();
        passtxt=addStudentPass.getText().toString();
        email=addStudentEmail.getText().toString();
        phone=addStudentPhone.getText().toString();

        if(name.isEmpty()){
            addStudentName.setError("Field cannot be empty");
            addStudentName.requestFocus();
        }
        else if(email.isEmpty()){
            addStudentEmail.setError("Field cannot be empty");
            addStudentEmail.requestFocus();
        }
        else if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            addStudentEmail.setError("Email is not proper");
            addStudentEmail.requestFocus();
        }
        else if(rollno.isEmpty()){
            addStudentRollno.setError("Field cannot be empty");
            addStudentRollno.requestFocus();
        }
        else if(passtxt.isEmpty()){
            addStudentPass.setError("Field cannot be empty");
            addStudentPass.requestFocus();
        }
        else if(passtxt.length()<8){
            addStudentPass.setError("Minimun length 8");
            addStudentPass.requestFocus();
        }
        else if(phone.isEmpty()) {
            addStudentPhone.setError("Field cannot be empty");
            addStudentPhone.requestFocus();
        }
        else if(phone.length()<10){
            addStudentPhone.setError("10 Digits");
            addStudentPhone.requestFocus();
        }
        else if(std.equals("Select student standard")){
            Toast.makeText(this, "Select student standard", Toast.LENGTH_SHORT).show();
        }
        else if(batch.equals("Select student batch")){
            Toast.makeText(this, "Select student batch", Toast.LENGTH_SHORT).show();
        }
        else {
            insertData();
        }

    }

    private void insertData() {
        dbref= dbreference.child(std);

        StudentData studentdata= new StudentData(name,rollno,passtxt,email,phone,batch,std);
        dbref.child(rollno).setValue(studentdata).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                pd.dismiss();
                Toast.makeText(AddStudent.this, "Student Info Uploaded successfully", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                pd.dismiss();
                Toast.makeText(AddStudent.this, "something went wrong", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
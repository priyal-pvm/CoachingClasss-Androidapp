package com.admin.spzone;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
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

import java.util.HashMap;

public class Viewstudent extends AppCompatActivity {


    private EditText UpdateStudentName,UpdateStudentRollno,UpdateStudentPass,UpdateStudentEmail,UpdateStudentPhone;
    private Button UpdateStudentBtn,DeleteStudentBtn;
    private Spinner UpdateStudentClass,UpdateStudentBatch;

    private String name,rollno,passtxt,email,phone;
    private String std,batch;
    private DatabaseReference dbreference,dbref;

    private ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewstudent);


            UpdateStudentName=findViewById(R.id.UpdateStudentName);
            UpdateStudentRollno=findViewById(R.id.UpdateStudentRollno);
            UpdateStudentPass=findViewById(R.id.UpdateStudentPass);
            UpdateStudentEmail=findViewById(R.id.UpdateStudentEmail);
            UpdateStudentClass=findViewById(R.id.UpdateStudentClass);
            UpdateStudentBatch=findViewById(R.id.UpdateStudentBatch);
            UpdateStudentPhone=findViewById(R.id.UpdateStudentPhone);

            UpdateStudentBtn=findViewById(R.id.UpdateStudentBtn);
            DeleteStudentBtn=findViewById(R.id.DeleteStudentBtn);
            pd= new ProgressDialog(this);

            dbreference= FirebaseDatabase.getInstance().getReference().child("Students");

            name=getIntent().getStringExtra("name");
            email=getIntent().getStringExtra("email");
            phone=getIntent().getStringExtra("phone");
            rollno=getIntent().getStringExtra("rollno");
            passtxt= getIntent().getStringExtra("passtxt");
            std=getIntent().getStringExtra("std");
            batch=getIntent().getStringExtra("batch");

            UpdateStudentName.setText(name);
            UpdateStudentRollno.setText(rollno);
            UpdateStudentPass.setText(passtxt);
            UpdateStudentEmail.setText(email);
            UpdateStudentPhone.setText(phone);

            DeleteStudentBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder builder= new AlertDialog.Builder(Viewstudent.this);
                    builder.setMessage("Are you sure you ant to delete student data?");
                    builder.setCancelable(true);
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            deletedata();
                        }
                    });
                    builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.cancel();
                        }
                    });
                    AlertDialog dialog=null;
                    try{
                        dialog=builder.create();
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                    if(dialog!=null){
                        dialog.show();}
                }
            });

            UpdateStudentBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder builder= new AlertDialog.Builder(Viewstudent.this);
                    builder.setMessage("Are yu sure you ant to update Student data?");
                    builder.setCancelable(true);
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            name=UpdateStudentName.getText().toString();
                            email=UpdateStudentEmail.getText().toString();
                            phone=UpdateStudentPhone.getText().toString();
                            passtxt=UpdateStudentPass.getText().toString();
                            rollno=UpdateStudentRollno.getText().toString();


                            checkValidation();
                        }
                    });
                    builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.cancel();
                        }
                    });
                    AlertDialog dialog=null;
                    try{
                        dialog=builder.create();
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                    if(dialog!=null){
                        dialog.show();}
                }
            });
            //select standard
            String[] standards =new String[]{"Select student standard","6th VI","7th VII","8th VIII","9th IX","10th X"};
            UpdateStudentClass.setAdapter(new ArrayAdapter<String>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,standards));
            UpdateStudentClass.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    std = UpdateStudentClass.getSelectedItem().toString();
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
            //select batch
            String[] batches =new String[]{"Select student batch","F001","F002"};
            UpdateStudentBatch.setAdapter(new ArrayAdapter<String>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,batches));
            UpdateStudentBatch.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    batch = UpdateStudentBatch.getSelectedItem().toString();
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

        }

        private void checkValidation() {
            if(name.isEmpty()){
                UpdateStudentName.setError("Field cannot be empty");
                UpdateStudentName.requestFocus();
            }
            else if(email.isEmpty()){
                UpdateStudentEmail.setError("Field cannot be empty");
                UpdateStudentEmail.requestFocus();
            }
            else if(rollno.isEmpty()){
                UpdateStudentRollno.setError("Field cannot be empty");
                UpdateStudentRollno.requestFocus();
            }
            else if(passtxt.isEmpty()){
                UpdateStudentPass.setError("Field cannot be empty");
                UpdateStudentPass.requestFocus();
            }
            else if(phone.isEmpty()){
                UpdateStudentPhone.setError("Field cannot be empty");
                UpdateStudentPhone.requestFocus();
            }
            else if(std.equals("Select Standard")){
                Toast.makeText(this, "Select student standard", Toast.LENGTH_SHORT).show();
            }
            else if(batch.equals("Select Batch")){
                Toast.makeText(this, "Select student batch", Toast.LENGTH_SHORT).show();
            }
            else {
                updatedata();
            }
        }

        private void updatedata() {
            HashMap hp=new HashMap();
            hp.put("name",name);
            hp.put("email",email);
            hp.put("phone",phone);
            hp.put("passtxt",passtxt);
            hp.put("rollno",rollno);
            hp.put("std",std);
            hp.put("batch",batch);

            dbreference.child(std).child(rollno).updateChildren(hp).addOnSuccessListener(new OnSuccessListener() {
                @Override
                public void onSuccess(Object o) {
                    Toast.makeText(Viewstudent.this, "Student data updated", Toast.LENGTH_SHORT).show();
                    Intent intent=new Intent(Viewstudent.this,ManageStudents.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(Viewstudent.this, "something isn't right", Toast.LENGTH_SHORT).show();
                }
            });
        }

        private void deletedata() {
            dbreference.child(std).child(rollno).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void unused) {
                    Toast.makeText(Viewstudent.this, "Student data deleted", Toast.LENGTH_SHORT).show();
                    Intent intent=new Intent(Viewstudent.this,ManageStudents.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(Viewstudent.this, "Unable to delete", Toast.LENGTH_SHORT).show();
                }
            });
    }
}
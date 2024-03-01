package com.admin.spzone;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Patterns;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.IOException;


public class AddTeacher extends AppCompatActivity {

    private ImageView teacherimage;
    private EditText addTeacherName,addTeacherEmail,addTeacherPhone,password;
    private Spinner AddTeacherSubject;
    private Button addTeacherButton;
    private String name,email,phone,downloadurl="",pass;

    private final int REQ =1;
    private Bitmap bitmap=null;
    private ProgressDialog pd;
    private StorageReference streference;
    private DatabaseReference dbreference,dbref;

    private String subject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_teacher);

        password=findViewById(R.id.addTeacherPassword);
        teacherimage=findViewById(R.id.teacherimage);
        addTeacherName=findViewById(R.id.addTeacherName);
        addTeacherEmail=findViewById(R.id.addTeacherEmail);
        addTeacherPhone=findViewById(R.id.addTeacherPhone);
        AddTeacherSubject=findViewById(R.id.AddTeacherSubject);
        addTeacherButton=findViewById(R.id.addTeacherButton);
        pd= new ProgressDialog(this);

        dbreference= FirebaseDatabase.getInstance().getReference().child("Teachers");
        streference= FirebaseStorage.getInstance().getReference();

        teacherimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                opengallery();
            }
        });

        addTeacherButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkvalidation();
            }
        });

        String[] subjects =new String[]{"Select Subject","Chemistry","Mathematics","Biology","English","Physics"};
        AddTeacherSubject.setAdapter(new ArrayAdapter<String>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,subjects));
        AddTeacherSubject.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                subject = AddTeacherSubject.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    private void checkvalidation() {
        name=addTeacherName.getText().toString();
        email=addTeacherEmail.getText().toString();
        phone=addTeacherPhone.getText().toString();
        pass=password.getText().toString();
        if(name.isEmpty()){
            addTeacherName.setError("Field cannot be empty");
            addTeacherName.requestFocus();
        }
        else if(email.isEmpty()){
            addTeacherEmail.setError("Field cannot be empty");
            addTeacherEmail.requestFocus();
        }
        else if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            addTeacherEmail.setError("Email is not proper");
            addTeacherEmail.requestFocus();
        }
        else if(pass.isEmpty()){
            password.setError("Field cannot be empty");
            password.requestFocus();
        }
        else if(pass.length()<8){
            password.setError("Minimum 8");
            password.requestFocus();
        }
        else if(phone.isEmpty()){
            addTeacherPhone.setError("Field cannot be empty");
            addTeacherPhone.requestFocus();
        }
        else if(phone.length()<10){
            addTeacherPhone.setError("10 Digits");
            addTeacherPhone.requestFocus();
        }
        else if(subject.equals("Select Subject")){
            Toast.makeText(this, "Select a subject category", Toast.LENGTH_SHORT).show();
        }
        else if(bitmap==null){
            insertData();
        }
        else{
            insertImage();
        }
    }

    private void insertData() {
        dbref= dbreference.child(subject);
        //final String uniquekey= dbref.push().getKey();

        TeacherData teacherdata= new TeacherData(name,email,phone,downloadurl,pass);
        dbref.child(phone).setValue(teacherdata).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                pd.dismiss();
                startActivity(new Intent(AddTeacher.this,Updatefaculty.class));
                finish();
                Toast.makeText(AddTeacher.this, "Teacher Info Uploaded successfully", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                pd.dismiss();
                Toast.makeText(AddTeacher.this, "something went wrong", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void insertImage() {
        pd.setMessage("Uploading...");
        pd.show();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress (Bitmap.CompressFormat.JPEG, 50, baos);
        byte[] finalimg= baos.toByteArray();
        final StorageReference filePath;
        filePath= streference.child("Teachers").child(finalimg+".jpg");
        final UploadTask uptask= filePath.putBytes(finalimg);
        uptask.addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                if (task.isSuccessful()){
                    uptask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            filePath.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    downloadurl= String.valueOf(uri);
                                    insertData();
                                }
                            });
                        }
                    });
                }else{
                    pd.dismiss();
                    Toast.makeText(AddTeacher.this,"something went wrong",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    private void opengallery() {
        Intent pickimage = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(pickimage,REQ);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQ && resultCode == RESULT_OK){
            Uri uri=data.getData();
            try {
                bitmap= MediaStore.Images.Media.getBitmap(getContentResolver(),uri);
            } catch (IOException e) {
                Toast.makeText(AddTeacher.this, "error loading image", Toast.LENGTH_SHORT).show();
            }
            teacherimage.setImageBitmap(bitmap);
        }
    }

}
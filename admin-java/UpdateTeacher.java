package com.admin.spzone;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;

public class UpdateTeacher extends AppCompatActivity {

    private ImageView upteacherimage;
    private EditText upTeacherName,upTeacherEmail,upTeacherPhone;
    private Button UpdateTeacherBtn,DeleteTeacherBtn;

    private String name,email,phone,image,downloadurl,uniquekey,category;
    private final int REQ =1;
    private Bitmap bitmap=null;
    private ProgressDialog pd;
    private StorageReference streference;
    private DatabaseReference dbreference,dbref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_teacher);

        name=getIntent().getStringExtra("name");
        email=getIntent().getStringExtra("email");
        phone=getIntent().getStringExtra("phone");
        image=getIntent().getStringExtra("image");
        uniquekey= getIntent().getStringExtra("key");
        category=getIntent().getStringExtra("category");

        upteacherimage=findViewById(R.id.upteacherimage);
        upTeacherName=findViewById(R.id.upTeacherName);
        upTeacherEmail=findViewById(R.id.upTeacherEmail);
        upTeacherPhone=findViewById(R.id.upTeacherPhone);
        UpdateTeacherBtn=findViewById(R.id.UpdateTeacherBtn);
        DeleteTeacherBtn=findViewById(R.id.DeleteTeacherBtn);
        pd= new ProgressDialog(this);

        dbreference= FirebaseDatabase.getInstance().getReference().child("Teachers");
        streference= FirebaseStorage.getInstance().getReference();

        try{
            Picasso.get().load(image).into(upteacherimage);
        }catch (Exception e){
            e.printStackTrace();
        }

        upTeacherName.setText(name);
        upTeacherEmail.setText(email);
        upTeacherPhone.setText(phone);

        upteacherimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                opengallery();
            }
        });

        UpdateTeacherBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name=upTeacherName.getText().toString();
                email=upTeacherEmail.getText().toString();
                phone=upTeacherPhone.getText().toString();
                checkValidation();
            }
        });

        DeleteTeacherBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder= new AlertDialog.Builder(UpdateTeacher.this);
                builder.setMessage("Are yu sure you ant to delete teacher data?");
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

    }

    private void checkValidation() {
        if(name.isEmpty()){
            upTeacherName.setError("empty");
            upTeacherName.requestFocus();
        }else if(email.isEmpty()){
            upTeacherEmail.setError("empty");
            upTeacherEmail.requestFocus();
        }else if(phone.isEmpty()){
            upTeacherPhone.setError("empty");
            upTeacherPhone.requestFocus();
        }else if(bitmap==null){
            updateData(image);
        }else{
            uploadImage();
        }
    }

    private void updateData(String s) {
        HashMap hp=new HashMap();
        hp.put("name",name);
        hp.put("email",email);
        hp.put("phone",phone);
        hp.put("image",s);

        dbreference.child(category).child(phone).updateChildren(hp).addOnSuccessListener(new OnSuccessListener() {
            @Override
            public void onSuccess(Object o) {
                Toast.makeText(UpdateTeacher.this, "Teacher data updated", Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(UpdateTeacher.this,Updatefaculty.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(UpdateTeacher.this, "something isn't right", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void uploadImage() {
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
                                    updateData(downloadurl);
                                }
                            });
                        }
                    });
                }else{
                    pd.dismiss();
                    Toast.makeText(UpdateTeacher.this,"something went wrong",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void deletedata() {
        dbreference.child(category).child(phone).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(UpdateTeacher.this, "Teacher data deleted", Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(UpdateTeacher.this,Updatefaculty.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(UpdateTeacher.this, "Unable to delete", Toast.LENGTH_SHORT).show();
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
                e.printStackTrace();
            }
            upteacherimage.setImageBitmap(bitmap);
        }
    }

}
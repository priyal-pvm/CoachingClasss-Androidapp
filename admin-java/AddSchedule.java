package com.admin.spzone;

import android.app.ProgressDialog;
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
import androidx.cardview.widget.CardView;

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
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class AddSchedule extends AppCompatActivity {

    private CardView schedule_image;
    private ImageView Schedulepreview;
    private EditText ScheduleTitle;
    private Button schedule_btn;

    private final int REQ =1;
    private Bitmap bitmap;
    private DatabaseReference dbreference,dbref;
    private StorageReference streference;
    String downloadurl="";
    private ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_schedule);

        dbreference= FirebaseDatabase.getInstance().getReference();
        streference= FirebaseStorage.getInstance().getReference();
        schedule_image=findViewById(R.id.schedule_image);
        Schedulepreview=findViewById(R.id.Schedulepreview);
        ScheduleTitle=findViewById(R.id.ScheduleTitle);
        schedule_btn=findViewById(R.id.schedule_btn);
        pd= new ProgressDialog(this);

        schedule_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                opengallery();
            }
        });
        schedule_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ScheduleTitle.getText().toString().isEmpty()){
                    ScheduleTitle.setError("title cannot be empty");
                    ScheduleTitle.requestFocus();
                }
                else if(bitmap==null){
                    UploadData();
                }else{
                    UploadImage();
                }
            }
        });
    }


    private void opengallery() {
        Intent pickimage = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(pickimage,REQ);
    }
    private void UploadData() {
        dbref= dbreference.child("Schedule");
        final String uniquekey= dbref.push().getKey();
        String title=ScheduleTitle.getText().toString();

        Calendar calForDate= Calendar.getInstance();
        SimpleDateFormat currentDate = new SimpleDateFormat("dd-MM-yy");
        String date = currentDate.format(calForDate.getTime());

        Calendar calForTime= Calendar.getInstance();
        SimpleDateFormat currentTime = new SimpleDateFormat("hh:mm a");
        String time = currentDate.format(calForTime.getTime());

        ScheduleData schdata= new ScheduleData(title,downloadurl,date,time,uniquekey);
        dbref.child(uniquekey).setValue(schdata).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                pd.dismiss();
                Toast.makeText(AddSchedule.this, " Uploaded successfully", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                pd.dismiss();
                Toast.makeText(AddSchedule.this, "something went wrong", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void UploadImage() {
        pd.setMessage("Uploading...");
        pd.show();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress (Bitmap.CompressFormat.JPEG, 50, baos);
        byte[] finalimg= baos.toByteArray();
        final StorageReference filePath;
        filePath= streference.child("Schedule").child(finalimg+".jpg");
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
                                    UploadData();
                                }
                            });
                        }
                    });
                }else{
                    pd.dismiss();
                    Toast.makeText(AddSchedule.this,"something went wrong",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQ && resultCode == RESULT_OK){
            Uri uri=data.getData();
            try {
                bitmap= MediaStore.Images.Media.getBitmap(getContentResolver(),uri);
            } catch (IOException e) {
                Toast.makeText(AddSchedule.this, "error loading image", Toast.LENGTH_SHORT).show();
            }
            Schedulepreview.setImageBitmap(bitmap);
        }
    }

}
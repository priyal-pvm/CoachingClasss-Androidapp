package com.admin.spzone;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

        public class ManageStudents extends AppCompatActivity {
            private FloatingActionButton fab;
            private DatabaseReference dbreference,dbref;
            private RecyclerView sixthstd,seventhstd,eigthstd,ninthstd,tenthstd,eleventhstd,twelvethstd;
            private LinearLayout sixth_nodata,seventh_nodata,eigth_nodata,ninth_nodata,tenth_nodata,eleventh_nodata,twelveth_nodata;
            private List<StudentData> list1,list2,list3,list4,list5,list6,list7;
            private StudentAdapter adapter;

            @Override
            protected void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                setContentView(R.layout.activity_manage_students);

                sixth_nodata=findViewById(R.id.sixth_nodata);
                seventh_nodata=findViewById(R.id.seventh_nodata);
                eigth_nodata=findViewById(R.id.eigth_nodata);
                ninth_nodata=findViewById(R.id.ninth_nodata);
                tenth_nodata=findViewById(R.id.tenth_nodata);
                eleventh_nodata=findViewById(R.id.eleventh_nodata);
                twelveth_nodata=findViewById(R.id.twelveth_nodata);

                sixthstd=findViewById(R.id.sixthstd);
                seventhstd=findViewById(R.id.seventhstd);
                eigthstd=findViewById(R.id.eigthstd);
                ninthstd=findViewById(R.id.ninthstd);
                tenthstd=findViewById(R.id.tenthstd);
                eleventhstd=findViewById(R.id.eleventhstd);
                twelvethstd=findViewById(R.id.twelvethstd);

                dbreference= FirebaseDatabase.getInstance().getReference().child("Students");
                Log.e("anyText",""+dbreference);
                sixthstd();
                seventhstd();
                eigthstd();
                ninthstd();
                tenthstd();
                eleventhstd();
                twelvethstd();

                fab =findViewById(R.id.fab);
                fab.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(ManageStudents.this,AddStudent.class));
                    }
                });
            }

            private void sixthstd() {
                dbref= dbreference.child("6th VI");
                dbref.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        list1= new ArrayList<>();
                        if(!dataSnapshot.exists()){
                            sixth_nodata.setVisibility(View.VISIBLE);
                            sixthstd.setVisibility(View.GONE);
                        }
                        else{
                            sixth_nodata.setVisibility(View.GONE);
                            sixthstd.setVisibility(View.VISIBLE);
                            for(DataSnapshot snapshot:dataSnapshot.getChildren()){
                                StudentData data=snapshot.getValue(StudentData.class);

                                list1.add(data);
                                for (int i =0;i<list1.size();i++){
                                    Log.e("anyText", String.valueOf(list1.get(i)))
;                                }
                            }

                            sixthstd.setHasFixedSize(true);
                            sixthstd.setLayoutManager(new LinearLayoutManager(ManageStudents.this));
                            adapter=new StudentAdapter(list1,ManageStudents.this,"6th VI");
                            sixthstd.setAdapter(adapter);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(ManageStudents.this, "Error Loading Data", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            private void seventhstd() {
                dbref= dbreference.child("7th VII");
                dbref.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        list2= new ArrayList<>();
                        if(!dataSnapshot.exists()){
                            seventh_nodata.setVisibility(View.VISIBLE);
                            seventhstd.setVisibility(View.GONE);
                        }
                        else{
                            seventh_nodata.setVisibility(View.GONE);
                            seventhstd.setVisibility(View.VISIBLE);
                            for(DataSnapshot snapshot:dataSnapshot.getChildren()){
                                StudentData data=snapshot.getValue(StudentData.class);
                                list2.add(data);
                            }
                            seventhstd.setHasFixedSize(true);
                            seventhstd.setLayoutManager(new LinearLayoutManager(ManageStudents.this));
                            adapter=new StudentAdapter(list2,ManageStudents.this,"7th VII");
                            seventhstd.setAdapter(adapter);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(ManageStudents.this, "Error Loading Data", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            private void eigthstd() {
                dbref= dbreference.child("8th VIII");
                dbref.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        list3= new ArrayList<>();
                        if(!dataSnapshot.exists()){
                            eigth_nodata.setVisibility(View.VISIBLE);
                            eigthstd.setVisibility(View.GONE);
                        }
                        else{
                            eigth_nodata.setVisibility(View.GONE);
                            eigthstd.setVisibility(View.VISIBLE);
                            for(DataSnapshot snapshot:dataSnapshot.getChildren()){
                                StudentData data=snapshot.getValue(StudentData.class);
                                list3.add(data);
                            }
                            eigthstd.setHasFixedSize(true);
                            eigthstd.setLayoutManager(new LinearLayoutManager(ManageStudents.this));
                            adapter=new StudentAdapter(list3,ManageStudents.this,"8th VIII");
                            eigthstd.setAdapter(adapter);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(ManageStudents.this, "Error Loading Data", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            private void ninthstd() {
                dbref= dbreference.child("9th IX");
                dbref.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        list4= new ArrayList<>();
                        if(!dataSnapshot.exists()){
                            ninth_nodata.setVisibility(View.VISIBLE);
                            ninthstd.setVisibility(View.GONE);
                        }
                        else{
                            ninth_nodata.setVisibility(View.GONE);
                            ninthstd.setVisibility(View.VISIBLE);
                            for(DataSnapshot snapshot:dataSnapshot.getChildren()){
                                StudentData data=snapshot.getValue(StudentData.class);
                                list4.add(data);
                            }
                            ninthstd.setHasFixedSize(true);
                            ninthstd.setLayoutManager(new LinearLayoutManager(ManageStudents.this));
                            adapter=new StudentAdapter(list2,ManageStudents.this,"9th IX");
                            ninthstd.setAdapter(adapter);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(ManageStudents.this, "Error Loading Data", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            private void tenthstd() {
                dbref= dbreference.child("10th X");
                dbref.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        list5= new ArrayList<>();
                        if(!dataSnapshot.exists()){
                            tenth_nodata.setVisibility(View.VISIBLE);
                            tenthstd.setVisibility(View.GONE);
                        }
                        else{
                            tenth_nodata.setVisibility(View.GONE);
                            tenthstd.setVisibility(View.VISIBLE);
                            for(DataSnapshot snapshot:dataSnapshot.getChildren()){
                                StudentData data=snapshot.getValue(StudentData.class);
                                list5.add(data);
                            }
                            tenthstd.setHasFixedSize(true);
                            tenthstd.setLayoutManager(new LinearLayoutManager(ManageStudents.this));
                            adapter=new StudentAdapter(list5,ManageStudents.this,"10th X");
                            tenthstd.setAdapter(adapter);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(ManageStudents.this, "Error Loading Data", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            private void eleventhstd() {
                dbref= dbreference.child("11th XI");
                dbref.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        list6= new ArrayList<>();
                        if(!dataSnapshot.exists()){
                            eleventh_nodata.setVisibility(View.VISIBLE);
                            eleventhstd.setVisibility(View.GONE);
                        }
                        else{
                            eleventh_nodata.setVisibility(View.GONE);
                            eleventhstd.setVisibility(View.VISIBLE);
                            for(DataSnapshot snapshot:dataSnapshot.getChildren()){
                                StudentData data=snapshot.getValue(StudentData.class);
                                list6.add(data);
                            }
                            eleventhstd.setHasFixedSize(true);
                            eleventhstd.setLayoutManager(new LinearLayoutManager(ManageStudents.this));
                            adapter=new StudentAdapter(list6,ManageStudents.this,"11th XI");
                            eleventhstd.setAdapter(adapter);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(ManageStudents.this, "Error Loading Data", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            private void twelvethstd() {
                dbref= dbreference.child("12th XII");
                dbref.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        list7= new ArrayList<>();
                        if(!dataSnapshot.exists()){
                            twelveth_nodata.setVisibility(View.VISIBLE);
                            twelvethstd.setVisibility(View.GONE);
                        }
                        else{
                            twelveth_nodata.setVisibility(View.GONE);
                            twelvethstd.setVisibility(View.VISIBLE);
                            for(DataSnapshot snapshot:dataSnapshot.getChildren()){
                                StudentData data=snapshot.getValue(StudentData.class);
                                list7.add(data);
                            }
                            twelvethstd.setHasFixedSize(true);
                            twelvethstd.setLayoutManager(new LinearLayoutManager(ManageStudents.this));
                            adapter=new StudentAdapter(list7,ManageStudents.this,"12th XII");
                            twelvethstd.setAdapter(adapter);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(ManageStudents.this, "Error Loading Data", Toast.LENGTH_SHORT).show();
                    }
                });
            }


}
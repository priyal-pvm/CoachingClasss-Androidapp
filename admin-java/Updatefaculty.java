package com.admin.spzone;

import android.content.Intent;
import android.os.Bundle;
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

public class Updatefaculty extends AppCompatActivity {

    private FloatingActionButton fab;
    private RecyclerView ChemistryDepartment,MathsDepartment,PhysicsDepartment,BiologyDepartment,EnglishDepartment;
    private LinearLayout chem_nodata,maths_nodata,physics_nodata,bio_nodata,eng_nodata;
    private List<TeacherData> list1,list2,list3,list4,list5;
    private TeacherAdapter adapter;

    private DatabaseReference dbreference,dbref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_updatefaculty);

        chem_nodata=findViewById(R.id.chem_nodata);
        maths_nodata=findViewById(R.id.maths_nodata);
        physics_nodata=findViewById(R.id.physics_nodata);
        bio_nodata=findViewById(R.id.bio_nodata);
        eng_nodata=findViewById(R.id.eng_nodata);

        ChemistryDepartment=findViewById(R.id.ChemistryDepartment);
        MathsDepartment=findViewById(R.id.MathsDepartment);
        PhysicsDepartment=findViewById(R.id.PhysicsDepartment);
        BiologyDepartment=findViewById(R.id.BiologyDepartment);
        EnglishDepartment=findViewById(R.id.EnglishDepartment);

        dbreference= FirebaseDatabase.getInstance().getReference().child("Teachers");

        ChemistryDepartment();
        MathsDepartment();
        PhysicsDepartment();
        EnglishDepartment();
        BiologyDepartment();

        fab =findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Updatefaculty.this,AddTeacher.class));
            }
        });
    }

    private void BiologyDepartment()
    {
        dbref= dbreference.child("Biology");
        dbref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                list2= new ArrayList<>();
                if(!dataSnapshot.exists()){
                    bio_nodata.setVisibility(View.VISIBLE);
                    BiologyDepartment.setVisibility(View.GONE);
                }
                else{
                    bio_nodata.setVisibility(View.GONE);
                    BiologyDepartment.setVisibility(View.VISIBLE);
                    for(DataSnapshot snapshot:dataSnapshot.getChildren()){
                        TeacherData data=snapshot.getValue(TeacherData.class);
                        list2.add(data);
                    }
                    BiologyDepartment.setHasFixedSize(true);
                    BiologyDepartment.setLayoutManager(new LinearLayoutManager(Updatefaculty.this));
                    adapter=new TeacherAdapter(list2,Updatefaculty.this,"Biology");
                    BiologyDepartment.setAdapter(adapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(Updatefaculty.this, "Error Loading Data", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void EnglishDepartment() {
        dbref= dbreference.child("English");
        dbref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                list5= new ArrayList<>();
                if(!dataSnapshot.exists()){
                    eng_nodata.setVisibility(View.VISIBLE);
                    EnglishDepartment.setVisibility(View.GONE);
                }
                else{
                    eng_nodata.setVisibility(View.GONE);
                    EnglishDepartment.setVisibility(View.VISIBLE);
                    for(DataSnapshot snapshot:dataSnapshot.getChildren()){
                        TeacherData data=snapshot.getValue(TeacherData.class);
                        list5.add(data);
                    }
                    EnglishDepartment.setHasFixedSize(true);
                    EnglishDepartment.setLayoutManager(new LinearLayoutManager(Updatefaculty.this));
                    adapter=new TeacherAdapter(list5,Updatefaculty.this,"English");
                    EnglishDepartment.setAdapter(adapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(Updatefaculty.this, "Error Loading Data", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void PhysicsDepartment() {
        dbref= dbreference.child("Physics");
        dbref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                list3= new ArrayList<>();
                if(!dataSnapshot.exists()){
                    physics_nodata.setVisibility(View.VISIBLE);
                    PhysicsDepartment.setVisibility(View.GONE);
                }
                else{
                    physics_nodata.setVisibility(View.GONE);
                    PhysicsDepartment.setVisibility(View.VISIBLE);
                    for(DataSnapshot snapshot:dataSnapshot.getChildren()){
                        TeacherData data=snapshot.getValue(TeacherData.class);
                        list3.add(data);
                    }
                    PhysicsDepartment.setHasFixedSize(true);
                    PhysicsDepartment.setLayoutManager(new LinearLayoutManager(Updatefaculty.this));
                    adapter=new TeacherAdapter(list3,Updatefaculty.this,"Physics");
                    PhysicsDepartment.setAdapter(adapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(Updatefaculty.this, "Error Loading Data", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void MathsDepartment() {
        dbref= dbreference.child("Mathematics");
        dbref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                list4= new ArrayList<>();
                if(!dataSnapshot.exists()){
                    maths_nodata.setVisibility(View.VISIBLE);
                    MathsDepartment.setVisibility(View.GONE);
                }
                else{
                    maths_nodata.setVisibility(View.GONE);
                    MathsDepartment.setVisibility(View.VISIBLE);
                    for(DataSnapshot snapshot:dataSnapshot.getChildren()){
                        TeacherData data=snapshot.getValue(TeacherData.class);
                        list4.add(data);
                    }
                    MathsDepartment.setHasFixedSize(true);
                    MathsDepartment.setLayoutManager(new LinearLayoutManager(Updatefaculty.this));
                    adapter=new TeacherAdapter(list4,Updatefaculty.this,"Mathematics");
                    MathsDepartment.setAdapter(adapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(Updatefaculty.this, "Error Loading Data", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void ChemistryDepartment() {
        dbref= dbreference.child("Chemistry");
        dbref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                list1= new ArrayList<>();
                if(!dataSnapshot.exists()){
                    chem_nodata.setVisibility(View.VISIBLE);
                    ChemistryDepartment.setVisibility(View.GONE);
                }
                else{
                    chem_nodata.setVisibility(View.GONE);
                    ChemistryDepartment.setVisibility(View.VISIBLE);
                    for(DataSnapshot snapshot:dataSnapshot.getChildren()){
                        TeacherData data=snapshot.getValue(TeacherData.class);
                        list1.add(data);
                    }
                    ChemistryDepartment.setHasFixedSize(true);
                    ChemistryDepartment.setLayoutManager(new LinearLayoutManager(Updatefaculty.this));
                    adapter=new TeacherAdapter(list1,Updatefaculty.this,"Chemistry");
                    ChemistryDepartment.setAdapter(adapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(Updatefaculty.this, "Error Loading Data", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
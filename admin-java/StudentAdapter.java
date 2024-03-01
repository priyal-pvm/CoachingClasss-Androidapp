package com.admin.spzone;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class StudentAdapter extends RecyclerView.Adapter<StudentAdapter.StudentViewAdapter> {

    private List<StudentData> list;
    private Context context;
    private String category;

    public StudentAdapter(List<StudentData> list, Context context,String category){
        this.list = list;
        this.context = context;
        this.category=category;
    }

    @NonNull
    @Override
    public StudentViewAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view= LayoutInflater.from(context).inflate(R.layout.student_item_layout, parent ,false);
        return new StudentViewAdapter(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StudentViewAdapter holder, int position)
    {
        StudentData item=list.get(position);
        holder.name.setText(item.getName());
        holder.rollno.setText(item.getRollno());
        holder.batch.setText(item.getBatch());

        holder.view_studinfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, Viewstudent.class);
                intent.putExtra("name",item.getName());
                intent.putExtra("rollno",item.getRollno());
                intent.putExtra("std",item.getStd());
                intent.putExtra("password",item.getPasstxt());
                intent.putExtra("email",item.getEmail());
                intent.putExtra("phone",item.getPhone());
                intent.putExtra("batch",item.getBatch());
                intent.putExtra("category",category);

                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() { return list.size(); }

    public class StudentViewAdapter extends RecyclerView.ViewHolder {
        private TextView name,rollno,batch;
        private TextView view_studinfo;
        public StudentViewAdapter(@NonNull View itemView) {
            super(itemView);
            name= itemView.findViewById(R.id.student_name);
            rollno= itemView.findViewById(R.id.studentRollno);
            batch= itemView.findViewById(R.id.student_batch);
            view_studinfo= itemView.findViewById(R.id.view_studinfo);

        }
    }
}

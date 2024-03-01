package com.admin.spzone;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class TeacherAdapter extends RecyclerView.Adapter<TeacherAdapter.TeacherViewAdapter>{

    private List<TeacherData> list;
    private Context context;
    private String category;

    public TeacherAdapter(List<TeacherData> list, Context context,String category) {
        this.list = list;
        this.context = context;
        this.category=category;
    }

    @NonNull
    @Override
    public TeacherViewAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.teacher_item_layout, parent ,false);
        return new TeacherViewAdapter(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TeacherViewAdapter holder, int position) {
        TeacherData item=list.get(position);
        holder.name.setText(item.getName());
        holder.email.setText(item.getEmail());
        holder.phone.setText(item.getPhone());
//        holder.pass.setText(item.getPass());
        try{
        Picasso.get().load(item.getImage()).into(holder.image);
        }catch (Exception e){
            e.printStackTrace();
        }

        holder.update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, UpdateTeacher.class);
                intent.putExtra("name",item.getName());
                intent.putExtra("email",item.getEmail());
                intent.putExtra("phone",item.getPhone());
                intent.putExtra("image",item.getImage());
               // intent.putExtra("key",item.getKey());
                intent.putExtra("pass",item.getPass());
                intent.putExtra("category",category);
                context.startActivity(intent);
            }

        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class TeacherViewAdapter extends RecyclerView.ViewHolder {

        private TextView name,email,phone,pass;
        private Button update;
        private ImageView image;

        public TeacherViewAdapter(@NonNull View itemView) {
            super(itemView);
            pass=itemView.findViewById(R.id.addTeacherPassword);
            name= itemView.findViewById(R.id.teacherName);
            email= itemView.findViewById(R.id.teacherEmail);
            phone= itemView.findViewById(R.id.teacherPhone);
            update= itemView.findViewById(R.id.teacherUpdate);
            image= itemView.findViewById(R.id.teacherImage);

        }
    }
}

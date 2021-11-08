package com.example.realfetchproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

//The class takes care of the design portion of the assessment. Takes care how the ids and the names are going to placed.
//Sets the position of the id and the name right on top of each other
public class Adapter extends RecyclerView.Adapter<Adapter.MyViewClass>{
    ArrayList<String> name;
    ArrayList<String> id;
    Context context;

    //Set Function function
    public Adapter(ArrayList<String> name, ArrayList<String> id, Context context) {
        this.name = name;
        this.id = id;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewClass onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row, parent,false);
        MyViewClass myViewClass= new MyViewClass(view);
        return myViewClass;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewClass holder, int position) {
        holder.name.setText(name.get(position));
        holder.id.setText(id.get(position));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context , "Item Clicked", Toast.LENGTH_LONG);

            }
        });
    }

    @Override
    public int getItemCount() {
        return name.size();
    }

    public class MyViewClass extends RecyclerView.ViewHolder {
        TextView name;
        TextView id;

        public MyViewClass(@NonNull View itemView) {
            super(itemView);
            name=(TextView) itemView.findViewById(R.id.name);
            id=(TextView) itemView.findViewById(R.id.id);
        }
    }
}

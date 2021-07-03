package com.example.jobortal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.jobortal.Model.Data;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class PostJobActivity extends AppCompatActivity {
    private FloatingActionButton add_post_button;
    private RecyclerView recyclerView;
    private Toolbar toolbar_post_job;


    FirebaseAuth mAuth;
    DatabaseReference JobPostDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_job);
        add_post_button = findViewById(R.id.btn_fab);
        toolbar_post_job = findViewById(R.id.toolbar_post_job);
        setSupportActionBar(toolbar_post_job);

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser mUser = mAuth.getCurrentUser();
        String uId = mUser.getUid();
        //to be searched
        //databse creation
        JobPostDatabase = FirebaseDatabase.getInstance().getReference().child("Job Post").child(uId);

        recyclerView = findViewById(R.id.job_post_rv);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        //recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(linearLayoutManager);

        add_post_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),InsertJobPostActivity.class));

            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseRecyclerOptions<Data> options =
                new FirebaseRecyclerOptions.Builder<Data>()
                        .setQuery(JobPostDatabase, Data.class)
                        .build();
        FirebaseRecyclerAdapter<Data, MyViewHolder> adapter = new FirebaseRecyclerAdapter<Data, MyViewHolder>(options) {

            @NonNull
            @Override
            public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.job_post_item,parent,false);
                return new MyViewHolder(view);

            }

            @Override
            protected void onBindViewHolder(@NonNull MyViewHolder holder, int position, @NonNull Data model) {

                holder.setJobTitle(model.getTittle());
                holder.setJobDate(model.getDate());
                holder.setJobDescription(model.getDescription());
                holder.setJobSkill(model.getSkills());
                holder.setJobSalary(model.getSalary());
            }
        };
        adapter.startListening();
        recyclerView.setAdapter(adapter);


    }
    public static class MyViewHolder extends RecyclerView.ViewHolder{
        View myView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            myView=itemView;
        }
        public void setJobTitle(String title){
            TextView mtitle = myView.findViewById(R.id.job_title);
             mtitle.setText(title);
        }

        public void setJobDate(String date){
            TextView mdate = myView.findViewById(R.id.job_post_date);
           mdate.setText(date);
        }

        public void setJobDescription(String description){
            TextView mdes = myView.findViewById(R.id.job_description);
            mdes.setText(description);
        }

        public void setJobSkill(String skill){
            TextView mskil = myView.findViewById(R.id.job_skill);
            mskil.setText(skill);
        }

        public void setJobSalary(String salary){
            TextView msalary = myView.findViewById(R.id.job_salary);
            msalary.setText(salary);
        }
    }
}
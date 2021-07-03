package com.example.jobortal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;

import com.example.jobortal.Model.Data;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AllJobActivity extends AppCompatActivity {
    private Toolbar toolbar_all_job;
    RecyclerView recyclerView_all_job;
    DatabaseReference AllJobPostDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_job);
        toolbar_all_job = findViewById(R.id.all_job_toolbar);
        setSupportActionBar(toolbar_all_job);
        getSupportActionBar().setTitle("All Job Posts");
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        //to be searched
        //databse creation
        AllJobPostDatabase = FirebaseDatabase.getInstance().getReference().child("Public database");
        AllJobPostDatabase.keepSynced(true);


        recyclerView_all_job = findViewById(R.id.all_job_rv);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        //recyclerView.setHasFixedSize(true);
        recyclerView_all_job.setLayoutManager(linearLayoutManager);

    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseRecyclerOptions<Data> options =
                new FirebaseRecyclerOptions.Builder<Data>()
                        .setQuery(AllJobPostDatabase, Data.class)
                        .build();
        FirebaseRecyclerAdapter<Data, AllJobActivity.AllJobPostViewHolder> adapter = new FirebaseRecyclerAdapter<Data, AllJobActivity.AllJobPostViewHolder>(options) {

            @NonNull
            @Override
            public AllJobActivity.AllJobPostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.alljobpost, parent, false);
                return new AllJobActivity.AllJobPostViewHolder(view);

            }

            @Override
            protected void onBindViewHolder(@NonNull AllJobActivity.AllJobPostViewHolder holder, int position, @NonNull Data model) {

                holder.setallJobTitle(model.getTittle());
                holder.setallJobDate(model.getDate());
                holder.setallJobDescription(model.getDescription());
                holder.setallJobSkill(model.getSkills());
                holder.setallJobSalary(model.getSalary());

                holder.myView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(getApplicationContext(),JobDetailsActivity.class);
                        intent.putExtra("title",model.getTittle());
                        intent.putExtra("date",model.getDate());
                        intent.putExtra("des",model.getDescription());
                        intent.putExtra("skill",model.getSkills());
                        intent.putExtra("sal",model.getSalary());
                        startActivity(intent);
                    }
                });
            }
        };
        adapter.startListening();
        recyclerView_all_job.setAdapter(adapter);
    }


    public static class AllJobPostViewHolder extends RecyclerView.ViewHolder{
    View myView;

    public AllJobPostViewHolder(@NonNull View itemView) {
        super(itemView);
        myView=itemView;
    }
    public void setallJobTitle(String title){
        TextView mmtitle = myView.findViewById(R.id.all_job_title);
        mmtitle.setText(title);
    }

    public void setallJobDate(String date){
        TextView mmdate = myView.findViewById(R.id.all_job_post_date);
        mmdate.setText(date);
    }

    public void setallJobDescription(String description){
        TextView mmdes = myView.findViewById(R.id.all_job_description);
        mmdes.setText(description);
    }

    public void setallJobSkill(String skill){
        TextView mmskil = myView.findViewById(R.id.all_job_skill);
        mmskil.setText(skill);
    }

    public void setallJobSalary(String salary){
        TextView mmsalary = myView.findViewById(R.id.all_job_salary);
        mmsalary.setText(salary);
    }
}
}
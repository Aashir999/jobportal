package com.example.jobortal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;

import com.example.jobortal.Model.Data;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.util.Date;

public class InsertJobPostActivity extends AppCompatActivity {
    private Button postJob;
    //toolbar
    private Toolbar toolbar;
    private EditText jobTitle;
    private EditText jobDescription;
    private EditText jobSkills;
    private EditText jobSalary;
    //firebase
    private FirebaseAuth mAuth;

    private DatabaseReference mPublicDatabase;
    private DatabaseReference mJobPost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_job_post);
        toolbar = findViewById(R.id.insert_job_post_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Post Job");

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser mUser = mAuth.getCurrentUser();
        String uId = mUser.getUid();
        //to be searched

        //databse creation
        mJobPost = FirebaseDatabase.getInstance().getReference().child("Job Post").child(uId);
        mPublicDatabase = FirebaseDatabase.getInstance().getReference().child("Public database");


        InsertJob();
    }
    private void InsertJob(){
        jobTitle = findViewById(R.id.job_title);
        jobDescription = findViewById(R.id.job_description);
        jobSkills = findViewById(R.id.job_skill);
        jobSalary = findViewById(R.id.job_salary);

        postJob = findViewById(R.id.btn_postJob);
        postJob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String tittle = jobTitle.getText().toString().trim();
                String description = jobDescription.getText().toString().trim();
                String skills = jobSkills.getText().toString().trim();
                String salary = jobSalary.getText().toString().trim();

                if (TextUtils.isEmpty(tittle)){
                    jobTitle.setError("Job Tittle Required..");
                    return;
                }

                if (TextUtils.isEmpty(description)){
                    jobDescription.setError("Job Description Required..");
                    return;
                }

                if (TextUtils.isEmpty(skills)){
                    jobSkills.setError("Skills Required..");
                    return;
                }

                if (TextUtils.isEmpty(salary)){
                    jobSalary.setError("Salary Required..");
                    return;
                }
            //Insrting records in database
                String Id = mJobPost.push().getKey();
                String Date = DateFormat.getDateInstance().format(new Date());
                Data data = new Data(tittle, description, skills, salary, Id, Date);
                mJobPost.child(Id).setValue(data);
                mPublicDatabase.child(Id).setValue(data);
                Toast.makeText(getApplicationContext(),"Successfully Posted Job",Toast.LENGTH_SHORT);
                startActivity(new Intent(getApplicationContext(),PostJobActivity.class));


            }
        });
   }
}
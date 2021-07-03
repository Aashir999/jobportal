package com.example.jobortal;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;


public class JobDetailsActivity extends AppCompatActivity {
    private Toolbar detail_toolbar;
    private TextView mtitle;
    private TextView mdate;
    private TextView mdes;
    private TextView mskil;
    private TextView msalary;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_details);
        detail_toolbar = findViewById(R.id.detail_toolbar);
        setSupportActionBar(detail_toolbar);
        getSupportActionBar().setTitle("Job Details");


        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mtitle = findViewById(R.id.details_job_title);
        mdate = findViewById(R.id.details_job_post_date);
        mdes = findViewById(R.id.details_job_description);
        mskil = findViewById(R.id.details_job_skill);
        msalary = findViewById(R.id.details_job_salary);

        Intent intent = getIntent();
        String title = intent.getStringExtra("title");
        String date = intent.getStringExtra("date");
        String des = intent.getStringExtra("des");
        String skil = intent.getStringExtra("skill");
        String Sal = intent.getStringExtra("sal");
        mtitle.setText(title);
        mdate.setText(date);
        mdes.setText(des);
        mskil.setText(skil);
        msalary.setText(Sal);



    }
}
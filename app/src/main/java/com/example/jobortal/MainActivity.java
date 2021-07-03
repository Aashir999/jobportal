package com.example.jobortal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
    private EditText email;
    private EditText password;

    private Button btnLogin;
    private Button btnReg;
    //firebase auth
    private FirebaseAuth mAuth;
    //progress dialog
    private ProgressDialog mDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();
        mDialog = new ProgressDialog(this);
        LoginFunction();
    }
    private void LoginFunction(){
        email = findViewById(R.id.email_login);
        password = findViewById(R.id.login_password);

        btnLogin = findViewById(R.id.btn_login);
        btnReg = findViewById(R.id.btn_reg);


        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String memail = email.getText().toString().trim();
                String pass = password.getText().toString().trim();

                if (TextUtils.isEmpty(memail)){
                    email.setError("Email Required..");
                    return;
                }

                if (TextUtils.isEmpty(pass)){
                    password.setError("Password Required..");
                    return;
                }
                mDialog.setMessage("Processing..");
                mDialog.show();

                mAuth.signInWithEmailAndPassword(memail,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(getApplicationContext(),"Successfully Signed In..", Toast.LENGTH_SHORT);
                            startActivity(new Intent(getApplicationContext(),HomeActivity.class));
                            mDialog.dismiss();


                        }else{
                            Toast.makeText(getApplicationContext(),"Sign In failed..",Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }
        });

        btnReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),RegitrationActivity.class));

            }
        });
    }
}
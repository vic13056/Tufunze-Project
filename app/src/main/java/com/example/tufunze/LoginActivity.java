package com.example.tufunze;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {
    TextView tvdont;
    EditText mname,memail2,mpassword2;
    Button mnext;
    FirebaseAuth fAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        tvdont = findViewById(R.id.tvtdont);
        mname = findViewById(R.id.edtfullname2);
        memail2 = findViewById(R.id.edtemail2);
        mpassword2 = findViewById(R.id.edtpassword2);
        mnext = findViewById(R.id.btnnext2);
        fAuth = FirebaseAuth.getInstance();

        mnext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String name = mname.getText().toString().trim();
                String email = memail2.getText().toString().trim();
                String password = mpassword2.getText().toString().trim();

                if (name.isEmpty()){
                    mname.setError("Input Name");
                    return;
                }
                if (email.isEmpty()){
                    memail2.setError("Input Email");
                    return;
                }
                if (password.isEmpty()){
                    mpassword2.setError("Input Password");
                    return;
                }

                fAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(LoginActivity.this, "Signed in Successful", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(),Home.class));
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(LoginActivity.this, "Failed "+e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        tvdont.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),Sign_UpActivity.class));
            }
        });

    }
}

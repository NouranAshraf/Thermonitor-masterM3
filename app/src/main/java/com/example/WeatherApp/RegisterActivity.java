package com.example.WeatherApp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {
    EditText Email1;
    EditText Password1;
    Button LogIn;
    Button Register;
    TextView TextView1;
    TextView TextView2;
    ProgressDialog progressDialog1;
    ImageView ImageView1;

    private FirebaseAuth firebaseAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_register);
        progressDialog1 = new ProgressDialog(this);
        firebaseAuth = FirebaseAuth.getInstance();
        Email1 = findViewById(R.id.Email1);
        Password1 = findViewById(R.id.Password1);
        LogIn = findViewById(R.id.LogIn);
        Register = findViewById(R.id.Register);
        TextView2 = findViewById(R.id.TextView2);
        TextView1 = findViewById(R.id.TextView1);
        Register.setOnClickListener(this);
        LogIn.setOnClickListener(this);
        ImageView1=findViewById(R.id.imageView1);


    }

    private void registerUser() {
        String email = Email1.getText().toString().trim();
        String password = Password1.getText().toString().trim();
//        String username = Username.getText().toString().trim();
//        if(TextUtils.isEmpty(username)){
//            Toast.makeText(this,"Please enter Username",Toast.LENGTH_SHORT).show();
//            return;
//        }
        if (TextUtils.isEmpty(email)) {
            Toast.makeText(this, "Please enter email", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Please enter Password", Toast.LENGTH_SHORT).show();
            return;
        }
        progressDialog1.setMessage("Registering User...");
        progressDialog1.show();
        firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(RegisterActivity.this, "Registered Successfully", Toast.LENGTH_SHORT).show();
                    progressDialog1.dismiss();
                    Intent Intent2 =new Intent(RegisterActivity.this,LoginActivity.class);
                    startActivity(Intent2);
                } else {
                    Toast.makeText(getApplicationContext(),task.getException().getMessage(),Toast.LENGTH_SHORT).show();

                    Toast.makeText(RegisterActivity.this, "could not register.Please try again", Toast.LENGTH_SHORT).show();

                }
                if (!task.isSuccessful()) {
                    Log.e("testing", "onComplete: Failed=" + task.getException().getMessage());
                    progressDialog1.dismiss();
                }
            }
        });

    }


    @Override
    public void onClick(View view) {
        if (view == Register) {
            registerUser();
        }
        if (view == LogIn) {
            Intent Intent1 = new Intent(RegisterActivity.this, LoginActivity.class);
            startActivity(Intent1);
        }
    }
}

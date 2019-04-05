package com.example.WeatherApp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.media.Image;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {
    EditText Email2;
    EditText Password2;
    Button SignIn;
    Button SignUp;
    TextView TextView4;
    TextView TextView3;
    ImageView imageView2;
    private FirebaseAuth firebaseAuth;
    ProgressDialog progressDialog2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        firebaseAuth = FirebaseAuth.getInstance();
        progressDialog2 = new ProgressDialog(this);
        Email2 = findViewById(R.id.Email2);
        Password2 = findViewById(R.id.Password2);
        SignIn = findViewById(R.id.SignIn);
        TextView4=findViewById(R.id.TextView4);
        SignUp = findViewById(R.id.SignUp);
         TextView3 = findViewById(R.id.TextView3);
        imageView2 = findViewById(R.id.imageView2);


        SignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SignInUser();

            }
        });

        SignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent Intent3 =new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(Intent3);

            }
        });

    }

    private void SignInUser() {
        String email = Email2.getText().toString().trim();
        String password = Password2.getText().toString().trim();
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
        progressDialog2.setMessage("Loging In...");
        progressDialog2.show();
        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("testing", "signInWithEmail:success");
                            progressDialog2.dismiss();
                            FirebaseUser user = firebaseAuth.getCurrentUser();
                            Intent Intent4 =new Intent(LoginActivity.this,ListActivity.class);
                            startActivity(Intent4);

                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("testing", "signInWithEmail:failure", task.getException());
                            progressDialog2.dismiss();
                            Toast.makeText(getApplicationContext(),task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                            Toast.makeText(LoginActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();


                        }

                        // ...
                    }

                });
    }
    }




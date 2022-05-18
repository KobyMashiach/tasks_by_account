package com.example.first_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Login extends AppCompatActivity {
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        TextView textViewTitle = findViewById(R.id.tv_login);
        Typeface myfont = Typeface.createFromAsset(getAssets(), "DancingScript.ttf");
        textViewTitle.setTypeface(myfont);

        mAuth = FirebaseAuth.getInstance();
    }


    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null) {
            updateUI(currentUser);
            Intent intent = new Intent(Login.this, Account.class);
            intent.putExtra("Email", currentUser.getEmail());
//            startActivity(intent);
//            finish();
        } else{
            //Toast.makeText(Login.this,"Please Sign In or Sing Up..",Toast.LENGTH_SHORT).show();
        }
    }

    private void updateUI(FirebaseUser currentUser) {
//        String email = currentUser.getEmail();
//        Toast.makeText(Login.this,"email: "+email,Toast.LENGTH_SHORT).show();
//        Toast.makeText(this, "Please Sign in", Toast.LENGTH_SHORT).show();
    }

    public void toMain(View view) {
        Intent intent = new Intent(Login.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    public void toAccount() {
        Intent intent = new Intent(Login.this, Account.class);
        startActivity(intent);
        finish();
    }

    public void to_signup(View view) {
        Intent intent = new Intent(Login.this, Signup.class);
        startActivity(intent);
        finish();
    }

    public void submit(View view) {
        EditText etEmail = findViewById(R.id.et_email_singup);
        EditText etPassword = findViewById(R.id.et_numPassword_singup);
        String email = etEmail.getText().toString().trim(); //trip cut spaces end and start
        String password = etPassword.getText().toString().trim();

//        String emailPattern = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$";

        if(email.length()>0 && password.length()>0){
//            if(email.matches(emailPattern)) {
            if(Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                mAuth.signInWithEmailAndPassword(email,password)
                        .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(Login.this, "Auth Success", Toast.LENGTH_SHORT).show();
                            //saveEmail = email;
                            Intent intent = new Intent(Login.this, Account.class);
                            intent.putExtra("EMAIL",email);
                            startActivity(intent);
                            finish();
//                            toAccount();
                        } else{
                            Toast.makeText(Login.this,"Auth Failed",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                if(password.length()>=6) {
                    //Toast.makeText(this, "email: " + email, Toast.LENGTH_SHORT).show();
                    //Toast.makeText(this, "password: " + password, Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(this, "password need to be up 6 digits", Toast.LENGTH_SHORT).show();
                    etPassword.setError("Password error");
                    etPassword.requestFocus();
                    return;
                }
            }
            else{
                Toast.makeText(this, "Wrong Email", Toast.LENGTH_SHORT).show();
                etEmail.setError("Email error");
                etEmail.requestFocus();
                return;
            }
        }
        else{
            Toast.makeText(this, "Missing email", Toast.LENGTH_SHORT).show();
        }
    }

    public void toResetPassword(View view) {
            Intent intent = new Intent(Login.this, ResetPassword.class);
            startActivity(intent);
            finish();
        }
    }

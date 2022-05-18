package com.example.first_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ResetPassword extends AppCompatActivity {
    private FirebaseAuth mAuth;

    private Handler myHandler = new Handler();

    private Runnable timer = new Runnable() {
        @Override
        public void run() {
            Intent intent = new Intent(ResetPassword.this, Login.class);
            startActivity(intent);
            finish();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);
        mAuth = FirebaseAuth.getInstance();
    }

    public void toMain_resetpassword(View view) {
        Intent intent = new Intent(ResetPassword.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    public void reset_password(View view) {
        EditText etEmail_reset = findViewById(R.id.et_email_reset_password);
        mAuth.sendPasswordResetEmail(etEmail_reset.getText().toString().trim())
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(ResetPassword.this,"Reset password send to your email",Toast.LENGTH_SHORT).show();
                            myHandler.postDelayed(timer, 2000);
                        } else{
                            Toast.makeText(ResetPassword.this,"problem in reset",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }




}
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

public class Signup extends AppCompatActivity {
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_singup);

        TextView textViewTitle = findViewById(R.id.tv_singup_page);
        Typeface myfont = Typeface.createFromAsset(getAssets(), "DancingScript.ttf");
        textViewTitle.setTypeface(myfont);

        mAuth = FirebaseAuth.getInstance();
    }


    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null) {
            updateUI(currentUser);
//            Intent intent = new Intent(Login.this, Account.class);
//            intent.putExtra("Email", currentUser.getEmail());
        } else{
            Toast.makeText(Signup.this,"Please Sign In or Sing Up..",Toast.LENGTH_SHORT).show();
        }
    }

    private void updateUI(FirebaseUser currentUser) {
//        String email = currentUser.getEmail();
//        Toast.makeText(Login.this,"email: "+email,Toast.LENGTH_SHORT).show();
        Toast.makeText(this, "Please Sign up", Toast.LENGTH_SHORT).show();
    }

    public void toMainSignUp(View view) {
        Intent intent = new Intent(Signup.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    public void toAccountSignUp() {
        Intent intent = new Intent(Signup.this, Account.class);
        startActivity(intent);
        finish();
    }

    public void to_login(View view) {
        Intent intent = new Intent(Signup.this, Login.class);
        startActivity(intent);
        finish();
    }

    public void submit_signup(View view) {
        EditText etEmail = findViewById(R.id.et_email_singup);
        EditText etPassword = findViewById(R.id.et_numPassword_singup);
        EditText etPassword2 = findViewById(R.id.et_numPassword2_singup);

        String email = etEmail.getText().toString().trim(); //trip cut spaces end and start
        String password = etPassword.getText().toString().trim();
        String password2 = etPassword2.getText().toString().trim();


        if(email.length()>0 && password.length()>0){
            if(Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                if(password.matches(password2)) {
                    mAuth.createUserWithEmailAndPassword(email, password)
                            .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(Signup.this, "Singup Success", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(Signup.this, Account.class);
                                        intent.putExtra("EMAIL", email);
                                        startActivity(intent);
                                        finish();
//                            toAccount();
                                    } else {
                                        Toast.makeText(Signup.this, "Auth Failed", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                    if (password.length() >= 6) {
                        //Toast.makeText(this, "email: " + email, Toast.LENGTH_SHORT).show();
                        //Toast.makeText(this, "password: " + password, Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(this, "password need to be up 6 digits", Toast.LENGTH_SHORT).show();
                        etPassword.setError("Password error");
                        etPassword.requestFocus();
                        return;
                    }
                }else{
                    Toast.makeText(this, "password don't match", Toast.LENGTH_SHORT).show();
                    etPassword2.setError("Password don't match");
                    etPassword2.requestFocus();
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
}
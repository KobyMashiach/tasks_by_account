package com.example.first_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.EmailAuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class Account extends AppCompatActivity {

    private FirebaseAuth mAuth;
    EditText etNewTask;
    TextView tv_screen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        mAuth = FirebaseAuth.getInstance();

//        TextView tv_showAccountEmail = (TextView) findViewById(R.id.tv_showAccountEmail);
//        tv_showAccountEmail.setText(Login.getAccountEmail());

        String email = getIntent().getExtras().getString("EMAIL");
        TextView tvEmailAccount = findViewById(R.id.tv_showAccountEmail);
        tvEmailAccount.setText(email);
        etNewTask = findViewById(R.id.etNewTask);
        tv_screen = findViewById(R.id.tv_screen);
        readTasks();
    }

    public void Logout(View view) {
        Intent intent = new Intent(Account.this,Login.class);
        startActivity(intent);
        finish();
    }

    public void DeleteAccount(View view) {
        mAuth.getCurrentUser().delete().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(Account.this,"The account deleted successfully",Toast.LENGTH_SHORT).show();
                } else{
                    Toast.makeText(Account.this,"The account don't deleted",Toast.LENGTH_SHORT).show();
                }
            }
        });
        Intent intent = new Intent(Account.this,Login.class);
        startActivity(intent);
        finish();
    }

    public void writeTask(View view) {
        String taskMessage = etNewTask.getText().toString().trim();
        String uid = mAuth.getCurrentUser().getUid();

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("tasks");

        myRef.child("uid: " + uid).child("user tasks: ").push().setValue(taskMessage).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(Account.this,"Task add successfully",Toast.LENGTH_SHORT).show();
                } else{
                    Toast.makeText(Account.this,"Task don't add",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void readTasks(){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        String uid = mAuth.getCurrentUser().getUid();
        DatabaseReference myRef = database.getReference("tasks/uid: " + uid + "/user tasks: ");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                tv_screen.setText(""); //clear
                int count = 1;
                for(DataSnapshot singleTask: snapshot.getChildren()) {
                    if (singleTask.exists()){
                        tv_screen.append(count + ": " + singleTask.getValue().toString().trim() + "\n");
                        count++;
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

}
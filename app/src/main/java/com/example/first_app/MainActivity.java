package com.example.first_app;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView textViewTitle = findViewById(R.id.TV_Title);
        Typeface myfont = Typeface.createFromAsset(getAssets(), "DancingScript.ttf");
        textViewTitle.setTypeface(myfont);
    }

    public void clicked(View view) {
        Toast.makeText(MainActivity.this, "button clicked" + view.getId(), Toast.LENGTH_SHORT).show();
    }

    public void toLogin(View view) {
        Intent intent = new Intent(MainActivity.this,Login.class);
        startActivity(intent);
        finish();
    }
}
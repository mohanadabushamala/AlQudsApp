package com.example.alqudsapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class LogInActivity extends AppCompatActivity {

    public static SharedPreferences sharedPreferences;
    public static SharedPreferences.Editor editor;

    public static final String USERNAME = "username";

    EditText usernameET;
    Button button6;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        usernameET = findViewById(R.id.usernameET);
        button6 = findViewById(R.id.button6);

        sharedPreferences = getSharedPreferences(USERNAME, MODE_PRIVATE);
        editor = sharedPreferences.edit();

        button6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = usernameET.getText().toString().trim();
                if (username != null && !username.isEmpty()) {
                    editor.putString(USERNAME, username);
                    editor.apply();
                    editor.commit();
                    Intent intent = new Intent(LogInActivity.this, MainActivity.class);
                    startActivity(intent);
                }
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!sharedPreferences.getString(USERNAME, "null").equals("null")) {
            Intent intent = new Intent(LogInActivity.this, MainActivity.class);
            startActivity(intent);
        }
    }
}
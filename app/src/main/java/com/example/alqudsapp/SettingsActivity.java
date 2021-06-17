package com.example.alqudsapp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

import petrov.kristiyan.colorpicker.ColorPicker;

public class SettingsActivity extends AppCompatActivity {

    public static SharedPreferences sharedPreferences;
    public static SharedPreferences.Editor editor;


    public static final String SHARED_PREFS = "colors";
    public static final String COLORTYPE = "color";
    public static final String THEMETYPE = "theme";
    public static final String POSITION = "position";

    Button changeColorBtn, nameChangeBtn, commintBtn;
    EditText nameChangeET, commintET;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        editor = sharedPreferences.edit();
        switch (sharedPreferences.getInt(POSITION, 4)) {
            case 0:
                setTheme(R.style.ThemeAlQudsAppBlack);
                break;
            default:
                setTheme(R.style.ThemeAlQudsApp);
                break;
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        changeColorBtn = findViewById(R.id.changeColorBtn);
        nameChangeBtn = findViewById(R.id.nameChangeBtn);
        commintBtn = findViewById(R.id.commintBtn);

        nameChangeET = findViewById(R.id.nameChangeET);
        commintET = findViewById(R.id.commintET);

        nameChangeET.setText(LogInActivity.sharedPreferences.getString(LogInActivity.USERNAME, "") + "");
        nameChangeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = nameChangeET.getText().toString().trim();
                if (name != null && !name.isEmpty()) {
                    LogInActivity.editor.putString(LogInActivity.USERNAME, name);
                    LogInActivity.editor.apply();
                    LogInActivity.editor.commit();

                    Intent intent = new Intent(SettingsActivity.this, MainActivity.class);
                    startActivity(intent);
                }
            }
        });

        commintBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String commint = commintET.getText().toString().trim();
                if (commint != null && !commint.isEmpty()) {
                    DBHelper dbHelper = new DBHelper(SettingsActivity.this);
                    boolean b = dbHelper.insertCommint(commint);
                    if (b) {
                        Toast.makeText(SettingsActivity.this, "تم إضافط التعليق الخاص بك", Toast.LENGTH_SHORT).show();
                        commintET.setText("");
                    }else {
                        Toast.makeText(SettingsActivity.this, "خطا", Toast.LENGTH_SHORT).show();

                    }
                }
            }
        });

        sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        editor = sharedPreferences.edit();

        switch (sharedPreferences.getInt(POSITION, 4)) {
            case 0:
                setTheme(R.style.ThemeAlQudsAppBlack);
                changeColorBtn.setBackgroundColor(getResources().getColor(R.color.black));
                nameChangeBtn.setBackgroundColor(getResources().getColor(R.color.black));
                commintBtn.setBackgroundColor(getResources().getColor(R.color.black));
                break;
            default:
                setTheme(R.style.ThemeAlQudsApp);
                changeColorBtn.setBackgroundColor(getResources().getColor(R.color.purple_200));
                nameChangeBtn.setBackgroundColor(getResources().getColor(R.color.purple_200));
                commintBtn.setBackgroundColor(getResources().getColor(R.color.purple_200));
                break;
        }

        changeColorBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openColorPicker();
            }
        });


    }

    private void openColorPicker() {

        ColorPicker colorPicker = new ColorPicker(SettingsActivity.this);
        final ArrayList<String> colors = new ArrayList<>();
        colors.add("#FF000000");
        colors.add("#FFCA28");

        colorPicker.setColors(colors)
                .setColumns(2)
                .setTitle("إختر اللون الخاص بك")
                .setRoundColorButton(true)
                .setOnChooseColorListener(new ColorPicker.OnChooseColorListener() {
                    @SuppressLint("NewApi")
                    @Override
                    public void onChooseColor(int position, int color) {
                        Constant.postion = position;
                        Constant.color = color;

                        switch (position) {
                            case 0:
                                Constant.theme = R.style.ThemeAlQudsAppBlack;
                                changeColorBtn.setBackgroundColor(color);
                                nameChangeBtn.setBackgroundColor(color);
                                commintBtn.setBackgroundColor(color);
                                break;
                            default:
                                Constant.theme = R.style.ThemeAlQudsApp;
                                changeColorBtn.setBackgroundColor(color);
                                nameChangeBtn.setBackgroundColor(color);
                                commintBtn.setBackgroundColor(color);
                                break;

                        }
                        editor.putInt(COLORTYPE, color);
                        editor.putInt(POSITION, position);
                        editor.putInt(THEMETYPE, Constant.theme);
                        editor.apply();
                        editor.commit();

                        Intent intent = new Intent(SettingsActivity.this, MainActivity.class);
                        startActivity(intent);
                    }

                    @Override
                    public void onCancel() {

                    }
                }).show();
    }

}
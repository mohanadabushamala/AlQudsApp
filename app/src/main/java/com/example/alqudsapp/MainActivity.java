package com.example.alqudsapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    Button button, button2, button3,
            button4, button5;

    TextView HelloTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        SettingsActivity.sharedPreferences = getSharedPreferences(SettingsActivity.SHARED_PREFS, MODE_PRIVATE);
        SettingsActivity.editor = SettingsActivity.sharedPreferences.edit();
        switch (SettingsActivity.sharedPreferences.getInt(SettingsActivity.POSITION, 4)) {
            case 0:
                setTheme(R.style.ThemeAlQudsAppBlack);
                break;
            default:
                setTheme(R.style.ThemeAlQudsApp);
                break;
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        Calendar calendar = Calendar.getInstance();

        Intent intent1 = new Intent(getApplicationContext(), MyService2.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 100, intent1, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), 3000, pendingIntent);

        button = findViewById(R.id.button);
        button2 = findViewById(R.id.button2);
        button3 = findViewById(R.id.button3);
        button4 = findViewById(R.id.button4);
        button5 = findViewById(R.id.button5);

        HelloTV = findViewById(R.id.HelloTV);

        HelloTV.setText("أهلا " + LogInActivity.sharedPreferences.getString(LogInActivity.USERNAME, ""));
        SettingsActivity.sharedPreferences = getSharedPreferences(SettingsActivity.SHARED_PREFS, MODE_PRIVATE);
        SettingsActivity.editor = SettingsActivity.sharedPreferences.edit();
        switch (SettingsActivity.sharedPreferences.getInt(SettingsActivity.POSITION, 4)) {
            case 0:
                setTheme(R.style.ThemeAlQudsAppBlack);
                button.setBackgroundColor(getResources().getColor(R.color.black));
                button2.setBackgroundColor(getResources().getColor(R.color.black));
                button3.setBackgroundColor(getResources().getColor(R.color.black));
                button4.setBackgroundColor(getResources().getColor(R.color.black));
                button5.setBackgroundColor(getResources().getColor(R.color.black));

                break;
            default:
                setTheme(R.style.ThemeAlQudsApp);
                button.setBackgroundColor(getResources().getColor(R.color.purple_200));
                button2.setBackgroundColor(getResources().getColor(R.color.purple_200));
                button3.setBackgroundColor(getResources().getColor(R.color.purple_200));
                button4.setBackgroundColor(getResources().getColor(R.color.purple_200));
                button5.setBackgroundColor(getResources().getColor(R.color.purple_200));

                break;
        }

    }

    public void whatsAlQuds(View view) {
        Intent intent = new Intent(MainActivity.this, AlQudsActivity.class);
        startActivity(intent);
    }

    public void lastNews(View view) {
        Intent intent = new Intent(MainActivity.this, LastNewsActivity.class);
        startActivity(intent);
    }

    public void numberAlQuds(View view) {
        Intent intent = new Intent(MainActivity.this, NumberAlQudsActivity.class);
        startActivity(intent);
    }

    public void imageAlQuds(View view) {
        Intent intent = new Intent(MainActivity.this, PhotoAlQudsActivity.class);
        startActivity(intent);
    }

    public void settingAlQuds(View view) {
        Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
        startActivity(intent);
    }

}
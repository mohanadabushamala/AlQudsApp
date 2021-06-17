package com.example.alqudsapp;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.os.IBinder;
import android.util.Log;

import androidx.core.app.NotificationCompat;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;

public class MyService2 extends Service {
    Context context;
    RequestQueue referenceQueue;

    String title;

    public MyService2(Context context) {
        this.context = context;
    }
    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        referenceQueue = Volley.newRequestQueue(context);

        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int second = calendar.get(Calendar.SECOND);
        int minute = calendar.get(Calendar.MINUTE);

        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int month = calendar.get(Calendar.MONTH);
        int year = calendar.get(Calendar.YEAR);

        showAllDetails(year + "-" + month + 1 + "-" + day);

        if (hour == 9 && second == 1 && minute == 1) {
            NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

            Intent intent1 = new Intent(getApplicationContext(), MainActivity.class);
            intent1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 100, intent1, PendingIntent.FLAG_UPDATE_CURRENT);
            NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(), "ch1");
            builder.setContentIntent(pendingIntent);
            builder.setSmallIcon(R.drawable.ic_launcher_background);
            builder.setContentTitle("Al Quds App");
            builder.setContentText(title + "");
            builder.setAutoCancel(true);
            builder.setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION));
            manager.notify(1, builder.build());

        }
        return super.onStartCommand(intent, flags, startId);
    }

    private void showAllDetails(String date) {

        String url = "https://newsapi.org/v2/everything?q=%D8%A7%D9%84%D9%82%D8%AF%D8%B3&from=2021-05-08&to=" + date +
                "&sortBy=popularity&apiKey=665e8db06838464b8c0cc38bfb8df1c1&fbclid=IwAR0PyIOdx26V2mk2pv2G7m-7CP7_-aqv_7MVyu1Am3svjwwBgm3hCaYRmkQ";

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        if (response.length() > 0) {
                            try {
                                String status = response.getString("status");

                                if (status.equals("ok")) {
                                    JSONArray jsonArray = response.getJSONArray("articles");
                                    for (int i = 0; i > jsonArray.length(); i++) {
                                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                                        title = jsonObject.getString("title");
                                    }
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        } else {
                            Log.d("sss", "noooo");
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("sss", error.getLocalizedMessage() + "");
            }
        });

        referenceQueue.add(request);

    }

}
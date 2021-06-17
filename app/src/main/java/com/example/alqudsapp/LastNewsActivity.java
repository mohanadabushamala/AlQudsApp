package com.example.alqudsapp;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LastNewsActivity extends AppCompatActivity {

    RequestQueue referenceQueue;
    RecyclerView lastNewsRecyclerView;

    List<LastNewsItem> lastNewsItemList;
    String url = "https://newsapi.org/v2/everything?q=palestine&from=2021-06-09&sortBy=ar&apiKey=665e8db06838464b8c0cc38bfb8df1c1";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_last_news);

        referenceQueue = Volley.newRequestQueue(LastNewsActivity.this);
        lastNewsRecyclerView = findViewById(R.id.lastNewsRecyclerView);
        StaggeredGridLayoutManager manager = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
        lastNewsRecyclerView.setLayoutManager(manager);

        lastNewsItemList = new ArrayList<>();

        Calendar calendar = Calendar.getInstance();

        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int month = calendar.get(Calendar.MONTH);
        int year = calendar.get(Calendar.YEAR);

        showAllDetails(year + "-" + month + 1 + "-" + day);
    }

    private void showAllDetails(String date) {
//        String url = "https://newsapi.org/v2/everything?q=%D8%A7%D9%84%D9%82%D8%AF%D8%B3&sortBy=ar&apiKey=665e8db06838464b8c0cc38bfb8df1c1";

        //  String url = "https://newsapi.org/v2/everything?q=palestine&from=" + date + "&sortBy=ar&apiKey=665e8db06838464b8c0cc38bfb8df1c1";
        String url = "https://www.aljazeera.net/aljazeerarss/a7c186be-1baa-4bd4-9d80-a84db769f779/73d0e1b4-532f-45ef-b135-bfdff8b8cab9";
        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
                    factory.setNamespaceAware(true);
                    XmlPullParser parser = factory.newPullParser();
                    parser.setInput(new StringReader(response));
                    int eventType = parser.getEventType();
                    LastNewsItem item = null;
                    String text = null;
                    while (eventType != XmlPullParser.END_DOCUMENT) {
                        if (eventType == XmlPullParser.START_TAG) {
                            String tagName = parser.getName();
                            if (tagName.equalsIgnoreCase("item")) {
                                item = new LastNewsItem();
                            } else if (tagName.equalsIgnoreCase("thumbnail") && item != null) {
                                String url = parser.getAttributeValue(null, "url");
                                item.setUrl(url);
                            }
                        } else if (eventType == XmlPullParser.TEXT) {
                            text = parser.getText();
                        } else if (eventType == XmlPullParser.END_TAG) {
                            if (item != null) {
                                String tagName = parser.getName();
                                if (tagName.equalsIgnoreCase("title")) {
                                    item.setTitle(text);
                                } else if (tagName.equalsIgnoreCase("link")) {
                                    item.setUrl(text);
                                } else if (tagName.equalsIgnoreCase("item")) {
                                    lastNewsItemList.add(item);
                                    item = null;
                                }
                            }
                        }
                        eventType = parser.next();
                    }

                    LastNewsAdapter adapter = new LastNewsAdapter(getApplicationContext(), lastNewsItemList);
                    lastNewsRecyclerView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();

                } catch (XmlPullParserException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        referenceQueue.add(request);

        /*
                Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(UrlClass.url)
                .addConverterFactory(GsonConverterFactory.create());
        Retrofit retrofit = builder.build();
        ApiClass apiClass = retrofit.create(ApiClass.class);
        Call<DataItem> call = apiClass.getLastNews(
                "Mozilla/5.0",
                "القدس",
                "2021-05-08",
                "2021-05-08",
                "ar",
                "665e8db06838464b8c0cc38bfb8df1c1");

        call.enqueue(new Callback<DataItem>() {
            @Override
            public void onResponse(Call<DataItem> call, retrofit2.Response<DataItem> response) {
                DataItem dataItem = response.body();
                if (response.isSuccessful()) {
                    if (dataItem.getStatus().equals("ok")) {
                        for (LastNewsItem item : dataItem.getArticles()) {
                            lastNewsItemList.add(item);
                        }

                        LastNewsAdapter adapter = new LastNewsAdapter(getApplicationContext(), lastNewsItemList);
                        lastNewsRecyclerView.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void onFailure(Call<DataItem> call, Throwable t) {
                Log.d("sss", t.getMessage() + "nkkk");

            }
        });
*/
/*
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
                                        String title = jsonObject.getString("title");
                                        String urlToImage = jsonObject.getString("urlToImage");
                                        String url = jsonObject.getString("url");

                                        lastNewsItemList.add(new LastNewsItem(title, urlToImage, url));
                                        LastNewsAdapter adapter = new LastNewsAdapter(getApplicationContext(), lastNewsItemList);
                                        lastNewsRecyclerView.setAdapter(adapter);
                                        adapter.notifyDataSetChanged();

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
                Log.d("sss", error.networkResponse.statusCode + "");

            }
        });

        referenceQueue.add(request);
*/
    }

}
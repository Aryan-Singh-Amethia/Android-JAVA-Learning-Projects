package com.htmedia.newsrecyclerproject;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

//import com.htmedia.newsrecyclerproject.databinding.ActivityMainBinding;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    //Handler Function for handling activity switch


    ArrayList<ContentModel> arrayList = new ArrayList<>();
    RecyclerContactAdapter adapter;

    //ActivityMainBinding binding;
   // Handler mainHandler= new Handler();




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


//        binding=ActivityMainBinding.inflate(getLayoutInflater());
//        setContentView(binding.getRoot());

        RecyclerView recyclerView = findViewById(R.id.recycleContact);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

//        arrayList.add(new ContentModel(R.drawable.mrdickenson,"name","Contact"));
//        arrayList.add(new ContentModel(R.drawable.mrdickenson,"name","Contact"));
//        arrayList.add(new ContentModel(R.drawable.mrdickenson,"name","Contact"));
//        arrayList.add(new ContentModel(R.drawable.mrdickenson,"name","Contact"));
//        arrayList.add(new ContentModel(R.drawable.mrdickenson,"name","Contact"));
//        arrayList.add(new ContentModel(R.drawable.mrdickenson,"name","Contact"));
//        arrayList.add(new ContentModel(R.drawable.mrdickenson,"name","Contact"));
//        arrayList.add(new ContentModel(R.drawable.mrdickenson,"name","Contact"));
//        arrayList.add(new ContentModel(R.drawable.mrdickenson,"name","Contact"));

         /*
         Running the background thread for calling the news API.
          */
      //    new FetchData().start();

        /*
         * Running Background Thread for calling API
         * using Volley Library.
         */


          RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
          String url ="https://saurav.tech/NewsAPI/top-headlines/category/health/in.json";
          StringRequest stringRequest = new StringRequest(Request.Method.GET,
                  url,
                  new Response.Listener<String>() {
                      @Override
                      public void onResponse(String response) {
                          Log.i("API Call :: ", "Successful");
                          if (!response.isEmpty()) {
                              JSONObject jsonObject = null;
                              try {
                                  jsonObject = new JSONObject(response);
                                  JSONArray newsArray = jsonObject.getJSONArray("articles");
                                  arrayList.clear();
                                  for (int i = 0; i < newsArray.length(); i++) {
                                      JSONObject news = newsArray.getJSONObject(i);
                                      //   Uri img = Uri.parse(news.getString("urlToImage"));
                                      String img = news.getString("urlToImage");
                                      String title = news.getString("title");
                                      String description = news.getString("description");
                                      //Adding original url to get read-more functionality
                                      String url = news.getString("url");
                                      arrayList.add(new ContentModel(img, title, description,url));
                                      Log.i("news_item : : ", title);
                                  }
                              } catch (JSONException e) {
                                  e.printStackTrace();
                              }

                              runOnUiThread(new Runnable() {
                                  @Override
                                  public void run() {
                                      adapter.setArrayList(arrayList);
                                      adapter.notifyDataSetChanged();
                                  }
                              });
                          }
                      }
                  },
                  new Response.ErrorListener() {
                      @Override
                      public void onErrorResponse(VolleyError error) {
                          Log.i("API Call :: ", "NOT  Successful");
                      }
                  });


             requestQueue.add(stringRequest);
          //  Thread.sleep(3000);
            adapter = new RecyclerContactAdapter(this,arrayList);
            recyclerView.setAdapter(adapter);
    }

    public void configureNextButton(View view) {

        Button button = findViewById(R.id.activityButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,SecondaryActivity.class));
            }
        });

    }


    //API Calling class using multithreading.
    class FetchData extends Thread{

        String data="";

        @Override
        public void run() {
            super.run();

            try{
                URL url = new URL("https://saurav.tech/NewsAPI/top-headlines/category/health/in.json");
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader =new BufferedReader(new InputStreamReader(inputStream));
                String line;
                while((line = bufferedReader.readLine())!=null){
                    data+=line;
                }

                if(!data.isEmpty()){
                    JSONObject jsonObject = new JSONObject(data);
                    JSONArray newsArray = jsonObject.getJSONArray("articles");
                    arrayList.clear();
                    for(int i=0;i<newsArray.length();i++){
                        JSONObject news = newsArray.getJSONObject(i);
                     //   Uri img = Uri.parse(news.getString("urlToImage"));
                        String img = news.getString("urlToImage");
                        String title = news .getString("title");
                        String description = news.getString("description");
                        String Url = news.getString("url");
                        arrayList.add(new ContentModel(img,title,description,Url));
                        Log.i("news_item : : ",title);
                    }

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            adapter.setArrayList(arrayList);
                            adapter.notifyDataSetChanged();
                        }
                    });
                }

            }catch(Exception e){
                e.printStackTrace();
            }
        }
    }

}
package com.htmedia.newsrecyclerproject;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import java.sql.Array;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SecondActivity extends AppCompatActivity {

    private RecyclerView recyclerView;

    NewsContentV2Adapter newsContentV2Adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        recyclerView= findViewById(R.id.newsRecycler);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(SecondActivity.this));

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://saurav.tech/NewsAPI/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        try {
            Thread.sleep(3000);
            NewsContent newsContent = retrofit.create(NewsContent.class);
            Call<List<ContentModelV2.News>> call = newsContent.getNewsContent();
            call.enqueue(new Callback<List<ContentModelV2.News>>() {
                @Override
                public void onResponse(Call<List<ContentModelV2.News>> call, Response<List<ContentModelV2.News>> response) {
                    if(!response.isSuccessful()){
                        Log.i("API RESPONSE ::: ","Some error occured during API call..");
                     //   return ;

                    }
                    Log.i("API CALL STATUS :: ","SUCCESS!!");
                    List<ContentModelV2.News> arrList =response.body();
                    //  Log.i("RESULT :: ",list.toString());
                    NewsContentV2Adapter newsContentV2Adapter = new NewsContentV2Adapter(arrList,SecondActivity.this);
                    recyclerView.setAdapter(newsContentV2Adapter);
                }

                @Override
                public void onFailure(Call<List<ContentModelV2.News>> call, Throwable t) {
                    Log.i("API CALL STATUS :: ","FAILURE");
                    Toast.makeText(SecondActivity.this,"API call failed for second activity",Toast.LENGTH_SHORT);
                }
            });

         //   System.out.println(arrList.toString());
//            newsContentV2Adapter =new NewsContentV2Adapter(arrList,SecondActivity.this);
//            recyclerView.setAdapter(newsContentV2Adapter

            Log.i("EXITING :: ","Exiting Second Activity");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


    }
}
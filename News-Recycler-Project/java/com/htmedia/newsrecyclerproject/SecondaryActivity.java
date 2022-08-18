package com.htmedia.newsrecyclerproject;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class SecondaryActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    ProgressBar progressBar;
    LinearLayoutManager linearLayoutManager;
    PostAdapter postAdapter;
    ArrayList<Posts> postsList=new ArrayList<Posts>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.secondary_activity_layout);


        recyclerView = findViewById(R.id.recyclerView);
        progressBar=findViewById(R.id.progressBar);
        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        postAdapter = new PostAdapter(postsList);
        recyclerView.setAdapter(postAdapter);

        Button button = findViewById(R.id.backButton2);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


    //    fetchPosts();
        try {
            fetchPosts();
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    private void fetchPosts() {
        progressBar.setVisibility(View.VISIBLE);
        RetrofitClient.getRetrofitClient().getPosts().enqueue(new Callback<List<Posts>>() {
            @Override
            public void onResponse(Call<List<Posts>> call, Response<List<Posts>> response) {
                Log.i("INSIDE onResponse() :: ","YES");
                if(response.isSuccessful() && response.body()!=null){
                    postsList.addAll(response.body());
                    Log.i("LIST :: ", postsList.toString());
                    progressBar.setVisibility(View.GONE);
                    postAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<List<Posts>> call, Throwable t) {
                Log.i("INSIDE onResponse() :: ","No");
                progressBar.setVisibility(View.GONE);
                Toast.makeText(SecondaryActivity.this, "Error :: "+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }
}
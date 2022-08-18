package com.htmedia.newsrecyclerproject;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface NewsContent {

    @GET("/sources.json")
    Call<List<ContentModelV2.News>> getNewsContent();
}

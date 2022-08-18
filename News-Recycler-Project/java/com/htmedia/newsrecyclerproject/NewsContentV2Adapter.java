package com.htmedia.newsrecyclerproject;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class NewsContentV2Adapter extends RecyclerView.Adapter<NewsContentV2Adapter.NewsContentV2ViewHolder> {

    List<ContentModelV2.News> newsList;
    Context context;

    public List<ContentModelV2.News> getNewsList() {
        return newsList;
    }

    public void setNewsList(List<ContentModelV2.News> newsList) {
        this.newsList = newsList;
    }

    public NewsContentV2Adapter(List<ContentModelV2.News> newsList, Context context) {
        this.newsList = newsList;
        this.context = context;
    }

    @NonNull
    @Override
    public NewsContentV2ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.news_row,viewGroup,false);
        return new NewsContentV2ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NewsContentV2ViewHolder newsContentV2ViewHolder, int i) {

        ContentModelV2.News contentModelV2 = newsList.get(i);
        newsContentV2ViewHolder.author.setText(contentModelV2.getAuthor());
        newsContentV2ViewHolder.description.setText(contentModelV2.getDescription());
        newsContentV2ViewHolder.title.setText(contentModelV2.getTitle());
        newsContentV2ViewHolder.content.setText(contentModelV2.getContent());
        newsContentV2ViewHolder.url.setText(contentModelV2.getUrl());
        newsContentV2ViewHolder.publishedAt.setText(contentModelV2.getPublishedAt());
        newsContentV2ViewHolder.urlToImage.setText(contentModelV2.getUrlToImage());

    }

    @Override
    public int getItemCount() {
        return newsList.size();
    }

    public class NewsContentV2ViewHolder extends RecyclerView.ViewHolder{

        TextView author,title,description,url,urlToImage,publishedAt,content;

        public NewsContentV2ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.author=itemView.findViewById(R.id.author);
            this.description=itemView.findViewById(R.id.description);
            this.title=itemView.findViewById(R.id.title);
            this.content=itemView.findViewById(R.id.content);
            this.url=itemView.findViewById(R.id.url);
            this.publishedAt=itemView.findViewById(R.id.publishedAt);
            this.urlToImage=itemView.findViewById(R.id.newsImg);
        }

    }
}

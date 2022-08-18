package com.htmedia.newsrecyclerproject;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.Volley;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class RecyclerContactAdapter extends RecyclerView.Adapter<RecyclerContactAdapter.ViewHolder> {

    Context context;
    ArrayList<ContentModel> arrayList;

    public ArrayList<ContentModel> getArrayList() {
        return arrayList;
    }

    public void setArrayList(ArrayList<ContentModel> arrayList) {
        this.arrayList = arrayList;
    }

    RecyclerContactAdapter(Context context, ArrayList<ContentModel> arrayList){
        this.context=context;
        this.arrayList=arrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View v = LayoutInflater.from(context).inflate(R.layout.contact_row,viewGroup,false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {


        /*
         * fetching image using multithreading.
         */
    //    new ImageLoadTask(arrayList.get(i).img, viewHolder.imgContact).execute();
        /*
         * Fetching image using Volley Library.
         */
        fetchImageFromURL(context,arrayList.get(i).img,viewHolder);
        viewHolder.txtName.setText(arrayList.get(i).title);
        viewHolder.txtNumber.setText(arrayList.get(i).description);
        String url=arrayList.get(i).url;

       // viewHolder.imgContact.setImageResource();
        viewHolder.readMoreButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(context,News_Details_Activity.class);
                intent.putExtra("url",url); //
                context.startActivity(intent);
            }
        });

    }

    private void fetchImageFromURL(Context context,String urlToImage,ViewHolder viewHolder) {
        //Volley Library
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        ImageRequest imageRequest = new ImageRequest(urlToImage, new Response.Listener<Bitmap>(){
            @Override
            public void onResponse(Bitmap response) {
                Log.i("Volley Image :: ","Image fetched through Volley");
                viewHolder.imgContact.setImageBitmap(response);
            }
        },100,100, null,null);
        requestQueue.add(imageRequest);
                }


    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView txtName , txtNumber;
        ImageView imgContact;
        Button readMoreButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtName=itemView.findViewById(R.id.nameContact);
            txtNumber=itemView.findViewById(R.id.numberContact);
            imgContact=itemView.findViewById(R.id.contactImg);
            readMoreButton=itemView.findViewById(R.id.readMoreButton);//Adding read more button.
        }
    }


    /*
     * Aync class to fetch image from url.
     */
    public class ImageLoadTask extends AsyncTask<Void, Void, Bitmap> {

        private String url;
        private ImageView imageView;

        public ImageLoadTask(String url, ImageView imageView) {
            this.url = url;
            this.imageView = imageView;
        }

        @Override
        protected Bitmap doInBackground(Void... params) {
            try {
                URL urlConnection = new URL(url);
                HttpURLConnection connection = (HttpURLConnection) urlConnection
                        .openConnection();
                connection.setDoInput(true);
                connection.connect();
                InputStream input = connection.getInputStream();
                Bitmap myBitmap = BitmapFactory.decodeStream(input);
                return myBitmap;
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Bitmap result) {
            super.onPostExecute(result);
            imageView.setImageBitmap(result);
        }

    }
}

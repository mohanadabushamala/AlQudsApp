package com.example.alqudsapp;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class LastNewsAdapter extends RecyclerView.Adapter<LastNewsAdapter.ViewHolder> {

    Context context;
    List<LastNewsItem> lastNewsItemList;

    public LastNewsAdapter(Context context, List<LastNewsItem> lastNewsItemList) {
        this.context = context;
        this.lastNewsItemList = lastNewsItemList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.last_news_layout, parent, false);
        return new LastNewsAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        LastNewsItem item = lastNewsItemList.get(position);

        holder.titleNewsTV.setText(item.getTitle() + "");
        Picasso.get().load(item.getUrlToImage()).into(holder.imageNewsIV);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent browse = new Intent(Intent.ACTION_VIEW, Uri.parse(item.getUrl()));
                browse.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(browse);

            }
        });

    }

    @Override
    public int getItemCount() {
        return lastNewsItemList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView titleNewsTV;
        ImageView imageNewsIV;

        ViewHolder(@NonNull View itemView) {
            super(itemView);

            titleNewsTV = itemView.findViewById(R.id.titleNewsTV);
            imageNewsIV = itemView.findViewById(R.id.imageNewsIV);
        }

    }
}

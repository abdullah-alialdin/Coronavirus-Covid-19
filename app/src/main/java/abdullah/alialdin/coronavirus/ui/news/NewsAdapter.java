package abdullah.alialdin.coronavirus.ui.news;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

import abdullah.alialdin.coronavirus.R;
import abdullah.alialdin.coronavirus.pojo.NewsModel;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsViewHolder> {

    private List<NewsModel> mNewsList;
    Context mContext;
    public final static String EXTRA_URL = "news url";

    public NewsAdapter(Context context, List<NewsModel> newsList){
        this.mContext = context;
        this.mNewsList = newsList;
    }
    @NonNull
    @Override
    public NewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.news_item, parent, false);
        return new NewsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NewsViewHolder holder, int position) {
        NewsModel newsModel = mNewsList.get(position);
        holder.newsTitle.setText(newsModel.getTitle());
        String date = newsModel.getPublishedAt();
        String[] exDate = date.split("T");
        holder.newsDate.setText(exDate[0]);
        holder.source.setText(newsModel.getSource());
        if (newsModel.getUrlToImage()!= null && !newsModel.getUrlToImage().isEmpty()) {
            Picasso.get()
                    .load(newsModel.getUrlToImage())
                    .resize(200, 200)
                    .placeholder(R.drawable.placeholder)
                    .into(holder.newsImageView);
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, NewsViewerActivity.class);
                intent.putExtra(EXTRA_URL, newsModel.getUrl());
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mNewsList.size();
    }

    public static class NewsViewHolder extends RecyclerView.ViewHolder {
        ImageView newsImageView;
        TextView newsTitle, newsDate, source;
        public NewsViewHolder(@NonNull View itemView) {
            super(itemView);
            newsImageView = itemView.findViewById(R.id.news_image);
            newsTitle = itemView.findViewById(R.id.news_title_textView);
            newsDate = itemView.findViewById(R.id.date_textView);
            source = itemView.findViewById(R.id.source_textView);
        }
    }
}

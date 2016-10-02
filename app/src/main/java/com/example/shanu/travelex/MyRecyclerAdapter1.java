package com.example.shanu.travelex;

/**
 * Created by Shanu on 02-10-2016.
 */
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by NikTin on 07/11/15.
 */
public class MyRecyclerAdapter1 extends RecyclerView.Adapter<MyRecyclerAdapter1.CustomViewHolder> {
    private List<FeedItem> feedItemList;
    private Context mContext;

    public MyRecyclerAdapter1(Context context, List<FeedItem> feedItemList) {
        this.feedItemList = feedItemList;
        this.mContext = context;
    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_row1, null);

        CustomViewHolder viewHolder = new CustomViewHolder(view);
        //Toast.makeText(mContext,"CustomViewHolder",Toast.LENGTH_SHORT).show();
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(CustomViewHolder customViewHolder, int i) {
        FeedItem feedItem = feedItemList.get(i);
        /*Picasso.with(mContext).load(feedItem.getThumbnail())
                .error(R.drawable.placeholder)
                .placeholder(R.drawable.placeholder)
                .into(customViewHolder.imageView);
*/
        //Setting text view title
        customViewHolder.textView.setText(Html.fromHtml(feedItem.getTitle()));

        //Handle click event on both title and image click
        customViewHolder.textView.setOnClickListener(clickListener);
       // customViewHolder.imageView.setOnClickListener(clickListener);

        customViewHolder.textView.setTag(customViewHolder);
        //customViewHolder.imageView.setTag(customViewHolder);
    }

    @Override
    public int getItemCount() {
        return (null != feedItemList ? feedItemList.size() : 0);
    }


    View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            CustomViewHolder holder = (CustomViewHolder) view.getTag();
            int position = holder.getAdapterPosition();

            FeedItem feedItem = feedItemList.get(position);
            // Toast.makeText(mContext, feedItem.geturl(), Toast.LENGTH_SHORT).show();
            Intent myIntent = new Intent(view.getContext(), Detail_Restaurant.class);
            myIntent.putExtra("title",feedItem.getTitle());
            myIntent.putExtra("url",feedItem.geturl());
            myIntent.putExtra("thumb",feedItem.getThumbnail());
            myIntent.putExtra("cuisines",feedItem.getCuisines());
            myIntent.putExtra("location",feedItem.getLocation());
            mContext.startActivity(myIntent);
        }
    };


    public class CustomViewHolder extends RecyclerView.ViewHolder {
        protected TextView textView;
      //  protected ImageView imageView;

        public CustomViewHolder(View view) {
            super(view);
            this.textView = (TextView) view.findViewById(R.id.name_rest);
          //  this.imageView = (ImageView) view.findViewById(R.id.description);
        }
    }
}


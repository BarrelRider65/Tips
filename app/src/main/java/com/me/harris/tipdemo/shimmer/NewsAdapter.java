package com.me.harris.tipdemo.shimmer;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.me.harris.tipdemo.R;

public class NewsAdapter extends RecyclerView.Adapter<SimpleRcvViewHolder> {


    @Override
    public SimpleRcvViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new SimpleRcvViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_news, parent, false));
    }

    @Override
    public void onBindViewHolder(SimpleRcvViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 10;
    }


}

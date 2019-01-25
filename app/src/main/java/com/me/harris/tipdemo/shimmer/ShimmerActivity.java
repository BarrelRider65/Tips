package com.me.harris.tipdemo.shimmer;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.me.harris.tipdemo.R;
import com.me.harris.tipdemo.shimmer.skeleton.Skeleton;
import com.me.harris.tipdemo.shimmer.skeleton.SkeletonScreen;


public class ShimmerActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_skelton);
         recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        NewsAdapter adapter = new NewsAdapter();
        final SkeletonScreen skeletonScreen = Skeleton.bind(recyclerView)
                .adapter(adapter)
                .shimmer(true)
                .angle(20)
                .frozen(false)
                .duration(1200)
                .count(100)
                .load(R.layout.item_skeleton_news)
                .show(); //default count is 10
        recyclerView.postDelayed(skeletonScreen::hide, 3000);



    }
}

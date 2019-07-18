package com.android.erp.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.erp.CategoriesActivity;
import com.android.erp.Models.PagerModel;
import com.android.erp.Models.TitleChild;
import com.android.erp.Models.TitleParent;
import com.android.erp.R;
import com.android.erp.ViewHolders.TitleChildViewHolder;
import com.android.erp.ViewHolders.TitleParentViewHolder;
import com.thoughtbot.expandablerecyclerview.ExpandableRecyclerViewAdapter;
import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;

import java.util.ArrayList;
import java.util.List;

public class MainAdapter extends ExpandableRecyclerViewAdapter<TitleParentViewHolder, TitleChildViewHolder> {

    private Context context;

    public MainAdapter(List<? extends ExpandableGroup> groups, Context context) {
        super(groups);
        this.context = context;
    }

    @Override
    public TitleParentViewHolder onCreateGroupViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_parent, parent, false);
        return new TitleParentViewHolder(view);
    }

    @Override
    public TitleChildViewHolder onCreateChildViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_child, parent, false);
        return new TitleChildViewHolder(view);
    }

    @Override
    public void onBindChildViewHolder(TitleChildViewHolder holder, int flatPosition, ExpandableGroup group, int childIndex) {
        Typeface avenir_book = Typeface.createFromAsset(context.getAssets(),"fonts/AvenirBook.ttf");
        final TitleChild child = ((TitleParent) group).getItems().get(childIndex);
        holder.setViews(child.getName(),child.getDone(),child.getUndone(),avenir_book);
        holder.itemView.setOnClickListener(v -> {
            if (flatPosition ==1 || flatPosition == 2 || flatPosition == 3 || flatPosition ==4){
                ArrayList<String> pagerModels = new ArrayList<>();
                ArrayList<String> pagerModelss = new ArrayList<>();
                pagerModels.add("Twitter");
                pagerModelss.add("https://images.vexels.com/media/users/3/137419/isolated/preview/b1a3fab214230557053ed1c4bf17b46c-twitter-icon-logo-by-vexels.png");
                pagerModels.add("Instagram");
                pagerModelss.add("http://pluspng.com/img-png/instagram-png-instagram-png-logo-1455.png");
                pagerModels.add("Facebook");
                pagerModelss.add("https://images.vexels.com/media/users/3/137253/isolated/preview/90dd9f12fdd1eefb8c8976903944c026-facebook-icon-logo-by-vexels.png");
                pagerModels.add("Linkedin");
                pagerModelss.add("https://images.vexels.com/media/users/3/137382/isolated/preview/c59b2807ea44f0d70f41ca73c61d281d-linkedin-icon-logo-by-vexels.png");
                Intent intent = new Intent(context, CategoriesActivity.class);
                intent.putExtra("myList",pagerModels);
                intent.putExtra("myLists",pagerModelss);
                intent.putExtra("userId","1");
                intent.putExtra("categoryId",String.valueOf(flatPosition));
                context.startActivity(intent);
            }else if (flatPosition == 6 || flatPosition == 7){
                ArrayList<String> pagerModels = new ArrayList<>();
                ArrayList<String> pagerModelss = new ArrayList<>();
                pagerModels.add("Photo");
                pagerModelss.add("https://www.sccpre.cat/mypng/full/15-155680_camera-icon-white-small-white-camera-icon-transparent.png");
                pagerModels.add("Video");
                pagerModelss.add("http://www.sclance.com/pngs/video-play-button-transparent-png/video_play_button_transparent_png_1463609.png");
                Intent intent = new Intent(context, CategoriesActivity.class);
                intent.putExtra("myList",pagerModels);
                intent.putExtra("myLists",pagerModelss);
                intent.putExtra("userId","1");
                intent.putExtra("categoryId",String.valueOf(flatPosition));
                context.startActivity(intent);
            }else if (flatPosition == 9){
                ArrayList<String> pagerModels = new ArrayList<>();
                ArrayList<String> pagerModelss = new ArrayList<>();
                pagerModels.add("SMS");
                pagerModelss.add("https://vires.com/wp-content/uploads/2016/11/Mail-Icon-white.png");
                Intent intent = new Intent(context, CategoriesActivity.class);
                intent.putExtra("myList",pagerModels);
                intent.putExtra("myLists",pagerModelss);
                intent.putExtra("userId","1");
                intent.putExtra("categoryId",String.valueOf(flatPosition));
                context.startActivity(intent);
            }

        });
    }

    @Override
    public void onBindGroupViewHolder(TitleParentViewHolder holder, int flatPosition, ExpandableGroup group) {
        Typeface avenir_book = Typeface.createFromAsset(context.getAssets(),"fonts/AvenirBook.ttf");
        holder.setGenreTitle(group,avenir_book);
    }
}

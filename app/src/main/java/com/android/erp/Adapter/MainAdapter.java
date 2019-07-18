package com.android.erp.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.erp.CategoriesActivity;
import com.android.erp.Models.TitleChild;
import com.android.erp.Models.TitleParent;
import com.android.erp.R;
import com.android.erp.ViewHolders.TitleChildViewHolder;
import com.android.erp.ViewHolders.TitleParentViewHolder;
import com.thoughtbot.expandablerecyclerview.ExpandableRecyclerViewAdapter;
import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;

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
            Log.d("sdasdas","flat:" + flatPosition + "   " + "child:"+childIndex);
            Intent intent = new Intent(context, CategoriesActivity.class);
            intent.putExtra("userId","1");
            intent.putExtra("categoryId",String.valueOf(flatPosition));
            context.startActivity(intent);
        });
    }

    @Override
    public void onBindGroupViewHolder(TitleParentViewHolder holder, int flatPosition, ExpandableGroup group) {
        Typeface avenir_book = Typeface.createFromAsset(context.getAssets(),"fonts/AvenirBook.ttf");
        holder.setGenreTitle(group,avenir_book);
    }
}

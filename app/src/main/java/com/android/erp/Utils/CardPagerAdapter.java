package com.android.erp.Utils;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.support.v4.view.PagerAdapter;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.erp.Models.PagerModel;
import com.android.erp.R;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class CardPagerAdapter extends PagerAdapter implements CardAdapter {

    private List<CardView> mViews;
    private List<PagerModel> mData;
    private float mBaseElevation;
    Context context;

    public CardPagerAdapter() {
        mData = new ArrayList<>();
        mViews = new ArrayList<>();
    }


    public void addCardItem(PagerModel item , Context context , float baseElevation) {
        mViews.add(null);
        mData.add(item);
        this.context = context;
        this.mBaseElevation = baseElevation;
    }

    public float getBaseElevation() {
        return mBaseElevation;
    }

    @Override
    public CardView getCardViewAt(int position) {
        try {
            return mViews.get(position);
        }catch (Exception e){
        }
        return null;
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = LayoutInflater.from(container.getContext())
                .inflate(R.layout.card_pager, container, false);

        container.addView(view);
        bind(mData.get(position), view);
        CardView cardView = (CardView) view.findViewById(R.id.cardView);

        if (mBaseElevation == 0) {
            mBaseElevation = cardView.getCardElevation();
        }

        cardView.setMaxCardElevation(mBaseElevation * MAX_ELEVATION_FACTOR);
        mViews.set(position, cardView);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
        mViews.set(position, null);
    }

    private void bind(final PagerModel item, View view) {
        Typeface avenir_medium = Typeface.createFromAsset(context.getAssets(),"fonts/AvenirMedium.ttf");
        ImageView ivBannerSlider =  view.findViewById(R.id.pager_image);
        TextView tvBannersSlider = view.findViewById(R.id.pager_text);
        tvBannersSlider.setText(item.getName());
        tvBannersSlider.setTypeface(avenir_medium);

        Glide.with(context).load(item.getImage()).into(ivBannerSlider);

    }

}

package com.android.erp.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.erp.Models.AllDataModel;
import com.android.erp.Network.Response.CategoriesResponse;
import com.android.erp.PostDetailsActivity;
import com.android.erp.R;

import java.util.List;

public class AllDataAdapter extends RecyclerView.Adapter<AllDataAdapter.AllDataViewHolder> {

    private Context context;
    private List<CategoriesResponse> list;

    public AllDataAdapter(Context context, List<CategoriesResponse> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public AllDataViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_all,viewGroup,false);
        return new AllDataViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AllDataViewHolder allDataViewHolder, int i) {
        Typeface avenir_light = Typeface.createFromAsset(context.getAssets(),"fonts/AvenirLight.ttf");
        allDataViewHolder.date.setText(list.get(i).getDate());
        allDataViewHolder.date.setTypeface(avenir_light);
        allDataViewHolder.done.setTypeface(avenir_light);
        if (list.get(i).getChecking().equals("0")){
            allDataViewHolder.done.setText(R.string.undone);
            allDataViewHolder.done.setTextColor(context.getResources().getColor(R.color.falseColor));
        }else {
            allDataViewHolder.done.setText(R.string.done);
            allDataViewHolder.done.setTextColor(context.getResources().getColor(R.color.trueColor));
        }
        allDataViewHolder.itemView.setOnClickListener(v -> {
            String name = null;
            if (list.get(i).getChecking().equals("0")){
                name = "undone";
            }else {
                name = "done";
            }
            Intent intent = new Intent(context, PostDetailsActivity.class);
            intent.putExtra("postId",list.get(i).getId());
            intent.putExtra("userId",list.get(i).getUserId());
            intent.putExtra("categoryId",list.get(i).getCategoryId());
            intent.putExtra("date",list.get(i).getDate());
            intent.putExtra("title",list.get(i).getTitle());
            intent.putExtra("text",list.get(i).getText());
            intent.putExtra("price",list.get(i).getAdvertisementPrice());
            intent.putExtra("isdone",name);
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class AllDataViewHolder extends RecyclerView.ViewHolder{

        private TextView date,done;

        public AllDataViewHolder(@NonNull View itemView) {
            super(itemView);
            date = itemView.findViewById(R.id.all_name_categories);
            done = itemView.findViewById(R.id.isDone);
        }
    }
}

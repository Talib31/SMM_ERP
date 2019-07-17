package com.android.erp;

import android.graphics.Typeface;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

public class PostDetailsActivity extends AppCompatActivity {

    private AppBarLayout appBarLayout;
    private Toolbar toolbar;
    private ImageView back;
    private TextView title,lang,all_name_details,isDoneDetails;
    private String date,isdone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_details);
        date = getIntent().getStringExtra("date");
        isdone = getIntent().getStringExtra("isdone");
        initData();
        setClicks();
    }

    private void initData() {
        Typeface avenir_light = Typeface.createFromAsset(getAssets(),"fonts/AvenirLight.ttf");
        appBarLayout = findViewById(R.id.appBarLayDetails);
        all_name_details = findViewById(R.id.all_name_details);
        isDoneDetails = findViewById(R.id.isDoneDetails);
        toolbar = findViewById(R.id.toolbarDetails);
        setSupportActionBar(toolbar);
        back = findViewById(R.id.tb_back_details);
        title = findViewById(R.id.toolbar_details_title);
        lang = findViewById(R.id.lang_details);
        title.setTypeface(avenir_light);
        lang.setTypeface(avenir_light);
        isDoneDetails.setTypeface(avenir_light);
        if (isdone.equals("done")){
            isDoneDetails.setText(R.string.done);
            isDoneDetails.setTextColor(getResources().getColor(R.color.trueColor));
        }else {
            isDoneDetails.setText(R.string.undone);
            isDoneDetails.setTextColor(getResources().getColor(R.color.falseColor));
        }
        all_name_details.setText(date);
        all_name_details.setTypeface(avenir_light);
    }
    private void setClicks(){
        lang.setOnClickListener(v -> {
            PopupMenu p = new PopupMenu(PostDetailsActivity.this,lang);
            p.getMenuInflater().inflate(R.menu.main_menu,p.getMenu());
            p.setOnMenuItemClickListener(item -> {
                Toast.makeText(getApplicationContext(),"s",Toast.LENGTH_SHORT).show();
                return true;
            });
            p.show();
        });
        back.setOnClickListener(v -> finish());
    }
}
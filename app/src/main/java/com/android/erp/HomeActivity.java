package com.android.erp;

import android.graphics.Typeface;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.erp.Adapter.MainAdapter;
import com.android.erp.Models.TitleChild;
import com.android.erp.Models.TitleParent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class HomeActivity extends AppCompatActivity {

    private AppBarLayout appBarLayout;
    private Toolbar toolbar;
    private ImageView back;
    private TextView title,packet,lang,date,static_date;
    //parent_title1,parent_title2,parent_title3,
      //      media_name,insta_first,insta_last;
    //private ScrollView main_scroller;
    //private FrameLayout digital,photo,sms_marketing;
    //private LinearLayout media_expand,photo_expand,sms_expand;
    //private RelativeLayout instagram;

    private RecyclerView main_recycler;
    private MainAdapter adapter;
    private LinearLayoutManager layoutManager;

    private boolean check,check2,check3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        check = false;
        check2 = false;
        check3 = false;


        initData();
        setClicks();
        RecyclerView.ItemAnimator animator = main_recycler.getItemAnimator();
        if (animator instanceof DefaultItemAnimator) {
            ((DefaultItemAnimator) animator).setSupportsChangeAnimations(false);
        }
        adapter = new MainAdapter(makeGenres(),this);
        main_recycler.setLayoutManager(layoutManager);
        main_recycler.setAdapter(adapter);


    }
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        adapter.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        adapter.onRestoreInstanceState(savedInstanceState);
    }

    private void initData(){
        main_recycler = findViewById(R.id.main_recycler);
        layoutManager = new LinearLayoutManager(this);
//        digital = findViewById(R.id.digital);
//        insta_last = findViewById(R.id.insta_last);
//        insta_first = findViewById(R.id.insta_first);
//        media_name = findViewById(R.id.media_name);
//        sms_expand = findViewById(R.id.sms_expand);
//        parent_title3 = findViewById(R.id.parent_title3);
//        sms_marketing = findViewById(R.id.sms_marketing);
//        photo_expand = findViewById(R.id.photo_expand);
//        parent_title2 = findViewById(R.id.parent_title2);
//        photo = findViewById(R.id.photo);
//        instagram = findViewById(R.id.instagram);
//        media_expand = findViewById(R.id.media_expand);
//        parent_title1 = findViewById(R.id.parent_title1);
        appBarLayout = findViewById(R.id.appBarLay);
//        main_scroller = findViewById(R.id.main_scroller);
        toolbar = findViewById(R.id.toolbarHome);
        back = findViewById(R.id.tb_back);
        title = findViewById(R.id.toolbar_home_title);
        packet = findViewById(R.id.packet_text);
        lang = findViewById(R.id.lang);
        date = findViewById(R.id.date);
        static_date = findViewById(R.id.static_date);
        setSupportActionBar(toolbar);
        Typeface avenir_book = Typeface.createFromAsset(getAssets(),"fonts/AvenirBook.ttf");
        Typeface avenir_light = Typeface.createFromAsset(getAssets(),"fonts/AvenirLight.ttf");
        Typeface avenir_black = Typeface.createFromAsset(getAssets(),"fonts/AvenirBlack.ttf");
        Typeface avenir_medium = Typeface.createFromAsset(getAssets(),"fonts/AvenirMedium.ttf");
        date.setTypeface(avenir_medium);
        static_date.setTypeface(avenir_light);
        title.setTypeface(avenir_light);
        packet.setTypeface(avenir_black);
        lang.setTypeface(avenir_light);
//        parent_title1.setTypeface(avenir_book);
//        parent_title2.setTypeface(avenir_book);
//        parent_title3.setTypeface(avenir_book);
    }

    private void setClicks(){
        //Button Clicks
//        digital.setOnClickListener(v -> {
//            if (check){
//                media_expand.setVisibility(View.GONE);
//                check = false;
//            }else {
//                media_expand.setVisibility(View.VISIBLE);
//                check = true;
//            }
//        });
//
//        photo.setOnClickListener(v -> {
//            if (check2){
//                photo_expand.setVisibility(View.GONE);
//                check2 = false;
//            }else {
//                photo_expand.setVisibility(View.VISIBLE);
//                check2 = true;
//            }
//        });
//        sms_marketing.setOnClickListener(v -> {
//            if (check3){
//                sms_expand.setVisibility(View.GONE);
//                check3 = false;
//            }else {
//                sms_expand.setVisibility(View.VISIBLE);
//                check3 = true;
//            }
//        });

        lang.setOnClickListener(v -> {
            PopupMenu p = new PopupMenu(HomeActivity.this,lang);
            p.getMenuInflater().inflate(R.menu.main_menu,p.getMenu());
            p.setOnMenuItemClickListener(item -> {
                Toast.makeText(getApplicationContext(),"s",Toast.LENGTH_SHORT).show();
                return true;
            });
            p.show();
        });
        back.setOnClickListener(v -> finish());
        date.setOnClickListener(v -> {
            PopupMenu p = new PopupMenu(HomeActivity.this,date);
            p.getMenuInflater().inflate(R.menu.date_menu,p.getMenu());
            p.setOnMenuItemClickListener(item -> {
                Toast.makeText(getApplicationContext(),"s",Toast.LENGTH_SHORT).show();
                return true;
            });
            p.show();
        });
    }

    private static List<TitleParent> makeGenres() {
        return Arrays.asList(makeDigitalMedia(),
                makePhotoVideo(),
                makeSMSMarketing(),
                makeAnimation(),
                makeInfluencer(),
                makeKiv());
    }
    public static TitleParent makeDigitalMedia() {
        return new TitleParent("DIGITAL MEDIA", makeDigitalChild(), true);
    }
    public static List<TitleChild> makeDigitalChild() {
        TitleChild instagram = new TitleChild("Instagram", 10,35);
        TitleChild facebook = new TitleChild("Facebook", 18,30);
        TitleChild twitter = new TitleChild("Twitter", 30,50);
        TitleChild linkedin = new TitleChild("Linkedin", 22,40);

        return Arrays.asList(instagram, facebook, twitter, linkedin);
    }
    public static TitleParent makePhotoVideo() {
        return new TitleParent("PHOTO & VIDEO", makePhotoVideoChild(), true);
    }
    public static List<TitleChild> makePhotoVideoChild() {
        TitleChild photo = new TitleChild("Photo", 10,35);
        TitleChild video = new TitleChild("Video", 18,30);

        return Arrays.asList(photo,video);
    }
    public static TitleParent makeSMSMarketing() {
        return new TitleParent("SMS MARKETING", makeSMSChild(), true);
    }
    public static List<TitleChild> makeSMSChild() {
        TitleChild photo = new TitleChild("SMS", 10,35);

        return Arrays.asList(photo);
    }
    public static TitleParent makeAnimation() {
        return new TitleParent("ANIMATION", new ArrayList<>(), false);
    }
    public static TitleParent makeInfluencer() {
        return new TitleParent("INFLUENCER", new ArrayList<>(), false);
    }
    public static TitleParent makeKiv() {
        return new TitleParent("KIV",new ArrayList<>(), false);
    }

    public static List<TitleChild> makeEmptyChild() {
        TitleChild photo = new TitleChild("", 10,35);

        return Arrays.asList(photo);
    }


}

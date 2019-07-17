package com.android.erp;

import android.graphics.Typeface;
import android.support.design.widget.AppBarLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.erp.Adapter.AllDataAdapter;
import com.android.erp.Models.AllDataModel;
import com.android.erp.Models.PagerModel;
import com.android.erp.Utils.CardPagerAdapter;
import com.android.erp.Utils.GeneralUtils;
import com.android.erp.Utils.ShadowTransformer;

import java.util.ArrayList;
import java.util.List;

public class CategoriesActivity extends AppCompatActivity {

    private AppBarLayout appBarLayout;
    private Toolbar toolbar;
    private ImageView back;
    private TextView title,packet,lang,date,static_date;

    private RecyclerView recyclerView;
    private AllDataAdapter allDataAdapter;
    private List<AllDataModel> list;
    private ViewPager viewPager;
    private CardPagerAdapter mCardAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories);
        initData();
        initViewPager();
        initRecycler();
    }

    private void initViewPager() {
        List<PagerModel> pagerModels = new ArrayList<>();
        pagerModels.add(new PagerModel("Twitter","https://images.vexels.com/media/users/3/137419/isolated/preview/b1a3fab214230557053ed1c4bf17b46c-twitter-icon-logo-by-vexels.png"));
        pagerModels.add(new PagerModel("Instagram","http://pluspng.com/img-png/instagram-png-instagram-png-logo-1455.png"));
        pagerModels.add(new PagerModel("Facebook","https://images.vexels.com/media/users/3/137253/isolated/preview/90dd9f12fdd1eefb8c8976903944c026-facebook-icon-logo-by-vexels.png"));
        pagerModels.add(new PagerModel("Linkedin","https://images.vexels.com/media/users/3/137382/isolated/preview/c59b2807ea44f0d70f41ca73c61d281d-linkedin-icon-logo-by-vexels.png"));
        mCardAdapter = new CardPagerAdapter();
        for (PagerModel mservice :pagerModels){
            mCardAdapter.addCardItem(mservice,getApplicationContext(), GeneralUtils.convertDpToPixel(2));
        }
        ShadowTransformer fragmentCardShadowTransformer = new ShadowTransformer(viewPager, mCardAdapter,this);
        fragmentCardShadowTransformer.enableScaling(true);
        viewPager.setAdapter(mCardAdapter);
        viewPager.setPageTransformer(false, fragmentCardShadowTransformer);
        viewPager.setOffscreenPageLimit(3);
        viewPager.setCurrentItem(1,true);
    }

    private void initData(){
        viewPager = findViewById(R.id.viewPager);
        recyclerView = findViewById(R.id.categories_recycler);
        appBarLayout = findViewById(R.id.appBarLayCategories);
        toolbar = findViewById(R.id.toolbarCategories);
        back = findViewById(R.id.tb_back_categories);
        title = findViewById(R.id.toolbar_categories_title);
        packet = findViewById(R.id.packet_text_categories);
        lang = findViewById(R.id.lang_categories);
        date = findViewById(R.id.date_categories);
        static_date = findViewById(R.id.static_date_categories);
        setSupportActionBar(toolbar);
        Typeface avenir_light = Typeface.createFromAsset(getAssets(),"fonts/AvenirLight.ttf");
        Typeface avenir_black = Typeface.createFromAsset(getAssets(),"fonts/AvenirBlack.ttf");
        Typeface avenir_medium = Typeface.createFromAsset(getAssets(),"fonts/AvenirMedium.ttf");
        date.setTypeface(avenir_medium);
        static_date.setTypeface(avenir_light);
        title.setTypeface(avenir_light);
        packet.setTypeface(avenir_black);
        lang.setTypeface(avenir_light);
    }

    private void initRecycler(){
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        list = new ArrayList<>();
        list.add(new AllDataModel("21/03/2019",true));
        list.add(new AllDataModel("22/03/2019",true));
        list.add(new AllDataModel("23/03/2019",false));
        list.add(new AllDataModel("24/03/2019",true));
        list.add(new AllDataModel("25/03/2019",true));
        list.add(new AllDataModel("26/03/2019",false));
        list.add(new AllDataModel("27/03/2019",true));
        list.add(new AllDataModel("28/03/2019",true));
        list.add(new AllDataModel("29/03/2019",false));
        list.add(new AllDataModel("30/03/2019",false));
        list.add(new AllDataModel("31/03/2019",true));
        list.add(new AllDataModel("01/04/2019",true));
        list.add(new AllDataModel("02/04/2019",false));
        allDataAdapter = new AllDataAdapter(this,list);
        recyclerView.setAdapter(allDataAdapter);
    }
}

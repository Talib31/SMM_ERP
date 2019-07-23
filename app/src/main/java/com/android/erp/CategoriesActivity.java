package com.android.erp;

import android.animation.ArgbEvaluator;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.design.widget.AppBarLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.erp.Adapter.AllDataAdapter;
import com.android.erp.Models.AllDataModel;
import com.android.erp.Models.PagerModel;
import com.android.erp.Network.ApiService;
import com.android.erp.Network.Response.CategoriesResponse;
import com.android.erp.Network.Response.HomeResponse;
import com.android.erp.Network.RetrofitClient;
import com.android.erp.Utils.CardPagerAdapter;
import com.android.erp.Utils.GeneralUtils;
import com.android.erp.Utils.ShadowTransformer;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class CategoriesActivity extends AppCompatActivity {

    private AppBarLayout appBarLayout;
    private Toolbar toolbar;
    private ImageButton back;
    private TextView title,packet,lang,date,static_date;

    private RecyclerView recyclerView;
    private AllDataAdapter allDataAdapter;
    private List<CategoriesResponse> list;
    private ViewPager viewPager;
    private CardPagerAdapter mCardAdapter;
    private ArgbEvaluator argbEvaluator = new ArgbEvaluator();
    private ProgressBar categoriesProgress;

    private String month,year,day;

    private Calendar now;

    private String userId,categoryId;

    private Disposable disposable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories);
        Intent intent = getIntent();
         userId = intent.getStringExtra("userId");
         categoryId = intent.getStringExtra("categoryId");
        now = Calendar.getInstance();
        day = String.valueOf(now.get(Calendar.DATE));

        year =  String.valueOf(now.get(Calendar.YEAR));

        month = String.valueOf(now.get(Calendar.MONTH) + 1);
        initData();
        setClicks();
        initViewPager();
        fetchData(categoryId);

    }

    private void initViewPager() {
        ArrayList<String> list = new ArrayList<>();
        ArrayList<String> lists = new ArrayList<>();
        list = (ArrayList<String>)getIntent().getSerializableExtra("myList");
        lists = (ArrayList<String>)getIntent().getSerializableExtra("myLists");
        List<PagerModel> pagerModels = new ArrayList<>();
        for (int i = 0;i<list.size();i++){
            pagerModels.add(new PagerModel(list.get(i),lists.get(i)));
        }
//        pagerModels.add(new PagerModel("Twitter","https://images.vexels.com/media/users/3/137419/isolated/preview/b1a3fab214230557053ed1c4bf17b46c-twitter-icon-logo-by-vexels.png"));
//        pagerModels.add(new PagerModel("Instagram","http://pluspng.com/img-png/instagram-png-instagram-png-logo-1455.png"));
//        pagerModels.add(new PagerModel("Facebook","https://images.vexels.com/media/users/3/137253/isolated/preview/90dd9f12fdd1eefb8c8976903944c026-facebook-icon-logo-by-vexels.png"));
//        pagerModels.add(new PagerModel("Linkedin","https://images.vexels.com/media/users/3/137382/isolated/preview/c59b2807ea44f0d70f41ca73c61d281d-linkedin-icon-logo-by-vexels.png"));

        int currentItem=Integer.parseInt(categoryId);

        if (currentItem<5){

            if (currentItem==3)
                currentItem=0;

        }

        else {
            if (currentItem<8)
                currentItem-=6;
            else
                currentItem-=9;
        }
        mCardAdapter = new CardPagerAdapter(currentItem);
        for (PagerModel mservice :pagerModels){
            mCardAdapter.addCardItem(mservice,getApplicationContext(), GeneralUtils.convertDpToPixel(2));
        }
        ShadowTransformer fragmentCardShadowTransformer = new ShadowTransformer(viewPager, mCardAdapter,this);
        fragmentCardShadowTransformer.enableScaling(true);
        viewPager.setAdapter(mCardAdapter);
        viewPager.setPageTransformer(true, fragmentCardShadowTransformer);
        viewPager.setOffscreenPageLimit(4);




        viewPager.setCurrentItem(currentItem,true);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {

                if (categoryId.equals("1") || categoryId.equals("2") || categoryId.equals("4") || categoryId.equals("3")){
                    categoriesProgress.setVisibility(View.VISIBLE);
                    recyclerView.setVisibility(View.INVISIBLE);


                    /*if (categoryId.equals("1")){
                        fetchData("3");
                    }else if (categoryId.equals("2")){
                        fetchData("1");
                    }*/


                    switch (i){


                        case 0:
                            fetchData("3");
                            break;
                        case 1:
                            fetchData("1");
                            break;
                        case 2:
                            fetchData("2");
                            break;
                        case 3:
                            fetchData("4");
                            break;
                    }

                }


                else if (categoryId.equals("6") || categoryId.equals("7")){
                    categoriesProgress.setVisibility(View.VISIBLE);
                    recyclerView.setVisibility(View.INVISIBLE);
                   /* if (categoryId.equals("6")){
                        fetchData("6");
                    } else if (categoryId.equals("7")) {
                        fetchData("7");
                    }*/

                    switch (i){


                        case 0:
                            fetchData("6");
                            break;
                        case 1:
                            fetchData("7");
                            break;

                    }
                }


                else if (categoryId.equals("9")){
                   categoriesProgress.setVisibility(View.VISIBLE);
                    recyclerView.setVisibility(View.INVISIBLE);
                    fetchData("9");
                }
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
    }
    private String capitalize(String word){
        return word.substring(0,1).toUpperCase() + word.substring(1);
    }
    private void fetchData(String categoryId) {
        ApiService service = new RetrofitClient().create();
        Observable<List<CategoriesResponse>> get = null;

        get = service.getPosts(userId,categoryId,month,year);

        disposable = get
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnError(error -> GeneralUtils.largeLog("doOnErrorEventsFragmentCall", error.getMessage()))
                .doOnComplete(() -> {
            categoriesProgress.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
            allDataAdapter.notifyDataSetChanged();

        })
                .subscribe(this::initRecycler,
                        Throwable::getMessage);

    }

    private void setClicks(){
        lang.setOnClickListener(v -> {
            PopupMenu p = new PopupMenu(this,lang);
            p.getMenuInflater().inflate(R.menu.main_menu,p.getMenu());
            p.setOnMenuItemClickListener(item -> {
                return true;
            });
            p.show();
        });
        back.setOnClickListener(v -> finish());
        date.setOnClickListener(v -> {
            PopupMenu p = new PopupMenu(this,date);
            p.getMenuInflater().inflate(R.menu.date_menu,p.getMenu());
            p.setOnMenuItemClickListener(item -> {
                date.setText(item.getTitle());
                return true;
            });
            p.show();
        });
    }

    private void initData(){
        viewPager = findViewById(R.id.viewPager);
        categoriesProgress = findViewById(R.id.categoriesProgress);
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



    private void initRecycler(List<CategoriesResponse> response){
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        list = new ArrayList<>();
        list = response;
//        for (int i =0;i<response.size();i++){
//            boolean check = false;
//            if (response.get(i).getChecking().equals("0")){
//                check =false;
//            }else {
//                check = true;
//            }
//            list.add(new AllDataModel(response.get(i).getDate(),check));
//        }
        allDataAdapter = new AllDataAdapter(this,list);
        recyclerView.setAdapter(allDataAdapter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (disposable != null)
            disposable.dispose();
    }

}

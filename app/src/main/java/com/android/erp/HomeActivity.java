package com.android.erp;

import android.content.Intent;
import android.content.SharedPreferences;
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
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.erp.Adapter.MainAdapter;
import com.android.erp.Models.TitleChild;
import com.android.erp.Models.TitleParent;
import com.android.erp.Network.ApiService;
import com.android.erp.Network.Response.HomeResponse;
import com.android.erp.Network.Response.LoginResponse;
import com.android.erp.Network.RetrofitClient;
import com.android.erp.Utils.GeneralUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;


public class HomeActivity extends AppCompatActivity {

    private AppBarLayout appBarLayout;
    private Toolbar toolbar;
    private ImageView back;
    private TextView title,packet,lang,date,static_date;
    private View line;

    private RecyclerView main_recycler;
    private MainAdapter adapter;
    private LinearLayoutManager layoutManager;
    private ProgressBar progressBar;
    private String userId;

    private Disposable disposable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        userId = getIntent().getStringExtra("userId");
        initData();
        invisible();
        fetchData();
        setClicks();
        RecyclerView.ItemAnimator animator = main_recycler.getItemAnimator();
        if (animator instanceof DefaultItemAnimator) {
            ((DefaultItemAnimator) animator).setSupportsChangeAnimations(false);
        }
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
        line = findViewById(R.id.lineView);
        progressBar = findViewById(R.id.homeProgress);
        main_recycler = findViewById(R.id.main_recycler);
        layoutManager = new LinearLayoutManager(this);
        appBarLayout = findViewById(R.id.appBarLay);
        toolbar = findViewById(R.id.toolbarHome);
        back = findViewById(R.id.tb_back);
        title = findViewById(R.id.toolbar_home_title);
        packet = findViewById(R.id.packet_text);
        lang = findViewById(R.id.lang);
        date = findViewById(R.id.date);
        static_date = findViewById(R.id.static_date);
        setSupportActionBar(toolbar);
        //Typeface avenir_book = Typeface.createFromAsset(getAssets(),"fonts/AvenirBook.ttf");
        Typeface avenir_light = Typeface.createFromAsset(getAssets(),"fonts/AvenirLight.ttf");
        Typeface avenir_black = Typeface.createFromAsset(getAssets(),"fonts/AvenirBlack.ttf");
        Typeface avenir_medium = Typeface.createFromAsset(getAssets(),"fonts/AvenirMedium.ttf");
        date.setTypeface(avenir_medium);
        static_date.setTypeface(avenir_light);
        title.setTypeface(avenir_light);
        packet.setTypeface(avenir_black);
        lang.setTypeface(avenir_light);
    }

    private void fetchData() {
        ApiService service = new RetrofitClient().create();
        Observable<HomeResponse> get = null;

        get = service.getMain(userId);
        disposable = get
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnError(error -> GeneralUtils.largeLog("doOnErrorEventsFragmentCall", error.getMessage()))
                .doOnComplete(this::visible)
                .subscribe(event -> {
                            addData(event);
                        },
                        Throwable::getMessage);

    }

    private void addData(HomeResponse event) {
        int insta1 = Integer.parseInt(event.getNumbers().getNbInstagramChecked());
        int insta2 = Integer.parseInt(event.getNumbers().getNbInstagramPosts());
        int fb1 = Integer.parseInt(event.getNumbers().getNbFacebookChecked());
        int fb2 = Integer.parseInt(event.getNumbers().getNbFacebookPosts());
        int twitter1 = Integer.parseInt(event.getNumbers().getNbTwitterChecked());
        int twitter2 = Integer.parseInt(event.getNumbers().getNbTwitterPosts());
        int linkedin1 = Integer.parseInt(event.getNumbers().getNbLinkedinChecked());
        int linkedin2 = Integer.parseInt(event.getNumbers().getNbLinkedinPosts());
        int photo1 = Integer.parseInt(event.getNumbers().getNbPhotoChecked());
        int photo2 = Integer.parseInt(event.getNumbers().getNbPhotoPosts());
        int video1 = Integer.parseInt(event.getNumbers().getNbVideoChecked());
        int video2 = Integer.parseInt(event.getNumbers().getNbVideoPosts());
        int sms1 = Integer.parseInt(event.getNumbers().getNbSMSChecked());
        int sms2 = Integer.parseInt(event.getNumbers().getNbSMSPosts());

        adapter = new MainAdapter(makeGenres(insta1,insta2,fb1,fb2,twitter1,twitter2,linkedin1,linkedin2,photo1,photo2,
                video1,video2,sms1,sms2),this);
        main_recycler.setLayoutManager(layoutManager);
        main_recycler.setAdapter(adapter);
    }

    private void setClicks(){
        lang.setOnClickListener(v -> {
            PopupMenu p = new PopupMenu(HomeActivity.this,lang);

            p.getMenuInflater().inflate(R.menu.main_menu,p.getMenu());
            p.setOnMenuItemClickListener(item -> {
                Toast.makeText(getApplicationContext(),"s",Toast.LENGTH_SHORT).show();
                return true;
            });
            p.show();
        });
        back.setOnClickListener(view -> {
            SharedPreferences.Editor editor = getSharedPreferences("USER", MODE_PRIVATE).edit();
            editor.putString("userId", "");
            editor.putBoolean("check",false);
            editor.putBoolean("isAdmin",false);
            editor.apply();
            Intent intent = new Intent(HomeActivity.this,LoginActivity.class);
            startActivity(intent);
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            finish();
        });
        date.setOnClickListener(v -> {
            PopupMenu p = new PopupMenu(HomeActivity.this,date);
            p.getMenuInflater().inflate(R.menu.date_menu,p.getMenu());
            p.setOnMenuItemClickListener(item -> {

                return true;
            });
            p.show();
        });
    }

    private void visible(){
        packet.setVisibility(View.VISIBLE);
        static_date.setVisibility(View.VISIBLE);
        date.setVisibility(View.VISIBLE);
        line.setVisibility(View.VISIBLE);
        main_recycler.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.INVISIBLE);
    }
    private void invisible(){
        packet.setVisibility(View.INVISIBLE);
        static_date.setVisibility(View.INVISIBLE);
        date.setVisibility(View.INVISIBLE);
        line.setVisibility(View.INVISIBLE);
        main_recycler.setVisibility(View.INVISIBLE);
        progressBar.setVisibility(View.VISIBLE);
    }

    private static List<TitleParent> makeGenres(int insta1,int insta2,int fb1,int fb2,int twitter1,int twitter2,
                                                int linkedin1,int linkedin2,int photo1,int photo2,int video1,int video2,
                                                int sms1,int sms2) {
        return Arrays.asList(makeDigitalMedia(insta1,insta2,fb1,fb2,twitter1,twitter2,linkedin1,linkedin2),
                makePhotoVideo(photo1,photo2,video1,video2),
                makeSMSMarketing(sms1,sms2),
                makeAnimation(),
                makeInfluencer(),
                makeKiv());
    }
    public static TitleParent makeDigitalMedia(int insta1,int insta2,int fb1,int fb2,int twitter1,int twitter2,
                                               int linkedin1,int linkedin2) {
        return new TitleParent("DIGITAL MEDIA", makeDigitalChild(insta1,insta2,fb1,fb2,twitter1,twitter2,linkedin1,linkedin2), true);
    }
    public static List<TitleChild> makeDigitalChild(int insta1,int insta2,int fb1,int fb2,int twitter1,int twitter2,
                                                    int linkedin1,int linkedin2) {
        TitleChild instagram = new TitleChild("Instagram", insta1,insta2);
        TitleChild facebook = new TitleChild("Facebook", fb1,fb2);
        TitleChild twitter = new TitleChild("Twitter", twitter1,twitter2);
        TitleChild linkedin = new TitleChild("Linkedin", linkedin1,linkedin2);


        return Arrays.asList(instagram, facebook, twitter, linkedin);
    }
    public static TitleParent makePhotoVideo(int photo1,int photo2,int video1,int video2) {
        return new TitleParent("PHOTO & VIDEO", makePhotoVideoChild(photo1,photo2,video1,video2), true);
    }
    public static List<TitleChild> makePhotoVideoChild(int photo1,int photo2,int video1,int video2) {
        TitleChild photo = new TitleChild("Photo",photo1 ,photo2);
        TitleChild video = new TitleChild("Video", video1,video2);

        return Arrays.asList(photo,video);
    }
    public static TitleParent makeSMSMarketing(int sms1,int sms2) {
        return new TitleParent("SMS MARKETING", makeSMSChild(sms1,sms2), true);
    }
    public static List<TitleChild> makeSMSChild(int sms1,int sms2) {
        TitleChild photo = new TitleChild("SMS", sms1,sms2);

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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (disposable != null)
            disposable.dispose();
    }

}

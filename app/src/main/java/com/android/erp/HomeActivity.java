package com.android.erp;


import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import java.lang.reflect.Field;
import java.util.Calendar;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageButton;
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

    static final int DATE_DIALOG_ID = 1;
    private AppBarLayout appBarLayout;
    private Toolbar toolbar;
    private ImageButton back;
    private TextView title,packet,lang,date,static_date;
    private View line;

    private RecyclerView main_recycler;
    private MainAdapter adapter;
    private LinearLayoutManager layoutManager;
    private ProgressBar progressBar;
    private String userId;

    private Disposable disposable;
    private AlertDialog exitDialog;

    private String month,year,day;
    private int mMonth,mYear,mDay;
    Calendar now;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        userId = getIntent().getStringExtra("userId");
        initData();
        invisible();
        now = Calendar.getInstance();
        day = String.valueOf(now.get(Calendar.DATE));

        year =  String.valueOf(now.get(Calendar.YEAR));

        month = String.valueOf(now.get(Calendar.MONTH) + 1);
        fetchData(month,year);
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

    private void fetchData(String month,String year) {
        ApiService service = new RetrofitClient().create();
        Observable<HomeResponse> get = null;

        get = service.getMain(userId,month,year);
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
        if (event.getResult().equals("fail")){
            invisible();
            progressBar.setVisibility(View.VISIBLE);
            main_recycler.setVisibility(View.INVISIBLE);
            fetchData(month,year);
        }else {
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
            String digitalName = event.getPaketContents().get(0).getName();
            String digital_name_check = event.getPaketContents().get(0).getCheck();
            boolean digital_check = false;
            if (digital_name_check.equals("1")){
                digital_check = true;
            }else {
                digital_check = false;
            }
            String photoName = event.getPaketContents().get(1).getName();
            String photo_name_check = event.getPaketContents().get(1).getCheck();
            boolean photo_check;
            if (photo_name_check.equals("1")){
                photo_check = true;
            }else {
                photo_check = false;
            }
            String smsName = event.getPaketContents().get(2).getName();
            String sms_name_check = event.getPaketContents().get(2).getCheck();
            boolean sms_check;
            if (sms_name_check.equals("1")){
                sms_check = true;
            }else {
                sms_check = false;
            }
            String animName = event.getPaketContents().get(3).getName();
            String anim_name_check = event.getPaketContents().get(3).getCheck();
            boolean anim_check;
            if (anim_name_check.equals("1")){
                anim_check = true;
            }else {
                anim_check = false;
            }
            String infName = event.getPaketContents().get(4).getName();
            String inf_name_check = event.getPaketContents().get(4).getCheck();
            boolean inf_check;
            if (inf_name_check.equals("1")){
                inf_check = true;
            }else {
                inf_check = false;
            }
            String kivName = event.getPaketContents().get(5).getName();
            String kiv_name_check = event.getPaketContents().get(5).getCheck();
            boolean kiv_check;
            if (kiv_name_check.equals("1")){
                kiv_check = true;
            }else {
                kiv_check = false;
            }

            adapter = new MainAdapter(makeGenres(digitalName,digital_check,photoName,photo_check,smsName,sms_check,
                    animName,anim_check,infName,inf_check,kivName,kiv_check,
                    insta1, insta2, fb1, fb2, twitter1, twitter2,
                    linkedin1, linkedin2, photo1, photo2,
                    video1, video2, sms1, sms2), this);
            main_recycler.setLayoutManager(layoutManager);
            main_recycler.setAdapter(adapter);
            packet.setText(capitalize(event.getPaketName().getName()));
        }
    }

    private String capitalize(String word){
        return word.substring(0,1).toUpperCase() + word.substring(1);
    }


    private static List<TitleParent> makeGenres(String dig_n,boolean dig_b,String p_n,boolean p_b,
                                                String s_n,boolean s_b,String a_n,boolean a_b,
                                                String i_n,boolean i_b,String k_n,boolean k_b,
                                                int insta1,int insta2,int fb1,int fb2,int twitter1,int twitter2,
                                                int linkedin1,int linkedin2,int photo1,int photo2,int video1,int video2,
                                                int sms1,int sms2) {
        return Arrays.asList(makeDigitalMedia(dig_n,dig_b,insta1,insta2,fb1,fb2,twitter1,twitter2,linkedin1,linkedin2),
                makePhotoVideo(p_n,p_b,photo1,photo2,video1,video2),
                makeSMSMarketing(s_n,s_b,sms1,sms2),
                makeAnimation(a_n,a_b),
                makeInfluencer(i_n,i_b),
                makeKiv(k_n,k_b));
    }
    public static TitleParent makeDigitalMedia(String name,boolean active,int insta1,int insta2,int fb1,int fb2,int twitter1,int twitter2,
                                               int linkedin1,int linkedin2) {
        return new TitleParent(name, makeDigitalChild(insta1,insta2,fb1,fb2,twitter1,twitter2,linkedin1,linkedin2), active);
    }
    public static List<TitleChild> makeDigitalChild(int insta1,int insta2,int fb1,int fb2,int twitter1,int twitter2,
                                                    int linkedin1,int linkedin2) {
        TitleChild instagram = new TitleChild("Instagram", insta1,insta2);
        TitleChild facebook = new TitleChild("Facebook", fb1,fb2);
        TitleChild twitter = new TitleChild("Twitter", twitter1,twitter2);
        TitleChild linkedin = new TitleChild("Linkedin", linkedin1,linkedin2);


        return Arrays.asList(instagram, facebook, twitter, linkedin);
    }
    public static TitleParent makePhotoVideo(String name,boolean active,int photo1,int photo2,int video1,int video2) {
        return new TitleParent(name, makePhotoVideoChild(photo1,photo2,video1,video2), active);
    }
    public static List<TitleChild> makePhotoVideoChild(int photo1,int photo2,int video1,int video2) {
        TitleChild photo = new TitleChild("Photo",photo1 ,photo2);
        TitleChild video = new TitleChild("Video", video1,video2);

        return Arrays.asList(photo,video);
    }
    public static TitleParent makeSMSMarketing(String name,boolean active,int sms1,int sms2) {
        return new TitleParent(name, makeSMSChild(sms1,sms2), active);
    }
    public static List<TitleChild> makeSMSChild(int sms1,int sms2) {
        TitleChild photo = new TitleChild("SMS", sms1,sms2);

        return Arrays.asList(photo);
    }
    public static TitleParent makeAnimation(String name,boolean active) {
        return new TitleParent(name, new ArrayList<>(), active);
    }
    public static TitleParent makeInfluencer(String name,boolean active) {
        return new TitleParent(name, new ArrayList<>(), active);
    }
    public static TitleParent makeKiv(String name,boolean active) {
        return new TitleParent(name,new ArrayList<>(), active);
    }

    public static List<TitleChild> makeEmptyChild() {
        TitleChild photo = new TitleChild("", 10,35);

        return Arrays.asList(photo);
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
            boolean isAdmin = getSharedPreferences("USER", MODE_PRIVATE).getBoolean("isAdmin", false);
            if (isAdmin) {
                finish();
            }
            else {

                AlertDialog.Builder exitDialogBuilder=new AlertDialog.Builder(this);
                exitDialogBuilder.setTitle(R.string.exitDialogTitle);
                exitDialogBuilder.setMessage(R.string.exitDialogMessage);
                exitDialogBuilder.setIcon(R.drawable.ic_exit);



                exitDialogBuilder.setNegativeButton(R.string.exitDialogNegativeBtnTxt, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        exitDialog.dismiss();
                    }
                });

                exitDialogBuilder.setPositiveButton(R.string.exitDialogPositiveBtnTxt, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        SharedPreferences.Editor editor = getSharedPreferences("USER", MODE_PRIVATE).edit();
                        editor.putString("userId", "");
                        editor.putBoolean("check", false);
                        editor.putBoolean("isAdmin", false);
                        editor.apply();
                        Intent intent = new Intent(HomeActivity.this, LoginActivity.class);
                        startActivity(intent);
                        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                        finish();
                    }
                });
                exitDialog=exitDialogBuilder.create();
                exitDialog.show();

        }
        });
        date.setOnClickListener(v -> {
            showDialog(DATE_DIALOG_ID);
            //calendarShow();
//            PopupMenu p = new PopupMenu(HomeActivity.this,date);
//            p.getMenuInflater().inflate(R.menu.date_menu,p.getMenu());
//            p.setOnMenuItemClickListener(item -> {
//                date.setText(item.getTitle());
//                return true;
//            });
//            p.show();
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (disposable != null)
            disposable.dispose();
    }

    private DatePickerDialog createDialogWithoutDateField() {
        DatePickerDialog dpd = new DatePickerDialog(this, null, now.get(Calendar.YEAR), (now.get(Calendar.MONTH) + 1), now.get(Calendar.DATE));
        try {
            java.lang.reflect.Field[] datePickerDialogFields = dpd.getClass().getDeclaredFields();
            for (java.lang.reflect.Field datePickerDialogField : datePickerDialogFields) {
                if (datePickerDialogField.getName().equals("mDatePicker")) {
                    datePickerDialogField.setAccessible(true);
                    DatePicker datePicker = (DatePicker) datePickerDialogField.get(dpd);
                    java.lang.reflect.Field[] datePickerFields = datePickerDialogField.getType().getDeclaredFields();
                    for (java.lang.reflect.Field datePickerField : datePickerFields) {
                        Log.i("test", datePickerField.getName());
                        if ("mDaySpinner".equals(datePickerField.getName())) {
                            datePickerField.setAccessible(true);
                            Object dayPicker = datePickerField.get(datePicker);
                            ((View) dayPicker).setVisibility(View.GONE);
                        }
                    }
                }
            }
        }
        catch (Exception ex) {
        }
        return dpd;
    }
    private void calendarShow() {
        final Calendar c = Calendar.getInstance();

        int y = c.get(Calendar.YEAR) + 4;
        int m = c.get(Calendar.MONTH) - 2;
        int d = c.get(Calendar.DAY_OF_MONTH);
        final String[] MONTH = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};

        DatePickerDialog dp = new DatePickerDialog(HomeActivity.this,
                (view, year, monthOfYear, dayOfMonth) -> {
                    String erg = "";
                    erg = String.valueOf(dayOfMonth);
                    erg += "." + String.valueOf(monthOfYear + 1);
                    erg += "." + year;
                    date.setText(erg);

                }, y, m, d);
        dp.setTitle("Calender");
        dp.setMessage("Select Your Graduation date Please?");

        dp.show();


    }
    OnDateSetListener mDateSetListner = new OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {

            mYear = year;
            mMonth = monthOfYear;
            mDay = dayOfMonth;
            updateDate();
        }
    };

    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case DATE_DIALOG_ID:
                /*
                 * return new DatePickerDialog(this, mDateSetListner, mYear, mMonth,
                 * mDay);
                 */
                DatePickerDialog datePickerDialog = this.customDatePicker();
                return datePickerDialog;
        }
        return null;
    }

    protected void updateDate() {
        int localMonth = (mMonth + 1);
        String monthString = localMonth < 10 ? "0" + localMonth : Integer
                .toString(localMonth);
        String localYear = Integer.toString(mYear).substring(2);
        date.setText(new StringBuilder()
                // Month is 0 based so add 1
                .append(monthString).append("/").append(localYear).append(" "));
        showDialog(R.id.congDone);
    }

    private DatePickerDialog customDatePicker() {
        DatePickerDialog dpd = new DatePickerDialog(this, mDateSetListner,
                mYear, mMonth, mDay);
        try {

            Field[] datePickerDialogFields = dpd.getClass().getDeclaredFields();
            for (Field datePickerDialogField : datePickerDialogFields) {
                if (datePickerDialogField.getName().equals("mDatePicker")) {
                    datePickerDialogField.setAccessible(true);
                    DatePicker datePicker = (DatePicker) datePickerDialogField
                            .get(dpd);
                    Field datePickerFields[] = datePickerDialogField.getType()
                            .getDeclaredFields();
                    for (Field datePickerField : datePickerFields) {
                        if ("mDayPicker".equals(datePickerField.getName())
                                || "mDaySpinner".equals(datePickerField
                                .getName())) {
                            datePickerField.setAccessible(true);
                            Object dayPicker = new Object();
                            dayPicker = datePickerField.get(datePicker);
                            ((View) dayPicker).setVisibility(View.GONE);
                        }
                    }
                }

            }
        } catch (Exception ex) {
        }
        return dpd;
    }
}



package com.android.erp;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.erp.Network.ApiService;
import com.android.erp.Network.Response.LoginResponse;
import com.android.erp.Network.Response.ResultResponse;
import com.android.erp.Network.RetrofitClient;
import com.android.erp.Utils.GeneralUtils;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class PostDetailsActivity extends AppCompatActivity {
    private RelativeLayout mainContainer;
    private AppBarLayout appBarLayout;
    private Toolbar toolbar;
    private ImageButton back;
    private TextView title,lang,all_name_details,isDoneDetails,doneConfirm,undDoneConfirm;
    private String date,isdone="",movzu,text,price,userId,categoryId,postId;
    private CheckBox doneBox,undoneBox;
    private AppCompatEditText date_editText,movzu_editText,metn_editText,reklam_editText;
    private Button confirm;
    private Dialog myDialog;

    private Disposable disposable;
    private PopupMenu p;

    private boolean checkAz,checkEn,checkRu;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_details);
        checkAz = getIntent().getBooleanExtra("checkAz",false);
        checkEn = getIntent().getBooleanExtra("checkEn",false);
        checkRu = getIntent().getBooleanExtra("checkRu",false);
        userId = getIntent().getStringExtra("userId");
        categoryId = getIntent().getStringExtra("categoryId");
        postId = getIntent().getStringExtra("postId");
        date = getIntent().getStringExtra("date");
        isdone = getIntent().getStringExtra("isdone");
        movzu = getIntent().getStringExtra("title");
        text = getIntent().getStringExtra("text");
        price = getIntent().getStringExtra("price");
        initData();
        setClicks();
        SharedPreferences editor = getSharedPreferences("USER", MODE_PRIVATE);
        boolean isAdmin=editor.getBoolean("isAdmin",false);
        setEditable(isAdmin);//If it is admin account can change texts of EditTextx,otherwise disable them
    }

    private void setEditable(boolean isAdmin) {
        //Hide Keyboard if it is not Admin Account
        mainContainer.setFocusable(!isAdmin);
        mainContainer.setFocusableInTouchMode(!isAdmin);

        title.setFocusable(isAdmin);

        date_editText.setEnabled(isAdmin);
        movzu_editText.setEnabled(isAdmin);
        metn_editText.setEnabled(isAdmin);
        reklam_editText.setEnabled(isAdmin);
        doneBox.setEnabled(isAdmin);
        undoneBox.setEnabled(isAdmin);
        if (isAdmin){
            confirm.setVisibility(View.VISIBLE);
            doneBox.setVisibility(View.VISIBLE);
            undoneBox.setVisibility(View.VISIBLE);
            doneConfirm.setVisibility(View.VISIBLE);
            undDoneConfirm.setVisibility(View.VISIBLE);
        }else {
            confirm.setVisibility(View.INVISIBLE);
            doneBox.setVisibility(View.INVISIBLE);
            undoneBox.setVisibility(View.INVISIBLE);
            doneConfirm.setVisibility(View.INVISIBLE);
            undDoneConfirm.setVisibility(View.INVISIBLE);
        }
    }


    private void initData() {
        Typeface avenir_light = Typeface.createFromAsset(getAssets(),"fonts/AvenirLight.ttf");
        mainContainer=findViewById(R.id.mainContainerPostDetails);
        doneConfirm = findViewById(R.id.done_confirm);
        undDoneConfirm = findViewById(R.id.undone_confirm);
        confirm = findViewById(R.id.btnConfirm);
        doneBox = findViewById(R.id.doneBox);
        date_editText = findViewById(R.id.date_editText);
        movzu_editText = findViewById(R.id.movzu_editText);
        metn_editText = findViewById(R.id.metn_editText);
        reklam_editText = findViewById(R.id.reklam_editText);
        undoneBox = findViewById(R.id.undone_checkbox);
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

        if (isdone!=null&&isdone.equals("done")){
            isDoneDetails.setText(R.string.done);
            isDoneDetails.setTextColor(getResources().getColor(R.color.trueColor));
            doneBox.setChecked(true);
        }else {
            isDoneDetails.setText(R.string.undone);
            isDoneDetails.setTextColor(getResources().getColor(R.color.falseColor));
            undoneBox.setChecked(true);
        }
        date_editText.setText(date);
        movzu_editText.setText(movzu);
        metn_editText.setText(text);
        reklam_editText.setText(price);
        all_name_details.setText(date);
        all_name_details.setTypeface(avenir_light);
    }
    private void setClicks(){
        p = new PopupMenu(PostDetailsActivity.this,lang);
        p.getMenuInflater().inflate(R.menu.main_menu,p.getMenu());

        MenuItem az = p.getMenu().findItem(R.id.az);
        MenuItem en = p.getMenu().findItem(R.id.en);
        MenuItem ru = p.getMenu().findItem(R.id.ru);
        az.setVisible(false);
        en.setVisible(false);
        ru.setVisible(false);
        SharedPreferences prefs = getSharedPreferences("LANG", MODE_PRIVATE);

        String langs = prefs.getString("lang", "");
        if (checkAz){
            if (langs.equals("AZ")){
                az.setVisible(false);
            }else {
                az.setVisible(true);
            }
        }
        if (checkEn){
            if (langs.equals("EN")){
                en.setVisible(false);
            }else {
                en.setVisible(true);
            }
        }
        if (checkRu){
            if (langs.equals("RU")){
                ru.setVisible(false);
            }else {
                ru.setVisible(true);
            }
        }
        lang.setText(langs.toUpperCase());
        doneBox.setOnClickListener(v -> {
            if (doneBox.isChecked()){
                undoneBox.setChecked(false);
            }else {
                undoneBox.setChecked(true);
            }
        });
        undoneBox.setOnClickListener(v -> {
            if (undoneBox.isChecked()){
                doneBox.setChecked(false);
            }else {
                doneBox.setChecked(true);
            }
        });
        lang.setOnClickListener(v -> {
            p.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem menuItem) {
                    SharedPreferences.Editor editor = getSharedPreferences("LANG", MODE_PRIVATE).edit();
                    editor.putString("lang",String.valueOf(menuItem.getTitle()));
                    editor.apply();
                    SharedPreferences prefs = getSharedPreferences("LANG", MODE_PRIVATE);
                    String langss = prefs.getString("lang", "");
                    lang.setText(langss.toUpperCase());
                    if (langss.equals("AZ")){
                        az.setVisible(false);
                        if (checkEn){
                            en.setVisible(true);
                        }
                        if (checkRu){
                            ru.setVisible(true);
                        }
                    }else if (langss.equals("EN")){
                        en.setVisible(false);
                        if (checkAz){
                            az.setVisible(true);
                        }
                        if (checkRu){
                            ru.setVisible(true);
                        }
                    }else if (langss.equals("RU")){
                        ru.setVisible(false);
                        if (checkAz){
                            az.setVisible(true);
                        }
                        if (checkEn){
                            en.setVisible(true);
                        }
                    }
                    return true;
                }
            });
            p.show();
        });
        back.setOnClickListener(v -> finish());
        myDialog = new Dialog(PostDetailsActivity.this);
        myDialog.setContentView(R.layout.confirm_layout);
        confirm.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                ProgressBar loa = myDialog.findViewById(R.id.addPostProgress);
                ImageView question = myDialog.findViewById(R.id.question);
                TextView lorem = myDialog.findViewById(R.id.lorem);
                TextView mQusetion = myDialog.findViewById(R.id.main_question);
                Button buttonLegv = myDialog.findViewById(R.id.btnLegv);
                Button buttonTesdiq = myDialog.findViewById(R.id.btnTesdqi);
                buttonLegv.setOnClickListener(v1 -> myDialog.dismiss());
                buttonTesdiq.setOnClickListener(v12 -> {
                    loa.setVisibility(View.VISIBLE);
                    buttonLegv.setVisibility(View.INVISIBLE);
                    buttonTesdiq.setVisibility(View.INVISIBLE);
                    fetchData();
                });

                myDialog.show();
            }
        });
    }
    private void fetchData() {
        ApiService service = new RetrofitClient().create();
        Observable<ResultResponse> result = null;
        String checking = "0";
        boolean check =false;
        if (doneBox.isChecked() && !undoneBox.isChecked()){
            check = true;
            checking = "1";
        }else {
            if (!doneBox.isChecked() && undoneBox.isChecked()){
                check = false;
                checking = "0";
            }
        }

        result = service.add(userId,categoryId,movzu_editText.getText().toString(),metn_editText.getText().toString(),reklam_editText.getText().toString(),
                date_editText.getText().toString(),checking);
        disposable = result
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnError(error -> GeneralUtils.largeLog("doOnErrorEventsFragmentCall", error.getMessage()))
                .doOnComplete(() -> {
                    myDialog.dismiss();
                })
                .subscribe(event -> {
                            goToActivity(event);
                        },
                        Throwable::getMessage);

    }

    private void goToActivity(ResultResponse event) {
        if (event.getResult().equals("fail")){
            Intent intent = new Intent(PostDetailsActivity.this, SuccesActivity.class);
            intent.putExtra("succes", "0");
            startActivity(intent);
            finish();
        }else {
            Intent intent = new Intent(PostDetailsActivity.this, SuccesActivity.class);
            intent.putExtra("succes", "1");
            startActivity(intent);
            finish();
        }
    }
    private String capitalize(String word){
        return word.substring(0,1).toUpperCase() + word.substring(1);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (disposable != null)
            disposable.dispose();
    }
}

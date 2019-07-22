package com.android.erp;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.erp.Adapter.ClientAdapter;
import com.android.erp.Adapter.MainAdapter;
import com.android.erp.Network.ApiService;
import com.android.erp.Network.Response.ClientResponse;
import com.android.erp.Network.Response.HomeResponse;
import com.android.erp.Network.RetrofitClient;
import com.android.erp.Utils.GeneralUtils;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class FirstHomeActivity extends AppCompatActivity {

    private AppBarLayout appBarLayout;
    private Toolbar toolbar;
    private ImageButton back;
    private TextView title,lang;

    private RecyclerView main_recycler;
    private LinearLayoutManager layoutManager;
    private ClientAdapter adapter;
    private ProgressBar progressBar;
    private String userId;

    private Disposable disposable;
    private AlertDialog exitDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_home);
        Intent intent = getIntent();
        userId = intent.getStringExtra("userId");
        initData();
        invisible();
        fetchData();
        setClicks();
    }

    private void fetchData() {
        ApiService service = new RetrofitClient().create();
        Observable<List<ClientResponse>> get = null;

        get = service.getClients();
        List<ClientResponse> list = new ArrayList<>();
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

    private void addData(List<ClientResponse> event) {
            adapter = new ClientAdapter(this,event);
            main_recycler.setLayoutManager(layoutManager);
            main_recycler.setAdapter(adapter);
    }

    private void initData(){
        progressBar = findViewById(R.id.firstProgress);
        lang = findViewById(R.id.lang_first);
        main_recycler = findViewById(R.id.first_recycler);
        layoutManager = new LinearLayoutManager(this);
        appBarLayout = findViewById(R.id.appBarLayFirst);
        toolbar = findViewById(R.id.toolbarFirst);
        back = findViewById(R.id.tb_back_first);
        title = findViewById(R.id.toolbar_first_title);
        setSupportActionBar(toolbar);
        Typeface avenir_light = Typeface.createFromAsset(getAssets(),"fonts/AvenirLight.ttf");
        title.setTypeface(avenir_light);
        lang.setTypeface(avenir_light);
    }
    private void invisible(){
        main_recycler.setVisibility(View.INVISIBLE);
        progressBar.setVisibility(View.VISIBLE);
    }
    private void visible(){
        main_recycler.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.INVISIBLE);
    }
    private void setClicks(){
        lang.setOnClickListener(v -> {
            PopupMenu p = new PopupMenu(FirstHomeActivity.this,lang);

            p.getMenuInflater().inflate(R.menu.main_menu,p.getMenu());
            p.setOnMenuItemClickListener(item -> {
                Toast.makeText(getApplicationContext(),"s",Toast.LENGTH_SHORT).show();
                return true;
            });
            p.show();
        });
        back.setOnClickListener(view -> {
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
                    editor.putBoolean("check",false);
                    editor.putBoolean("isAdmin",false);
                    editor.apply();
                    Intent intent = new Intent(FirstHomeActivity.this,LoginActivity.class);
                    startActivity(intent);
                    overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                    finish();

                }
            });
            exitDialog=exitDialogBuilder.create();
            exitDialog.show();


        });
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (disposable != null)
            disposable.dispose();
    }
}

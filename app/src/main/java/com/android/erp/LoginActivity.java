package com.android.erp;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.erp.Fragments.ForgotFragment;
import com.android.erp.Utils.StatusbarTransparent;
import com.android.erp.Utils.TransitionUtils;

public class LoginActivity extends AppCompatActivity {

    private AppBarLayout appBarLayout;
    private Toolbar toolbar;
    private ImageView back;
    private TextView title,login_text,forgot_password;
    private AppCompatEditText edtMail,edtPassword;
    private TextInputLayout textInputLayout,passLayout;
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        appBarLayout = findViewById(R.id.appBar);
        toolbar = findViewById(R.id.toolbar);
        back = findViewById(R.id.tb_info);
        title = findViewById(R.id.toolbar_title);
        edtMail = findViewById(R.id.edtMail);
        edtPassword = findViewById(R.id.edtPassword);
        textInputLayout = findViewById(R.id.text_layout);
        passLayout = findViewById(R.id.pass_layout);
        login_text = findViewById(R.id.login_text);
        button = findViewById(R.id.loginBtn);
        forgot_password = findViewById(R.id.forgot_password);
        setSupportActionBar(toolbar);
        Typeface avenir_light = Typeface.createFromAsset(getAssets(),"fonts/AvenirLight.ttf");
        Typeface avenir_black = Typeface.createFromAsset(getAssets(),"fonts/AvenirBlack.ttf");
        Typeface avenir_book = Typeface.createFromAsset(getAssets(),"fonts/AvenirBook.ttf");
        forgot_password.setTypeface(avenir_light);
        title.setTypeface(avenir_light);
        login_text.setTypeface(avenir_black);

        edtMail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (edtMail.getText().length() >0 && edtPassword.getText().length() >= 6){
                    button.setEnabled(true);
                    button.setBackgroundResource(R.drawable.blue_button);
                    button.setTextColor(getResources().getColor(R.color.white));
                }else {
                    button.setEnabled(false);
                    button.setBackgroundResource(R.drawable.grey_button);
                    button.setTextColor(getResources().getColor(R.color.textColor));
                }
            }
        });

        edtPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (edtMail.getText().length() >0 && edtPassword.getText().length() >= 6){
                    button.setEnabled(true);
                    button.setBackgroundResource(R.drawable.blue_button);
                    button.setTextColor(getResources().getColor(R.color.white));
                }else {
                    button.setEnabled(false);
                    button.setBackgroundResource(R.drawable.grey_button);
                    button.setTextColor(getResources().getColor(R.color.textColor));
                }
            }
        });

        button.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this,HomeActivity.class);
            startActivity(intent);
            finish();
        });
        forgot_password.setOnClickListener(v -> {
            ForgotFragment forgotFragment = new ForgotFragment();
            TransitionUtils.replaceFragmentInActivityRight(getSupportFragmentManager(), forgotFragment,
                    "forgot", R.id.mainFrame, true);
            invisible();
        });
        back.setOnClickListener(v -> {
            int count = getSupportFragmentManager().getBackStackEntryCount();

            if (count == 0) {
                onBackPressed();
            } else {
                visible();
                getSupportFragmentManager().popBackStack();
            }
        });
    }
    private void invisible(){
        login_text.setVisibility(View.INVISIBLE);
        textInputLayout.setVisibility(View.INVISIBLE);
        edtMail.setVisibility(View.INVISIBLE);
        passLayout.setVisibility(View.INVISIBLE);
        edtPassword.setVisibility(View.INVISIBLE);
        button.setVisibility(View.INVISIBLE);
        forgot_password.setVisibility(View.INVISIBLE);
    }
    private void visible(){
        login_text.setVisibility(View.VISIBLE);
        textInputLayout.setVisibility(View.VISIBLE);
        edtMail.setVisibility(View.VISIBLE);
        passLayout.setVisibility(View.VISIBLE);
        edtPassword.setVisibility(View.VISIBLE);
        button.setVisibility(View.VISIBLE);
        forgot_password.setVisibility(View.VISIBLE);
    }
    @Override
    public void onBackPressed() {
        int count = getSupportFragmentManager().getBackStackEntryCount();

        if (count == 0) {
            finish();
        } else {
            visible();
            getSupportFragmentManager().popBackStack();
        }
    }
}

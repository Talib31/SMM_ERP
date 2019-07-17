package com.android.erp.Fragments;


import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatEditText;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.android.erp.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class ForgotFragment extends Fragment {

    private View view;

    private TextView forgot_text;
    private AppCompatEditText forgot_mail;
    private Button forgotBtn;

    public ForgotFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_forgot, container, false);

        Typeface avenir_black = Typeface.createFromAsset(getActivity().getAssets(),"fonts/AvenirBlack.ttf");

        forgot_text = view.findViewById(R.id.forgot_text);
        forgot_mail = view.findViewById(R.id.edtForgotMail);
        forgotBtn = view.findViewById(R.id.forgotBtn);
        forgot_text.setTypeface(avenir_black);
        forgot_mail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (forgot_mail.getText().length() >1){
                    forgotBtn.setEnabled(true);
                    forgotBtn.setBackgroundResource(R.drawable.blue_button);
                    forgotBtn.setTextColor(getResources().getColor(R.color.white));
                }else {
                    forgotBtn.setEnabled(false);
                    forgotBtn.setBackgroundResource(R.drawable.grey_button);
                    forgotBtn.setTextColor(getResources().getColor(R.color.textColor));
                }
            }
        });

        return view;
    }

}

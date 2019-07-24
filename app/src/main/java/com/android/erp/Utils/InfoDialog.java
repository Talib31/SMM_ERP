package com.android.erp.Utils;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatDialogFragment;
import android.support.v7.widget.AppCompatEditText;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.erp.Network.ApiService;
import com.android.erp.Network.Response.ResultResponse;
import com.android.erp.Network.RetrofitClient;
import com.android.erp.R;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class InfoDialog extends AppCompatDialogFragment {

    private AppCompatEditText companyNameEditText,adminNameEditText,phoneEditText,mailEditText,addressEditText,siteEditText;
    private String displayName="",adminName="",phone="",mail="",address="",site="",userId="";
    private Button changeBtn;

    private Disposable disposable;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.client_info,null);

        Bundle userData=getArguments();

        if (userData!=null) {
             displayName = userData.getString("displayName");
             adminName = userData.getString("adminName");
             phone = userData.getString("phone");
             mail = userData.getString("mail");
             address = userData.getString("address");
             site = userData.getString("site");
             userId=userData.getString("userId");

        }
        initData(view);


        changeBtn.setOnClickListener(v -> {
            //TODO change User Data in Database passing as a parametr userId

            displayName=companyNameEditText.getEditableText().toString();
            mail=mailEditText.getEditableText().toString();
            phone=phoneEditText.getEditableText().toString();
            address=addressEditText.getEditableText().toString();
            site=siteEditText.getEditableText().toString();

            saveToDatabase(userId,displayName,phone,mail,address,site);


        });




        alert.setView(view).setTitle("Info").setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });




        return alert.create();

    }

    private void saveToDatabase(String userId, String displayName, String phone, String mail, String address, String site) {

        //TODO Url=http://mealappeazi.alwaysdata.net/erpapp/adduser.php
        // parametrs: userId,displayname,telephone,username,address,site
        ApiService service = new RetrofitClient().create();
        Observable<ResultResponse> result = null;

        result = service.addUser(userId,displayName,phone,mail,address,site);
        disposable = result
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnError(error -> GeneralUtils.largeLog("doOnErrorEventsFragmentCall", error.getMessage()))
                .doOnComplete(() -> {

                })
                .subscribe(event -> {
                            dism(event);
                        },
                        Throwable::getMessage);


    }

    private void dism(ResultResponse event) {
        if (event.getResult().equals("fail")){
            Toast.makeText(getContext(),"Sorry,we can't complete your process",Toast.LENGTH_LONG).show();
        }else {
            Toast.makeText(getContext(),"Completed",Toast.LENGTH_LONG).show();
            dismiss();
        }
    }

    private void initData(View view) {
        changeBtn=view.findViewById(R.id.buttonChange);
        companyNameEditText=view.findViewById(R.id.edtCompanyNameInfo);
        adminNameEditText=view.findViewById(R.id.edtAdminNameInfo);
        phoneEditText=view.findViewById(R.id.edtMailInfoTel);
        mailEditText=view.findViewById(R.id.edtMailInfomail);
        addressEditText=view.findViewById(R.id.edtMailInfoAddress);
        siteEditText=view.findViewById(R.id.edtMailInfoSite);


        companyNameEditText.setText(displayName);
        adminNameEditText.setText(adminName);
        phoneEditText.setText(phone);
        mailEditText.setText(mail);
        addressEditText.setText(address);
        siteEditText.setText(site);




    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        if (disposable != null)
            disposable.dispose();
    }
}

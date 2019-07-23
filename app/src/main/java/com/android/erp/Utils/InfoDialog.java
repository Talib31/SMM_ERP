package com.android.erp.Utils;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatDialogFragment;
import android.support.v7.widget.AppCompatEditText;
import android.view.LayoutInflater;
import android.view.View;

import com.android.erp.R;

public class InfoDialog extends AppCompatDialogFragment {

    private AppCompatEditText companyNameEditText,adminNameEditText,phoneEditText,mailEditText,addressEditText,siteEditText;
    private String displayName="",adminName="",phone="",mail="",address="",site="";

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

        }
        initData(view);




        alert.setView(view).setTitle("Info").setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        return alert.create();
    }

    private void initData(View view) {

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
}

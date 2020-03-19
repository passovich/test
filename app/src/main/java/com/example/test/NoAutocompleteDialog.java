package com.example.test;

import android.app.DialogFragment;
import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.libraries.places.widget.AutocompleteSupportFragment;

public class NoAutocompleteDialog extends DialogFragment implements View.OnClickListener {
    private Button btnYes;
    private EditText addressET;
    private UserViewModel userVM;
    private Context context;
    private String [] address;
    private AutocompleteSupportFragment autocompleteSupportFragment;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        Resources res = getResources();
        getDialog().setTitle(res.getString(R.string.address));
        View v = inflater.inflate(R.layout.dialog_no_autocompleate,null);

        btnYes = (Button) v.findViewById(R.id.btnYes);
        btnYes.setOnClickListener(this);
        addressET = (EditText) v.findViewById(R.id.address);
        userVM = new UserViewModel(context);
        return v;
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnYes:
                String editTexData = addressET.getText().toString().trim();
                String valid = userVM.addressValidation(editTexData);
                if (valid == null) {
                    address[0] = editTexData;
                    autocompleteSupportFragment.setText(editTexData);
                    dismiss();
                } else {
                    Toast.makeText(context,valid, Toast.LENGTH_LONG).show();
                }
                break;
        }
    }
    public void initialize(Context context,String [] address,AutocompleteSupportFragment aSF){
        this.context = context;
        this.address = address;
        this.autocompleteSupportFragment = aSF;
    }
 }

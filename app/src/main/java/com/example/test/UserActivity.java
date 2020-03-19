package com.example.test;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.common.api.Status;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Arrays;

public class UserActivity extends AppCompatActivity implements View.OnClickListener, View.OnFocusChangeListener {
    private String TAG = "MyLogs";
    private Button confirmButton;
    private TextInputLayout lName, lEmail, lPhone, lPassword, lPasswordConf;
    private EditText name, eMail, phone, password, passwordConf;
    private Drawable img;
    private String address;
    private UserViewModel userVM;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_activity);

        userVM = new UserViewModel(this);
        //api key for google places
        String apiKey = getString(R.string.api_key);

        confirmButton = (Button) findViewById(R.id.confirm_button);
        confirmButton.setOnClickListener(this);
        img = getResources().getDrawable(R.drawable.conf);
        img.setBounds(0, 0, 60, 60);

        lName = (TextInputLayout) findViewById(R.id.l_name);
        lEmail = (TextInputLayout) findViewById(R.id.l_email);
        lPhone = (TextInputLayout) findViewById(R.id.l_phone);
        lPassword = (TextInputLayout) findViewById(R.id.l_password);
        lPasswordConf = (TextInputLayout) findViewById(R.id.l_password_confirm);

        name = (EditText) findViewById(R.id.name);
        name.setOnFocusChangeListener(this);
        eMail = (EditText) findViewById(R.id.email);
        eMail.setOnFocusChangeListener(this);
        phone = (EditText) findViewById(R.id.phone);
        phone.setOnFocusChangeListener(this);
        password = (EditText) findViewById(R.id.password);
        password.setOnFocusChangeListener(this);
        passwordConf = (EditText) findViewById(R.id.password_confirm);
        passwordConf.setOnFocusChangeListener(this);

        if (!Places.isInitialized()) Places.initialize(getApplicationContext(), apiKey);

        // Create a new Places client instance.
        PlacesClient placesClient = Places.createClient(this);

        // Initialize the AutocompleteSupportFragment.
        AutocompleteSupportFragment autocompleteFragment = (AutocompleteSupportFragment)
                getSupportFragmentManager().findFragmentById(R.id.autocomplete_fragment);
        autocompleteFragment.setPlaceFields(Arrays.asList(Place.Field.ID, Place.Field.ADDRESS,Place.Field.NAME));
        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                address = place.getAddress();
                Log.d(TAG,"Address = "+place.getAddress());
            }

            @Override
            public void onError(Status status) {
                Log.d(TAG, "An error occurred: " + status);
            }
        });
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.confirm_button) {
            userVM.confirmValidation(
                    name.getText().toString().trim(),
                    eMail.getText().toString().trim(),
                    phone.getText().toString().trim(),
                    address,
                    password.getText().toString().trim(),
                    passwordConf.getText().toString().trim()
            );
        }
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        switch (v.getId()) {
            case R.id.name:
                if (!hasFocus) {
                    if (userVM.nameValidation(name.getText().toString().trim()) == null)
                        name.setCompoundDrawables(null, null, img, null);
                    else name.setCompoundDrawables(null, null, null, null);
                    lName.setError(userVM.nameValidation(name.getText().toString().trim()));
                }
                break;
            case R.id.email:
                if (!hasFocus) {
                    if (userVM.emailValidation(eMail.getText().toString().trim()) == null)
                        eMail.setCompoundDrawables(null, null, img, null);
                    else eMail.setCompoundDrawables(null, null, null, null);
                    lEmail.setError(userVM.emailValidation(eMail.getText().toString().trim()));
                }
                break;
            case R.id.phone:
                if (!hasFocus) {
                    if (userVM.phoneValidation(phone.getText().toString().trim()) == null)
                        phone.setCompoundDrawables(null, null, img, null);
                    else phone.setCompoundDrawables(null, null, null, null);
                    lPhone.setError(userVM.phoneValidation(phone.getText().toString().trim()));
                }
                break;
            case R.id.password:
                if (!hasFocus) {
                    lPassword.setError(userVM.passValidation(password.getText().toString().trim()));
                }
                break;
            case R.id.password_confirm:
                if (!hasFocus) {
                    lPasswordConf.setError(userVM.passValidation(passwordConf.getText().toString().trim()));
                }
                break;
        }
    }
}

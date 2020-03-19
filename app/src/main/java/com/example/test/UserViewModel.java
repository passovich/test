package com.example.test;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber;

import java.util.LinkedList;
import java.util.List;
import java.util.regex.Pattern;

public class UserViewModel {
    private String TAG = "myLog";
    private Context context;
    private List<User> users = new LinkedList<User>();

    public UserViewModel (Context context){
        this.context = context;
    }

    public String nameValidation (String inputString){
        if (inputString.isEmpty()) return context.getString(R.string.name_empty);
        return null;
    }

    public String emailValidation(String inputString){
        if (inputString.isEmpty()) return context.getString(R.string.email_empty);
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(inputString).matches()) return  context.getString(R.string.email_bad);
        return null;
    }

    public String addressValidation(String inputString){
        if (inputString == null || inputString.isEmpty())
            return context.getString(R.string.address_empty);
        return null;
    }

    public String phoneValidation (String inputString){
        if (inputString.isEmpty()) return context.getString(R.string.phone_empty);
        if (inputString.length() < 4) return context.getString(R.string.phone_shirt);
        if (inputString.charAt(0) != '+') return context.getString(R.string.phone_without_plus);
        try {
            Long.parseLong(inputString.substring(1, inputString.length()));
        } catch (Exception e) {return context.getString(R.string.phone_without_plus);}
        if (getCountryCode(inputString) == null) {return context.getString(R.string.country_code_bad);}
        return null;
    }

    public String passValidation (String inputString){
        if (inputString.isEmpty()) return context.getString(R.string.password_empty);
        if (inputString.length() < 8) return context.getString(R.string.password_shirt);
        if (!passwordStrengthCheck(inputString)) return context.getString(R.string.password_weak);
        return null;
    }

    public boolean confirmValidation (
            String name,
            String eMail,
            String phone,
            String address,
            String password,
            String passwordConf
    ){
        String validation = nameValidation(name);
        if (validation == null) validation = emailValidation(eMail);
        if (validation == null) validation = phoneValidation(phone);
        if (validation == null) validation = addressValidation(address);
        if (validation == null) validation = passValidation(password);
        if (!passwordsEqualsCheck(password, passwordConf) && validation == null){
            validation = context.getString(R.string.passwords_not_same);
        }
        if (validation != null){
            Toast.makeText(context, validation, Toast.LENGTH_LONG).show();
            return false;
        }
        User user = new User(
                null,
                name,
                eMail,
                password,
                getCountryCode(phone),
                getPhoneNumber(phone),
                address
        );
        users.add(user);
        Toast.makeText(context,context.getString(R.string.Success_adding), Toast.LENGTH_LONG).show();
        Log.d(TAG,"m_token              = " + user.getM_token());
        Log.d(TAG,"m_name               = " + user.getM_name());
        Log.d(TAG,"m_eMail              = " + user.getM_email());
        Log.d(TAG,"m_password           = " + user.getM_password());
        Log.d(TAG,"m_phone_country_code = " + user.getM_phone_country_code());
        Log.d(TAG,"m_phone              = " + user.getM_phone());
        Log.d(TAG,"m_address            = " + user.getM_address());
        return true;
    }

    private boolean passwordsEqualsCheck(String password, String passwordConfirm){
        return password.equals(passwordConfirm);
    }

    private String getCountryCode(String phoneNumber){
        PhoneNumberUtil phoneUtil = PhoneNumberUtil.getInstance();
        try {
            Phonenumber.PhoneNumber numberProto = phoneUtil.parse(phoneNumber, "");
            return Integer.toString(numberProto.getCountryCode());
        } catch (NumberParseException e) {
            return null;
        }
    }
    private String getPhoneNumber(String phoneNumber){
        PhoneNumberUtil phoneUtil = PhoneNumberUtil.getInstance();
        try {
            Phonenumber.PhoneNumber numberProto = phoneUtil.parse(phoneNumber, "");
            return Long.toString(numberProto.getNationalNumber());
        } catch (NumberParseException e) {
            return null;
        }
    }
    private boolean passwordStrengthCheck(String inputString){
        boolean numbers = false, upCase = false, downCase = false;
        for (int i = 0; i < inputString.length(); i++){
            //Numbers including check
            if ((int)inputString.charAt(i) >= 48 && (int)inputString.charAt(i) <= 57) numbers = true;
            //Upper case characters check
            if ((int)inputString.charAt(i) >= 65 && (int)inputString.charAt(i) <= 90) upCase = true;
            //Lower case characters check
            if ((int)inputString.charAt(i) >= 97 && (int)inputString.charAt(i) <= 122) downCase = true;
        }
        return numbers && upCase && downCase;
    }
}

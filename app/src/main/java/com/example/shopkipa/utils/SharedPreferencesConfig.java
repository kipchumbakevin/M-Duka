package com.example.shopkipa.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.example.shopkipa.R;

public class SharedPreferencesConfig {
    private SharedPreferences sharedPreferences;
    private Context context;
    public SharedPreferencesConfig(Context context) {
        this.context = context;
        sharedPreferences = context.getSharedPreferences(context.getResources().getString(R.string.SHARED_PREFERENCES), Context.MODE_PRIVATE);
    }
    public void saveChangedDetails(String firstname,String lastname, String location,String username){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(context.getResources().getString(R.string.CLIENTS_LOCATION),location);
        editor.putString(context.getResources().getString(R.string.CLIENTS_USERNAME),username);
        editor.putString(context.getResources().getString(R.string.CLIENTS_FIRSTNAME),firstname);
        editor.putString(context.getResources().getString(R.string.CLIENTS_LASTNAME),lastname);

        editor.commit();
    }
        public void saveAuthenticationInformation(String accessToken,String firstname,String lastname, String location,String username, String phone,String status){
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString(context.getResources().getString(R.string.CLIENTS_TOKEN),accessToken);
            editor.putString(context.getResources().getString(R.string.CLIENTS_PHONE),phone);
            editor.putString(context.getResources().getString(R.string.CLIENTS_STATUS),status);
            editor.putString(context.getResources().getString(R.string.CLIENTS_LOCATION),location);
            editor.putString(context.getResources().getString(R.string.CLIENTS_USERNAME),username);
            editor.putString(context.getResources().getString(R.string.CLIENTS_FIRSTNAME),firstname);
            editor.putString(context.getResources().getString(R.string.CLIENTS_LASTNAME),lastname);

            editor.commit();
            Log.d("shared", accessToken);
        }

        public String readClientsPhone(){
            String phone;
            phone = sharedPreferences.getString(context.getResources().getString(R.string.CLIENTS_PHONE),"");
            return  phone;
        }
        public String readClientsFirstName(){
            String firstname;
            firstname = sharedPreferences.getString(context.getResources().getString(R.string.CLIENTS_FIRSTNAME),"");
            return  firstname;
        }

        public String readClientsLastName(){
            String lastname;
            lastname = sharedPreferences.getString(context.getResources().getString(R.string.CLIENTS_LASTNAME),"");
            return  lastname;
        }
        public String readClientsUsername(){
            String username;
            username = sharedPreferences.getString(context.getResources().getString(R.string.CLIENTS_USERNAME),"");
            return  username;
        }
    public String readClientsLocation(){
        String location;
        location = sharedPreferences.getString(context.getResources().getString(R.string.CLIENTS_LOCATION),"");
        return location;
    }
        public String readClientsStatus(){
            String status;
            status = sharedPreferences.getString(context.getResources().getString(R.string.CLIENTS_STATUS),"");
            return status;
        }
    public String readClientsAccessToken(){
        String accessToken;
        accessToken = sharedPreferences.getString(context.getResources().getString(R.string.CLIENTS_TOKEN),"");
        return accessToken;
    }
        public  boolean isloggedIn(){
            return readClientsStatus().equals(Constants.ACTIVE_CONSTANT);
        }

        public void clear() {
            SharedPreferences.Editor editor=sharedPreferences.edit();
            editor.clear()
                    .apply();
        }
}

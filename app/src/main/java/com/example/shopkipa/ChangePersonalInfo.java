package com.example.shopkipa;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;

import android.content.Context;
import android.os.Bundle;

import com.example.shopkipa.utils.SharedPreferencesConfig;

public class ChangePersonalInfo extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_personal_info);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.settings, new ChangePersonalInfo.SettingsFragment())
                .commit();
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }





    }

    public static class SettingsFragment extends PreferenceFragmentCompat {
        @Override
        public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
            addPreferencesFromResource(R.xml.personal_information_preferences);
            SharedPreferencesConfig sharedPreferencesConfig;

            final Context context = getContext();
            sharedPreferencesConfig = new SharedPreferencesConfig(context);


            Preference preferenceUsername, preferencefirstname, preferencelastname, preferencephone;
            preferenceUsername = findPreference("username_edit_preference");
            preferencefirstname = findPreference("first_name_edit_preference");
            preferencelastname = findPreference("last_name_edit_preference");
            preferencephone = findPreference("phone_edit_preference");

            String username = sharedPreferencesConfig.readClientsUsername();
            String firstname = sharedPreferencesConfig.readClientsFirstName();
            String lastame = sharedPreferencesConfig.readClientsLastName();
            String phone = sharedPreferencesConfig.readClientsPhone();

            if (preferenceUsername != null) {
                preferenceUsername.setSummary(username);
            }
            if (preferencefirstname != null) {
                preferencefirstname.setSummary(firstname);
            }
            if (preferencelastname != null) {
                preferencelastname.setSummary(lastame);
            }
            if (preferencephone != null) {
                preferencephone.setSummary(phone);
            }


        }
    }
}

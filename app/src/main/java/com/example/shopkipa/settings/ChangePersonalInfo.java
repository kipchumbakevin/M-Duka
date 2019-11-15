package com.example.shopkipa.settings;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.EditTextPreference;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.PreferenceManager;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

import com.example.shopkipa.R;
import com.example.shopkipa.models.ChangeDetailsModel;
import com.example.shopkipa.networking.RetrofitClient;
import com.example.shopkipa.utils.SharedPreferencesConfig;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
            bindSummaryValue(findPreference("username_edit_preference"));
            bindSummaryValue(findPreference("first_name_edit_preference"));
            bindSummaryValue(findPreference("last_name_edit_preference"));
            bindSummaryValue(findPreference("location_edit_preference"));

            final Context context = getContext();
            sharedPreferencesConfig = new SharedPreferencesConfig(context);


            Preference preferenceUsername, preferencefirstname, preferencelastname,preferenceLocation;
            preferenceUsername = findPreference("username_edit_preference");
            preferencefirstname = findPreference("first_name_edit_preference");
            preferencelastname = findPreference("last_name_edit_preference");
            preferenceLocation = findPreference("location_edit_preference");

            String username = sharedPreferencesConfig.readClientsUsername();
            String firstname = sharedPreferencesConfig.readClientsFirstName();
            String lastname = sharedPreferencesConfig.readClientsLastName();
            String location = sharedPreferencesConfig.readClientsLocation();

            if (preferenceUsername != null) {
                preferenceUsername.setSummary(username);
            }
            if (preferencefirstname != null) {
                preferencefirstname.setSummary(firstname);
            }
            if (preferencelastname != null) {
                preferencelastname.setSummary(lastname);
            }
            if (preferenceLocation != null){
                preferenceLocation.setSummary(location);
            }



            Call<ChangeDetailsModel> call = RetrofitClient.getInstance(context)
                    .getApiConnector()
                    .changeDetails(username,firstname,lastname);
            Toast.makeText(context,username +" "+firstname,Toast.LENGTH_LONG).show();
            call.enqueue(new Callback<ChangeDetailsModel>() {
                @Override
                public void onResponse(Call<ChangeDetailsModel> call, Response<ChangeDetailsModel> response) {
                    if(response.code()==201){
                        Toast.makeText(context,response.message(),Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Toast.makeText(context,"Response:"+response.message(),Toast.LENGTH_SHORT).show();
                    }

                }

                @Override
                public void onFailure(Call<ChangeDetailsModel> call, Throwable t) {
                    Toast.makeText(context,"Error:"+t.getMessage(),Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
    private static void bindSummaryValue(Preference preference){
        preference.setOnPreferenceChangeListener(listener);
        listener.onPreferenceChange(preference, PreferenceManager.getDefaultSharedPreferences(preference.getContext())
        .getString(preference.getKey(),""));

    }
    private static Preference.OnPreferenceChangeListener listener = new Preference.OnPreferenceChangeListener() {
        @Override
        public boolean onPreferenceChange(Preference preference, Object newValue) {
            String value = newValue.toString();
            if (preference instanceof EditTextPreference){
                preference.setSummary(value);
            }
            return false;
        }
    };

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        Intent intent = new Intent (ChangePersonalInfo.this,SettingsActivity.class);
        startActivity(intent);
        finish();

    }
}

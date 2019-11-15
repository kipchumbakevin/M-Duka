package com.example.shopkipa.settings;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.shopkipa.R;

public class SecurityOptions extends AppCompatActivity {
    TextView phoneChange,passChange;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_security_options);
        phoneChange = findViewById(R.id.phone_change);
        passChange = findViewById(R.id.password_change);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar!=null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        passChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SecurityOptions.this,ChangePasswordActivity.class);
                startActivity(intent);
            }
        });
        phoneChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SecurityOptions.this,ChangePhoneNumber.class);
                startActivity(intent);
            }
        });
    }
}

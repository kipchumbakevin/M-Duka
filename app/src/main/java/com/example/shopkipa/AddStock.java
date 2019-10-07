package com.example.shopkipa;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class AddStock extends AppCompatActivity implements NumberPicker.OnValueChangeListener {
    Spinner spinnerCategory,spinnerType,spinnerSize;
    NumberPicker numberPickerSize,numberPickerQuantity;
    CheckBox rCloth,rShoe;
    ImageView addImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_stock);
        spinnerCategory = findViewById(R.id.spinnerCategory);
        spinnerType = findViewById(R.id.spnnerType);
        spinnerSize = findViewById(R.id.spinnerSize);
        rCloth = findViewById(R.id.rCloth);
        rShoe = findViewById(R.id.rShoe);
        addImage = findViewById(R.id.addImage);
        numberPickerQuantity = findViewById(R.id.numberPickerQuantity);
        numberPickerSize = findViewById(R.id.numberPickerSize);
        List<String> spinnerArray =  new ArrayList<String>();
        spinnerArray.add("Male");
        spinnerArray.add("Female");
        spinnerArray.add("Boy");
        spinnerArray.add("Girl");
        spinnerArray.add("Unisex");
        spinnerArray.add("Other");
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item, spinnerArray);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinnerCategory.setAdapter(adapter);
        spinnerType.setAdapter(adapter);
        spinnerSize.setAdapter(adapter);
        setTitle("Add new product");
        numberPickers();
        rCloth.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (rCloth.isChecked()) {
                    spinnerSize.setVisibility(View.VISIBLE);
                    rShoe.setChecked(false);
                }else {
                    spinnerSize.setVisibility(View.GONE);
                }
            }
        });
        rShoe.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (rShoe.isChecked()) {
                    spinnerSize.setVisibility(View.GONE);
                    rCloth.setChecked(false);
                }
            }
        });
        ActionBar actionBar = getSupportActionBar();
        if (actionBar!=null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        addImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startStockImageDialog();
            }
        });
    }

    private void startStockImageDialog() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(AddStock.this);
        View view = getLayoutInflater().inflate(R.layout.image_dilaog,null);
        ImageView camera = view.findViewById(R.id.dialog_gallery);
        ImageView gallery = view.findViewById(R.id.dialog_camera);
        ImageView closedialog = view.findViewById(R.id.dialog_close);

        alertDialogBuilder.setView(view);
        final AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
        closedialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });


    }

    private void addStockImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
    }


    private void numberPickers() {
        numberPickerSize.setMaxValue(100);
        numberPickerSize.setMinValue(0);
        numberPickerSize.setWrapSelectorWheel(false);
        numberPickerSize.setOnValueChangedListener(AddStock.this);
        numberPickerQuantity.setMaxValue(100);
        numberPickerQuantity.setMinValue(0);
        numberPickerQuantity.setWrapSelectorWheel(false);
        numberPickerQuantity.setOnValueChangedListener(AddStock.this);

    }

    @Override
    public void onValueChange(NumberPicker numberPicker, int i, int i1) {

    }
}

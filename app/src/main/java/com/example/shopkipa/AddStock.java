package com.example.shopkipa;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.shopkipa.models.AddStockModel;
import com.example.shopkipa.networking.RetrofitClient;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddStock extends AppCompatActivity implements NumberPicker.OnValueChangeListener {
    private static final int GALLERY_REQUEST_CODE = 766;
    private static final int REQUEST_CODE = 422;
    Spinner spinnerCategory,spinnerType,spinnerSize;
    NumberPicker numberPickerSize,numberPickerQuantity;
    CheckBox rCloth,rShoe;
    ImageView addImage,productImage;
    private File mPhotoFile;
    private Uri photoUri;
    Button buttonAddStock;
    EditText itemName,itemColor,itemDesign,itemCompany,itemBP,itemSP;
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_stock);
        spinnerCategory = findViewById(R.id.spinnerCategory);
        spinnerType = findViewById(R.id.spnnerType);
        spinnerSize = findViewById(R.id.spinnerSize);
        itemBP = findViewById(R.id.item_bp);
        itemColor = findViewById(R.id.item_color);
        itemName = findViewById(R.id.item_name);
        itemSP = findViewById(R.id.item_sp);
        itemCompany = findViewById(R.id.item_company);
        itemDesign = findViewById(R.id.item_design);
        rCloth = findViewById(R.id.rCloth);
        mContext = getApplicationContext();
        buttonAddStock = findViewById(R.id.buttonAddStock);
        productImage = findViewById(R.id.productImage);
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
        buttonAddStock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (photoUri!=null){
                    newStock();
                }else {
                    Toast.makeText(AddStock.this,"add photo",Toast.LENGTH_SHORT).show();
                }
            }
        });
        numberPickerSize.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker numberPicker, int i, int i1) {
            }
        });
        numberPickerQuantity.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker numberPicker, int i, int i1) {
            }
        });
    }

    private void newStock() {
        int sizeI = numberPickerSize.getValue();
        int quantityI = numberPickerQuantity.getValue();
        String category = spinnerCategory.getSelectedItem().toString();
        String type = spinnerType.getSelectedItem().toString();
        String name = itemName.getText().toString();
        String color = itemColor.getText().toString();
        String design = itemDesign.getText().toString();
        String size = Integer.toString(sizeI);
        String quantity = Integer.toString(quantityI);
        String company = itemCompany.getText().toString();
        String buyingprice = itemBP.getText().toString();
        String sellingprice = itemSP.getText().toString();
        String image = photoUri.toString();


        Call<AddStockModel> call = RetrofitClient.getInstance(mContext)
                .getApiConnector()
                .addnewstock(category,type,name,color,design,company,buyingprice,sellingprice,size,quantity,image);
        call.enqueue(new Callback<AddStockModel>() {
            @Override
            public void onResponse(Call<AddStockModel> call, Response<AddStockModel> response) {
                if(response.code()==201){
                    Toast.makeText(AddStock.this,"product added",Toast.LENGTH_SHORT).show();

                }
                else{
                    Toast.makeText(AddStock.this,response.message(),Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<AddStockModel> call, Throwable t) {
                Toast.makeText(AddStock.this,"Network Connection Failed",Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void startStockImageDialog() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(AddStock.this);
        View view = getLayoutInflater().inflate(R.layout.image_dilaog,null);
        ImageView gallery = view.findViewById(R.id.dialog_gallery);
        ImageView camera = view.findViewById(R.id.dialog_camera);
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
        gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addStockImageGallery();
                alertDialog.dismiss();
            }
        });
        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addStockImageCamera();
                alertDialog.dismiss();
            }
        });


    }

    private void addStockImageCamera() {
        mPhotoFile = getPhotoFile();
        Intent captureImage = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        Uri uri = FileProvider.getUriForFile(AddStock.this,"com.example.shopkipa.fileprovider",mPhotoFile);
        captureImage.putExtra(MediaStore.EXTRA_OUTPUT,uri);
        List<ResolveInfo>cameraActivities = AddStock.this.getPackageManager().queryIntentActivities(captureImage,
                PackageManager.MATCH_DEFAULT_ONLY);
        for (ResolveInfo activity : cameraActivities){
            AddStock.this.grantUriPermission(activity.activityInfo.packageName,uri,Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        }
        startActivityForResult(captureImage,REQUEST_CODE);
    }

    private File getPhotoFile() {
        File filesDir = getFilesDir();
        return new File(filesDir,getPhotoFilename());
    }

    public String getPhotoFilename() {

        return "IMG_" + new Random().nextDouble() + ".jpg";

    }

    private void addStockImageGallery() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,"Choose product photo"),GALLERY_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK){
            return;
        }else if (requestCode == REQUEST_CODE){
            Uri uri = FileProvider.getUriForFile(AddStock.this,"com.example.shopkipa.fileprovider",mPhotoFile);
            photoUri = uri;
            revokeUriPermission(uri,Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
            updatePhotoView();
        }else if (requestCode == GALLERY_REQUEST_CODE){
            Uri photopath = data.getData();
            photoUri = photopath;
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(),photopath);
                updatePhotoView();
            }catch (IOException e){
                e.printStackTrace();
            }
        }
    }

    private void updatePhotoView() {
        if(photoUri!=null) {

            Glide.with(this).load(photoUri)
                    .into(productImage);
        }
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

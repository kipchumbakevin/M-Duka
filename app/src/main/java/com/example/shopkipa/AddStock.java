package com.example.shopkipa;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.shopkipa.models.AddExpenseModel;
import com.example.shopkipa.models.AddStockModel;
import com.example.shopkipa.models.GetAllGroupsModel;
import com.example.shopkipa.models.GetCategoriesModel;
import com.example.shopkipa.models.GetSizeModel;
import com.example.shopkipa.models.GetTypesInGroupModel;
import com.example.shopkipa.models.GetTypesModel;
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
    Spinner spinnerCategory,spinnerType,spinnerSize,selectItemGroupSpinner;
    NumberPicker numberPickerSize,numberPickerQuantity;
    ImageView addImage,productImage;
    private File mPhotoFile;
    TextView pickFormat,titleSize;
    View sizeView;
    String gg;
    private Uri photoUri;
    Button buttonAddStock;
    RelativeLayout progressLyt;
    EditText itemName,itemColor,itemDesign,itemCompany,itemBP,itemSP;
    private Context mContext;
    private ArrayAdapter<String> categoryadapter;
    private ArrayAdapter<String> typeadapter;
    private ArrayAdapter<String>sizeadapter;
    private ArrayAdapter<String>groupadapter;
    private List<String> categorySpinnerArray;
    private List<String> typeSpinnerArray;
    private List<String> sizeSpinnerArray;
    private List<String> groupSpinnerArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_stock);
        spinnerCategory = findViewById(R.id.spinnerCategory);
        spinnerType = findViewById(R.id.spnnerType);
        sizeView = findViewById(R.id.sizeView);
        spinnerSize = findViewById(R.id.spinnerSize);
        selectItemGroupSpinner = findViewById(R.id.select_item);
        itemBP = findViewById(R.id.item_bp);
        itemColor = findViewById(R.id.item_color);
        progressLyt = findViewById(R.id.progressLoad);
        itemName = findViewById(R.id.item_name);
        itemSP = findViewById(R.id.item_sp);
        itemCompany = findViewById(R.id.item_company);
        itemDesign = findViewById(R.id.item_design);
        mContext = getApplicationContext();
        pickFormat = findViewById(R.id.pickFormat);
        buttonAddStock = findViewById(R.id.buttonAddStock);
        productImage = findViewById(R.id.productImage);
        titleSize = findViewById(R.id.titleSize);
        addImage = findViewById(R.id.addImage);
        numberPickerQuantity = findViewById(R.id.numberPickerQuantity);
        numberPickerSize = findViewById(R.id.numberPickerSize);

        categorySpinnerArray = new ArrayList<>();

        categoryadapter = new ArrayAdapter<>(
                this, android.R.layout.simple_spinner_item, categorySpinnerArray);

        categoryadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinnerCategory.setAdapter(categoryadapter);


        sizeSpinnerArray = new ArrayList<>();

        sizeadapter = new ArrayAdapter<>(
                this, android.R.layout.simple_spinner_item, sizeSpinnerArray);

        sizeadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinnerSize.setAdapter(sizeadapter);
        selectItemGroupSpinner.setAdapter(groupadapter);

        groupSpinnerArray = new ArrayList<>();

        groupadapter = new ArrayAdapter<>(
                this, android.R.layout.simple_spinner_item, groupSpinnerArray);

        groupadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        selectItemGroupSpinner.setAdapter(groupadapter);
        typeSpinnerArray = new ArrayList<>();

        typeadapter = new ArrayAdapter<>(
                this, android.R.layout.simple_spinner_item, typeSpinnerArray);

        typeadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinnerType.setAdapter(typeadapter);

        fetchAllGroups();
        fetchCategory();
        fetchSize();


        setTitle("Add products");
        itemSP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               sellingPriceInfo();
            }
        });

        numberPickers();
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
                    Toast.makeText(AddStock.this,"Add photo",Toast.LENGTH_SHORT).show();
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
        pickFormat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pickSizeFormat();
            }
        });
        titleSize.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pickSizeFormat();
            }
        });
        selectItemGroupSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(getApplicationContext(), "This is " +
                        adapterView.getItemAtPosition(i).toString(), Toast.LENGTH_LONG).show();
                gg = adapterView.getItemAtPosition(i).toString();
                String groupname = gg;
                typeSpinnerArray.clear();
                Call<List<GetTypesInGroupModel>> call = RetrofitClient.getInstance(AddStock.this)
                        .getApiConnector()
                        .gettypeGroup(groupname);
                call.enqueue(new Callback<List<GetTypesInGroupModel>>() {
                    @Override
                    public void onResponse(Call<List<GetTypesInGroupModel>> call, Response<List<GetTypesInGroupModel>> response) {
                        hideProgress();
                        if(response.code()==200){

                            for(int index= 0;index<response.body().size();index++){
                                typeSpinnerArray.add(response.body().get(index).getName());
                            }
                            typeadapter.notifyDataSetChanged();
                        }
                        else{
                            Toast.makeText(AddStock.this,response.message(),Toast.LENGTH_SHORT).show();
                        }

                    }

                    @Override
                    public void onFailure(Call<List<GetTypesInGroupModel>> call, Throwable t) {
                        Toast.makeText(AddStock.this,t.getMessage() + "hhhhhd",Toast.LENGTH_SHORT).show();
                        hideProgress();
                    }
                });

                try {
                    //Your task here
                }catch (Exception e)
                {
                    e.printStackTrace();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void sellingPriceInfo() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(AddStock.this);
        alertDialogBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        alertDialogBuilder.setMessage("This price will be visible to buyers on our website");

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    private void newStock() {
        showProgress();
        int quantityI = numberPickerQuantity.getValue();
        String category = spinnerCategory.getSelectedItem().toString();
        String type = spinnerType.getSelectedItem().toString();
        String name = itemName.getText().toString();
        String color = itemColor.getText().toString();
        String design = itemDesign.getText().toString();
        String size = ss();
        String quantity = Integer.toString(quantityI);
        String company = itemCompany.getText().toString();
        String buyingprice = itemBP.getText().toString();
        String sellingprice = itemSP.getText().toString();
        String image = photoUri.toString();
        String item_group = selectItemGroupSpinner.getSelectedItem().toString();


        Call<AddStockModel> call = RetrofitClient.getInstance(mContext)
                .getApiConnector()
                .addnewstock(item_group,category,type,name,color,design,company,size,quantity,buyingprice,sellingprice,image);
        call.enqueue(new Callback<AddStockModel>() {
            @Override
            public void onResponse(Call<AddStockModel> call, Response<AddStockModel> response) {
                hideProgress();
                if(response.code()==201){
                    Toast.makeText(AddStock.this,"product added",Toast.LENGTH_SHORT).show();

                }
                else{
                    Toast.makeText(AddStock.this,"response:"+response.message(),Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<AddStockModel> call, Throwable t) {
                hideProgress();
                Toast.makeText(AddStock.this,"errot:"+t.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
    }

    private String ss() {
        String size = "";
        if (spinnerSize.isShown()) {
            size = spinnerSize.getSelectedItem().toString();
        }else if (numberPickerSize.isShown()) {
            size = Integer.toString(numberPickerSize.getValue());
        }
        return size;
    }

    private void hideProgress() {
        progressLyt.setVisibility(View.INVISIBLE);
    }

    private void showProgress() {
        progressLyt.setVisibility(View.VISIBLE);
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
    public void fetchCategory(){
        Call<List<GetCategoriesModel>> call = RetrofitClient.getInstance(AddStock.this)
                .getApiConnector()
                .getAllCategories();
        call.enqueue(new Callback<List<GetCategoriesModel>>() {
            @Override
            public void onResponse(Call<List<GetCategoriesModel>> call, Response<List<GetCategoriesModel>> response) {
                hideProgress();
                if(response.code()==200){

                   for(int index= 0;index<response.body().size();index++){
                      categorySpinnerArray.add(response.body().get(index).getName());
                    }
                   categoryadapter.notifyDataSetChanged();
                }
                else{
                    Toast.makeText(AddStock.this,response.message(),Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<List<GetCategoriesModel>> call, Throwable t) {
                Toast.makeText(AddStock.this,t.getMessage(),Toast.LENGTH_SHORT).show();
                hideProgress();
            }
        });

    }
    public void fetchAllGroups(){
        Call<List<GetAllGroupsModel>> call = RetrofitClient.getInstance(AddStock.this)
                .getApiConnector()
                .getAllGroups();
        call.enqueue(new Callback<List<GetAllGroupsModel>>() {
            @Override
            public void onResponse(Call<List<GetAllGroupsModel>> call, Response<List<GetAllGroupsModel>> response) {
                hideProgress();
                if(response.code()==200){

                    for(int index= 0;index<response.body().size();index++){
                        groupSpinnerArray.add(response.body().get(index).getName());
                    }
                    groupadapter.notifyDataSetChanged();
                }
                else{
                    Toast.makeText(AddStock.this,response.message(),Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<List<GetAllGroupsModel>> call, Throwable t) {
                Toast.makeText(AddStock.this,t.getMessage(),Toast.LENGTH_SHORT).show();
                hideProgress();
            }
        });
    }
    public void fetchSize(){
        Call<List<GetSizeModel>> call = RetrofitClient.getInstance(AddStock.this)
                .getApiConnector()
                .getAllSizes();
        call.enqueue(new Callback<List<GetSizeModel>>() {
            @Override
            public void onResponse(Call<List<GetSizeModel>> call, Response<List<GetSizeModel>> response) {
                hideProgress();
                if(response.code()==200){

                    for(int index= 0;index<response.body().size();index++){
                        sizeSpinnerArray.add(response.body().get(index).getName());
                    }
                    sizeadapter.notifyDataSetChanged();
                }
                else{
                    Toast.makeText(AddStock.this,response.message(),Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<List<GetSizeModel>> call, Throwable t) {
                Toast.makeText(AddStock.this,t.getMessage(),Toast.LENGTH_SHORT).show();
                hideProgress();
            }
        });
    }
    public void pickSizeFormat(){
        TextView pickWord,pickNumber;
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(AddStock.this);
        View view = getLayoutInflater().inflate(R.layout.pick_size_format,null);
        pickWord = view.findViewById(R.id.pickWord);
        pickNumber = view.findViewById(R.id.pickNumber);

        alertDialogBuilder.setView(view);
        final AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();

        pickWord.setOnClickListener(new View.OnClickListener() {
            int wi = 60;
            @Override
            public void onClick(View view) {
                pickFormat.setVisibility(View.GONE);
                numberPickerSize.setVisibility(View.GONE);
                spinnerSize.setVisibility(View.VISIBLE);
                titleSize.setText(pickFormat.getText().toString());
                sizeView.setVisibility(View.GONE);
                alertDialog.dismiss();
            }
        });
        pickNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pickFormat.setVisibility(View.GONE);
                spinnerSize.setVisibility(View.GONE);
                numberPickerSize.setVisibility(View.VISIBLE);
                titleSize.setText(pickFormat.getText().toString());
                sizeView.setVisibility(View.GONE);
                alertDialog.dismiss();
            }
        });
    }
}

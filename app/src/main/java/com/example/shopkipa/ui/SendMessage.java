package com.example.shopkipa.ui;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.FileUtils;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.shopkipa.R;
import com.example.shopkipa.models.SendMessageModel;
import com.example.shopkipa.networking.RetrofitClient;

import java.io.File;
import java.io.IOException;
import java.text.Normalizer;
import java.util.Random;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.http.Multipart;

public class SendMessage extends AppCompatActivity {
    private static final int GALLERY_REQUEST_CODE = 422;
    EditText writeMessage;
    Button sendMessage;
    TextView describe;
    View vieew;
    ImageView imageOne,addImageOne;
    private Uri photoUri;
    RelativeLayout firstScreenshot,progressLyt;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_message);
        writeMessage = findViewById(R.id.write_message);
        imageOne = findViewById(R.id.image_one);
        describe = findViewById(R.id.describe);
        vieew = findViewById(R.id.vieew);
        addImageOne = findViewById(R.id.addImageOne);
        progressLyt = findViewById(R.id.progressLoad);
        sendMessage = findViewById(R.id.send_message);
        firstScreenshot = findViewById(R.id.first_screenshot);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar!=null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        writeMessage.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                sendMessage.setEnabled(false);
                sendMessage.setBackground(getDrawable(R.drawable.no_message));
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.length()!=0){
                    vieew.setBackgroundColor(getResources().getColor(R.color.colorTab));
                    describe.setVisibility(View.GONE);
                    sendMessage.setEnabled(true);
                    sendMessage.setBackground(getDrawable(R.drawable.button));
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        sendMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (writeMessage.getText().length()<20){
                    vieew.setBackgroundColor(getResources().getColor(R.color.colorRed));
                    describe.setVisibility(View.VISIBLE);
                }
                else{
                    send();
                }
            }
        });
        firstScreenshot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gallery();
            }
        });
    }

    private void send() {

        RequestBody message = RequestBody.create(MultipartBody.FORM,writeMessage.getText().toString());
        Call<SendMessageModel> call = RetrofitClient.getInstance(this)
                .getApiConnector()
                .sendMessage();
               // .sendMessage(message,image);
        call.enqueue(new Callback<SendMessageModel>() {
            @Override
            public void onResponse(Call<SendMessageModel> call, Response<SendMessageModel> response) {
                hideProgress();
                if(response.code()==201){
                    writeMessage.getText().clear();
                    photoUri = null;
                    AlertDialog.Builder alertdialog = new AlertDialog.Builder(SendMessage.this);
                    alertdialog.setTitle("Success:")
                            .setMessage("Your message has been received. We will get back as soon as possible")
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {

                                }
                            });
                    AlertDialog alert = alertdialog.create();
                    alert.show();

                }
                else{
                    Toast.makeText(SendMessage.this,"response:"+response.message(),Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<SendMessageModel> call, Throwable t) {
                hideProgress();
                Toast.makeText(SendMessage.this,"errot:"+t.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void gallery() {
        Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(i,GALLERY_REQUEST_CODE);
//        Intent intent = new Intent();
//        intent.setType("image/*");
//        intent.setAction(Intent.ACTION_GET_CONTENT);
//        startActivityForResult(Intent.createChooser(intent,"Choose product photo"),GALLERY_REQUEST_CODE);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK){
            return;
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
            addImageOne.setVisibility(View.GONE);
            Glide.with(this).load(photoUri)
                    .into(imageOne);
        }
    }
    private void hideProgress() {
        progressLyt.setVisibility(View.INVISIBLE);
    }

    private void showProgress() {
        progressLyt.setVisibility(View.VISIBLE);
    }
}

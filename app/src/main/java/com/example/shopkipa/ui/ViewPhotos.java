package com.example.shopkipa.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.shopkipa.R;
import com.example.shopkipa.adapters.GetImagesAdapter;
import com.example.shopkipa.adapters.ViewPagerAdapter;
import com.example.shopkipa.models.GetItemImagesModel;
import com.example.shopkipa.networking.RetrofitClient;
import com.example.shopkipa.utils.Constants;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ViewPhotos extends AppCompatActivity {

    ViewPagerAdapter viewPagerAdapter;
    ViewPager viewPager;
    GetItemImagesModel getItemImagesModel;
    private String imageURL;
    String item_id;
    private ArrayList<GetItemImagesModel> mImagesArrayList=new ArrayList<>();
    GetImagesAdapter getImagesAdapter;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_photos);
        LinearLayoutManager linearLayoutManager =
                new LinearLayoutManager(ViewPhotos.this,LinearLayoutManager.HORIZONTAL,false);
        getImagesAdapter = new GetImagesAdapter(ViewPhotos.this,mImagesArrayList);
        recyclerView = findViewById(R.id.imagesRecyclerView);
        recyclerView.setAdapter(getImagesAdapter);
        recyclerView.setLayoutManager(linearLayoutManager);
       // viewPager = findViewById(R.id.viewPagerImages);
       // getItemImagesModel = new GetItemImagesModel();
//        viewPagerAdapter = new ViewPagerAdapter(this);
//        viewPager.setAdapter(viewPagerAdapter);
        item_id = getIntent().getExtras().getString("ITEMID");
        viewImages();
    }

    private void viewImages() {
        mImagesArrayList.clear();
        Call<List<GetItemImagesModel>> call = RetrofitClient.getInstance(ViewPhotos.this)
                .getApiConnector()
                .getImages(item_id);
        call.enqueue(new Callback<List<GetItemImagesModel>>() {
            @Override
            public void onResponse(Call<List<GetItemImagesModel>> call, Response<List<GetItemImagesModel>> response) {
                if(response.isSuccessful()){
                    mImagesArrayList.addAll(response.body());
                    getImagesAdapter.notifyDataSetChanged();
//                    String character = "http";
//                    String[] ProductImages = new String[mImagesArrayList.size()];
//                for (int i = 0; i < mImagesArrayList.size(); i++) {
//                if (mImagesArrayList.get(i).getImageurl().charAt(0) == character.charAt(0)) {
//                    ProductImages[i] = mImagesArrayList.get(i).getImageurl();
//
//                } else {
//                    ProductImages[i] = Constants.BASE_URL + mImagesArrayList.get(i).getImageurl();
//                }
//            }
//            ViewPagerAdapter.images = ProductImages;
                }
                else{
                    Toast.makeText(ViewPhotos.this,"Internal server error. Please retry",Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<List<GetItemImagesModel>> call, Throwable t) {
                Toast.makeText(ViewPhotos.this,"Network error",Toast.LENGTH_SHORT).show();
            }
        });
    }

//    private void viewImages() {
//        ViewPagerAdapter.images = new String[]{};
//
//        //Attaching Images To ViewPager
//        //TO-DO, Make This Process Cleaner!
//        if (getItemImagesModel.getImageurl().size() < 1) {
//            ViewPagerAdapter.images = new String[]{imageURL,};
//        } else {
//            String[] ProductImages = new String[product.getImages().size()];
//            for (int i = 0; i < product.getImages().size(); i++) {
//                if (product.getImages().get(i).getImage().charAt(0) == character.charAt(0)) {
//                    ProductImages[i] = product.getImages().get(i).getImage();
//
//                } else {
//                    ProductImages[i] = Constants.BASE_URL + product.getImages().get(i).getImage();
//                }
//            }
//            ViewPagerAdapter.images = ProductImages;
//        }
//
//    }
}

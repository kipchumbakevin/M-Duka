package com.example.shopkipa.networking;

import com.example.shopkipa.models.AddExpenseModel;
import com.example.shopkipa.models.AddSaleModel;
import com.example.shopkipa.models.AddStockModel;
import com.example.shopkipa.models.GetCategoriesModel;
import com.example.shopkipa.models.GetExpenseModel;
import com.example.shopkipa.models.GetStockModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface JsonPlaceHolderInterface {
    @FormUrlEncoded
    @POST("api/addstock")
    Call<AddStockModel> addnewstock(
            @Field("category")String category,
            @Field("type")String type,
            @Field("name")String name,
            @Field("color")String color,
            @Field("design")String design,
            @Field("company")String company,
            @Field("size")String size,
            @Field("quantity")String quantity,
            @Field("buyingprice")String buyingprice,
            @Field("sellingprice")String sellingprice,
            @Field("image")String image
    );
    @FormUrlEncoded
    @POST("api/addexpense")
    Call<AddExpenseModel> addnewexpense(
            @Field("expensetype")String expensetype,
            @Field("amount")String amount
    );
    @FormUrlEncoded
    @POST("api/addsales")
    Call<AddSaleModel> addnewsale(
            @Field("quantity")String quantitysold,
            @Field("costprice")String costprice,
            @Field("purchase_id")String purchaseId
    );
    @FormUrlEncoded
    @POST("api/getstock")
    Call<List<GetStockModel>> getAllStock(
            @Field("category_name")String categoryname
    );
    @GET("api/getcategories")
    Call<List<GetCategoriesModel>> getAllCategories();
    @GET("api/getexpense")
    Call<List<GetExpenseModel>> getAllExpenses();

}

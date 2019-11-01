package com.example.shopkipa.networking;

import android.net.Uri;

import com.example.shopkipa.models.AddExpenseModel;
import com.example.shopkipa.models.AddSaleModel;
import com.example.shopkipa.models.AddStockModel;
import com.example.shopkipa.models.DeleteExpenseModel;
import com.example.shopkipa.models.DeleteItemModel;
import com.example.shopkipa.models.EditStockModel;
import com.example.shopkipa.models.GetAllGroupsModel;
import com.example.shopkipa.models.GetCategoriesModel;
import com.example.shopkipa.models.GetExpenseModel;
import com.example.shopkipa.models.GetGroupModel;
import com.example.shopkipa.models.GetMonthsModel;
import com.example.shopkipa.models.GetSalesInMonthModel;
import com.example.shopkipa.models.GetSummaryModel;
import com.example.shopkipa.models.GetSizeModel;
import com.example.shopkipa.models.GetStockInTypeModel;
import com.example.shopkipa.models.GetTypesInCategoryModel;
import com.example.shopkipa.models.GetTypesInGroupModel;
import com.example.shopkipa.models.GetTypesModel;
import com.example.shopkipa.models.GetYearsModel;

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
            @Field("item_group")String item_group,
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
            @Field("image") Uri image
    );
    @FormUrlEncoded
    @POST("api/editstock")
    Call<EditStockModel> editStock(
            @Field("name")String name,
            @Field("color")String color,
            @Field("design")String design,
            @Field("company")String company,
            @Field("sellingprice")String sellingprice,
            @Field("size")String size,
            @Field("quantity")String quantity,
            @Field("item_id")String item_id
    );
    @FormUrlEncoded
    @POST("api/deletestock")
    Call<DeleteItemModel> deleteStock(
            @Field("item_id")String item_id
    );
    @FormUrlEncoded
    @POST("api/deleteexpense")
    Call<DeleteExpenseModel> deleteExpense(
            @Field("id")String iD
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
            @Field("purchase_id")String purchaseId,
            @Field("quantity")String quantitysold,
            @Field("costprice")String costprice
    );
    @FormUrlEncoded
    @POST("api/gettypeitem")
    Call<List<GetStockInTypeModel>> getAllStock(
            @Field("name")String typename,
            @Field("namecategory")String category
    );
    @FormUrlEncoded
    @POST("api/getcategorytype")
    Call<List<GetTypesInCategoryModel>> getTypes(
            @Field("namecategory")String categoryname,
            @Field("namegroup")String groupname
    );
    @FormUrlEncoded
    @POST("api/getgroup")
    Call<List<GetGroupModel>> getGroup(
            @Field("category_name")String categoryname
    );
    @FormUrlEncoded
    @POST("api/gettypegroup")
    Call<List<GetTypesInGroupModel>> gettypeGroup(
            @Field("group_name")String groupname
    );
    @GET("api/getyears")
    Call<List<GetYearsModel>> getYears();

    @FormUrlEncoded
    @POST("api/getmonths")
    Call<List<GetMonthsModel>> getMonths(
            @Field("year") String year
    );
    @GET("api/getcategories")
    Call<List<GetCategoriesModel>> getAllCategories();
    @GET("api/getalltypes")
    Call<List<GetTypesModel>> getAllTypes();
    @GET("api/getsizes")
    Call<List<GetSizeModel>> getAllSizes();
    @GET("api/getgroups")
    Call<List<GetAllGroupsModel>> getAllGroups();

    @FormUrlEncoded
    @POST("api/getprofitloss")
    Call<GetSummaryModel> getSummary(
            @Field("year")String year,
            @Field("month")String month
    );
    @FormUrlEncoded
    @POST("api/getexpenses")
    Call<List<GetExpenseModel>> getAllExpenses(
            @Field("year")String year,
            @Field("month")String month
    );
    @FormUrlEncoded
    @POST("api/getmonthlysales")
    Call<List<GetSalesInMonthModel>> getAllSales(
            @Field("year")String year,
            @Field("month")String month
    );

}

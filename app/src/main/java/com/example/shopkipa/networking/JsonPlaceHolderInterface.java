package com.example.shopkipa.networking;

import com.example.shopkipa.models.AddExpenseModel;
import com.example.shopkipa.models.AddSaleModel;
import com.example.shopkipa.models.AddStockModel;
import com.example.shopkipa.models.GetAllGroupsModel;
import com.example.shopkipa.models.GetCategoriesModel;
import com.example.shopkipa.models.GetExpenseModel;
import com.example.shopkipa.models.GetGroupModel;
import com.example.shopkipa.models.GetSalesModel;
import com.example.shopkipa.models.GetSizeModel;
import com.example.shopkipa.models.GetStockInTypeModel;
import com.example.shopkipa.models.GetTypesInCategoryModel;
import com.example.shopkipa.models.GetTypesInGroupModel;
import com.example.shopkipa.models.GetTypesModel;

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
    @GET("api/getcategories")
    Call<List<GetCategoriesModel>> getAllCategories();
    @GET("api/getalltypes")
    Call<List<GetTypesModel>> getAllTypes();
    @GET("api/getsizes")
    Call<List<GetSizeModel>> getAllSizes();
    @GET("api/getgroups")
    Call<List<GetAllGroupsModel>> getAllGroups();
    @GET("api/getsales")
    Call<GetSalesModel> getsale();
    @GET("api/getexpense")
    Call<List<GetExpenseModel>> getAllExpenses();

}

package com.example.shopkipa.networking;

import com.example.shopkipa.models.AddExpenseModel;
import com.example.shopkipa.models.AddGivenStockModel;
import com.example.shopkipa.models.AddObscoleteModel;
import com.example.shopkipa.models.AddSaleModel;
import com.example.shopkipa.models.AddShoppingListModel;
import com.example.shopkipa.models.AddStockModel;
import com.example.shopkipa.models.ChangeDetailsModel;
import com.example.shopkipa.models.ChangedForgotPassModel;
import com.example.shopkipa.models.ConfirmPhoneChangeCodeModel;
import com.example.shopkipa.models.DeleteSaleModel;
import com.example.shopkipa.models.EditQuantityModel;
import com.example.shopkipa.models.GetItemImagesModel;
import com.example.shopkipa.models.SendSignUpCode;
import com.example.shopkipa.models.ForgotPasswordModel;
import com.example.shopkipa.models.GenerateCodeModel;
import com.example.shopkipa.models.DeleteExpenseModel;
import com.example.shopkipa.models.DeleteItemModel;
import com.example.shopkipa.models.EditStockModel;
import com.example.shopkipa.models.GetAllGroupsModel;
import com.example.shopkipa.models.GetBuyingPricesModel;
import com.example.shopkipa.models.GetCategoriesModel;
import com.example.shopkipa.models.GetExpenseModel;
import com.example.shopkipa.models.GetFaqsModel;
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
import com.example.shopkipa.models.RestockModel;
import com.example.shopkipa.models.SendMessageModel;
import com.example.shopkipa.models.SignUpMessagesModel;
import com.example.shopkipa.models.SuggestedRestockModel;
import com.example.shopkipa.models.UsersModel;
import com.example.shopkipa.models.ViewAdsModel;
import com.example.shopkipa.models.ViewGivenStockModel;
import com.example.shopkipa.models.ViewObscoleteStockModel;
import com.example.shopkipa.models.ViewShoppingListModel;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

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
            @Field("image")String image
    );
    @FormUrlEncoded
    @POST("api/auth/signup")
    Call<UsersModel> signUp(
            @Field("first_name")String firstname,
            @Field("last_name")String lastname,
            @Field("username")String username,
            @Field("location")String location,
            @Field("phone")String phone,
            @Field("password")String password,
            @Field("password_confirmation")String confirmPassword,
            @Field("code") String code
    );
    @FormUrlEncoded
    @POST("api/signupcode")
    Call<SendSignUpCode> signUpCode(
            @Field("phone")String phone,
            @Field("appSignature")String appSignature
    );
    @FormUrlEncoded
    @POST("api/checkifexist")
    Call<SignUpMessagesModel> checkIfExist(
            @Field("phone")String phone,
            @Field("username")String username
    );
    @FormUrlEncoded
    @POST("api/auth/login")
    Call<UsersModel> login(
            @Field("username")String username,
            @Field("password")String password
    );
    @FormUrlEncoded
    @POST("api/editstock")
    Call<EditStockModel> editStock(
            @Field("name")String name,
            @Field("sellingprice")String sellingprice,
            @Field("item_id")String item_id,
            @Field("quantity")String quantity
    );
    @FormUrlEncoded
    @POST("api/restock")
    Call<RestockModel> restock(
            @Field("quantity")String quantity,
            @Field("buyingp")String buyingprice,
            @Field("item_id")String item_id
    );
    @FormUrlEncoded
    @POST("api/restock")
    Call<SendMessageModel> sendMessage(
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
    @POST("api/deletesale")
    Call<DeleteSaleModel> deleteSale(
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
            @Field("costprice")String costprice,
            @Field("buyingprice") String bp,
            @Field("item_id")String itemid
    );
    @FormUrlEncoded
    @POST("api/gettypeitem")
    Call<List<GetStockInTypeModel>> getAllStock(
            @Field("nametype")String typename,
            @Field("namecategory")String category
    );
    @FormUrlEncoded
    @POST("api/suggestedrestock")
    Call<List<SuggestedRestockModel>> getSuggestedRestock(
            @Field("namecategory")String category
    );
    @FormUrlEncoded
    @POST("api/getcategorytype")
    Call<List<GetTypesInCategoryModel>> getTypes(
            @Field("namecategory")String categoryname,
            @Field("namegroup")String groupname
    );
    @FormUrlEncoded
    @POST("api/generatecode")
    Call<GenerateCodeModel> generateCode(
            @Field("oldphone")String oldphone,
            @Field("appSignature")String appSignature
    );
    @FormUrlEncoded
    @POST("api/checkphone")
    Call<SignUpMessagesModel> checkIfNoCorrect(
            @Field("oldphone")String oldphone,
            @Field("pas")String pas,
            @Field("newphone")String newnumber
    );
    @FormUrlEncoded
    @POST("api/changephone")
    Call<ConfirmPhoneChangeCodeModel> changePhone(
            @Field("newphone")String newphone,
            @Field("oldphone")String oldphone,
            @Field("code")String code
    );
    @FormUrlEncoded
    @POST("api/changepassword")
    Call<ChangedForgotPassModel> changePassword(
            @Field("newpass")String newPass,
            @Field("oldpass")String oldPass
    );
    @FormUrlEncoded
    @POST("api/changedetails")
    Call<ChangeDetailsModel> changeDetails(
            @Field("username")String username,
            @Field("firstname")String firstname,
            @Field("lastname")String lastname,
            @Field("location")String location
    );
    @FormUrlEncoded
    @POST("api/sendcode")
    Call<ForgotPasswordModel> sendForgotCode(
            @Field("phone")String newPass
    );
    @FormUrlEncoded
    @POST("api/newpassword")
    Call<ChangedForgotPassModel> newPass(
            @Field("code")String code,
            @Field("newpass")String newPass,
            @Field("phone")String phone
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
    @FormUrlEncoded
    @POST("api/getbuyingprices")
    Call<List<GetBuyingPricesModel>> getBP(
            @Field("item_id") String i
    );
    @GET("api/getcategories")
    Call<List<GetCategoriesModel>> getAllCategories();
    @GET("api/getalltypes")
    Call<List<GetTypesModel>> getAllTypes();
    @GET("api/getsizes")
    Call<List<GetSizeModel>> getAllSizes();
    @GET("api/getgroups")
    Call<List<GetAllGroupsModel>> getAllGroups();
    @GET("api/auth/logout")
    Call<SignUpMessagesModel> logOut();

    @GET("api/getqa")
    Call<List<GetFaqsModel>> getFaqs();

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
    @FormUrlEncoded
    @POST("api/images")
    Call<List<GetItemImagesModel>> getImages(
            @Field("item_id")String item_id
    );
    //shopping
    @FormUrlEncoded
    @POST("api/addshoppinglist")
    Call<AddShoppingListModel> addshoppinglist(
            @Field("item_id")String itemid,
            @Field("quantity")String shopQ
    );
    @FormUrlEncoded
    @POST("api/getshoppinglist")
    Call<List<ViewShoppingListModel>> getShoppingList(
            @Field("namecategory")String category
    );
    @FormUrlEncoded
    @POST("api/deletefromshoppinglist")
    Call<DeleteItemModel> deleteShopping(
            @Field("id")String item_id
    );
   // givenstock
   @FormUrlEncoded
   @POST("api/addtogiven")
   Call<AddGivenStockModel> addgiven(
           @Field("item_id")String itemid,
           @Field("quantity")String givenQ
   );
    @FormUrlEncoded
    @POST("api/getgivenlist")
    Call<List<ViewGivenStockModel>> getGiven(
            @Field("namecategory")String category
    );
    @FormUrlEncoded
    @POST("api/deletefromgiven")
    Call<DeleteItemModel> deleteGiven(
            @Field("id")String item_id
    );

   //obscolete
   @FormUrlEncoded
   @POST("api/addtoobscolete")
   Call<AddObscoleteModel> addobsc(
           @Field("item_id")String itemid,
           @Field("quantity")String obscQ
   );
    @FormUrlEncoded
    @POST("api/getobscoletelist")
    Call<List<ViewObscoleteStockModel>> getObscolete(
            @Field("namecategory")String category
    );
    @FormUrlEncoded
    @POST("api/deletefromobscolete")
    Call<DeleteItemModel> deleteObsc(
            @Field("id")String item_id
    );
    //edit shoppinglist,given,obscolete
    @FormUrlEncoded
    @POST("api/editshopping")
    Call<EditQuantityModel> editS(
            @Field("quantity")String qq,
            @Field("id")String id
    );
    @FormUrlEncoded
    @POST("api/editgiven")
    Call<EditQuantityModel> editG(
            @Field("quantity")String qq,
            @Field("id")String id
    );
    @FormUrlEncoded
    @POST("api/editobscolete")
    Call<EditQuantityModel> editO(
            @Field("quantity")String qq,
            @Field("id")String id
    );

    //ads
    @GET("api/getads")
    Call<List<ViewAdsModel>> getAds();

    //testing
    @Multipart
    @POST("api/sendmessage")
    Call<ResponseBody>upload(@Part MultipartBody.Part file,@Part("image")RequestBody requestBody);
}

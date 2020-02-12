package com.example.shopkipa.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ViewObscoleteStockModel {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("user_id")
    @Expose
    private Integer userId;
    @SerializedName("category_id")
    @Expose
    private String categoryId;
    @SerializedName("type_id")
    @Expose
    private Integer typeId;
    @SerializedName("item_group_id")
    @Expose
    private Integer itemGroupId;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("shopping_list")
    @Expose
    private Object shoppingList;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    @SerializedName("color")
    @Expose
    private String color;
    @SerializedName("design")
    @Expose
    private String design;
    @SerializedName("company")
    @Expose
    private String company;
    @SerializedName("size")
    @Expose
    private String size;
    @SerializedName("quantity")
    @Expose
    private Integer quantity;
    @SerializedName("sellingprice")
    @Expose
    private String sellingprice;
    @SerializedName("image")
    @Expose
    private String image;
    @SerializedName("purchaseId")
    @Expose
    private Integer purchaseId;
    @SerializedName("typeName")
    @Expose
    private String typeName;
    @SerializedName("obscoleteId")
    @Expose
    private Integer obscoleteId;
    @SerializedName("obscoleteQuantity")
    @Expose
    private Integer obscoleteQuantity;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public Integer getTypeId() {
        return typeId;
    }

    public void setTypeId(Integer typeId) {
        this.typeId = typeId;
    }

    public Integer getItemGroupId() {
        return itemGroupId;
    }

    public void setItemGroupId(Integer itemGroupId) {
        this.itemGroupId = itemGroupId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Object getShoppingList() {
        return shoppingList;
    }

    public void setShoppingList(Object shoppingList) {
        this.shoppingList = shoppingList;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getDesign() {
        return design;
    }

    public void setDesign(String design) {
        this.design = design;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getSellingprice() {
        return sellingprice;
    }

    public void setSellingprice(String sellingprice) {
        this.sellingprice = sellingprice;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Integer getPurchaseId() {
        return purchaseId;
    }

    public void setPurchaseId(Integer purchaseId) {
        this.purchaseId = purchaseId;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public Integer getObscoleteId() {
        return obscoleteId;
    }

    public void setObscoleteId(Integer obscoleteId) {
        this.obscoleteId = obscoleteId;
    }

    public Integer getObscoleteQuantity() {
        return obscoleteQuantity;
    }

    public void setObscoleteQuantity(Integer obscoleteQuantity) {
        this.obscoleteQuantity = obscoleteQuantity;
    }
}

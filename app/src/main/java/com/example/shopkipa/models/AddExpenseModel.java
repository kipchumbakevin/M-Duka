package com.example.shopkipa.models;

public class AddExpenseModel {
    String expensetype,amount;

    public AddExpenseModel() {
    }

    public String getExpensetype() {
        return expensetype;
    }

    public void setExpensetype(String expensetype) {
        this.expensetype = expensetype;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }
}

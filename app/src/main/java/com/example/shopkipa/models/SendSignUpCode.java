package com.example.shopkipa.models;

public class SendSignUpCode {
    public String phone;

    public SendSignUpCode(String phone, String appSignature) {
        this.phone = phone;
        this.appSignature = appSignature;
    }

    public String getPhoneNumber() {
        return phone;
    }

    public void setPhoneNumber(String phone_number) {
        this.phone = phone_number;
    }

    public String getAppSignature() {
        return appSignature;
    }

    public void setAppSignature(String appSignature) {
        this.appSignature = appSignature;
    }

    public String appSignature;
}

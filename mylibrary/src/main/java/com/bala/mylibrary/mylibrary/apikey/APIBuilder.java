package com.bala.mylibrary.mylibrary.apikey;

import android.content.Context;

public class APIBuilder {

    private String appSecret;
    private String apiKey;
    private Context context;

    public APIBuilder(String appSecret, String apiKey, Context context) {
        this.appSecret = appSecret;
        this.apiKey = apiKey;
        this.context = context;
    }
    public APIBuilder setSecret(String appSecret) {
        this.appSecret = appSecret;
        return this;
    }
    public APIBuilder setAPIKey(String apiKey) {
        this.apiKey = apiKey;
        return this;
    }
    public APIBuilder setContext(Context context) {
        this.context = context;
        return this;
    }

    public APIBuilder()
    {

    }

    public APIKey build() {
        return new APIKey(appSecret,apiKey,context);
    }

}

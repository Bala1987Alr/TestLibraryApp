package com.bala.mylibrary.mylibrary.apikey;

import android.content.Context;

import com.bala.mylibrary.mylibrary.Payment.Payment;
import com.bala.mylibrary.mylibrary.exceptions.PaymentException;
import com.bala.mylibrary.mylibrary.exceptions.SecretKeyException;


public class APIKey{

    private String appSecret;
    private String apiKey;
    private Context context;

    public Payment getPayment() {
        return payment;
    }

    public void setPayment(Payment payment) {
        this.payment = payment;
    }

    private Payment payment;


    public APIKey(String appSecret, String apiKey)
    {
        this.appSecret=appSecret;
        this.apiKey=apiKey;
    }
    public APIKey(String appSecret, String apiKey,Context context)
    {
        this.appSecret=appSecret;
        this.apiKey=apiKey;
        this.context=context;

    }

    public String getAppSecret() {
        return appSecret;
    }

    public void setAppSecret(String appSecret) {

        this.appSecret = appSecret;
    }

    public String getApiKey() {

        return apiKey;
    }

    public void setApiKey(String apiKey) {

        this.apiKey = apiKey;
    }

    public Context getContext() {

        return context;
    }

    public void setContext(Context context) {

        this.context = context;
    }

    public interface PaymentInterface {

        public void onPaymentSuccess() throws PaymentException;
        public void onPaymentFailed() throws PaymentException;
        public void onPaymentCancelled() throws PaymentException;

    }
    private PaymentInterface paymentInterface;

    public void doPayment(PaymentInterface paymentInterface)
    {

        this.paymentInterface=paymentInterface;

        try {
            if(checkAPIKey(this.appSecret, this.apiKey))
            {
                paymentToMerchant();

            }else
            {
                paymentInterface.onPaymentFailed();
            }
        } catch (PaymentException e)
        {
            e.printStackTrace();
        }


    }

    private boolean checkAPIKey(String appSecret, String apiKey)
    {
        //validate against server that it is valid or not
        if(appSecret == null || apiKey==null || appSecret.length() ==0 || apiKey.length()==0)
        {
            try {
                throw new SecretKeyException("Invalid app secret ot invalid key");
            } catch (SecretKeyException e) {
                e.printStackTrace();
            }
        }
        else if(appSecret.equals("1") && appSecret.equals("1"))
        {
            return true;
        }
        return false;
    }

    private void paymentToMerchant() throws PaymentException
    {   //Add conditions whatever you want to make the transaction cancel
        if(payment.getTotal_amout()<0)
        {
            paymentInterface.onPaymentCancelled();
        }
        //Add conditions whatever you want to make the transaction failed
        else if(payment.getMerchant_id()==null || payment.getMerchant_id().length()==0)
        {

            paymentInterface.onPaymentFailed();
        }
        //Add conditions whatever you want to make the transaction success
        else
        {
            paymentInterface.onPaymentSuccess();
        }


    }
}

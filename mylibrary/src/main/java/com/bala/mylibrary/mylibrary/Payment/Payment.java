package com.bala.mylibrary.mylibrary.Payment;

public class Payment {

    public int getTotal_amout() {
        return total_amout;
    }

    public void setTotal_amout(int total_amout) {
        this.total_amout = total_amout;
    }

    public String getPayment_title() {
        return payment_title;
    }

    public void setPayment_title(String payment_title) {
        this.payment_title = payment_title;
    }

    public String getPayemt_qty() {
        return payemt_qty;
    }

    public void setPayemt_qty(String payemt_qty) {
        this.payemt_qty = payemt_qty;
    }

    public String getPayment_reference_number() {
        return payment_reference_number;
    }

    public void setPayment_reference_number(String payment_reference_number) {
        this.payment_reference_number = payment_reference_number;
    }

    public String getMerchant_id() {
        return merchant_id;
    }

    public void setMerchant_id(String merchant_id) {
        this.merchant_id = merchant_id;
    }

    private int total_amout;
    private String payment_title;
    private String payemt_qty;
    private String payment_reference_number;
    private String merchant_id;

    public Payment()
    {

    }
    public Payment(int total_amout,String payment_title,String payemt_qty,String payment_reference_number,String merchant_id)
    {
        this.total_amout = total_amout;
        this.payment_title = payment_title;
        this.payemt_qty = payemt_qty;
        this.payment_reference_number = payment_reference_number;
        this.merchant_id = merchant_id;


    }



}

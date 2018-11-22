package com.bala.mylibrary.mylibrary.Payment;

public class PaymentBuilder
{

    private int total_amout;
    private String payment_title;
    private String payemt_qty;
    private String payment_reference_number;
    private String merchant_id;

    public PaymentBuilder(int total_amout,String payment_title,String payemt_qty,String payment_reference_number,String merchant_id) {
        this.total_amout = total_amout;
        this.payment_title = payment_title;
        this.payemt_qty = payemt_qty;
        this.payment_reference_number = payment_reference_number;
        this.merchant_id = merchant_id;
    }
    public PaymentBuilder setTotalAmout(int total_amout) {
        this.total_amout = total_amout;
        return this;
    }
    public PaymentBuilder setPaymentTitle(String payment_title) {
        return this;
    }
    public PaymentBuilder setPayemtQty(String payemt_qty) {
        this.payemt_qty = payemt_qty;
        return this;
    }
    public PaymentBuilder setPaymentReferenceNumber(String payment_reference_number) {
        this.payment_reference_number = payment_reference_number;
        return this;
    }
    public PaymentBuilder setMerchantId(String merchant_id) {
        this.merchant_id = merchant_id;
        return this;
    }
    public PaymentBuilder()
    {

    }

    public Payment build() {
        return new Payment(total_amout,payment_title,payemt_qty,payment_reference_number,merchant_id);
    }
}

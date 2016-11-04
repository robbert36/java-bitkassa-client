package me.vante.bitkassa.model;

import lombok.Data;

import java.math.BigInteger;

/**
 * Created by robbertcoeckelbergh on 2/11/16.
 */

@Data
public class InvoiceRequest {
    private String action;
    private String merchant_id;
    private String currency;
    private BigInteger amount; //value in satoshi
    private String description;
    private String return_url;
    private String update_url;
    private String meta_info;

    public InvoiceRequest(BigInteger amount, String currency) {
        this.amount = amount;
        this.currency = currency;
    }

    public InvoiceRequest(BigInteger amount, String currency, String description) {
        this.amount = amount;
        this.currency = currency;
        this.description = description;
    }

    public InvoiceRequest(BigInteger amount, String currency, String description, String return_url) {
        this.amount = amount;
        this.currency = currency;
        this.description = description;
        this.return_url = return_url;
    }

    public InvoiceRequest(BigInteger amount, String currency, String description, String return_url, String update_url) {
        this.amount = amount;
        this.currency = currency;
        this.description = description;
        this.return_url = return_url;
        this.update_url = update_url;
    }

    public InvoiceRequest(BigInteger amount, String currency, String description, String return_url, String update_url, String meta_info) {
        this.amount = amount;
        this.currency = currency;
        this.description = description;
        this.return_url = return_url;
        this.update_url = update_url;
        this.meta_info = meta_info;
    }


    //Getters and Setters
    public void setAction(String action) {
        this.action = action;
    }

    public void setMerchant_id(String merchant_id) {
        this.merchant_id = merchant_id;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public void setAmount(BigInteger amount) {
        this.amount = amount;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setReturn_url(String return_url) {
        this.return_url = return_url;
    }

    public void setUpdate_url(String update_url) {
        this.update_url = update_url;
    }

    public void setMeta_info(String meta_info) {
        this.meta_info = meta_info;
    }

    public String getAction() {
        return action;
    }

    public String getMerchant_id() {
        return merchant_id;
    }

    public String getCurrency() {
        return currency;
    }

    public BigInteger getAmount() {
        return amount;
    }

    public String getDescription() {
        return description;
    }

    public String getReturn_url() {
        return return_url;
    }

    public String getUpdate_url() {
        return update_url;
    }

    public String getMeta_info() {
        return meta_info;
    }
}

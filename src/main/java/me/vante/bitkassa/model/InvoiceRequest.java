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
}
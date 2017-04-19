package me.vante.bitkassa.model;

import lombok.Data;

import java.math.BigInteger;


/**
 * Created by robbertcoeckelbergh on 3/11/16.
 */

@Data
public class Invoice {
    private String payment_id;
    private String payment_url;
    private String address;
    private BigInteger amount;
    private String bitcoin_url;
    private int expire;
    private Boolean success;
    private String error;
}

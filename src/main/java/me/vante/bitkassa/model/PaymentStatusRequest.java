package me.vante.bitkassa.model;

import lombok.Data;

/**
 * Created by robbertcoeckelbergh on 4/11/16.
 */

@Data
public class PaymentStatusRequest {

    private String action;
    private String merchant_id;
    private String payment_id;
}

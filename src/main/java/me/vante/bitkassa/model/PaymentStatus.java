package me.vante.bitkassa.model;

import lombok.Data;

/**
 * Created by robbertcoeckelbergh on 4/11/16.
 */

@Data
public class PaymentStatus {
    private String payment_status;
    private Boolean success;
    private String error;
}

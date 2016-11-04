package me.vante.bitkassa.model;

import lombok.Data;

/**
 * Created by robbertcoeckelbergh on 4/11/16.
 */

@Data
public class InvoiceUpdate {
    private String payment_id;
    private String payment_status;
    private String meta_info;
}

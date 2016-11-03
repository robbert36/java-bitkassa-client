package me.vante.bitkassa.controller;

import me.vante.bitkassa.model.Actions;
import me.vante.bitkassa.model.Invoice;
import org.restlet.Client;
import org.restlet.Request;
import org.restlet.data.Protocol;

import java.util.HashMap;
import java.util.Map;


/**
 * Created by robbertcoeckelbergh on 2/11/16.
 */
public class Bitkassa {
    public static final String BITKASSA_URL = "  https://www.bitkassa.nl/api/v1";

    private String _merchantId = "";
    private String _secretAPIKey = "";
    private String _apiUrl = BITKASSA_URL;

    public Bitkassa(String merchantId) {
        _merchantId = merchantId;
    }

    public Bitkassa(String merchantId, String secretAPIKey) {
        this(merchantId);
        _secretAPIKey=secretAPIKey;
    }

    public Bitkassa(String merchantId, String secretAPIKey, String apiUrl) {
        this(merchantId, secretAPIKey);
        _apiUrl = apiUrl;
    }

   
}

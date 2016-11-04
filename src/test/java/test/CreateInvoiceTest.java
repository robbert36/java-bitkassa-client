package test;

import me.vante.bitkassa.controller.Bitkassa;
import me.vante.bitkassa.model.BitkassaException;
import me.vante.bitkassa.model.InvoiceRequest;
import me.vante.bitkassa.model.Invoice;

import java.math.BigInteger;

/**
 * Created by robbertcoeckelbergh on 3/11/16.
 */


public class CreateInvoiceTest {
    private static final String API_SECRET = "abcdefgh";
    private static final String API_URL = "https://localhost:80/api";
    private static final String RETURN_URL = "https://localhost:80/return";
    private static final String UPDATE_URL = "https://localhost:80/update";

    private static final String MERCHANT_ID = "merchant";


    public void createInvoiceFakeTest() {
        //Bitkassa bitkassa = new Bitkassa(MERCHANT_ID, API_SECRET, API_URL);
        Bitkassa bitkassa = new Bitkassa(MERCHANT_ID, API_SECRET, API_URL);
        InvoiceRequest invoiceRequest = new InvoiceRequest(new BigInteger("10000"), "BTC", "Testinvoice", RETURN_URL, UPDATE_URL, "TestMetainfo");
        try {
            Invoice response = bitkassa.createInvoice(invoiceRequest);
        } catch (BitkassaException e) {
            e.printStackTrace();
        }
    }

}

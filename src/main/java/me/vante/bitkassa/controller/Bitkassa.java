package me.vante.bitkassa.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import me.vante.bitkassa.model.*;
import org.apache.commons.codec.binary.Hex;
import org.bouncycastle.util.encoders.Base64;
import org.restlet.Client;
import org.restlet.Request;
import org.restlet.Response;
import org.restlet.data.*;
import org.restlet.representation.Representation;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;

/**
 * Created by robbertcoeckelbergh on 2/11/16.
 */

public class Bitkassa {

    public static final String BITKASSA_URL = "https://www.bitkassa.nl/api/v1";

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

    public Invoice createInvoice(InvoiceRequest invoiceRequest) throws APIException {
        invoiceRequest.setAction(Action.START_PAYMENT);
        invoiceRequest.setMerchant_id(_merchantId);

        //Input check
        if (invoiceRequest.getAmount() == null || invoiceRequest.getAmount().compareTo(BigInteger.ZERO) == -1 ||
                invoiceRequest.getCurrency() == null || (! invoiceRequest.getCurrency().equals("BTC") && ! invoiceRequest.getCurrency().equals("EUR"))) {
            throw new APIException("Invalid input to create an invoice");
        }

        Representation representation = callBitkassaApi(invoiceRequest);
        if (representation == null) {
            throw new APIException("The resulting representation of the api call is null");
        }

        String response_text = null;
        try {
            response_text = representation.getText();
        } catch (IOException e) {
            e.printStackTrace();
            throw new APIException(e.getMessage());
        }
        if (response_text == null) {
            throw new APIException("Could not extract the text from the representation");
        }

        System.out.println("response_text: " + response_text);
        ObjectMapper mapper = new ObjectMapper();
        Invoice invoice = null;
        try {
            invoice = mapper.readValue(response_text, Invoice.class);
        } catch (IOException e) {
            e.printStackTrace();
            throw new APIException(e.getMessage());
        }

        if (invoice.getSuccess() != true)
        {
            throw new APIException(invoice.getError());
        }

        return invoice;
    }

    public boolean verifyAuthentication(String authentication, String raw_data) {
        byte[] data_bytes = Base64.decode(raw_data);
        String data = new String(data_bytes);

        String unixtime = authentication.substring(64);
        String verify = generateAuthentication(data, unixtime);

        boolean authenticated = verify.equalsIgnoreCase(authentication);

        return authenticated;
    }

    public PaymentStatus getPaymentStatus(String paymentId) throws APIException {
        if (paymentId == null)
        {
            throw new APIException("Invalid input to get payment status");
        }

        PaymentStatusRequest request = new PaymentStatusRequest();
        request.setAction(Action.PAYMENT_STATUS);
        request.setMerchant_id(_merchantId);
        request.setPayment_id(paymentId);

        Representation representation = callBitkassaApi(request);

        if (representation == null) {
            throw new APIException("The resulting representation of the api call is null");
        }

        String response_text = null;
        try {
            response_text = representation.getText();
        } catch (IOException e) {
            e.printStackTrace();
            throw new APIException(e.getMessage());
        }

        if (response_text == null) {
            throw new APIException("Could not extract the text from the representation");
        }
        System.out.println("response_text: " + response_text);

        ObjectMapper mapper = new ObjectMapper();
        PaymentStatus status = null;
        try {
            status = mapper.readValue(response_text, PaymentStatus.class);
        } catch (IOException e) {
            e.printStackTrace();
            throw new APIException(e.getMessage());
        }

        if (status.getSuccess() != true)
        {
            throw new APIException(status.getError());
        }

        return status;
    }

    public String generateAuthentication(String data) {
        return Authentication.generateAuthentication(_secretAPIKey, data);
    }

    public String generateAuthentication(String data, String unixtime) {
        return Authentication.generateAuthentication(_secretAPIKey, data, unixtime);
    }


    Representation callBitkassaApi(Object object) throws APIException {
        ObjectMapper mapper = new ObjectMapper();

        byte[] dataJSONBytes = new byte[0];
        try {
            dataJSONBytes = mapper.writeValueAsBytes(object);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new APIException(e.getMessage());
        }

        String dataJSONRaw = new String(dataJSONBytes);
        String dataJSON = new String(Base64.encode(dataJSONBytes));
        System.out.println("DataJSONRaw: " + dataJSONRaw);
        System.out.println("DataJSON: " + dataJSON);

        String authentication = generateAuthentication(dataJSONRaw);
        System.out.println("Authentication: " + authentication);

        Client client = new Client(Protocol.HTTPS);
        Request request = new Request();

        Reference reference = new Reference(_apiUrl);
        reference.addQueryParameter("p", dataJSON);
        reference.addQueryParameter("a", authentication);

        request.setResourceRef(reference);
        request.setMethod(Method.POST);

        request.getClientInfo().getAcceptedMediaTypes().add(new Preference<MediaType>(MediaType.APPLICATION_JSON));

        Response response = client.handle(request);
        Representation representation = response.getEntity();
        if(representation == null) {
            throw new APIException("Did not get a valid response from the API");
        }

        return representation;
    }


}

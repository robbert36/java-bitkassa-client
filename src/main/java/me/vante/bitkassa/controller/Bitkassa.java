package me.vante.bitkassa.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import me.vante.bitkassa.model.APIException;
import me.vante.bitkassa.model.Actions;
import me.vante.bitkassa.model.InvoiceRequest;
import me.vante.bitkassa.model.Invoice;
import org.bouncycastle.util.encoders.Base64;
import org.restlet.Client;
import org.restlet.Request;
import org.restlet.Response;
import org.restlet.data.MediaType;
import org.restlet.data.Method;
import org.restlet.data.Preference;
import org.restlet.data.Protocol;
import org.restlet.representation.Representation;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
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

    public Invoice createInvoice(InvoiceRequest invoiceRequest) throws APIException, IOException {
        invoiceRequest.set_action(Actions.START_PAYMENT);
        invoiceRequest.set_merchant_id(_merchantId);

        Representation representation = callBitkassaApi(invoiceRequest);
        String response_text = representation.getText();

        ObjectMapper mapper = new ObjectMapper();
        Invoice invoice = mapper.readValue(representation.getText(), Invoice.class);


        return invoice;
    }

    private String generateAuthentication(String secretAPIKey, String data) {
        String unixtime;
        MessageDigest md;

        Date now = new Date();
        Long longTime = new Long(now.getTime()/1000);
        unixtime = longTime.toString();

        StringBuilder builder = new StringBuilder();
        builder.append(secretAPIKey);
        builder.append(data);
        builder.append(unixtime);

        System.out.println("Authentication digest: "+ builder.toString());

        try {
            md = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return "";
        }

        try {
            md.update(builder.toString().getBytes("UTF-8")); // Change this to "UTF-16" if needed
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return "";
        }

        byte[] digest = md.digest();
        String digestString = new String(Base64.encode(digest));

        return digestString + unixtime;
    }


    Representation callBitkassaApi(Object object) throws IOException, APIException {
        Client client = new Client(Protocol.HTTPS);
        Request request = new Request();

        request.setResourceRef(_apiUrl);
        request.setMethod(Method.POST);

        request.getClientInfo().getAcceptedMediaTypes().add(new Preference<MediaType>(MediaType.APPLICATION_JSON));

        ObjectMapper mapper = new ObjectMapper();

        byte[] dataJSONBytes = mapper.writeValueAsBytes(object);
        String dataJSON = new String(Base64.encode(dataJSONBytes));

        System.out.println("invoiceJSON: " + dataJSON);

        String authentication = generateAuthentication(_secretAPIKey, dataJSON);
        System.out.println("Authentication: " + authentication);

        Map<String, Object> attributes = new HashMap<String, Object>();
        attributes.put("p", dataJSON);
        attributes.put("a", authentication);

        request.setAttributes(attributes);
        Response response = client.handle(request);
        Representation representation = response.getEntity();
        if(representation == null) {
            throw new APIException("Did not get a valid response from the API");
        }

        representation.write(System.out);

        return null;
    }

}

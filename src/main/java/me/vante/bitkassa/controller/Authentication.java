package me.vante.bitkassa.controller;

import org.apache.commons.codec.binary.Hex;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;

/**
 * Created by robbertcoeckelbergh on 4/11/16.
 */
public class Authentication {
    protected static String generateAuthentication(String secretAPIKey,String data) {
        String unixtime;

        Date now = new Date();
        Long longTime = new Long(now.getTime()/1000);
        unixtime = longTime.toString();

        return generateAuthentication(data, unixtime);
    }

    protected static String generateAuthentication(String secretAPIKey, String data, String unixtime) {
        MessageDigest md;

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
        String digestString = Hex.encodeHexString(digest);

        return digestString + unixtime;
    }
}

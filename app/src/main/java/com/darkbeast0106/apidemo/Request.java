package com.darkbeast0106.apidemo;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class Request {
    public static final String BASE_URL = "https://retoolapi.dev/NXu6yc/auto";

    public static String getData() throws IOException {
        URL url = new URL(BASE_URL);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setRequestProperty("Accept", "application/json");
        int responseCode = connection.getResponseCode();
        if (responseCode != 200){
            throw new RuntimeException("Hiba történt : hibakód : "+ responseCode);
        }
        BufferedReader br = new BufferedReader(new InputStreamReader(
                (connection.getInputStream())));

        StringBuilder builder = new StringBuilder();
        String output;
        while ((output = br.readLine()) != null) {
            builder.append(output);
            builder.append(System.lineSeparator());
        }
        connection.disconnect();
        return builder.toString().trim();
    }

    public static String postData(String kuldendoAdat) throws IOException {
        URL url = new URL(BASE_URL);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setDoOutput(true);
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Accept", "application/json");
        //Felvett tartalomhoz képest a hiba- ContentType helyett Content-Type kell
        connection.setRequestProperty("Content-Type", "application/json");

        OutputStream outStream = connection.getOutputStream();
        outStream.write(kuldendoAdat.getBytes());
        outStream.flush();

        int responseCode = connection.getResponseCode();
        if (responseCode != HttpURLConnection.HTTP_CREATED){
            throw new RuntimeException("Hiba történt : hibakód : "+ responseCode);
        }
        BufferedReader br = new BufferedReader(new InputStreamReader(
                (connection.getInputStream())));

        StringBuilder builder = new StringBuilder();
        String output;
        while ((output = br.readLine()) != null) {
            builder.append(output);
            builder.append(System.lineSeparator());
        }
        connection.disconnect();
        return builder.toString().trim();
    }

}

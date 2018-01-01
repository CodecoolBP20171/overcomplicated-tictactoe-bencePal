package com.codecool.enterprise.overcomplicated.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import javax.net.ssl.HttpsURLConnection;

@Service
public class FunFactService {

    private String readFromUrl(String endpoint) throws IOException {
        URL url;
        url = new URL(endpoint);
        HttpsURLConnection con = (HttpsURLConnection)url.openConnection();
        con.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95 Safari/537.11");
        con.connect();

        BufferedReader br =
                new BufferedReader(
                        new InputStreamReader(con.getInputStream()));


        String input = br.readLine();
        br.close();
        return input;
    }

    public String getRandomChuckNorrisJoke() {
        try {
            String url = "https://api.chucknorris.io/jokes/random";
            String result = readFromUrl(url);
            JSONObject json = new JSONObject(result);
            try {
                return json.get("value").toString();
            } catch (JSONException e) {
                return "";
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }
}
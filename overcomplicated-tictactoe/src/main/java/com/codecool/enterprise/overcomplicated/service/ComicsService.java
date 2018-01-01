package com.codecool.enterprise.overcomplicated.service;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Random;

@Service
public class ComicsService {

    @Autowired
    ReadFromURLService readFromURLService;

    public String getRandomComics() {
        Random r = new Random();
        int randomNumber = r.nextInt((1929 - 1) + 1) + 1;
        String url = String.format("https://xkcd.com/%s/info.0.json", randomNumber);
        String result = readFromURLService.readFromUrl(url);
        JSONObject json = new JSONObject(result);
        try {
            return json.get("img").toString();
        } catch (JSONException e) {
            return "";
        }

    }
}

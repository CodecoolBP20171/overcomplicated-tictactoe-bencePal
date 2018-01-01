package com.codecool.enterprise.overcomplicated.service;

import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

@Service
public class ReadFromURLService {
    public String readFromUrl(String endpoint) {
        try {
            URL url = new URL(endpoint);
            BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
            String inputLine = in.readLine();
            in.close();
            return inputLine;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }
}

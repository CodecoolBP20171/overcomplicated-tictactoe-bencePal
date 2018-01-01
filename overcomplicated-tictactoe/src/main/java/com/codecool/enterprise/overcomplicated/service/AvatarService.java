package com.codecool.enterprise.overcomplicated.service;

import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class AvatarService {

    public String getRandomAvatar() {
        Random r = new Random();
        int randomNumber = r.nextInt((1929 - 1) + 1) + 1;
        return String.format("https://robohash.org/%s", randomNumber);
    }
}

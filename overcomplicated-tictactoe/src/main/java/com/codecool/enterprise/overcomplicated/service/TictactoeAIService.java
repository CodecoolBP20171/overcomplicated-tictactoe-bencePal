package com.codecool.enterprise.overcomplicated.service;

import com.codecool.enterprise.overcomplicated.model.TictactoeField;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TictactoeAIService {

//    @Autowired
//    ReadFromURLService readFromURLService;

    public int getAIstep(String board) {
        ReadFromURLService readFromURLService = new ReadFromURLService();
        String url = String.format("http://tttapi.herokuapp.com/api/v1/%s/X", board);
        String result = readFromURLService.readFromUrl(url);
        JSONObject json = new JSONObject(result);
        try {
            return (int) json.get("recommendation") + 1;
        } catch (JSONException e) {
            return 0;
        }
    }

    public String convertArrayBoardToString(TictactoeField[][] board){
        String stringBoard = "";
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                if(board[i][j].getFieldValue() != "") {
                    stringBoard += board[i][j].getFieldValue();
                } else {
                    stringBoard += "-";
                }
            }
        }

        return stringBoard.toUpperCase();
    }

}


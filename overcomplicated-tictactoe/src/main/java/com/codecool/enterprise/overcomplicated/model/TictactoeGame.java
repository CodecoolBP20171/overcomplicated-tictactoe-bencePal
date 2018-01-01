package com.codecool.enterprise.overcomplicated.model;

import java.util.Objects;

public class TictactoeGame {

    private TictactoeField[][] board;
    private String placeMark;
    private String lastPlaceMark;

    public TictactoeGame() {
        board = new TictactoeField[3][3];
        initializeBoard();
        placeMark = "o";
    }

    public TictactoeField[][] getBoard() {
        return board;
    }

    public void initializeBoard() {
        int counter = 1;

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                board[i][j] = new TictactoeField("", counter);
                counter++;
            }
        }
    }

    public String getLastPlaceMark() {
        return lastPlaceMark;
    }

    public boolean isBoardFull() {
        boolean isFull = true;

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j].getFieldValue() == "") {
                    isFull = false;
                }
            }
        }

        return isFull;
    }

    public void placeMark(int place) {
        int counter = 1;

        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                if (counter == place && board[i][j].getFieldValue() == "") {
                    board[i][j].setFieldValue(placeMark);
                    lastPlaceMark = placeMark;
                }
                counter++;
            }
        }
        changePlaceMark();
    }

    private void changePlaceMark() {
        if (Objects.equals(placeMark, "o")) {
            placeMark = "x";
        } else {
            placeMark = "o";
        }
    }

    public boolean checkForWin() {
        return (checkRowsForWin() || checkColumnsForWin() || checkDiagonalsForWin());
    }


    // Loop through rows and see if any are winners.
    private boolean checkRowsForWin() {
        for (int i = 0; i < 3; i++) {
            if (checkRowCol(board[i][0].getFieldValue(), board[i][1].getFieldValue(),
                    board[i][2].getFieldValue())) {
                return true;
            }
        }
        return false;
    }

    // Loop through columns and see if any are winners.
    private boolean checkColumnsForWin() {
        for (int i = 0; i < 3; i++) {
            if (checkRowCol(board[0][i].getFieldValue(), board[1][i].getFieldValue(),
                    board[2][i].getFieldValue())) {
                return true;
            }
        }
        return false;
    }

    // Return true if either wins.
    private boolean checkDiagonalsForWin() {
        return ((checkRowCol(board[0][0].getFieldValue(), board[1][1].getFieldValue(),
                board[2][2].getFieldValue())) ||
                (checkRowCol(board[0][2].getFieldValue(), board[1][1].getFieldValue(),
                    board[2][0].getFieldValue())));
    }

    // Check to see if all three values are the same (and not empty) indicating a win.
    private boolean checkRowCol(String c1, String c2, String c3) {
        return ((c1 != "") && (c1 == c2) && (c2 == c3));
    }


}

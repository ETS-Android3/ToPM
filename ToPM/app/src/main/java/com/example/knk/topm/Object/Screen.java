package com.example.knk.topm.Object;

import java.util.ArrayList;
import java.util.HashMap;

public class Screen {

    int row;                // 행
    int col;                // 열
    int totalSeats;         // 총 좌석 개수
    String screenNum;       // 상영관 번호

    ArrayList buttonID;         // MyButton의 ID를 저장할 배열

    HashMap<String, Boolean> abledMap;
    HashMap<String, Boolean> bookedMap;
    HashMap<String, Boolean> specialMap;

    public Screen(int row, int col, String screenNum, ArrayList IDs) {
        // 변수 초기화
        this.row = row;
        this.col = col;

        buttonID = new ArrayList();
        this.buttonID = IDs;

        totalSeats = row * col;
        this.screenNum = screenNum;

        abledMap = new HashMap<>();
        bookedMap = new HashMap<>();
        specialMap = new HashMap<>();
    }

    public Screen() {
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getCol() {
        return col;
    }

    public void setCol(int col) {
        this.col = col;
    }


    public HashMap<String, Boolean> getAbledMap() {
        return abledMap;
    }

    public void setAbledMap(HashMap<String, Boolean> abledMap) {
        this.abledMap = abledMap;
    }

    public HashMap<String, Boolean> getBookedMap() {
        return bookedMap;
    }

    public void setBookedMap(HashMap<String, Boolean> bookedMap) {
        this.bookedMap = bookedMap;
    }

    public HashMap<String, Boolean> getSpecialMap() {
        return specialMap;
    }

    public void setSpecialMap(HashMap<String, Boolean> specialMap) {
        this.specialMap = specialMap;
    }

    public String getScreenNum() {
        return screenNum;
    }

    public ArrayList getButtonID() {
        return buttonID;
    }

    public void setButtonID(ArrayList buttonID) {
        this.buttonID = buttonID;
    }

    public void setScreenNum(String screenNum) {
        this.screenNum = screenNum;
    }

}

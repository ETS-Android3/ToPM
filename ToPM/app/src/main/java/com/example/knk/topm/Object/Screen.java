package com.example.knk.topm.Object;

public class Screen {

    int row;                // 행
    int col;                // 열
    int totalSeats;         // 총 좌석 개수
    boolean isAbled;        // 좌석인지 아닌지
    boolean isBooked;       // 예약 되었는지 아닌지
    boolean isSpecial;      // 우등석인지 아닌지
    String screenNum;       // 상영관 번호

    int[] ButtonID;         // MyButton의 ID를 저장할 배열

    /* 상수 */
    final static boolean ABLED = true;
    final static boolean UNABLED = false;
    final static boolean BOOKED = true;
    final static boolean UNBOOKED = false;
    final static boolean SPECIAL = true;
    final static boolean UNSPECIAL = false;

    public Screen(int row, int col, String screenNum) {
        // 변수 초기화
        this.row = row;
        this.col = col;

        totalSeats = row * col;
        this.screenNum = screenNum;

        this.isAbled = ABLED;   // 좌석임
        this.isBooked = UNBOOKED;  // 예매되지 않음
        this.isSpecial = UNSPECIAL; // 우등석이 아님


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

    public boolean isAbled() {
        return isAbled;
    }

    public void setAbled(boolean abled) {
        isAbled = abled;
    }

    public boolean isBooked() {
        return isBooked;
    }

    public void setBooked(boolean booked) {
        isBooked = booked;
    }

    public boolean isSpecial() {
        return isSpecial;
    }

    public void setSpecial(boolean special) {
        isSpecial = special;
    }

    public String getScreenNum() {
        return screenNum;
    }

    public void setScreenNum(String screenNum) {
        this.screenNum = screenNum;
    }

    public int[] getButtonID() {
        return ButtonID;
    }

    public void setButtonID(int[] buttonID) {
        ButtonID = buttonID;
    }
}

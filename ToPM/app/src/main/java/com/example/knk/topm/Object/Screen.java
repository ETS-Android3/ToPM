package com.example.knk.topm.Object;

public class Screen {
    int row; // 좌석 열
    int col; // 좌석 행
    int totalSeats; // 전체 좌석 개수
    String screenNum; // 상영관 번호
    MyButton[][] seats; // 좌석

    /* 상수 */
    final int ROW_MAX = 20;
    final int COL_MAX = 20;
    final int ROW_MIN = 5;
    final int COL_MIN = 5;

    public Screen(int row, int col, String screenNum) {
        this.row = row;
        this.col = col;
        this.screenNum = screenNum;

        seats = new MyButton[row][col];

        for(int i=0; i<row; i++) {
            for(int j=0; j<col; j++) {
                // seats[i][j] = new MyButton(); // 이거 괄호 안에 Context 들어가야 하는데 뭘 넣어야하지..?
            }
        }
    }
}

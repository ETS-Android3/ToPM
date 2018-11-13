package com.example.knk.topm.Object;

import java.util.ArrayList;

public class BookingInfo {
//    User user;
//    MovieSchedule schedule;
    String userID;                  // 예매자 아이디
    String scheduleKey;             // 예매한 스케줄 key
    int personnel;                  // 인원
    ArrayList<String> bookedSeats;  // 예매한 자리 번호 저장 (버튼 ID)

    public BookingInfo(String userID, String scheduleKey, int personnel, ArrayList<String> bookedSeats) {
        this.userID = userID;
        this.scheduleKey = scheduleKey;
        this.personnel = personnel;
        this.bookedSeats = bookedSeats;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getScheduleKey() {
        return scheduleKey;
    }

    public void setScheduleKey(String scheduleKey) {
        this.scheduleKey = scheduleKey;
    }

    public int getPersonnel() {
        return personnel;
    }

    public void setPersonnel(int personnel) {
        this.personnel = personnel;
    }

    public ArrayList<String> getBookedSeats() {
        return bookedSeats;
    }

    public void setBookedSeats(ArrayList<String> bookedSeats) {
        this.bookedSeats = bookedSeats;
    }
}

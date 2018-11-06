package com.example.knk.topm.Object;

import java.util.Date;

public class MovieSchedule {
    Movie movie; // 영화
    Screen screen; // 상영관
    Date screeningDate; // 상영 날짜
    int startHour; // 시작 시간
    int startMin; // 시작 분
    int bookedSeats; // 예약된 좌석 숫자
    int restSeats; // 남은 좌석 숫자

    public MovieSchedule() {

    }

    public MovieSchedule(Movie movie, Screen screen, Date screeningDate) {
        this.movie = movie;
        this.screen = screen;
        this.screeningDate = screeningDate;
    }

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    public Screen getScreen() {
        return screen;
    }

    public void setScreen(Screen screen) {
        this.screen = screen;
    }

    public Date getScreeningDate() {
        return screeningDate;
    }

    public void setScreeningDate(Date screeningDate) {
        this.screeningDate = screeningDate;
    }

    public int getStartHour() {
        return startHour;
    }

    public void setStartHour(int startHour) {
        this.startHour = startHour;
    }

    public int getStartMin() {
        return startMin;
    }

    public void setStartMin(int startMin) {
        this.startMin = startMin;
    }

    public int getBookedSeats() {
        return bookedSeats;
    }

    public void setBookedSeats(int bookedSeats) {
        this.bookedSeats = bookedSeats;
    }

    public int getRestSeats() {
        return restSeats;
    }

    public void setRestSeats(int restSeats) {
        this.restSeats = restSeats;
    }

    public int[] countEnd() {
        // startHour와 startMin에 runningtime(분)을 더하여 상영종료 시각을 구해 리턴하는 함수

        int hour, min; // 영화 상영 시간, 분
        int endHour, endMin; // 끝나는 시간, 분
        int[] endTime = new int[2];

        // 러닝타임을 몇시간 몇 분인지 계산
        hour = movie.runningTime / 60;
        min = movie.runningTime % 60;

        // 끝나는 시간과 분 계산
        endHour = startHour + hour;
        endMin = startMin + min;

        if(endMin >= 60) { // 시간 자리올림
            endHour += endMin / 60;
            endMin = endMin % 60;
        }

        endTime[0] = endHour;
        endTime[1] = endMin;

        return endTime;
    }

    public int countSeats() {
        // totalSeats 에서 bookedSeats 을 빼서 남은 좌석 수를 리턴하는 함수
        restSeats = screen.totalSeats - this.bookedSeats;
        return restSeats;
    }
}

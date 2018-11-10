package com.example.knk.topm.UserActivities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;

import com.example.knk.topm.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class UserMainActivity extends AppCompatActivity {

    ListView dayScheduleList;

    // 데이터베이스 관련
    private FirebaseDatabase firebaseDatabase;          //firebaseDatabase
    private DatabaseReference rootReference;            //rootReference
    final private String SCHEDULE_REF = "schedule";     // 레퍼런스할 이름 - 여기서는 영화이므로 schedule를 root로 참조

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_schedule);
        init();
    }

    public void init() {
        // 위젯 초기화
        dayScheduleList = findViewById(R.id.dayScheduleList);

        // 데이터베이스 연결
        firebaseDatabase = FirebaseDatabase.getInstance();
        rootReference = firebaseDatabase.getReference(SCHEDULE_REF);

        // 데이터베이스에서 영화 정보 받아오기


        // 영화 정보 출력하기

    }

    public void showMyBookingClick(View view) {
    }

    public void prevBtnClick(View view) {
    }

    public void nextBtnClick(View view) {
    }
}

package com.example.knk.topm.UserActivities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.ListView;

import com.example.knk.topm.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ShowScheduleActivity extends AppCompatActivity {

    // 위젯
    ListView dayScheduleList;
    Button showMyBookingBtn;

    // 데이터베이스 관련
    private FirebaseDatabase firebaseDatabase;      //firebaseDatabase
    private DatabaseReference rootReference;        //rootReference
    private static String movies_ref = "movies";    // 레퍼런스할 이름 - 여기서는 영화이므로 movies를 root로 참조해 그 아래에 데이터 추가.


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_schedule);

        init();
    }

    public void init() {
        // 위젯 초기화
        dayScheduleList = (ListView) findViewById(R.id.dayScheduleList);
        showMyBookingBtn = (Button) findViewById(R.id.showMyBookingBtn);

        // 데이터베이스 연결
        firebaseDatabase = FirebaseDatabase.getInstance();
        rootReference = firebaseDatabase.getReference(movies_ref);

        // 데이터베이스에서 영화 정보 받아오기


        // 영화 정보 출력하기

    }
}

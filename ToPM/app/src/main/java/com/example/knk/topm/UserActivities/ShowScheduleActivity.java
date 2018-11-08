package com.example.knk.topm.UserActivities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
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
        String title;
        String runningTime;
        int restSeats;
        int bookedSeats;
        int screenNum;

//        ArrayList<Movie> movieData;
//        ListView movieManageList;
//
//        EditText editTitle;
//        EditText editDir;
//        EditText editRun;
//
//        String title;
//        String director;
//        String runningTime;

        // 영화 정보 출력하기

    }

    // 나의 예매내역 버튼
    public void showMyBookingBtn(View view) {
        startActivity(new Intent(this, myBookingList.class));
    }
}

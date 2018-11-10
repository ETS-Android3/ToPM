package com.example.knk.topm.UserActivities;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;

import com.example.knk.topm.CustomAdapters.NormalScheduleListAdapter;
import com.example.knk.topm.Object.MovieSchedule;
import com.example.knk.topm.R;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class UserMainActivity extends AppCompatActivity {

    private ListView dayScheduleList;
    private ArrayList<MovieSchedule>[] scheduleData;
    private NormalScheduleListAdapter adapter;

    // 데이터베이스 관련
    private FirebaseDatabase firebaseDatabase;          //firebaseDatabase
    private DatabaseReference rootReference;            //rootReference
    final private String SCHEDULE_REF = "schedule";     // 레퍼런스할 이름 - 여기서는 영화이므로 schedule를 root로 참조

    // 상수
    final int FUTURE_DATE = 4;                          // 미래 날짜

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_main);
        init();
        getScheduleFromDB();
    }

    // 데이터베이스에서 스케쥴 정보 받아오기
    public void getScheduleFromDB(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy년 MM월 dd일");           //스케쥴 DB의 KEY 형태
        scheduleData = new ArrayList[FUTURE_DATE];                  //스케쥴정보를 날짜별로 저장할 arrayList 객체 배열
        for(int i = 0 ;i<FUTURE_DATE;i++)
            scheduleData[i] = new ArrayList<>();

        Calendar today = Calendar.getInstance();                    //오늘 시간날짜정보 저장
        today.add(Calendar.DATE,-1);                       //하루 빼기 - for문에서 하나씩 더할 것이므로 그래야 오늘날짜부터 시작할 수 있음.
        for(int i=0; i<FUTURE_DATE; i++) {
            today.add(Calendar.DATE, 1);                    //날짜 하루씩 더하기 for문순서대로돌면서, 오늘,내일,2일뒤,3일뒤 계산
            String strDate = sdf.format(today.getTime());            //yyyy년 MM월 dd일의 포맷으로 바꿔 스트링형태로 저장. - DB의 key형태
            final int index = i;
            // 스케줄 데이터베이스 변경 이벤트 핸들러
            rootReference.child(strDate).addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                    MovieSchedule newSchedule = dataSnapshot.getValue(MovieSchedule.class); // 새로 추가된 스케줄 받아옴
                    //scheduleData[index].add(newSchedule);                                   // 리스트 뷰에 갱신
                    //adapter.notifyDataSetChanged();
                }

                @Override
                public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                }

                @Override
                public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

                }

                @Override
                public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

        }
    }

    public void init() {
        // 위젯 초기화
        dayScheduleList = findViewById(R.id.dayScheduleList);

        // 데이터베이스 연결
        firebaseDatabase = FirebaseDatabase.getInstance();
        rootReference = firebaseDatabase.getReference(SCHEDULE_REF);

        //리스트 초기화
        dayScheduleList = findViewById(R.id.dayScheduleList);
    }

    public void showMyBookingClick(View view) {
    }

    public void prevBtnClick(View view) {
    }

    public void nextBtnClick(View view) {
    }
}

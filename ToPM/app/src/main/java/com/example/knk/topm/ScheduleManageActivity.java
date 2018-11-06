package com.example.knk.topm;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.knk.topm.Object.Movie;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ScheduleManageActivity extends AppCompatActivity {

    ListView dayScheduleList;       // 영화 스케줄 출력
    Spinner movieSpinner;           // 등록된 영화 목록 Spinner
    ArrayList<Movie> movieData;     // 서버에 저장된 영화 데이터 받아오는 배열
    ArrayAdapter<Movie> adapter;    // 스피너에 연결하는 어댑터

    // 데이터베이스
    private FirebaseDatabase firebaseDatabase;      //firebaseDatabase
    private DatabaseReference rootReference;        //rootReference
    private static String movie_ref = "movie";        //레퍼런스할 이름 - 여기서는 회원가입이므로 user를 root로 참조해 그 아래에 데이터 추가.


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule_manage);

        init();
    }

    public void init() {
        // 객체 생성
        dayScheduleList = (ListView) findViewById(R.id.dayScheduleList);
        movieSpinner = (Spinner) findViewById(R.id.movieSpinner);
        movieData = new ArrayList<Movie>();
        adapter = new ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, movieData);
        movieSpinner.setAdapter(adapter);

        // 영화 정보를 서버에서 받아옵시다.
        // 데이터베이스
        firebaseDatabase = FirebaseDatabase.getInstance();
        rootReference = firebaseDatabase.getReference(movie_ref);

        // 데이터 베이스에서 정보 받아와서 movieData에 저장
        rootReference.addListenerForSingleValueEvent(new ValueEventListener() { // 최초 한번 실행되고 삭제되는 콜백
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // 모든 데이터 가지고 오기
                for(DataSnapshot data : dataSnapshot.getChildren()) {
                    Movie movie = data.getValue(Movie.class);

                    if(movie == null)
                        Toast.makeText(ScheduleManageActivity.this, "null", Toast.LENGTH_SHORT).show();
                    else {
                        movieData.add(movie); // movieData에 삽입
                        // Toast.makeText(MovieManageActivity.this, movie.getTitle(), Toast.LENGTH_SHORT).show();
                    }
                }
                adapter.notifyDataSetChanged(); // 데이터 갱신 통지
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void selectScreenBtn(View view) {
        // 상영관 선택 다이어로그
    }

    public void inputStartTimeBtn(View view) {
        // 시작 시간 입력 (TimePicker 다이어로그)
    }
}

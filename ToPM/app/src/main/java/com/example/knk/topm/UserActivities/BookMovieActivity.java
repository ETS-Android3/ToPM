package com.example.knk.topm.UserActivities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.Toast;

import com.example.knk.topm.Object.MovieSchedule;
import com.example.knk.topm.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class BookMovieActivity extends AppCompatActivity {


    String scheduleKey;                             // 이전 액티비티에서 넘겨준 데이터베이스 접근 키
    String strDate;                                 // 이전 액티비티에서 넘겨준 상영 날짜 스트링
    MovieSchedule movieSchedule;                    // 해당 스케쥴

    // 데이터베이스
    private FirebaseDatabase firebaseDatabase;      // firebaseDatabase
    private DatabaseReference scheduleReference;        // rootReference
    private static String schedule_ref = "schedule";      // 레퍼런스할 이름 - 여기서는 영화 등록이므로 movie를 root로 참조해 그 아래에 데이터 추가.


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_movie);

        init();
    }

    public void init() {

        // 이전 액티비티에서 전송한 정보 수신
        Intent intent = getIntent();
        scheduleKey = intent.getStringExtra("key");
        strDate = intent.getStringExtra("date");
        Toast.makeText(this, strDate, Toast.LENGTH_SHORT).show();

        // 데이터베이스
        firebaseDatabase = FirebaseDatabase.getInstance();
        scheduleReference = firebaseDatabase.getReference(schedule_ref);
        // 리스너 달기
        scheduleReference.child(strDate).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for(DataSnapshot data : dataSnapshot.getChildren()){
                    if(data.getKey().equals(scheduleKey)) {
                        movieSchedule = data.getValue(MovieSchedule.class); // 객체 저장
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        // 뷰 초기화
        TextView titleTextView = (TextView) findViewById(R.id.titleTextView);
        titleTextView.setText(movieSchedule.getMovieTitle());


    }
}

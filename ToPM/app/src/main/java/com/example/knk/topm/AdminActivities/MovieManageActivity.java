package com.example.knk.topm.AdminActivities;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.knk.topm.CustomAdapters.MovieListAdapter;
import com.example.knk.topm.Object.InputException;
import com.example.knk.topm.Object.Movie;
import com.example.knk.topm.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MovieManageActivity extends AppCompatActivity {

    ArrayList<Movie> movieData;
    ListView movieManageList;

    EditText editTitle;
    EditText editDir;
    EditText editRun;

    String title;
    String director;
    String runningTime;

    // 데이터베이스
    private FirebaseDatabase firebaseDatabase;      //firebaseDatabase
    private DatabaseReference rootReference;        //rootReference
    private static String movie_ref = "movie";      //레퍼런스할 이름 - 여기서는 영화 등록이므로 movie를 root로 참조해 그 아래에 데이터 추가.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_manage);

        init();
    }

    public void getDataFromFB(){

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
                        Toast.makeText(MovieManageActivity.this, "nullllll", Toast.LENGTH_SHORT).show();
                    else {
                        movieData.add(movie); // movieData에 삽입
                        //Toast.makeText(MovieManageActivity.this, movie.getTitle(), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    public void init() {

        //ListView 초기화 및 어댑터 연결
        movieManageList = findViewById(R.id.movie_manage_List);
        movieData = new ArrayList<>();

        //파이어베이스로부터 데이터 가져오기
        getDataFromFB();

        MovieListAdapter adapter =  new MovieListAdapter(this,R.layout.movie_list_adapter_row, movieData);
        movieManageList.setAdapter(adapter);

        adapter.notifyDataSetChanged(); // 데이터 갱신 통지


        // 입력 위젯 초기화
        editTitle = findViewById(R.id.edittxttitle);
        editDir = findViewById(R.id.edittxtdir);
        editRun = findViewById(R.id.edittxtruntime);
    }

    public void completebtn(View view) {

        // 입력 값 받아옴
        title = editTitle.getText().toString();
        director = editDir.getText().toString();
        runningTime = editRun.getText().toString();

        if(title.length() <= 0 || director.length() <= 0 || runningTime.length() <= 0) {
            // 입력이 누락된 경우
            try {
                throw new InputException();
            } catch (InputException e) {
                Toast.makeText(this, "모두 입력하세요.", Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }
        }
        else {
            // 정상 입력
            // 데이터 베이스에 입력
            int runTime = Integer.parseInt(runningTime);
            Movie movie = new Movie(runTime, title, director, 0); // 객체 생성 후

            rootReference.child(movie.getTitle()).setValue(movie);

            // 다시 관리자 메인으로 이동
            this.finish();
        }
    }

    public boolean deleteCheck(){
        // detail method

        return false;
    }


}



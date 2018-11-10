package com.example.knk.topm.AdminActivities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;

import com.example.knk.topm.CustomAdapters.ScheduleListAdapter;
import com.example.knk.topm.Object.User;
import com.example.knk.topm.R;

public class AdminMainActivity extends AppCompatActivity {

    final private String USER_PUTEXTRA_TAG = "user";
    private User user;                                          //유저정보 (여기선 관리자 정보) 가져옴.
    private ListView dayScheduleList;
    private ScheduleListAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_main);
        Intent intent = getIntent();
        user = (User)intent.getSerializableExtra(USER_PUTEXTRA_TAG);
        //Toast.makeText(this,user.getId(),Toast.LENGTH_SHORT).show(); //테스트
        init();

    }
    public void init(){
        dayScheduleList = findViewById(R.id.dayScheduleList);

    }

    public void movieEditClick(View view) {
        startActivity(new Intent(this, MovieManageActivity.class));
    }

    public void screenEditClick(View view) {
        startActivity(new Intent(this, ScreenListActivity.class));
    }

    public void scheduleEditClick(View view) {
        startActivity(new Intent(this, ScheduleManageActivity.class));
    }

    public void prevBtnClick(View view) {
    }

    public void nextBtnClick(View view) {
    }
}

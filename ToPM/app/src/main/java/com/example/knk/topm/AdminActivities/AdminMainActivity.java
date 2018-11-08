package com.example.knk.topm.AdminActivities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.knk.topm.Object.User;
import com.example.knk.topm.R;
import com.example.knk.topm.ScheduleManageActivity;

public class AdminMainActivity extends AppCompatActivity {

    private String user_putExtra_tag = "user";
    private User user;                                          //유저정보 (여기선 관리자 정보) 가져옴.
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_main);
        Intent intent = getIntent();
        user = (User)intent.getSerializableExtra(user_putExtra_tag);
        //Toast.makeText(this,user.getId(),Toast.LENGTH_SHORT).show(); //테스트
    }

    public void movieEditBtn(View view) {
        startActivity(new Intent(this, MovieManageActivity.class));
    }

    public void screenEditBtn(View view) {
    }

    public void scheduleEditBtn(View view) {
        startActivity(new Intent(this, ScheduleManageActivity.class));
    }
}

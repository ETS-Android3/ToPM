package com.example.knk.topm;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class AdminMainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_main);
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

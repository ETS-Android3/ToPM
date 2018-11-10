package com.example.knk.topm.AdminActivities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.knk.topm.R;

public class ScreenEditActivity2 extends AppCompatActivity {

    int row, col;

    final int DEFAUL_VALUE = 10; // 전송 실패시 디폴트값 10

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screen_edit2);

        init();
    }

    public void init() {

        // 이전 액티비티에서 전송한 정보 수신
        Intent intent = getIntent();
        row = intent.getIntExtra("row", DEFAUL_VALUE);
        col = intent.getIntExtra("col", DEFAUL_VALUE);
    }
}

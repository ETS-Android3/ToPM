package com.example.knk.topm.AdminActivities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.knk.topm.R;

public class ScreenListActivity extends AppCompatActivity implements View.OnClickListener {

    final int SCREEN_COUNT = 5; // 상영관 개수
    Button[] screenEdits;
    LinearLayout buttonLayout;

    String Screen_Name_Split[];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screen_list);

        init();
    }

    public void init() {

        // 객체 생성
        screenEdits = new Button[SCREEN_COUNT];
        buttonLayout = findViewById(R.id.buttonLayout);

        // 상영관의 개수가 달라져도 SCREEN_COUNT만 바꾸면 버튼이 늘어나도록 버튼을 직접 자바코드로 작성
        for(int i=0; i<SCREEN_COUNT; i++) {
            screenEdits[i] = new Button(this);
            screenEdits[i].setId(i+1);          // id 추가함
            screenEdits[i].setText(String.valueOf(i+1)+"관");
            screenEdits[i].setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
            screenEdits[i].setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
            buttonLayout.addView(screenEdits[i]);

            screenEdits[i].setOnClickListener(this);
        }


    }

    @Override
    public void onClick(View v) {
        // 어느 button 누르는지 id 받을수있게 만든 buff 입니다
        int screenNum = v.getId();
//        Button DB_Connect_Button = (Button)findViewById(v.getId());
//
//        // test 하는 toast 입니다. 나중에 지우세요
//        //Toast.makeText(this, b.getText()+"", Toast.LENGTH_SHORT).show();
//
//        String Screen_Name_Buffer= DB_Connect_Button.getText().toString();  // "1관 / 2관" <- 이런 이름 받음
//        Screen_Name_Split= Screen_Name_Buffer.split("관");                  // "1","관"  <- 이런 식으로 나누기
//
//        //test 하는 toast 입니다. 나중에 지우세요
//        //Toast.makeText(this, Screen_Name_Split[0]+"", Toast.LENGTH_SHORT).show();

        Intent intent1 = new Intent(this, ScreenShowActivity.class);
        // 관 ID 정보 전송
        intent1.putExtra("SCREENID1", screenNum);
        startActivity(intent1);
    }
}

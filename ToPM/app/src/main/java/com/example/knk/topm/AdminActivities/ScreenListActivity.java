package com.example.knk.topm.AdminActivities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.knk.topm.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

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
        buttonLayout = (LinearLayout) findViewById(R.id.buttonLayout);

        for(int i=0; i<SCREEN_COUNT; i++) {
            screenEdits[i] = new Button(this);
            screenEdits[i].setId(i+1);          // id 추가함
            screenEdits[i].setText(String.valueOf(i+1)+"관");
            screenEdits[i].setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
            screenEdits[i].setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
            buttonLayout.addView(screenEdits[i]);

            //----
            screenEdits[i].setOnClickListener(this);
        }


    }

    @Override
    public void onClick(View v) {

        //startActivity(new Intent(this,ScreenEditActivity1.class));

        // DB 부분
        DatabaseReference mDatabase;
        mDatabase = FirebaseDatabase.getInstance().getReference();

        // 어느 button 누르는지 id 받을수있게 만든 buff 입니다
        Button DB_Connect_Button = (Button)findViewById(v.getId());

        // test 하는 toast 입니다. 나중에 지우세요
        //Toast.makeText(this, b.getText()+"", Toast.LENGTH_SHORT).show();

        String Screen_Name_Buffer= DB_Connect_Button.getText().toString();  // "1관 / 2관" <- 이런 이름 받음
        Screen_Name_Split= Screen_Name_Buffer.split("관");                  // "1","관"  <- 이런 식으로 나누기

        //test 하는 toast 입니다. 나중에 지우세요
        //Toast.makeText(this, Screen_Name_Split[0]+"", Toast.LENGTH_SHORT).show();

        // db에서 추가하기#1  예: ../DBScreenSits/1관/ScreenID=1
        mDatabase.child("DBScreenSits").child(Screen_Name_Buffer).child("ScreenID").setValue(Screen_Name_Split[0]);


        Intent intent1 = new Intent();
        intent1.setClass(this,ScreenEditActivity1.class);
        // 관 ID 정보 전송
        int c=Integer.parseInt(Screen_Name_Split[0]);
        intent1.putExtra("SCREENID1", c);
        startActivity(intent1);

    }
}

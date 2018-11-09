package com.example.knk.topm.AdminActivities;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.knk.topm.Object.MyButton;
import com.example.knk.topm.R;

public class ScreenEditActivity2 extends AppCompatActivity {

    int row;
    int col;
    int size ;  //size =row *col
    int db_button_index = -2000;  //database 에 올릴 때 쓰는 바톤 view.getvalue 값
    MyButton btn[];

    String r ;//test 용 string 나중에 지워도됩니다 .

    final int DEFAUL_VALUE = 5; // 전송 실패시 디폴트값 10

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
        size= row * col;
        //--------
        RelativeLayout layout = new RelativeLayout(this);  //linearlauout으로 button 추가 할때 절대값으로 지정할수없음 relative사용합
        btn = new MyButton[size];

//*******************일반 버튼 추가하려면 아래코드 참초 *******************
//        Button wall = new Button(this);
//        wall.setText("WALL");
//        RelativeLayout.LayoutParams p = new RelativeLayout.LayoutParams(100, 100);  //(100,100) 버튼의 크기
//        p.topMargin = 550;            //버튼 의 위치
//        p.leftMargin = 100;
//        layout.addView(wall, p);

//        wall.setOnClickListener(new Button.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                index = -2;
//            }
//        });


        //***********************


        int j = 0;   //행땅 버튼 개수 count 하는 변수
        for (int i = 0; i < size; i++) {            //1차원 배열로 정장할수있음
            btn[i] = new MyButton(this);        //객체
            btn[i].setId(2000 + i);                     //mybutton는  value 값 2000부터 시작함
            btn[i].setBackgroundColor(Color.GRAY);      //배경색
            btn[i].setText("" + i);
            btn[i].setTextSize(0, 8);         //끌시체 크기
            RelativeLayout.LayoutParams RL = new RelativeLayout.LayoutParams(40, 40);  //(40,40)-> 버튼의 크기: 40=50-10

            //***************** row * col 배치하는 구조
            if (i % row == 0) {    // >row 시 다음행으로 넘어가기
                j++;
            }
            RL.leftMargin = 50 * (i % col);   //50는 행간 사이입니다  최대값 450 입니다 .
            RL.topMargin = j * 50;            //50는 열간 사이입니다
            layout.addView(btn[i], RL);        //mybutton 출력함
            this.setContentView(layout);
        }
            //********************

            for (int k = 0; k < btn.length - 1; k++) {
                btn[k].setTag(k);
                btn[k].setOnTouchListener(new Button.OnTouchListener() {
                    @Override
                    public boolean onTouch(View view, MotionEvent m) {
                        MyButton mybutton_inside=(MyButton)findViewById(view.getId());  //

//                        this.isAbled = true;
//                        this.isBooked = false;
//                        this.isSpecial = false;
                        mybutton_inside.isAbled=false;
                        //mybutton_inside.isbooked=false ; //error
                        //MyButton Class 의 있는 boolean 변수는 public 추가해야함 일단 isabled만 바꿨음 .


                        if(!mybutton_inside.isAbled)
                            r = "abled =false ";  //r는 test 용 string 입니다 .나중에 지우면 됩니다 .
                        view.setBackgroundColor(Color.BLUE);
                        // buttonbuff[(int) view.getId() - db_button_index] //db id value
                        Toast.makeText(getApplicationContext(), "buttonid:" + view.getId() + "value:" + r,
                                Toast.LENGTH_SHORT).show();

                        return false;
                    }
                });
            }

        }

    }

package com.example.knk.topm.AdminActivities;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.knk.topm.Object.MyButton;
import com.example.knk.topm.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ScreenEditActivity2 extends AppCompatActivity {

    int row;  //x
    int col;   //y
    int size ;  //size =row *col

    String str = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"; // 대문자 string 배열 생성하기
    String a1[]=str.split("");
    MyButton btn[];
    RelativeLayout r1,r2; // index X Y
    String r ;//test 용 string 나중에 지워도됩니다 .

    final int DEFAUL_VALUE = 5; // 전송 실패시 디폴트값 10

    int Screen_ID_buff;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screen_edit2);



        init();

    }

    public void init() {
        // db
        DatabaseReference mDatabase;
        mDatabase = FirebaseDatabase.getInstance().getReference();

//        해상도 받는 구조 . 아래 코드 해상도 동작할당 안되어있음 .절대값입니다 .
//        DisplayMetrics dm = new DisplayMetrics();
//        getWindowManager().getDefaultDisplay().getMetrics(dm);
//        int width =dm.widthPixels;
//        int height=dm.heightPixels;

        // 이전 액티비티에서 전송한 정보 수신
        Intent intent = getIntent();
        row = intent.getIntExtra("row", DEFAUL_VALUE);
        col = intent.getIntExtra("col", DEFAUL_VALUE);

        // ScreenListActivity 에서 Screenid 받아오기
        Screen_ID_buff=intent.getIntExtra("SCREENID2", -1);
        // "1관" , "2관" ....
        String DB_HallNumber=Screen_ID_buff+"관";

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
//
//            }
//        });
//***********************

        //  index layout 부분
        for(int count=0;count<row;count++) {   //X (row)열  index
            r1 = new RelativeLayout(this);
            TextView textView = new TextView(this);
            textView.setText(String.valueOf(count+1));

            if(count>8){
            textView.setTextSize(10);   //x>10일 경우 이상헤게 나오기때문에 예외처리임
            }

            RelativeLayout.LayoutParams indexRlayout = new RelativeLayout.LayoutParams(40, 40);//(textview의 크기)
            indexRlayout.topMargin = 0;
            indexRlayout.leftMargin = 10 + (count * 50)+50;// 10( x0 left 까지 거리  )+(생선할때마다 50px 추가함)+(제일 왼쪽 index 빈칸으로 제움)
            r1.addView(textView, indexRlayout);
            layout.addView(r1);

        }

        for(int count=0;count<col;count++) {     //Y (col)열 index
            r2 = new RelativeLayout(this);
            TextView t1 = new TextView(this);
            t1.setText(a1[count+1]);//a1는 대문자 string 배열 입니다.
            RelativeLayout.LayoutParams indexRlayout = new RelativeLayout.LayoutParams(40, 40);
            indexRlayout.topMargin = 10 + (count * 50)+30;
            indexRlayout.leftMargin = 0;

            r2.addView(t1, indexRlayout);
            layout.addView(r2);

        }



        int j = 0;   //행땅 버튼 개수 count 하는 변수

        int Scree_Hall_ID_Count=Screen_ID_buff*1000+1;
        for (int i = 0; i < size; i++) {            //1차원 배열로 정장할수있음
            btn[i] = new MyButton(this);        //객체

            //btn[i].setId(2001 + i);                     //mybutton는  value 값 2000부터 시작함
            //id가 2001부터 설치하는 이유 :다른 activity에 있는 id 값과 겹치지않게 해야합니다 .

            btn[i].setId(Scree_Hall_ID_Count+i);

           mDatabase.child("DBScreenSits").child(DB_HallNumber).child("ButtonID").child(String.valueOf(Scree_Hall_ID_Count+i)).child("DBisAbler").setValue(btn[i].isAbled);
           mDatabase.child("DBScreenSits").child(DB_HallNumber).child("ButtonID").child(String.valueOf(Scree_Hall_ID_Count+i)).child("DBisBooked").setValue(btn[i].isBooked);
           mDatabase.child("DBScreenSits").child(DB_HallNumber).child("ButtonID").child(String.valueOf(Scree_Hall_ID_Count+i)).child("DBisSpecial").setValue(btn[i].isSpecial);



            //btn[i].setBackgroundColor(Color.GRAY);      //배경색
            btn[i].setBackgroundResource(R.drawable.movie_seat_ok);//배경 png로 바꿈
            btn[i].setText("" + i);
            btn[i].setTextSize(0, 8);         //끌시체 크기
            RelativeLayout.LayoutParams RL = new RelativeLayout.LayoutParams(40, 40);  //(40,40)-> 버튼의 크기: 40=50-10

            //***************** row * col 배치하기
            if (i % row == 0) {    // >row 시 다음행으로 넘어가기
                j++;
            }
            //RL.leftMargin = 50 * (i % col);    //index 때문에 수치바꿈
            RL.leftMargin = 50+50 * (i % row);     //50는 행간 사이입니다   . 추가된 50은 index (0,0)위치의 빈칸입니다 .
            RL.topMargin = j * 50;            //50는 열간 사이입니다
            layout.addView(btn[i], RL);        //mybutton 출력함
            this.setContentView(layout);
        }
            //********************

            for (int k = 0; k < btn.length ; k++) {
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
                        //view.setBackgroundColor(Color.BLUE);
                        view.setBackgroundResource(R.drawable.movie_seat_select);//배경사진 png 로 바꿈
                        // buttonbuff[(int) view.getId() - db_button_index] //db id value
                        Toast.makeText(getApplicationContext(), "buttonid:" + view.getId() + "value:" + r+"XY :"
                                //+GetMybuttonXY(view),//좌석 위치 판단함
                                +GetMybuttonXY(view.getId()),
                                Toast.LENGTH_SHORT).show();

                        return false;
                    }
                });
            }

        }

        //좌석 위치 판단하고 string로 출력하기
        public String GetMybuttonXY(View v){

            int x1=0;
            for(int cc =0;cc<((int)v.getId()-(Screen_ID_buff*1000));cc++){   //촤표 판단하기
                if(cc%row==0)
                    x1++;
            }

            int y1=((int)v.getId()-(Screen_ID_buff*1000))%row;
            if(y1==0){
                y1=row;                     //예외처리입니다 .
            }
            String sitXY="("+a1[x1]+","+String.valueOf(y1)+")";
        return sitXY;
        }

        public String GetMybuttonXY(int Viewid){   // overloading  int형 id 받는 함수

            int x1=0;
            for(int cc =0;cc<(Viewid-(Screen_ID_buff*1000));cc++){   //촤표 판단하기
             if(cc%row==0)
                x1++;
            }

         int y1=(Viewid-(Screen_ID_buff*1000))%row;
            if(y1==0){
                y1=row;                     //예외처리입니다 .
         }
         String sitXY="("+a1[x1]+","+String.valueOf(y1)+")";
            return sitXY;
    }

    }

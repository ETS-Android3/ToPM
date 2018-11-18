package com.example.knk.topm.AdminActivities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.knk.topm.Object.InputException;
import com.example.knk.topm.R;

public class ScreenEditActivity1 extends AppCompatActivity {

    EditText rowEdit, colEdit;
    int row, col;

    String screenName;          // db에 2차 메뉴열 넣을떄 쓰는 buff변수
    int Screen_ID_buff;         // db에 2차 메뉴열 넣을떄 쓰는 buff변수

    /* 상수 */
    final int ROW_MAX = 20;
    final int COL_MAX = 20;
    final int ROW_MIN = 5;
    final int COL_MIN = 5;

    //final private static String screen_ref = "screen";          // 상영관 레퍼런스로 가는 키

    final static int DEFAULT_VALUE = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screen_edit1);

        init();
    }
    // 초기화
    public void init() {

        // 가로 열 입력
        rowEdit = findViewById(R.id.rowEdit);
        // 세로 행 입력
        colEdit = findViewById(R.id.colEdit);

        // ScreenListActivity 에서 Screenid 받아오기
        Intent intent = getIntent();
        Screen_ID_buff = intent.getIntExtra("SCREENID1", DEFAULT_VALUE);

        // Toast.makeText(this,Screen_ID_buff+"",Toast.LENGTH_SHORT).show(); // 정상 확인
        // "1관" , "2관" ....
        screenName = Screen_ID_buff + "관";
    }

    public void nextBtnClicked(View view) {

        // 입력한 가로 세로 정보 받아옴

        String rowIsEmpty = rowEdit.getText().toString();
        String colIsEmpty = colEdit.getText().toString();

        if(rowIsEmpty.equals("")||colIsEmpty.equals(""))
            try {
                throw new InputException();
            } catch (InputException e) {
                Toast.makeText(this, "두 항목을 모두 입력해주세요.", Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }

        else{
            row = Integer.parseInt(rowEdit.getText().toString());
            col = Integer.parseInt(colEdit.getText().toString());
//        db에서 추사하기#2   예: ../Screen/x관/DBrow =9,DBcol=9
//        mDatabase.child(screen_ref).child(DB_HallNumber).child("row").setValue(row);
//        mDatabase.child(screen_ref).child(DB_HallNumber).child("col").setValue(col);

            if(row <= ROW_MAX && col <= COL_MAX && row >= ROW_MIN && col >= COL_MIN) {
                // 최소, 최대 조건 만족시
                // 다음 액티비티로 전환
                Intent intent = new Intent(this, ScreenEditActivity2.class);

                // 행 열 정보 전송
                intent.putExtra("row", row);
                intent.putExtra("col", col);
                intent.putExtra("SCREENID2",Screen_ID_buff);
                startActivity(intent);
            }
            else {
                try {
                    throw new InputException();
                } catch (InputException e) {
                    Toast.makeText(this, "5이상 20 사이의 숫자를 입력하세요.", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            }

        }
    }
}

package com.example.knk.topm.AdminActivities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.knk.topm.Object.InputException;
import com.example.knk.topm.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ScreenEditActivity1 extends AppCompatActivity {

    EditText rowEdit, colEdit;
    int row, col;

    /* 상수 */
    final int ROW_MAX = 20;
    final int COL_MAX = 20;
    final int ROW_MIN = 5;
    final int COL_MIN = 5;

    String DB_HallNumber;
    int Screen_ID_buff;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screen_edit1);

        init();
    }

    public void init() {
        rowEdit = (EditText) findViewById(R.id.rowEdit);
        colEdit = (EditText) findViewById(R.id.colEdit);

        // 입력하지 않았을 경우 -1
        row = -1;
        col = -1;


        // ScreenListActivity 에서 Screenid 받아오기
        Intent GetBuffintent = getIntent();
        Screen_ID_buff=GetBuffintent.getIntExtra("SCREENID1", -1);

        // "1관" , "2관" ....
        DB_HallNumber=Screen_ID_buff+"관";

    }

    public void nextBtnClicked(View view) {

        //db
        DatabaseReference mDatabase;
        mDatabase = FirebaseDatabase.getInstance().getReference();



        // 다음 버튼

        // 입력한 가로 세로 정보 받아옴
          row = Integer.parseInt(rowEdit.getText().toString());
          col = Integer.parseInt(colEdit.getText().toString());



          // db에서 추사하기#2   예: ../DBScreenSits/x관/DBrow =9,DBcol=9
        mDatabase.child("DBScreenSits").child(DB_HallNumber).child("DBrow").setValue(row);
        mDatabase.child("DBScreenSits").child(DB_HallNumber).child("DBcol").setValue(col);


        if(row != -1 && col != -1) {
            // 입력이 된 경우에만
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
                    Toast.makeText(this, "입력을 확인하세요.1", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            }
        }
        else {
            try {
                throw new InputException();
            } catch (InputException e) {
                Toast.makeText(this, "입력을 확인하세요.2", Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }
        }
    }
}

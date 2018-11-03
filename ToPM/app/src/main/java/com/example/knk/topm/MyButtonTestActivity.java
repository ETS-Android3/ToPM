package com.example.knk.topm;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.GridLayout;

import com.example.knk.topm.Object.MyButton;

public class MyButtonTestActivity extends AppCompatActivity {

    MyButton[][] seats;
    GridLayout gridLayout;
    Context context;

    /* 상수 */
    final int ROW = 10;
    final int COL = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_button_test);

        init();
    }

    public void init() {
        seats = new MyButton[ROW][COL];
        context = this;
        for(int i=0; i<ROW; i++) {
            for(int j=0; j<COL; j++) {
                seats[i][j] = new MyButton(context);
            }
        }

        gridLayout = (GridLayout) findViewById(R.id.gridLayout);
        gridLayout.setColumnCount(COL);
        gridLayout.setRowCount(ROW);

        for(int i=0; i<ROW; i++) {
            for(int j=0; j<COL; j++) {
                int index = i*ROW + j;
                gridLayout.addView(seats[i][j], index);
            }
        }
    }
}

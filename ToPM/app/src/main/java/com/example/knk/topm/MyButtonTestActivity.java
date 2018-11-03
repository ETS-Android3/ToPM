package com.example.knk.topm;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.GridLayout;

import com.example.knk.topm.Object.MyButton;

public class MyButtonTestActivity extends AppCompatActivity {

    MyButton[][] seats;
    GridLayout gridLayout;

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
        gridLayout = (GridLayout) findViewById(R.id.gridLayout);
        gridLayout.setRowCount(ROW);
        gridLayout.setColumnCount(COL);
    }
}

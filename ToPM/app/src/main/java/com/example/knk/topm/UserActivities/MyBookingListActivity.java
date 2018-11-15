package com.example.knk.topm.UserActivities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import com.example.knk.topm.R;

public class MyBookingListActivity extends AppCompatActivity {

    private ListView myBookingList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_booking_list);
    }

    // 데이터 베이스에서 내 예매목록 받아오기
    public void  getMyBookingListFromDB(){
    
    }



}

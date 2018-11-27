package com.example.knk.topm.UserActivities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.knk.topm.CustomAdapters.BookingListAdapter;
import com.example.knk.topm.Object.BookingInfo;
import com.example.knk.topm.Object.User;
import com.example.knk.topm.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MyBookingListActivity extends AppCompatActivity {

    User user;                                           // 현재 유저
    private ListView myBookingList;
    String strDate;                                      // 오늘 날짜 스트링
    ArrayList<BookingInfo> bookingData;                  // 예매 내역 받아와서 저장할 배열
    BookingListAdapter adapter;

    // 데이터베이스
    private FirebaseDatabase firebaseDatabase;      // firebaseDatabase
    private DatabaseReference scheduleReference;        // rootReference
    private DatabaseReference screenReference;        //rootReference
    private DatabaseReference userReference;
    private DatabaseReference bookingInfoReference;
    final private static String schedule_ref = "schedule";    // 스케줄 레퍼런스로 가는 키
    final private static String screen_ref = "screen";    // 스크린 레퍼런스로 가는 키
    final private static String user_ref = "user";    // 사용자 레퍼런스로 가는 키
    final private static String bookingInfo_ref = "bookingInfo";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_booking_list);

        init();
    }

    public void init() {
        bookingData = new ArrayList<>();
        adapter = new BookingListAdapter(this, R.layout.booking_list_adapter_row, bookingData);
        myBookingList = (ListView) findViewById(R.id.myBookingList);
        myBookingList.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        // 아이템 클릭 리스너
        myBookingList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                final BookingInfo bookingInfo = bookingData.get(position);  // 클릭한 예매 정보
                final String key = user.getId() + " " + bookingInfo.getScheduleKey();   // DB 접근에 필요한 BookingInfo Key
                // 예매를 취소할 것인지 묻는 Dialog 띄우기
                AlertDialog.Builder alertDlg = new AlertDialog.Builder(view.getContext());
                alertDlg.setTitle("예매를 취소하시겠습니까?");
                alertDlg.setPositiveButton("예", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick( DialogInterface dialog, int which ) {
                        // 예매 취소
                        bookingData.remove(bookingInfo);     // 현재 리스트에서 삭제
                        adapter.notifyDataSetChanged();
                        
                        bookingInfoReference.child(key).setValue(null); // DB 에서도 삭제

                        Toast.makeText(MyBookingListActivity.this, "예매가 취소되었습니다.", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();   // 다이얼로그 종료
                    }
                });
                alertDlg.setNegativeButton("아니오", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // 취소하지 않음
                        dialog.dismiss();   // 다이얼로그 종료
                    }
                });

                alertDlg.setMessage(bookingInfo.getTitle()+" 예매를 취소하시겠습니까?");
                alertDlg.show();
            }
        });

        // 이전 액티비티에서 전송한 내용 수신
        Intent intent = getIntent();
        user = (User) intent.getSerializableExtra("user");      // 현재 로그인 중인 회원
        strDate = intent.getStringExtra("date");                // 오늘 날짜

        getMyBookingListFromDB();
    }

    // 데이터 베이스에서 내 예매목록 받아오기
    public void  getMyBookingListFromDB() {

        // 데이터베이스
        firebaseDatabase = FirebaseDatabase.getInstance();
        scheduleReference = firebaseDatabase.getReference(schedule_ref);
        screenReference = firebaseDatabase.getReference(screen_ref);
        userReference = firebaseDatabase.getReference(user_ref);
        bookingInfoReference = firebaseDatabase.getReference(bookingInfo_ref);

        bookingInfoReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot data : dataSnapshot.getChildren()) {
                    BookingInfo b = data.getValue(BookingInfo.class);

                    if(b.getUserID().equals(user.getId())) {
                        // 현재 유저가 예매한 정보만
                        bookingData.add(b);
                        adapter.notifyDataSetChanged();
                    }
                    else continue;
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }



}

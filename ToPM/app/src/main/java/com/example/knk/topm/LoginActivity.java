package com.example.knk.topm;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.knk.topm.AdminActivities.AdminMainActivity;
import com.example.knk.topm.Object.User;
import com.example.knk.topm.UserActivities.ShowScheduleActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {

    private EditText login_id;
    private EditText login_pw;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference rootReference;
    private String user_ref = "user";
    private String user_putExtra_tag = "user";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        login_id = findViewById(R.id.login_id);
        login_pw = findViewById(R.id.login_pw);
        firebaseDatabase = FirebaseDatabase.getInstance();          //파이어베이스 데이터베이스 인스턴스 호출
        rootReference = firebaseDatabase.getReference(user_ref);    //"user"라는 이름으로 루트참조 생성
    }

    //회원 가입 액티비티로
    public void joinClick(View view) {
        startActivity(new Intent(this, JoinActivity.class));
    }

    //로그인 판단 후 다음 메인 액티비티로
    public void loginClick(View view) {
        rootReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot data : dataSnapshot.getChildren()){
                    //아이디가 같은지 판단
                    if(data.getKey().equals(login_id.getText().toString())) {
                        Log.d("DataSnapshot", "onValueAdded:" + data);
                        //아이디가 같다면 유저 인스턴스를 하나 생성해서 데이터를 받아온 다음
                        User user = data.getValue(User.class);
                        //유저 인스턴스의 비밀번호가 입력한 비밀번호와 일치한다면 -> 로그인 성공
                        if(user.getPw().equals(login_pw.getText().toString())) {
                            Intent intent;
                            //유저 인스턴스가 관리자라면
                            if (user.isStaff())  //관리자 액티비티로
                                intent = new Intent(getApplicationContext(), AdminMainActivity.class);
                                //유저 인스턴스가 일반 사용자라면
                            else                //일반 사용자 액티비티로
                                intent = new Intent(getApplicationContext(), ShowScheduleActivity.class);
                            //유저 인스턴스의 객체정보를 다음 액티비티로 전달한다.
                            intent.putExtra(user_putExtra_tag, user);
                            //로그인 정보 입력창을 다시 빈칸으로 초기화한 다음 (뒤로가기 눌렀을 때 정보가 남아있는 것을 방지)
                            login_pw.setText("");
                            login_id.setText("");
                            //해당 액티비티로 이동
                            startActivity(intent);
                            break;
                        }
                    }
//                    //로그인 오류 처리
//                    else{
//                        //로그인 오류 알림
//                        Toast.makeText(LoginActivity.this, "잘못된 로그인 정보입니다", Toast.LENGTH_SHORT).show();
//                        //비밀번호 지움
//                        login_pw.setText("");
//                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}

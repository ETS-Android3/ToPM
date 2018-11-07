package com.example.knk.topm;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.example.knk.topm.AdminActivities.MovieManageActivity;
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

                    if(data.getKey().equals(login_id.getText())) {
                        Log.d("DataSnapshot", "onValueAdded:" + data);

                        break;
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    public void goTest(View view) {
        Intent intent = new Intent(this, MovieManageActivity.class);
        startActivity(intent);
    }


}

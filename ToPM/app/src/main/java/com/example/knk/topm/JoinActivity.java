package com.example.knk.topm;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.knk.topm.Object.User;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class JoinActivity extends AppCompatActivity {

    private FirebaseDatabase firebaseDatabase;      //firebaseDatabase
    private DatabaseReference rootReference;        //rootReference
    private static String user_ref = "user";        //레퍼런스할 이름 - 여기서는 회원가입이므로 user를 root로 참조해 그 아래에 데이터 추가.

    private String input_id;                        //사용자 ID 저장 변수
    private String input_pw;                        //사용자 PW 저장 변수
    private String input_birth;                     //사용자 Birth 저장 변수
    private String input_name;                      //사용자 Name 저장 변수
    private User newbie;                            //사용자 객체 인스턴스
    private Button joinBtn;                         //회원가입 완료 버튼

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join);

        firebaseDatabase = FirebaseDatabase.getInstance();
        rootReference = firebaseDatabase.getReference(user_ref);
        joinBtn = (Button)findViewById(R.id.joinBtn);
        joinBtn.setEnabled(false);
    }

    //ID 중복검사 버튼 클릭이벤트 함수
    public void idCheckClick(View view) {
        input_id = ((EditText)findViewById(R.id.input_id)).getText().toString();
        //if(rootReference.equals(input_id))

    }

    //회원 가입 완료 버튼 클릭이벤트 함수
    public void completeClick(View view) {
        //ID 중복검사 버튼을 반드시 누르고 그 검사에 통과한 후에 활성화돼야 함.

        input_pw = ((EditText)findViewById(R.id.input_pw)).getText().toString();
        input_birth = ((EditText)findViewById(R.id.input_birth)).getText().toString();
        input_name = ((EditText)findViewById(R.id.input_name)).getText().toString();

        newbie = new User(input_id,input_pw,input_name,input_birth);

        rootReference.child(newbie.id).setValue(newbie);
        this.finish();
    }

}
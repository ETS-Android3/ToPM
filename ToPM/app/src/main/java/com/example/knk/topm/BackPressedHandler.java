package com.example.knk.topm;

import android.app.Activity;
import android.widget.Toast;

// 다음 세가지 경우에 뒤로가기를 실행했을 경우 처리하는 핸들러
// 1. 일반 사용자 메인화면
// 2. 관리자 메인화면
// 3. 로그인 화면 (앱 실행 초기화면)
// 에서 뒤로가기를 눌렀을 경우
// 1,2는 로그아웃
// 3은 앱 종료가 된다.
public class BackPressedHandler {
    private long backKeyPressedTime = 0;
    private Activity activity;
    private int mode;
    private Toast toast;

    private final static int BACK_FROM_MAIN = 1;
    private final static int BACK_FROM_LOGIN = 0;

    public BackPressedHandler(Activity activity, int mode) {
        this.activity = activity;
        this.mode = mode;
    }

    public void onBackPressed(){
        if(System.currentTimeMillis() > backKeyPressedTime+2000){
            backKeyPressedTime = System.currentTimeMillis();
            showToast();
            return;
        }else{
            activity.finish();
            toast.cancel();
        }
    }

    public void showToast(){
        String guide = null;
        if(mode == BACK_FROM_LOGIN)
            guide = "\'뒤로\'버튼을 한번 더 누르시면 종료합니다.";
        else if(mode == BACK_FROM_MAIN)
            guide = "\'뒤로\'버튼을 한번 더 누르시면 로그아웃합니다.";
        toast = Toast.makeText(activity,guide,Toast.LENGTH_SHORT);
        toast.show();
    }
}

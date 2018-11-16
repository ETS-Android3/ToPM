package com.example.knk.topm.Object;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

public class MyButton extends android.support.v7.widget.AppCompatButton implements View.OnClickListener{

    /* 상수 */
    public final static boolean ABLED = true; // 좌석입니다.
    public final static boolean UNABLED = false; // 좌석이 아닙니다.
    public final static boolean BOOKED = true; // 예약되었습니다.
    public final static boolean UNBOOKED = false; // 예약 안 되었습니다.
    public final static boolean SPECIAL = true; // 우등 좌석입니다.
    public final static boolean UNSPECIAL = false; // 일반 좌석입니다.

//    final static int ADMIN_MODE = 0;
//    final static int USER_MODE = 1;

    public MyButton(Context context) {
        // 자바 코드로 생성 시 호출되는 생성자
        super(context);

        // 모드 초기화
//        this.isAbled = true;
//        this.isBooked = false;
//        this.isSpecial = false;

        // accessMode 초기화
        // this.accessMode = USER_MODE;
        // 일단은 관리자 모드로 초기화하겠지만, 로그인 한 사람에 따라서 달라지게 하는 부분 추후 구현

        // nowSelected 초기화
        // nowSelected = false;

    }

    public MyButton(Context context, AttributeSet attrs) {
        // XML에서 생성 시 호출되는 생성자
        super(context, attrs);
       // 모드 초기화
//        this.isAbled = true;
//        this.isBooked = false;
//        this.isSpecial = false;

        // accessMode 초기화
        // this.accessMode = USER_MODE;
        // 일단은 테스트를 위해 임의로 초기화하겠지만, 로그인 한 사람에 따라서 달라지게 하는 부분 추후 구현

        // nowSelected 초기화
        // nowSelected = false;
    }
//
//    public void bookingComplete() {
//        // 좌석이 예매 완료되면 해당 좌석 mode 값을 변경
//
//        if(this.isBooked == UNBOOKED) {
//            // 미예약 좌석인 경우
//            this.isBooked = BOOKED;
//
//            // FireBase 업데이트
//            // ..
//        }
//        else {
//            // 이미 예약된 좌석인 경우
//            // 사실 이 부분에 들어갈 일은 없을 것 같음.
//            Toast.makeText(getContext(), "오류가 발생헀습니다.", Toast.LENGTH_SHORT).show();
//        }
//    }

    @Override
    public void onClick(View v) {

    }
}

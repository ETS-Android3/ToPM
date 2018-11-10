package com.example.knk.topm.Object;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Toast;

public class MyButton extends android.support.v7.widget.AppCompatButton implements View.OnClickListener{

    int accessMode; // 관리자가 눌렀는지 고객이 눌렀는지 판별
    // (관리자, 고객 외에도 있을 수 있다고 힌트 주셔서 int로 했습니다.)

    // boolean nowSelected; // 상태 저장 전 임시로 선택되었는지 판별 (true: 선택됨, false: 안 선택됨)

    public boolean isAbled; // 좌석인지 아닌지
    boolean isBooked; // 예약되었는지 아닌지
    boolean isSpecial; // 우등석인지 아닌지

    /* 상수 */
    final static boolean ABLED = true; // 좌석입니다.
    final static boolean UNABLED = false; // 좌석이 아닙니다.
    final static boolean BOOKED = true; // 예약되었습니다.
    final static boolean UNBOOKED = false; // 예약 안 되었습니다.
    final static boolean SPECIAL = true; // 우등 좌석입니다.
    final static boolean UNSPECIAL = false; // 일반 좌석입니다.

//    final static int ADMIN_MODE = 0;
//    final static int USER_MODE = 1;

    public MyButton(Context context) {
        // 자바 코드로 생성 시 호출되는 생성자
        super(context);

        // 모드 초기화
        this.isAbled = true;
        this.isBooked = false;
        this.isSpecial = false;

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
        this.isAbled = true;
        this.isBooked = false;
        this.isSpecial = false;

        // accessMode 초기화
        // this.accessMode = USER_MODE;
        // 일단은 테스트를 위해 임의로 초기화하겠지만, 로그인 한 사람에 따라서 달라지게 하는 부분 추후 구현

        // nowSelected 초기화
        // nowSelected = false;
    }


//    @Override
//    public boolean onTouchEvent(MotionEvent event) {
//        // 클릭 이벤트
//
//        Toast.makeText(getContext(), "onTouchEvent", Toast.LENGTH_SHORT).show();
//        if(this.accessMode == ADMIN_MODE) {
//            // 관리자 접근
//
//        }
//        else if(this.accessMode == USER_MODE) {
//            // 고객 접근
//
//            if(nowSelected) {
//                // 현재 선택됨 -> 선택된 것 풀어야 함
//                this.setBackgroundColor(Color.LTGRAY);
//                Toast.makeText(getContext(), "선택 취소", Toast.LENGTH_SHORT).show();
//                this.nowSelected = false;
//                return false;
//            }
//            else {
//                // 현재 선택되지 않음 -> 선택이 되어야 함
//                this.setBackgroundColor(Color.BLUE);
//                Toast.makeText(getContext(), "좌석 선택", Toast.LENGTH_SHORT).show();
//                this.nowSelected = true;
//                return false;
//            }
//        }
//
//        return super.onTouchEvent(event);
//    }

    public void showSeatsState() {
        // 현재 좌석 상태를 보여주는 함수

        // 예약 여부
        if(this.isBooked == BOOKED) { // 이미 예약된 좌석
            this.setEnabled(false); // 클릭할 수 없게
        }
        else { // 미예약

        }

        // 좌석 여부
        if(this.isAbled == ABLED) { // 좌석임

        }
        else { // 좌석이 아님
            this.setEnabled(false); // 클릭할 수 없고
            this.setVisibility(View.INVISIBLE); // 안 보이되 공간 차지
        }

        // 우등석 여부 - 나중에 추가 요청이 있으면 구현합시다.
//        if(this.isAbled == ABLED) { // 좌석임
//
//        }
//        else { // 좌석이 아님
//
//        }


    }

    public void bookingComplete() {
        // 좌석이 예매 완료되면 해당 좌석 mode 값을 변경

        if(this.isBooked == UNBOOKED) {
            // 미예약 좌석인 경우
            this.isBooked = BOOKED;

            // FireBase 업데이트
            // ..
        }
        else {
            // 이미 예약된 좌석인 경우
            // 사실 이 부분에 들어갈 일은 없을 것 같음.
            Toast.makeText(getContext(), "오류가 발생헀습니다.", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onClick(View v) {

    }
}

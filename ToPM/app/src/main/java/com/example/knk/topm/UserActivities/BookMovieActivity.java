package com.example.knk.topm.UserActivities;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.knk.topm.Object.BookingInfo;
import com.example.knk.topm.Object.InputException;
import com.example.knk.topm.Object.MovieSchedule;
import com.example.knk.topm.Object.MyButton;
import com.example.knk.topm.Object.Screen;
import com.example.knk.topm.Object.User;
import com.example.knk.topm.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

public class BookMovieActivity extends AppCompatActivity {

    User user;                                      // 현재 로그인한 유저
    String scheduleKey;                             // 이전 액티비티에서 넘겨준 데이터베이스 접근 키
    String strDate;                                 // 이전 액티비티에서 넘겨준 상영 날짜 스트링
    String screenKey;                               // 스케줄 키
    MovieSchedule movieSchedule;                    // 해당 스케쥴
    Screen screen;                                  // 해당 스크린
    MyButton[] seats;                               // 좌석
    int size;
    int personnel;                                  // 인원 저장
    int personnelCount;                             // 좌석을 클릭한 갯수 카운트하는 변수\
    ArrayList<String> bookedSeats;                  // 예매한 좌석 버튼 ID저장
    boolean personnelCheck;                         // 인원 입력이 완료되었는지 확인

    EditText editPersonnel;                         // 인원 받는 EditText

    HashMap<String, Boolean> abled;                 // DB에서 받아온 좌석인지 아닌지 정보 저장
    HashMap<String, Boolean> booked;                // DB에서 받아온 예매 되었는지 아닌지 정보 저장
    HashMap<String, Boolean> special;               // DB에서 받아온 우등석인지 아닌지 정보 저장
    HashMap<String, Boolean> couple;                // DB에서 받아온 커플석인지 아닌지 정보 저장
    HashMap<String, Boolean> tempBooked;                   // 현재 사용자가 선택한 좌석(아직 예매 확정되지 않은 좌석)을 저장

    // 레이아웃
    RelativeLayout totalLayout;

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

    // 올해 저장 - 나이제한 대비
    final int THIS_YEAR = Calendar.getInstance().get(Calendar.YEAR);

    ArrayList CheckIndex;
   // ArrayList backup;
    int Check_beside_sit=0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_movie);

        init();

    }

    public void init() {
        // View를 초기화
        final TextView titleTextView = (TextView) findViewById(R.id.titleTextView);
        editPersonnel = (EditText) findViewById(R.id.editPersonnel);
        personnelCount = 0;
        personnelCheck = false;
        bookedSeats = new ArrayList<>();
        tempBooked = new HashMap<>();

        CheckIndex= new ArrayList();

        // 이전 액티비티에서 전송한 정보 수신
        Intent intent = getIntent();
        user = (User) intent.getSerializableExtra("user");  // 현재 로그인 한 유저
        scheduleKey = intent.getStringExtra("key");         // 스케줄 키
        strDate = intent.getStringExtra("date");            // 날짜

        // 데이터베이스
        firebaseDatabase = FirebaseDatabase.getInstance();
        scheduleReference = firebaseDatabase.getReference(schedule_ref);
        screenReference = firebaseDatabase.getReference(screen_ref);
        userReference = firebaseDatabase.getReference(user_ref);
        bookingInfoReference = firebaseDatabase.getReference(bookingInfo_ref);

        // 리스너 달기
        scheduleReference.child(strDate).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot data : dataSnapshot.getChildren()) {
                    if (data.getKey().equals(scheduleKey)) {
                        movieSchedule = data.getValue(MovieSchedule.class); // 객체 저장

                        // 객체를 가지고 온 후 객체의 정보로 초기화 하는 변수는 이 안에서 초기화 해야합니다.
                        // 왜냐하면, 이 리스너가 Asynchronous 하기 때문입니다.
                        booked = movieSchedule.getBookedMap();
                        screenKey = movieSchedule.getScreenNum() + "관";       // 스크린 키
                        titleTextView.setText(movieSchedule.getMovieTitle());
                        break;
                    }
                }
                tempBooked.putAll(booked);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        screenReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot data : dataSnapshot.getChildren()) {

                    if(data.getKey().equals(screenKey)) {
                        screen = data.getValue(Screen.class);

                        size = screen.getRow() * screen.getCol();
                        abled = screen.getAbledMap();
                        special = screen.getSpecialMap();
                        couple = screen.getCoupleMap();
                        printSeats();       // 좌석 출력
                        break;
                    }
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void printSeats() {

        totalLayout = (RelativeLayout) findViewById(R.id.totalLayout);            // 전체 좌석을 출력하는 레이아웃
        seats = new MyButton[size];                                               // 사이즈 개수 만큼 버튼 생성

        createLayout();     // 레이아웃 생성
        assignButtonID();   // 버튼 ID 할당
        // saveToDataBase();   // 데이터베이스에 저장

        // 버튼 클릭 리스너 설정
        for (int k = 0; k < seats.length ; k++) {
            seats[k].setTag(k);
            final int index = Integer.parseInt(screen.getScreenNum()) * 1000 + (k + 1); // 실제 DB에 저장되어있는 버튼의 ID값
            final int copyK = k;    // k를 리스너 안에서 사용하기 위함

            seats[k].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if(personnelCheck) {
                        // 인원 입력이 완료되었을 경우에만 좌석 선택이 가능하다.
                        if(personnelCount < personnel) {
                            // 입력 인원보다 작을 시에만 좌석 클릭이 가능하고요
                            if(tempBooked.get(String.valueOf(index)).equals(MyButton.UNBOOKED)) {
                                // 현재 선택한 자리가 아닌 경우
                                // 선택한다



                                if(couple.get(String.valueOf(index)).equals(MyButton.COUPLE)) {
                                    // 클릭한 자리가 커플석인 경우..
                                    // 왼쪽 자리랑 세트인지 오른쪽 자리랑 세트인지 판별해야 한다.
                                    int leftSet = index + index - 1;    // 클릭한 자리와 왼쪽 자리의 ID 합
                                    int rightSet = index + index + 1;   // 클릭한 자리와 오른쪽 자리의 ID 합
                                    if (couple.get(String.valueOf(leftSet)) != null && couple.get(String.valueOf(index - 1)) != null
                                            && couple.get(String.valueOf(index - 1)).equals(MyButton.COUPLE)&&personnel-personnelCount>=2) {
                                        //예외처리추가함 :인원이 1명만 남으면 커플석 예약못하게했습니다 . ->&&personnel-personnelCount>2
                                        // 왼쪽 자리와 세트
                                        tempBooked.put(String.valueOf(index), MyButton.BOOKED);
                                        tempBooked.put(String.valueOf(index - 1), MyButton.BOOKED);     // 선택으로 상태 변경

                                        CheckIndex.add(index);
                                        CheckIndex.add(index-1);

                                        v.setBackgroundResource(R.drawable.movie_seat_couple_selected);
                                        seats[copyK - 1].setBackgroundResource(R.drawable.movie_seat_couple_selected); // 좌석 이미지 변경
                                        personnelCount += 2;    // 인원 2명 증가
                                        bookedSeats.add(String.valueOf(index));
                                        bookedSeats.add(String.valueOf(index - 1));  // 예매 목록에 추가
                                    } else if (couple.get(String.valueOf(rightSet)) != null && couple.get(String.valueOf(index + 1)) != null
                                            && couple.get(String.valueOf(index + 1)).equals(MyButton.COUPLE)&&personnel-personnelCount>=2) {
                                        //예외처리추가함 :인원이 1명만 남으면 커플석 예약못하게했습니다 .
                                        // 오른쪽 자리와 세트
                                        tempBooked.put(String.valueOf(index), MyButton.BOOKED);
                                        tempBooked.put(String.valueOf(index + 1), MyButton.BOOKED);     // 선택으로 상태 변경

                                        CheckIndex.add(index);
                                        CheckIndex.add(index-1);

                                        v.setBackgroundResource(R.drawable.movie_seat_couple_selected);
                                        seats[copyK + 1].setBackgroundResource(R.drawable.movie_seat_couple_selected); // 좌석 이미지 변경
                                        personnelCount += 2;    // 인원 2명 증가
                                        bookedSeats.add(String.valueOf(index));
                                        bookedSeats.add(String.valueOf(index + 1));  // 예매 목록에 추가
                                    } else {
                                        // 아무것도 아니라면..? 그럴 리가 없다.
                                       // Toast.makeText(BookMovieActivity.this, "알 수 없는 오류입니다./<2", Toast.LENGTH_SHORT).show();
                                        Toast.makeText(BookMovieActivity.this, "남은 인원수 부족함. 커플석 예매할 수 없습니다.", Toast.LENGTH_SHORT).show();
                                    }
                                }

                                else {
                                    // 커플석이 아닌 경우
                                   //if (Check_sit_avaliable(index)) {

                                    tempBooked.put(String.valueOf(index), MyButton.BOOKED); // 선택으로 상태 변경
                                    v.setBackgroundResource(R.drawable.movie_seat_select);  // 좌석 이미지 변경

                                        CheckIndex.add(index);


                                    personnelCount++;                                       // 인원 수 증가
                                    bookedSeats.add(String.valueOf(index));                 // 예매 목록에 추가
                                //}
                                }
                            }
                            else if(tempBooked.get(String.valueOf(index)).equals(MyButton.BOOKED) && booked.get(String.valueOf(index)).equals(MyButton.UNBOOKED)){
                                // 현재 선택했고, 예매되지는 않은 자리
                                // 선택을 취소한다.

                                if(couple.get(String.valueOf(index)).equals(MyButton.COUPLE)) {
                                    // 클릭한 자리가 커플석인 경우..
                                    // 왼쪽 자리랑 세트인지 오른쪽 자리랑 세트인지 판별해야 한다.
                                    int leftSet = index + index - 1;    // 클릭한 자리와 왼쪽 자리의 ID 합
                                    int rightSet = index + index + 1;   // 클릭한 자리와 오른쪽 자리의 ID 합

                                    if(couple.get(String.valueOf(leftSet)) != null && couple.get(String.valueOf(index - 1)) != null
                                            && couple.get(String.valueOf(index - 1)).equals(MyButton.COUPLE)) {
                                        // 왼쪽 자리와 세트
                                        tempBooked.put(String.valueOf(index), MyButton.UNBOOKED);
                                        tempBooked.put(String.valueOf(index - 1), MyButton.UNBOOKED);     // 비선택으로 상태 변경
                                        v.setBackgroundResource(R.drawable.movie_seat_couple_ok);
                                        seats[copyK - 1].setBackgroundResource(R.drawable.movie_seat_couple_ok); // 좌석 이미지 변경
                                        personnelCount -= 2;    // 인원 2명 감소
                                        bookedSeats.remove(String.valueOf(index));
                                        bookedSeats.remove(String.valueOf(index - 1));  // 예매 목록에 추가

                                        Remove_Array_list_index(index);
                                        Remove_Array_list_index(index-1);

                                    }
                                    else if(couple.get(String.valueOf(rightSet)) != null && couple.get(String.valueOf(index + 1)) != null
                                            && couple.get(String.valueOf(index + 1)).equals(MyButton.COUPLE)) {
                                        // 오른쪽 자리와 세트
                                        tempBooked.put(String.valueOf(index), MyButton.UNBOOKED);
                                        tempBooked.put(String.valueOf(index + 1), MyButton.UNBOOKED);     // 비선택으로 상태 변경
                                        v.setBackgroundResource(R.drawable.movie_seat_couple_ok);
                                        seats[copyK + 1].setBackgroundResource(R.drawable.movie_seat_couple_ok); // 좌석 이미지 변경
                                        personnelCount -= 2;    // 인원 2명 감소
                                        bookedSeats.remove(String.valueOf(index));
                                        bookedSeats.remove(String.valueOf(index + 1));  // 예매 목록에 추가

                                        Remove_Array_list_index(index);
                                        Remove_Array_list_index(index+1);

                                    }
                                    else {
                                        // 아무것도 아니라면..? 그럴 리가 없다.
                                        //인원수 1명만 남는경우 예매못하게 했으니 취소할리도 없다.여기 안바꿈.  아래취소기능도 안바꿈.
                                        Toast.makeText(BookMovieActivity.this, "알 수 없는 오류입니다.", Toast.LENGTH_SHORT).show();
                                    }
                                }
                                else {
                                    // 커플석이 아닌 경우
                                    tempBooked.put(String.valueOf(index), MyButton.UNBOOKED);   // 비선택으로 상태 변경
                                    v.setBackgroundResource(R.drawable.movie_seat_ok);      // 좌석 이미지 변경
                                    personnelCount--;                                       // 인원 수 감소
                                    bookedSeats.remove(String.valueOf(index));              // 예매 목록에서 제거

                                    Remove_Array_list_index(index);



                                }
                            }
                        }
                        else if (personnelCount == personnel) {
                            // 선택 인원과 입력 인원이 같은 경우
                            // 선택한 좌석 취소만 가능
                            if(tempBooked.get(String.valueOf(index)).equals(MyButton.BOOKED) && booked.get(String.valueOf(index)).equals(MyButton.UNBOOKED)) {
                                // 선택을 취소하려고 하는 경우

                                if(couple.get(String.valueOf(index)).equals(MyButton.COUPLE)) {
                                    // 클릭한 자리가 커플석인 경우..
                                    // 왼쪽 자리랑 세트인지 오른쪽 자리랑 세트인지 판별해야 한다.
                                    int leftSet = index + index - 1;    // 클릭한 자리와 왼쪽 자리의 ID 합
                                    int rightSet = index + index + 1;   // 클릭한 자리와 오른쪽 자리의 ID 합

                                    if(couple.get(String.valueOf(leftSet)) != null && couple.get(String.valueOf(index - 1)) != null
                                            && couple.get(String.valueOf(index - 1)).equals(MyButton.COUPLE)) {
                                        // 왼쪽 자리와 세트
                                        tempBooked.put(String.valueOf(index), MyButton.UNBOOKED);
                                        tempBooked.put(String.valueOf(index - 1), MyButton.UNBOOKED);     // 비선택으로 상태 변경
                                        v.setBackgroundResource(R.drawable.movie_seat_couple_ok);
                                        seats[copyK - 1].setBackgroundResource(R.drawable.movie_seat_couple_ok); // 좌석 이미지 변경
                                        personnelCount -= 2;    // 인원 2명 감소
                                        bookedSeats.remove(String.valueOf(index));
                                        bookedSeats.remove(String.valueOf(index - 1));  // 예매 목록에 추가

                                        Remove_Array_list_index(index);
                                        Remove_Array_list_index(index-1);
                                    }
                                    else if(couple.get(String.valueOf(rightSet)) != null && couple.get(String.valueOf(index + 1)) != null
                                            && couple.get(String.valueOf(index + 1)).equals(MyButton.COUPLE)) {
                                        // 오른쪽 자리와 세트
                                        tempBooked.put(String.valueOf(index), MyButton.UNBOOKED);
                                        tempBooked.put(String.valueOf(index + 1), MyButton.UNBOOKED);     // 비선택으로 상태 변경
                                        v.setBackgroundResource(R.drawable.movie_seat_couple_ok);
                                        seats[copyK + 1].setBackgroundResource(R.drawable.movie_seat_couple_ok); // 좌석 이미지 변경
                                        personnelCount -= 2;    // 인원 2명 감소
                                        bookedSeats.remove(String.valueOf(index));
                                        bookedSeats.remove(String.valueOf(index + 1));  // 예매 목록에 추가
                                        Remove_Array_list_index(index);
                                        Remove_Array_list_index(index+1);
                                    }
                                    else {
                                        // 아무것도 아니라면..? 그럴 리가 없다.
                                        Toast.makeText(BookMovieActivity.this, "알 수 없는 오류입니다.", Toast.LENGTH_SHORT).show();
                                    }
                                }
                                else {
                                    // 커플석이 아닌 경우
                                    tempBooked.put(String.valueOf(index), MyButton.UNBOOKED);   // 비선택으로 상태 변경
                                    v.setBackgroundResource(R.drawable.movie_seat_ok);      // 좌석 이미지 변경
                                    personnelCount--;                                       // 인원 수 감소
                                    bookedSeats.remove(String.valueOf(index));              // 예매 목록에서 제거

                                    Remove_Array_list_index(index);
                                }
                            }
                            else {
                                // 추가로 선택하려고 하는 경우
                                Toast.makeText(BookMovieActivity.this, "이미 모두 선택했습니다.", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                    else {
                        Toast.makeText(BookMovieActivity.this, "인원 선택을 완료하세요.", Toast.LENGTH_SHORT).show();
                    }
                }
            });

        }
    }

    public void createLayout() {
        // 버튼들 출력할 레이아웃을 생성하는 함수

        // index layout 부분
        for(int count=0; count<screen.getRow(); count++) {   // 행 index

            // 행 번호 출력
            RelativeLayout rowLayout = new RelativeLayout(this);
            TextView rowNum = new TextView(this);
            rowNum.setText(String.valueOf(count+1));

            if(count > 8){
                rowNum.setTextSize(10);   // x>10일 경우 이상헤게 나오기때문에 예외처리임
            }

            RelativeLayout.LayoutParams indexRlayout = new RelativeLayout.LayoutParams(40, 40);//(textview의 크기)
            indexRlayout.topMargin = 0;
            indexRlayout.leftMargin = 10 + (count * 50)+50;// 10( x0 left 까지 거리  )+(생선할때마다 50px 추가함)+(제일 왼쪽 index 빈칸으로 제움)
            rowLayout.addView(rowNum, indexRlayout);
            totalLayout.addView(rowLayout);
        }

        // 열 번호에 해당하는 알파벳 할당해 배열에 저장
        String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String[] colChars = new String[alphabet.length()];
        colChars =  alphabet.split("");

        for(int count=0; count<screen.getCol(); count++) {     // 열 index

            // 열 번호 출력
            RelativeLayout colLayout = new RelativeLayout(this);
            TextView colNum = new TextView(this);
            colNum.setText(colChars[count+1]);      // 미리 알파벳을 저장했던 배열에서 가져와 출력

            RelativeLayout.LayoutParams indexRlayout = new RelativeLayout.LayoutParams(40, 40);
            indexRlayout.topMargin = 10 + (count * 50)+30;
            indexRlayout.leftMargin = 0;
            colLayout.addView(colNum, indexRlayout);
            totalLayout.addView(colLayout);
        }
    }

    public void assignButtonID() {
        // 각각의 버튼에 아이디를 할당하는 함수

        int j = 0;   // 행당 버튼 개수 count 하는 변수

        // ▼ DB에 스크린 아이디를 저장하기 위한 과정
        int screen_Hall_ID_Count = Integer.parseInt(screen.getScreenNum()) * 1000 + 1; // n관일 경우, 버튼의 아이디는 n001부터 시작함.

        for (int i = 0; i < size; i++) {                     // 1차원 배열로 저장

            seats[i] = new MyButton(this);           // 객체 생성
            int id = screen_Hall_ID_Count + i;
            seats[i].setId(id);           // n001 부터 시작해 모든 버튼에 ID 할당

            if(abled.get(String.valueOf(id)).equals(MyButton.UNABLED)) {
                // 좌석이 아닌 곳이라면..
                seats[i].setVisibility(View.INVISIBLE); // 자리는 차지하되 보이지는 않음
            }
            else {
                // 좌석인 곳이라면..
                if(booked.get(String.valueOf(id)).equals(MyButton.BOOKED)) {
                    // 예매 된 자리라면
                    seats[i].setBackgroundResource(R.drawable.movie_seat_selled);
                }
                else {
                    // 예매 되지 않은 자리라면
                    if(special.get(String.valueOf(id)).equals(MyButton.SPECIAL)) {
                        // 우등석이다..
                        seats[i].setBackgroundResource(R.drawable.movie_seat_special_ok);
                    }
                    else if(couple.get(String.valueOf(id)).equals(MyButton.COUPLE)) {
                        // 커플석이다..
                        seats[i].setBackgroundResource(R.drawable.movie_seat_couple_ok);
                    }
                    else {
                        // 일반석이다..
                        seats[i].setBackgroundResource(R.drawable.movie_seat_ok);
                    }
                }
            }


            seats[i].setText("" + i);
            seats[i].setTextSize(0, 8);         // 글자 크기
            RelativeLayout.LayoutParams RL = new RelativeLayout.LayoutParams(40, 40);  //(40,40)-> 버튼의 크기: 40=50-10

            //***************** row * col 배치하기
            if (i % screen.getRow() == 0) {    // >row 시 다음행으로 넘어가기
                j++;
            }
            //RL.leftMargin = 50 * (i % col);    // index 때문에 수치바꿈
            RL.leftMargin = 50+50 * (i % screen.getRow());     // 50는 행간 사이입니다   . 추가된 50은 index (0,0)위치의 빈칸입니다 .
            RL.topMargin = j * 50;            // 50는 열간 사이입니다
            totalLayout.addView(seats[i], RL);        //mybutton 출력함
        }
    }

    public void saveToDataBase() {

        /* MovieSchedule 갱신 */
        movieSchedule.setBookedMap(tempBooked);                             // bookedMap을 갱신한다.
        scheduleReference.child(strDate).child(scheduleKey).setValue(movieSchedule);   // 객체를 데이터베이스에 업로드한다.
        Toast.makeText(this, "영화 예매가 완료되었습니다.", Toast.LENGTH_SHORT).show();

        // 유저의 예매 리스트에 추가하기!!!
        BookingInfo bookingInfo = new BookingInfo(user.getId(), scheduleKey, personnel, bookedSeats, movieSchedule.getScreeningDate(), movieSchedule.getMovieTitle());   // bookingInfo 객체 생성
        String bookingInfoKey = user.getId() + " " + scheduleKey;        // 예매 정보 키 : 유저 아이디 + 스케줄 키

        bookingInfoReference.child(bookingInfoKey).setValue(bookingInfo);

        // 현재 액티비티 종료 후
        finish();
        // 나의 예매 내역으로 이동
        Intent intent = new Intent(this, MyBookingListActivity.class);
        intent.putExtra("user", user);
        startActivity(intent);
        // 이동한 나의 예매 내역에서 뒤로가기를 눌렀을 때 예매했었던 액티비티로 넘어가지 않게하기 위해서
    }




    @RequiresApi(api = Build.VERSION_CODES.N)
    public void completeBtnClicked(View view) {
        if(Check_Sit_ArrayList(CheckIndex)) {
            if (personnelCount == personnel) {
                // 입력 인원만큼 좌석을 선택한 경우
                saveToDataBase();               // 데이터베이스에 저장합니다. ^^
                //Toast.makeText(this, "csa = true", Toast.LENGTH_SHORT).show();
            } else {
                // 아닌 경우

                Toast.makeText(this, "입력 인원만큼 좌석을 선택해주세요.", Toast.LENGTH_SHORT).show();
            }
        }else
            //Toast.makeText(this, "Check_sit_avaliable==false", Toast.LENGTH_SHORT).show();
            Toast.makeText(this, "선택할 수 없습니다. 다시 선택해야 합니다", Toast.LENGTH_SHORT).show();

    }

    public boolean personnelInputComplete(View view) {
        // 인원 정보 입력 완료
        String str = editPersonnel.getText().toString();
        
        if(str.equals("")) {
            // 입력 되지 않음
            try {
                throw new InputException();
            } catch (InputException e) {
                Toast.makeText(this, "입력을 확인하세요.", Toast.LENGTH_SHORT).show();
                e.printStackTrace();
                return false;
            }
        }
        else {
            // 입력됨
            personnel = Integer.parseInt(str);

            if(personnel <= 0) {
                // 0보다 같거나 작다면
                try {
                    throw new InputException();
                } catch (InputException e) {
                    Toast.makeText(this, "입력을 확인하세요.", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                    return false;
                }
            }
            else {
                // 0보다 큰 숫자 입력시 입력창, 버튼 비활성화
                Button personnelInputBtn = (Button) findViewById(R.id.personnelInputBtn);
                personnelInputBtn.setEnabled(false);
                editPersonnel.setEnabled(false);
                personnelCheck = true;
                return true;
            }
        }
    }

    //여기부터 차리선택할때  (빈빈빈&빈&빈) 이런식으로 선택할수없는 예외처리부분입니다.

    public int Check_row(int id){//같은 행인지 아닌지 판단하기
        int buffer=id/1000;    // 몇관인지 계산함.
        int x1=0;               //행 (row)정보
        for(int count =0;count<id-buffer*1000;count++){   //촤표 판단하기
            if(count%screen.getRow()==0)
                x1++;
        }

        return x1;   //같은 행이면 x값이 같음
    }
    public void Remove_Array_list_index(int id){
        for(int i =0;i<CheckIndex.size();i++){
            if((int)CheckIndex.get(i)==id)
                CheckIndex.remove(i);
        }
    }
    public boolean Check_sit_avaliable(int id){

        int First_Check=0;   // 1.연속된 자라 예매할떄 무조건 살수있음 (거절안함)
        int buffid=id/1000;
        for(int count=0;count<CheckIndex.size()-1;count++) {
            if (((int) CheckIndex.get(count) + 1) == (int) CheckIndex.get(count + 1)) {
                First_Check++;
            }
        }
        if(First_Check>personnel-1) {
            //Toast.makeText(this, "연속된자리 true", Toast.LENGTH_SHORT).show();
            return true;
        }

        if(id==(buffid*1000+1)){  //2.인원 3명이하 && A1자리 선택하때 예외처리
            if(!booked.get(String.valueOf(id+1)).equals(MyButton.BOOKED)&&personnel<3){

                return false;
            }else
                return true;

        }

        int final_sit_ID = buffid* 1000 + (seats.length);
        if(id==final_sit_ID){
            if(!booked.get(String.valueOf(id-1)).equals(MyButton.BOOKED)&&personnel<3){
                return false;
            }else
                return true;
        }


        if(abled.get(String.valueOf(final_sit_ID)).equals(MyButton.UNABLED)){
            if(id==final_sit_ID-1){
                if(!booked.get(String.valueOf(id-1)).equals(MyButton.BOOKED)&&personnel<3){
                    return false;
                }else
                    return true;
            }

        }if (abled.get(String.valueOf(final_sit_ID-1)).equals(MyButton.UNABLED)) {
            if(id==final_sit_ID-2){
                if(!booked.get(String.valueOf(id-1)).equals(MyButton.BOOKED)&&personnel<3){
                    return false;
                }else
                    return true;
            }
        }if(abled.get(String.valueOf(final_sit_ID-2)).equals(MyButton.UNABLED)) {
            if(id==final_sit_ID-3){
                if(!booked.get(String.valueOf(id-1)).equals(MyButton.BOOKED)&&personnel<3){
                    return false;
                }else
                    return true;
            }
        }




        if( couple.get(String.valueOf(id +2)).equals(MyButton.COUPLE)){  //3. 연속된 커플석 예매할떄 예외처리
            return true;
        }
        if( couple.get(String.valueOf(id -2)).equals(MyButton.COUPLE)){  //3. 연속된 커플석 예매할떄 예외처리
            return true;
        }



        //4. 여기부터 다양한 케이스에 대한 예외차리입니다. 설명못하겠습니다 ..
        if((!booked.get(String.valueOf(id-1)).equals(MyButton.BOOKED)&&
                booked.get(String.valueOf(id-2)).equals(MyButton.BOOKED)&&
                !abled.get(String.valueOf(id-2)).equals(MyButton.UNABLED)&&
                Check_row(id)!=Check_row(id+1))
                ){
            //Toast.makeText(this, "error :0001", Toast.LENGTH_SHORT).show();
            return false;
        }
        //5.
        if(!booked.get(String.valueOf(id+1)).equals(MyButton.BOOKED)&&
                !booked.get(String.valueOf(id+2)).equals(MyButton.BOOKED)&&
                !abled.get(String.valueOf(id-1)).equals(MyButton.UNABLED)&&
                !abled.get(String.valueOf(id-2)).equals(MyButton.UNABLED)&&
                Check_row(id)==Check_row(id+1)&&
                Check_row(id)!=Check_row(id+2)

                ){
            //Toast.makeText(this, "error ：0002", Toast.LENGTH_SHORT).show();
            return false;
        }
        //6.
        if(!booked.get(String.valueOf(id-1)).equals(MyButton.BOOKED)&&
                booked.get(String.valueOf(id-2)).equals(MyButton.BOOKED)&&
                abled.get(String.valueOf(id+1)).equals(MyButton.UNABLED)&&
                !abled.get(String.valueOf(id+2)).equals(MyButton.UNABLED)&&
                Check_row(id)==Check_row(id+1)


                ){
            //Toast.makeText(this, "error ：0004", Toast.LENGTH_SHORT).show();
            return false;
        }

        if(
                Check_row(id)!=Check_row(id-1)||
                        abled.get(String.valueOf(id-1)).equals(MyButton.UNABLED)||
                        abled.get(String.valueOf(id+1)).equals(MyButton.UNABLED)||
                        Check_row(id)!=Check_row(id+1)||
                        couple.get(String.valueOf(id -1)).equals(MyButton.COUPLE)||
                        couple.get(String.valueOf(id + 1)).equals(MyButton.COUPLE)||
                        booked.get(String.valueOf(id-1)).equals(MyButton.BOOKED)||
                        booked.get(String.valueOf(id+1)).equals(MyButton.BOOKED)
                ){
            //왼쪽좌석은 unabled(경로석) 혹은 벽쪽석 혹은 옆자리 커플석인 경우 위 false 조건 만족하지않으면 선택가능함
            return true;
        }


        // 오른쪽 자리 예매 안하는 자리 3개 이상이면 석택가능
        if(Check_row(id)==Check_row(id+1)&&Check_row(id)==Check_row(id+2)&&Check_row(id)==Check_row(id+3)){
            if(!booked.get(String.valueOf(id+1)).equals(MyButton.BOOKED)&&
                    !booked.get(String.valueOf(id+2)).equals(MyButton.BOOKED)&&
                    !booked.get(String.valueOf(id+3)).equals(MyButton.BOOKED)){
                return true;
            }

        }
        // 왼쪽 자리 예매 안하는 자리 2개 이상이면 석택가능
        if(!booked.get(String.valueOf(id-1)).equals(MyButton.BOOKED)&&
                !booked.get(String.valueOf(id-2)).equals(MyButton.BOOKED)

                ){
            return true;
        }

        Toast.makeText(this, "없는 케이스입니다. true", Toast.LENGTH_SHORT).show();
        return true;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)    // arraylist sort기능 쓰려면 이줄 자동생성함
    public boolean Check_Sit_ArrayList(ArrayList arr){  // arraylist 검사하는 함수

        if(CheckIndex.size()>1)   // 밑에 있는 한자리씩 띌때 쓰는 예외처리 index
            Check_beside_sit=0;

        int buffid =(int)arr.get(0)/1000;  // 몇관인지 계산함
        if( (int)arr.get(0)==(buffid*1000+1)&&personnel==1)  //1인이며 a1자리 선택할때 예외차리
            return true;

        if(arr.size()!=1) {    // 1인 아니면 arraylist sort함
            arr.sort(null);
        }
        arr.add(9998);   //탐색할때 +2 까지 하기 때문에 쓰레기값 2개 넣음 (NullPointer 방지)
        arr.add(9999);
        for(int count=0;count<arr.size()-2;count++){
            if(!Check_sit_avaliable((int)arr.get(count))
                    ){
               // Toast.makeText(this, count+1+"번 자리 선택불가함니다", Toast.LENGTH_SHORT).show();
                Toast.makeText(this, "선택불가함니다,다시 시도해보세요", Toast.LENGTH_SHORT).show();
                return false;
            }

            if (((int) arr.get(count) + 2) == (int) arr.get(count + 1)) {
                Check_beside_sit++; // 한 자리씩 띌떄 예외처리부분   010101
            }
        }

        if(Check_beside_sit>=1) {//한 자리씩 띌떄 false리탄   010101
            //Toast.makeText(this, "error 0003", Toast.LENGTH_SHORT).show();
            for(int r=arr.size();r>1;r--){   // 리턴하기전에 arrlist지워야함 (NullPointer)
                arr.remove(r-1);
            }
            return false;
        }

        for(int r=arr.size();r>1;r--){//리턴하기전에 arrlist지워야함 (NullPointer)
            arr.remove(r-1);
        }
        //Toast.makeText(this, "ID : "+"always true"+" error", Toast.LENGTH_SHORT).show();
        return true;

    }


}



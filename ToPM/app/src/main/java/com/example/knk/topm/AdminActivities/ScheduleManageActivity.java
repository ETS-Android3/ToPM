package com.example.knk.topm.AdminActivities;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.NumberPicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.knk.topm.CustomAdapters.ScheduleListAdapter;
import com.example.knk.topm.Object.InputException;
import com.example.knk.topm.Object.Movie;
import com.example.knk.topm.Object.MovieSchedule;
import com.example.knk.topm.R;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

public class ScheduleManageActivity extends AppCompatActivity {

    Spinner movieSpinner;           // 등록된 영화 목록 Spinner
    ArrayList<Movie> movieData;     // 서버에 저장된 영화 데이터 받아오는 배열
    ArrayAdapter<Movie> adapter;    // 스피너에 연결하는 어댑터

    ListView dayScheduleList;       // 영화 스케줄 출력
    ArrayList<MovieSchedule>[] scheduleData; // 서버에 저장된 스케줄 데이터 받아오는 배열
    ScheduleListAdapter schAdapter; // 리스트뷰에 연결하는 어댑터

    Button screenBtn;
    Button dateBtn;
    Button timeBtn;

    TextView dateTextView;
    Button prevBtn;
    Button nextBtn;

    Date currentDate; // 오늘 날짜
    String strDate; // 오늘 날짜 문자열

    Movie selectedMovie; // 선택한 영화
    int screenNum; // 선택한 상영관
    int startHour; // 시작 시간
    int startMin; // 시작 분
    int showYear; // 상영 년도
    int showMonth; // 상영 월
    int showDay; // 상영 일

    int dateCount;

    // 데이터베이스
    private FirebaseDatabase firebaseDatabase;      //firebaseDatabase
    private DatabaseReference movieReference;        //rootReference
    private DatabaseReference scheduleReference;        //rootReference
    private static String movie_ref = "movie";
    private static String schedule_ref = "schedule";

    /* 상수 */
    final static int SCREENS = 5;    // 상영관 5개
    final int DATE_DIALOG = 1111; // 날짜 다이어로그
    final int TIME_DIALOG = 2222; // 시간 다이어로그
    final int FUTURE_DATE = 4; // 미래 날짜

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule_manage);

        init();
    }

    public void init() {
        //초기화

        /*1.액티비티 타이틀, 디비 연결*/
        // 상단 텍스트뷰, 버튼
        dateTextView = findViewById(R.id.dateTextView);
        prevBtn = findViewById(R.id.prevBtn);
        nextBtn = findViewById(R.id.nextBtn);
        //데이터베이스
        firebaseDatabase = FirebaseDatabase.getInstance();
        scheduleReference = firebaseDatabase.getReference(schedule_ref);
        movieReference = firebaseDatabase.getReference(movie_ref);


        /*2. 해당 날짜 스케줄*/
        // 리스트뷰
        dayScheduleList = findViewById(R.id.dayScheduleList);
        scheduleData = new ArrayList[FUTURE_DATE];
        dateCount = 0; // 0이면 오늘, 1, 2, 3일 +됨
        for(int i=0; i<FUTURE_DATE; i++) {
            scheduleData[i] = new ArrayList<>();
        }

        // 오늘 날짜 계산
        long now = System.currentTimeMillis();
        currentDate = new Date(now);// 오늘 날짜
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy년 MM월 dd일");
        strDate = sdf.format(currentDate);
        dateTextView.setText(strDate);

        Calendar today = Calendar.getInstance();
        // 미래 날짜 계산 후 오늘날짜로부터 이후 3일까지의 스케줄 데이터 arrayList 객체배열에 저장
        for(int i=0; i<FUTURE_DATE; i++) {

            today.add(Calendar.DATE, i);            //날짜 더하기
            strDate = sdf.format(today.getTime());

            final int index = i;
            // 스케줄 데이터베이스 변경 이벤트 핸들러
            scheduleReference.child(strDate).addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                    MovieSchedule newSchedule = dataSnapshot.getValue(MovieSchedule.class); // 새로 추가된 스케줄 받아옴
                    scheduleData[index].add(newSchedule); // 리스트 뷰에 갱신
                    schAdapter.notifyDataSetChanged();
                }

                @Override
                public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                }

                @Override
                public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

                }

                @Override
                public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

        }
        //init함수 안이므로 오늘 날짜에 맞게 어댑터 달기
        schAdapter = new ScheduleListAdapter(this,R.layout.schedule_list_adapter_row,scheduleData[0]);
        dayScheduleList.setAdapter(schAdapter);
        scheduleReference.child(strDate).addListenerForSingleValueEvent(new ValueEventListener() { // 최초 한번 실행되고 삭제되는 콜백
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // 모든 데이터 가지고 오기
                for(DataSnapshot data : dataSnapshot.getChildren()) {
                    MovieSchedule schedule = data.getValue(MovieSchedule.class);

                    if(schedule == null)
                        Toast.makeText(ScheduleManageActivity.this, "null", Toast.LENGTH_SHORT).show();
                    else {
                        scheduleData[0].add(schedule); // scheduleData에 삽입
                        // Toast.makeText(MovieManageActivity.this, movie.getTitle(), Toast.LENGTH_SHORT).show();
                    }
                }
                schAdapter.notifyDataSetChanged(); // 데이터 갱신 통지
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



        /*3. 입력 부분*/
        // 스피너
        movieSpinner = (Spinner) findViewById(R.id.movieSpinner);
        movieData = new ArrayList<Movie>();
        adapter = new ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, movieData);
        movieSpinner.setAdapter(adapter);
        // 데이터 베이스에서 정보 받아와서 movieData에 저장
        movieReference.addListenerForSingleValueEvent(new ValueEventListener() { // 최초 한번 실행되고 삭제되는 콜백
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // 모든 데이터 가지고 오기
                for(DataSnapshot data : dataSnapshot.getChildren()) {
                    Movie movie = data.getValue(Movie.class);

                    if(movie == null)
                        Toast.makeText(ScheduleManageActivity.this, "null", Toast.LENGTH_SHORT).show();
                    else
                        movieData.add(movie); // movieData에 삽입
                    adapter.notifyDataSetChanged();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        //스피너에 셀렉티드 리스너 달기
        selectedMovie = null;
        movieSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedMovie = movieData.get(position); // selectedMovie에 저장
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        // 변수 초기화
        screenNum = -1; // 상영관 번호 선택 전에는 -1, 선택 후 1~5
        startHour = -1; // 시작 시간
        startMin = -1; // 시작 분
        showYear = -1; // 상영 년도
        showMonth = -1; // 상영 월
        showDay = -1; // 상영 일

        screenBtn = (Button) findViewById(R.id.screenBtn);
        dateBtn = (Button) findViewById(R.id.dateBtn);
        timeBtn = (Button) findViewById(R.id.timeBtn);

    }

    public void selectScreenBtn(View view) {
        // 상영관 선택 다이어로그 (NumberPicker 다이어로그)

        final Dialog dialog = new Dialog(this);
        dialog.setTitle("상영관 선택");
        dialog.setContentView(R.layout.numberpicker_dialog);

        Button setBtn = (Button) dialog.findViewById(R.id.setButn);
        // Button cancelBtn = (Button) dialog.findViewById(R.id.cancelBtn);

        final NumberPicker screenPicker = (NumberPicker) dialog.findViewById(R.id.screenPicker);
        screenPicker.setMaxValue(SCREENS); // max value SCREENS (5)
        screenPicker.setMinValue(1);   // min value 1
        screenPicker.setWrapSelectorWheel(false);
        // screenPicker.setOnValueChangedListener((NumberPicker.OnValueChangeListener) this);

        setBtn.setOnClickListener(new View.OnClickListener() {
            // 확인 버튼 클릭
            @Override
            public void onClick(View v) {
                // 상영관 번호 저장
                screenNum = screenPicker.getValue();
                screenBtn.setText(String.valueOf(screenNum)+"관"); // 선택한 것 버튼 텍스트에 반영
                dialog.dismiss(); // 다이어로그 파괴
            }
        });
//        cancelBtn.setOnClickListener(new View.OnClickListener() {
//            // 취소 버튼 클릭
//            @Override
//            public void onClick(View v) {
//                dialog.dismiss(); // 그냥 끔
//            }
//        });
        dialog.show();
    }

    public void inputDateBtn(View view) {
        // 날짜 선택 버튼 (DatePicker 다이어로그)
        showDialog(DATE_DIALOG);
    }

    public void inputStartTimeBtn(View view) {
        // 시작 시간 입력 (TimePicker 다이어로그)
        showDialog(TIME_DIALOG);
    }

    public void scheduleComplete(View view) {
        // 완료 버튼 클릭
        // Input 검사
        int[] args = new int[6];
        args[0] = showYear;
        args[1] = showMonth;
        args[2] = showDay;
        args[3] = startHour;
        args[4] = startMin;
        args[5] = screenNum;

        if(inputCheck(args)) {
            // 검사 통과
            // 데이터 베이스에 업로드
            Date screeningDate = new Date(showYear, showMonth, showDay); // Date로 변환
            screeningDate.setHours(startHour); // 시간
            screeningDate.setMinutes(startMin); // 분도 넣어줍니다.
            Toast.makeText(this, String.valueOf(showYear) + String.valueOf(screeningDate.getYear()), Toast.LENGTH_SHORT).show();
//            Calendar today = Calendar.getInstance();
//            today.add(Calendar.DATE, dateCount);
//            Date future = today.getTime();
//
//            SimpleDateFormat sdf = new SimpleDateFormat("yyyy년 MM월 dd일");
//            strDate = sdf.format(future);

            SimpleDateFormat sdf = new SimpleDateFormat("MM월 dd일");
            strDate = sdf.format(screeningDate);
            strDate = String.valueOf(showYear) + "년 " + strDate;
            Toast.makeText(this, strDate, Toast.LENGTH_SHORT).show();

            MovieSchedule movieSchedule = new MovieSchedule(selectedMovie.getTitle(), String.valueOf(screenNum), screeningDate/*, startHour, startMin*/); // 객체 생성후
            // String key = movieSchedule.getScreenNum() + "관" + movieSchedule.screeningDate.getHours()+":"+movieSchedule.screeningDate.getMinutes();
            scheduleReference.child(strDate).push().setValue(movieSchedule); // "오늘 날짜" 아래 push\
            Toast.makeText(this, "저장되었습니다.", Toast.LENGTH_SHORT).show();
        }
        else {
            // 틀린 점이 있음
            try {
                throw new InputException();
            } catch (InputException e) {
                Toast.makeText(this, "입력을 확인하세요.", Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }
        }
    }

    public boolean inputCheck(int[] args) {
        // args가 모두 -1이 아니여야만 true 반환
        for(int i=0; i<args.length; i++) {
            if(args[i] == -1)
                return false;
        }
        return true;
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        // 입력 값에 따라 다른 다이어로그 생성

        switch(id) {
            case DATE_DIALOG: // 날짜 다이어로그
                Calendar calendar = new GregorianCalendar(Locale.KOREA);
                DatePickerDialog dateDialog = new DatePickerDialog
                        (this, // 현재화면의 제어권자
                                new DatePickerDialog.OnDateSetListener() {
                                    public void onDateSet(DatePicker view, int year, int monthOfYear,int dayOfMonth) {
                                        Toast.makeText(getApplicationContext(),year+"년 "+(monthOfYear+1)+"월 "+dayOfMonth+"일 을 선택했습니다",
                                                Toast.LENGTH_SHORT).show();
                                        // 설정 후 변수에 저장
                                        showYear = year;
                                        showMonth = monthOfYear;
                                        showDay = dayOfMonth;

                                        dateBtn.setText(year+"년 "+(monthOfYear+1)+"월 "+dayOfMonth+"일"); // 버튼에 선택 날짜 반영
                                    }
                                }
                                , // 사용자가 날짜설정 후 다이얼로그 빠져나올때
                                //    호출할 리스너 등록
                                calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)); // 기본값 연월일
                return dateDialog;

            case TIME_DIALOG: // 시간 다이어로그
                TimePickerDialog timeDialog =
                        new TimePickerDialog(this,
                                new TimePickerDialog.OnTimeSetListener() {
                                    @Override
                                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                        Toast.makeText(getApplicationContext(), hourOfDay +"시 " + minute+"분 을 선택했습니다",
                                                Toast.LENGTH_SHORT).show();
                                        // 설정 후 변수에 저장
                                        startHour = hourOfDay;
                                        startMin = minute;
                                        timeBtn.setText(hourOfDay +"시 " + minute+"분");
                                    }
                                }, // 값설정시 호출될 리스너 등록
                                0,0, false); // 기본값 시분 등록
                // true : 24 시간(0~23) 표시
                // false : 오전/오후 항목이 생김
                return timeDialog;
        }
        return super.onCreateDialog(id);
    }

    public void prevBtnClicked(View view) {
        // 이전 날짜 버튼

        if(dateCount != 0) {
            // 미래 날짜를 보고 있을 때만 동작하도록
            dateCount--; // 하루 감소

            nextBtn.setEnabled(true); // 다음 버튼 활성화

//            Calendar today = Calendar.getInstance();
//            today.add(Calendar.DATE, -dateCount);
//            Date future = today.getTime();
//
//            SimpleDateFormat sdf = new SimpleDateFormat("yyyy년 MM월 dd일");
//            String str = sdf.format(future);

            String str = dateCalculator(dateCount);
            dateTextView.setText(str);

            //Toast.makeText(this, str, Toast.LENGTH_SHORT).show();

            if(dateCount == 0) // 오늘 날짜에 도달
                prevBtn.setEnabled(false);

            schAdapter = new ScheduleListAdapter(this, R.layout.schedule_list_adapter_row, scheduleData[dateCount]); // 어댑터 새로 정의
            dayScheduleList.setAdapter(schAdapter); // 어댑터 새로 설정
            schAdapter.notifyDataSetChanged(); // 변경 통지
        }
    }

    public void nextBtnClicked(View view) {

        if(dateCount < FUTURE_DATE) {
            // 가장 미래 날짜를 보고 있으면 안되어야죠
            dateCount++; // 하루 증가

            prevBtn.setEnabled(true); // 다음 버튼 활성화

//            Calendar today = Calendar.getInstance();
//            today.add(Calendar.DATE, dateCount);
//            Date future = today.getTime();
//
//            SimpleDateFormat sdf = new SimpleDateFormat("yyyy년 MM월 dd일");
//            String str = sdf.format(future);
            String str = dateCalculator(dateCount);
            dateTextView.setText(str);

            if(dateCount == FUTURE_DATE - 1) // 가장 미래 날짜에 도달
                nextBtn.setEnabled(false);

            schAdapter = new ScheduleListAdapter(this, R.layout.schedule_list_adapter_row, scheduleData[dateCount]); // 어댑터 새로 정의
            dayScheduleList.setAdapter(schAdapter); // 어댑터 새로 설정
            schAdapter.notifyDataSetChanged(); // 변경 통지
        }
    }

    public String dateCalculator(int n) {
        // n일 후 날짜 스트링 계산
        String str;

        Calendar today = Calendar.getInstance();
        today.add(Calendar.DATE, n);
        Date future = today.getTime();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy년 MM월 dd일");
        str = sdf.format(future);

        return str;
    }
}

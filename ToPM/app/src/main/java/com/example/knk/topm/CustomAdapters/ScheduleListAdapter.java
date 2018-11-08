package com.example.knk.topm.CustomAdapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.knk.topm.Object.MovieSchedule;
import com.example.knk.topm.R;

import java.util.ArrayList;

public class ScheduleListAdapter extends ArrayAdapter<MovieSchedule> implements View.OnClickListener{

    private Context context;
    private ArrayList<MovieSchedule> data;
    private int resource;
    private ScheduleDeleteBtnClickListener scheduleDeleteBtnClickListener;
    private String strDateKey;
    private int dateCount;

    public ScheduleListAdapter(@NonNull Context context, int resource, ArrayList<MovieSchedule> objects, ScheduleDeleteBtnClickListener listener,String strDateKey, int dateCount) {
        super(context, resource, objects);
        this.context = context;
        this.data = objects;
        this.resource = resource;
        this.scheduleDeleteBtnClickListener = listener;
        this.strDateKey = strDateKey;
        this.dateCount = dateCount;
    }
    //삭제버튼 클릭리스너 인터페이스
    public interface ScheduleDeleteBtnClickListener{
        void onScheduleDeleteBtnClick(int position, String strDateKey, int dateCount);           //현재 클릭한 row의 리스트뷰에서의 position을 인자로 받음. -> 어댑터를 선언한 액티비티에서 position을 알 수 있게 됨.
    }

    @Override
    public int getCount() {
        return data == null ? 0 : data.size();
    }

    @Override
    public MovieSchedule getItem(int i) {
        return data.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {

        View v = view;
        TextView title;
        TextView time;
        TextView screen;
        Button deleteBtn;

        if(v == null){
            LayoutInflater inflater= (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(resource, null);
        }

        MovieSchedule movieSchedule = data.get(position);

        if(movieSchedule != null){
            title = v.findViewById(R.id.titleTextView);
            time = v.findViewById(R.id.timeTextview);
            screen = v.findViewById(R.id.screenTextview);
            deleteBtn = v.findViewById(R.id.deleteBtn);
            if (title!=null&&time!=null&&screen!=null){
                title.setText(movieSchedule.getMovieTitle());
                time.setText(movieSchedule.screeningDate.getHours()+":" + movieSchedule.screeningDate.getMinutes());
                screen.setText(movieSchedule.getScreenNum()+"관 ");
            }
            if(deleteBtn!=null){
                //삭제버튼에 해당 포지션 태그 달아주고
                deleteBtn.setTag(position);
                //클릭리스너도 달아줌
                deleteBtn.setOnClickListener(this);
            }
        }

        return v;
    }

    @Override
    public void onClick(View v) {
        if(this.scheduleDeleteBtnClickListener != null){
            //Toast.makeText(getContext(),"Delete버튼 누름",Toast.LENGTH_SHORT).show();
            this.scheduleDeleteBtnClickListener.onScheduleDeleteBtnClick((int)v.getTag(),strDateKey,dateCount);
        }
    }
}

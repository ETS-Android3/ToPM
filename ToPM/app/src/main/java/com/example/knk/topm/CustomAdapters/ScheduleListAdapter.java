package com.example.knk.topm.CustomAdapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.knk.topm.Object.MovieSchedule;
import com.example.knk.topm.R;

import java.util.ArrayList;

public class ScheduleListAdapter extends ArrayAdapter<MovieSchedule> {

    Context context;
    ArrayList<MovieSchedule> data;
    int resource;

    public ScheduleListAdapter(@NonNull Context context, int resource, ArrayList<MovieSchedule> objects) {
        super(context, resource, objects);
        this.context = context;
        this.data = objects;
        this.resource = resource;
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

        if(v == null){
            LayoutInflater inflater= (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(resource, null);
        }

        MovieSchedule movieSchedule = data.get(position);

        if(movieSchedule != null){
            title = v.findViewById(R.id.titleTextView);
            time = v.findViewById(R.id.timeTextview);

            if (title!=null){
                title.setText(movieSchedule.getMovieTitle());
                // time.setText(movieSchedule.);
                time.setText("시간");
            }
        }

        return v;
    }

}

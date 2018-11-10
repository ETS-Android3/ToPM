package com.example.knk.topm.CustomAdapters;

import android.content.Context;
import android.widget.ArrayAdapter;

import com.example.knk.topm.Object.MovieSchedule;

import java.util.List;

public class NormalScheduleListAdapter extends ArrayAdapter<MovieSchedule> {
    public NormalScheduleListAdapter(Context context, int resource,List<MovieSchedule> objects) {
        super(context, resource, objects);
    }
}

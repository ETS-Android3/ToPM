package com.example.knk.topm.CustomAdapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.knk.topm.Object.Movie;
import com.example.knk.topm.R;

import java.util.ArrayList;


public class MovieListAdapter extends ArrayAdapter<Movie> {

    Context context;
    ArrayList<Movie> data;
    int resource;

    public MovieListAdapter(Context context, int resource, ArrayList objects) {
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
    public Movie getItem(int i) {
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
        //Button deleteBtn;
        if(v==null){
            LayoutInflater inflater= (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(resource, null);
        }

        Movie movie = data.get(position);
        if(movie!=null){
            title = v.findViewById(R.id.movielistTextview);
            if (title!=null){
                title.setText(movie.getTitle());
            }
        }
        //deleteBtn = (Button)v.findViewById(R.id.deleteBtn);
        return v;
    }
}

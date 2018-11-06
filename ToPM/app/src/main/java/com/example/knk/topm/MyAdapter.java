package com.example.knk.topm;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.knk.topm.Object.Movie;

import java.util.List;



public class MyAdapter extends BaseAdapter implements View.OnClickListener {

    static class ViewHolder{
        TextView textView;
        Button button;
    }

    Context context;
    List<Movie> data;

    public MyAdapter(List<Movie> data){
        this.data=data;
    }

    @Override
    public int getCount() {
        return data == null ? 0 : data.size();
    }

    @Override
    public Object getItem(int i) {
        return i;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewholder = null;

        if(context == null){
            context=viewGroup.getContext();
        }

        if(view == null){
            view=LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.movielistxml,null);
            viewholder=new ViewHolder();
            viewholder.textView = (TextView)view.findViewById(R.id.movielisttextview);
            viewholder.button = (Button)view.findViewById(R.id.deleteBtn);
            view.setTag(viewholder);
        }

        viewholder = (ViewHolder)view.getTag();
        viewholder.textView.setText(data.get(i).getTitle());
        viewholder.textView.setOnClickListener(this);
        viewholder.button.setText("삭제");
        viewholder.button.setOnClickListener(this);

        return view;
    }
    @Override
    public void onClick(View view) {

      // i.getBooleanArrayExtra("DELETE_CHECK");

        // ******* 이런식으로 액티비티 생성하면 안될텐데요..ㅠㅠ;;
//        MovieManageActivity m = new MovieManageActivity();
//        m.deleteCheck();
//        String test = "";
//        if(m.deleteCheck() == false){
//            test="false";
//        }else{
//            test="true";
//        }

//    switch (view.getId()){
//        case R.id.movielistbtn:
//            Toast.makeText(context,"this is delete button "+test,Toast.LENGTH_SHORT).show();
//
//
//
//            break;
//        case R.id.movielisttextview:
//            Toast.makeText(context,"this is textview on click  ",Toast.LENGTH_SHORT).show();
//            break;
//
//    }
    }
}

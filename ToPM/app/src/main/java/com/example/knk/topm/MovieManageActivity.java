package com.example.knk.topm;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MovieManageActivity extends AppCompatActivity {

    ListView l;
    List<String> moviedata;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_manage);

        moviedata = new ArrayList<>();
        l=(ListView)findViewById(R.id.movieList1);

        moviedata.add("aaa");
        moviedata.add("bbb");


        //ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,moviedata);
        MyAdapter aa =  new MyAdapter(moviedata);
        l.setAdapter(aa);

        l.setOnItemClickListener(new ListView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(MovieManageActivity.this,"this is a item" + l,Toast.LENGTH_SHORT).show();
            }
        });






    }

    public void completebtn(View view) {
        EditText d=(EditText)findViewById(R.id.edittxtdir);
        EditText n=(EditText)findViewById(R.id.edittxtname);
        EditText r=(EditText)findViewById(R.id.edittxtruntime);

        if(d.getText().length()<1){
            Toast.makeText(this,"감독 정보 입력을 확인하세요",Toast.LENGTH_SHORT).show();
        }
        else if(n.getText().length()<1){
            Toast.makeText(this,"아름 정보 입력을 확인하세요",Toast.LENGTH_SHORT).show();
        }
        else if(r.getText().length()<1){
            Toast.makeText(this,"러잉타임 정보 입력을 확인하세요",Toast.LENGTH_SHORT).show();
        }
        else{
            //db

            startActivity(new Intent(this,AdminMainActivity.class));

        }
    }

    public boolean deleteCheck(){
        //detail method

        return false;
    }


}



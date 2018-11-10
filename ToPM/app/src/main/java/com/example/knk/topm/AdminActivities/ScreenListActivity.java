package com.example.knk.topm.AdminActivities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.knk.topm.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ScreenListActivity extends AppCompatActivity implements View.OnClickListener {

    final int SCREEN_COUNT = 5; // 상영관 개수
    Button[] screenEdits;
    LinearLayout buttonLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screen_list);

        init();
    }

    public void init() {

        // 객체 생성
        screenEdits = new Button[SCREEN_COUNT];
        buttonLayout = (LinearLayout) findViewById(R.id.buttonLayout);

        for(int i=0; i<SCREEN_COUNT; i++) {
            screenEdits[i] = new Button(this);
            screenEdits[i].setId(i+1);
            screenEdits[i].setText(String.valueOf(i+1)+"관");
            screenEdits[i].setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
            screenEdits[i].setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
            buttonLayout.addView(screenEdits[i]);

            //----
            screenEdits[i].setOnClickListener(this);
        }


    }

    @Override
    public void onClick(View v) {

        startActivity(new Intent(this,ScreenEditActivity1.class));

        DatabaseReference mDatabase;
        mDatabase = FirebaseDatabase.getInstance().getReference();

        Button b = (Button)findViewById(v.getId());

        //Toast.makeText(this, b.getText()+"", Toast.LENGTH_SHORT).show();

//
        String g= b.getText().toString();

        String c[]= g.split("관");

        Toast.makeText(this, c[0]+"", Toast.LENGTH_SHORT).show();

        mDatabase.child("DBScreenSits").child(g).child("ScreenID").setValue(c[0]);
    }
}

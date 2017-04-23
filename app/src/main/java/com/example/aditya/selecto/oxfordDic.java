package com.example.aditya.selecto;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.widget.TextView;

public class oxfordDic extends AppCompatActivity {
    TextView t;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_oxford_dic);
        Intent intent = getIntent();

        String item = intent.getStringExtra("selected-item");
        t=(TextView) findViewById(R.id.ress);
        t.setMovementMethod(new ScrollingMovementMethod());
        t.setText(item);
    }
}

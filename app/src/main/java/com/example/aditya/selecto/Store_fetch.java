package com.example.aditya.selecto;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class Store_fetch extends AppCompatActivity {
    DatabaseHandler db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_fetch);
        Intent intent = getIntent();
        String item = intent.getStringExtra("selected-item");
    }
    public void fetch(View view)
    {
        db=new DatabaseHandler(this);
        EditText key =(EditText)findViewById(R.id.key);
        EditText value =(EditText)findViewById(R.id.value);
        StoredData sd=db.getEntry(key.getText().toString());

        value.setText(sd.getvalue());

    }
    public void store(View view)
    {
        db=new DatabaseHandler(this);
        EditText key =(EditText)findViewById(R.id.key);
        EditText value =(EditText)findViewById(R.id.value);
        db.addEntry(new StoredData(key.getText().toString(), value.getText().toString()));

    }
}

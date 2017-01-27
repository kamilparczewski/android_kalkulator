package com.example.para.kalkulator;

import android.content.Intent;
import android.os.PatternMatcher;
import android.renderscript.Double2;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.regex.Pattern;

public class HistoryActivity extends AppCompatActivity {

    private String history = "";
    private TextView _screen;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        history = getIntent().getStringExtra("history");
        _screen = (TextView) findViewById(R.id.historyContent);
        _screen.setText(history);
    }

    public void clearHistory(View view){
        history = "";
        _screen = (TextView) findViewById(R.id.historyContent);
        _screen.setText(history);
    }
    public void showCalculator(View view){
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("history", history);
        startActivity(intent);
    }
}
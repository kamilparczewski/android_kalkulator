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

public class MainActivity extends AppCompatActivity {

    private TextView _screen;
    private String display = "";
    private String currentOperator = "";
    private String result = "";
    private String history = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        _screen = (TextView) findViewById(R.id.score);
        _screen.setText(display);
        String _history = getIntent().getStringExtra("history");
        if(_history==null){
            history="";
        }
        else {
            history = _history;
        }

    }

    private void updateScreen() {
        _screen.setText(display);
    }

    protected void onClickNumber(View v) {
        if(result != ""){
            clear();
            updateScreen();
        }
        Button b = (Button) v;
        display += b.getText();
        updateScreen();
    }

    private boolean isOperator(char op){
        switch (op){
            case '+': return true;
            case '-': return true;
            case 'x': return true;
            case '/': return true;
            default: return false;
        }
    }

    protected void onClickOperator(View v) {
        if(display=="") return;

        Button b = (Button) v;

        if(result != ""){
            String _display = result;
            clear();
            display = _display;

        }
        if(currentOperator != ""){
            Log.d("Error","" + display.charAt(display.length()-1));
            if(isOperator(display.charAt(display.length()-1))){
                currentOperator = b.getText().toString();
                display = display.replace(display.charAt(display.length()-1), b.getText().charAt(0));
                updateScreen();
                return;
            }else{
                getResult();
                display = result;
                result = "";
            }
        }
        display += b.getText();
        currentOperator = b.getText().toString();
        updateScreen();
    }

    private void clear() {
        display = "";
        currentOperator = "";
        result = "";
    }

    protected void onClickClear(View v) {
        clear();
        updateScreen();
    }

    private double operate(String a, String b, String op){
        switch (op){
            case "+": return Double.valueOf(a) + Double.valueOf(b);
            case "-": return Double.valueOf(a) - Double.valueOf(b);
            case "x": return Double.valueOf(a) * Double.valueOf(b);
            case "/": try{
                return Double.valueOf(a) / Double.valueOf(b);
            }catch(Exception e){
                Log.d("Error",e.getMessage());
            }
            default: return -1;
        }
    }

    private boolean getResult(){
        if(currentOperator=="") return false;
        String[] operation = display.split(Pattern.quote(currentOperator));
        if(operation.length <2) return false;
        result = String.valueOf(operate(operation[0],operation[1],currentOperator));
        return true;
    }

    protected void onClickEqual(View v) {
        if(display == "") return;

        if(!getResult()) return;
        _screen.setText(display + "\n" + String.valueOf(result));
        history += display + "=" + String.valueOf(result) + "\n\n";
    }
    public void showHistory(View view) {
        Intent intent = new Intent(this, HistoryActivity.class);
        intent.putExtra("history", history);
        startActivity(intent);
    }

}

package com.example.opitulab1;

import android.annotation.SuppressLint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private TextView resultTextView;
    private TextView signTextView;
    private TextView enterEditText;
    private String lastSign = "=";
    private Double lastValue = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        resultTextView = findViewById(R.id.resultTextView);
        enterEditText = findViewById(R.id.enterEditText);
        signTextView = findViewById(R.id.signTextView);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putString("OPERATION", lastSign);
        if(lastValue!=null)
            outState.putDouble("lastValue", lastValue);
        super.onSaveInstanceState(outState);
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        lastSign = savedInstanceState.getString("OPERATION");
        lastValue= savedInstanceState.getDouble("lastValue");
        resultTextView.setText(lastValue.toString());
        signTextView.setText(lastSign);
    }

    public void onNumberClick(View view){
        if(lastSign.equals("=") && lastValue != null){
            lastValue = null;
        }
        enterEditText.append(((Button)view).getText());
    }

    public void onDelete(View view){
        if(enterEditText.getText().toString().length() > 0) {
            String number = enterEditText.getText().toString();
            number = number.subSequence(0, number.length() - 1).toString();
            enterEditText.setText(number);
        }
    }

    public void onDeleteAll(View view){
        enterEditText.setText("");
        signTextView.setText("");
        resultTextView.setText("");
        lastSign = "";
        lastValue = null;
    }

    public void onChangeSign(View view){
        if(lastSign.equals("-")) {
            lastSign = "+";
            signTextView.setText(lastSign);
        } else if(lastSign.equals("+")){
            lastSign = "-";
            signTextView.setText(lastSign);
        }
    }
    
    public void onSingClick(View view){
        String sing = ((Button)view).getText().toString();
        String number = enterEditText.getText().toString();
        if(number.length() > 0){
            try{
                execute(Double.valueOf(number.replace(',', '.')), sing);
            }catch (NumberFormatException ex){
                enterEditText.setText("");
            }
        }
        lastSign = sing;
        signTextView.setText(sing);
    }

    @SuppressLint("SetTextI18n")
    private void execute(Double number, String sing){

        if(lastValue == null){
            lastValue = number;
        } else{
            if(lastSign.equals("=")){
                lastSign = sing;
            }
            checkSing(number);
        }
        resultTextView.setText(lastValue.toString().replace('.', ','));
        enterEditText.setText("");
    }

    private void checkSing(double number){
        switch(lastSign){
            case "=":
                lastValue = number;
                break;
            case "/":
                if(number == 0){
                    lastValue = 0.0;
                } else{
                    lastValue /= number;
                }
                break;
            case "*":
                lastValue *= number;
                break;
            case "+":
                lastValue += number;
                break;
            case "-":
                lastValue -= number;
                break;
        }
    }
}

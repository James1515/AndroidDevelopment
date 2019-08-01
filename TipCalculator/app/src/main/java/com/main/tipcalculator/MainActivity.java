package com.main.tipcalculator;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity; //base class
import android.os.Bundle; //for EditText event handling
import android.text.Editable;    // for EditText event Handling
import android.text.TextWatcher; // EditText Listener
import android.widget.EditText; // for bill amount input
import android.widget.SeekBar; // for changing the tip percentage
import android.widget.SeekBar.OnSeekBarChangeListener; //SeekBar Listener
import android.widget.TextView; //for displaying text

import java.text.NumberFormat;  //for currency formatting

public class MainActivity extends Activity
{
    // currency and percent formatter objects
    public static final NumberFormat currencyFormat =
            NumberFormat.getCurrencyInstance();
    public static final NumberFormat percentFormat =
            NumberFormat.getPercentInstance();

    private double billAmount  = 0.0; // bill amount entered by the user
    private double percent = 0.15;    // init tip percentage
    private TextView amountTextView;  // shows formatted bill amount
    private TextView percentTextView; // shows tip percentage
    private TextView tipTextView;     // shows calculated tip amount
    private TextView totalTextView;   // shows calculated total bill amount



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);      // call superclass version
        setContentView(R.layout.activity_main);  //Inflate GUI

        //get references for programmatically manipulated TextViews

        amountTextView = (TextView) findViewById(R.id.amountTextView);
        percentTextView = (TextView) findViewById(R.id.percentTextView);
        tipTextView = (TextView) findViewById(R.id.tipTextView);
        totalTextView = (TextView) findViewById(R.id.totalTextView);

        tipTextView.setText(currencyFormat.format(0));   //set text to 0
        totalTextView.setText(currencyFormat.format(0)); //set text to 0

        // set amountEditText's TextWatcher:
        EditText amountEditText =
                (EditText) findViewById(R.id.amountEditText);
        amountEditText.addTextChangedListener(amountEditTextWatcher);

        SeekBar percentSeekBar =
                (SeekBar) findViewById(R.id.percentSeekBar);
        percentSeekBar.setOnSeekBarChangeListener(seekBarListener);


    }


    // calculate and display the tip and total amounts:
    private void calculate()
    {
        //format percent and display in percentTextView:
        percentTextView.setText(percentFormat.format(percent));

        //calculate the tip & total:
        double tip = billAmount * percent;

        double total = billAmount + tip;

        // display tip & total formatted as currency:

        tipTextView.setText(currencyFormat.format(tip));

        totalTextView.setText(currencyFormat.format(total));


    }

    // listener object for SeekBar's progress changed events:


    private final OnSeekBarChangeListener seekBarListener = new OnSeekBarChangeListener() {
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            percent = progress / 100.0; //set percent based on progress:
            calculate();  // calculate and display tip and total:
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {

        }
    };

    private final TextWatcher amountEditTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {  }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

            //get bill amount and display currency formatted value
            try
            {
                billAmount = Double.parseDouble(s.toString()) / 100.0;
                amountTextView.setText(currencyFormat.format(billAmount));

            }
            catch (NumberFormatException e)
            {
                amountTextView.setText("");
                billAmount = 0.0;

            }

            calculate();

        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

}

package com.allureinfosystems.emi_calculator;

import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class MaturityDateCalculation extends AppCompatActivity {

    public void caculateMaturityDate(boolean status, int tenure, TextView dateOfInvestment,TextView maturityTextView,TextView investDateCalculated) {

        if (status) {
            String investDate = (String) dateOfInvestment.getText();
            Log.d("Current Date  *****", investDate);
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
            Calendar c = Calendar.getInstance();

            try {
                c.setTime(sdf.parse(investDate));

            } catch (ParseException e) {

                e.printStackTrace();
            }
            c.add(Calendar.YEAR, tenure);
            SimpleDateFormat sdf1 = new SimpleDateFormat("dd-MM-yyyy");
            String maturity = sdf1.format(c.getTime());
            Log.d("Maturiy Date ", maturity);

            maturityTextView.setText(String.valueOf(maturity));
            investDateCalculated.setText(String.valueOf(investDate));
        } else {
            String investDate = (String) dateOfInvestment.getText();

            Log.d("Current Date  *****", investDate);
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
            Calendar c = Calendar.getInstance();

            try {
                c.setTime(sdf.parse(investDate));

            } catch (ParseException e) {

                e.printStackTrace();
            }
            c.add(Calendar.MONTH, tenure);
            SimpleDateFormat sdf1 = new SimpleDateFormat("dd-MM-yyyy");
            String maturity = sdf1.format(c.getTime());
            Log.d("Maturiy Date ", maturity);

            maturityTextView.setText(String.valueOf(maturity));
            investDateCalculated.setText(String.valueOf(investDate));

        }
    }



}

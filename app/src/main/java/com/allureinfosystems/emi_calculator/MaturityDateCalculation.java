package com.allureinfosystems.emi_calculator;

import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.Objects;

public class MaturityDateCalculation extends AppCompatActivity {

    public void caculateMaturityDate(boolean status, int tenure, TextView dateOfInvestment,TextView maturityTextView,TextView investDateCalculated) {

        if (status) {
            String investDate = (String) dateOfInvestment.getText();
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
            Calendar c = Calendar.getInstance();

            try {
                c.setTime(Objects.requireNonNull(sdf.parse(investDate)));

            } catch (ParseException e) {

                e.printStackTrace();
            }
            c.add(Calendar.YEAR, tenure);
            SimpleDateFormat sdf1 = new SimpleDateFormat("dd-MM-yyyy", Locale.UK);
            String maturity = sdf1.format(c.getTime());


            maturityTextView.setText(maturity);
            investDateCalculated.setText(investDate);
        } else {
            String investDate = (String) dateOfInvestment.getText();


            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy",Locale.UK);
            Calendar c = Calendar.getInstance();

            try {
                c.setTime(Objects.requireNonNull(sdf.parse(investDate)));

            } catch (ParseException e) {

                e.printStackTrace();
            }
            c.add(Calendar.MONTH, tenure);
            SimpleDateFormat sdf1 = new SimpleDateFormat("dd-MM-yyyy",Locale.UK);
            String maturity = sdf1.format(c.getTime());
            maturityTextView.setText(maturity);
            investDateCalculated.setText(investDate);

        }
    }



}

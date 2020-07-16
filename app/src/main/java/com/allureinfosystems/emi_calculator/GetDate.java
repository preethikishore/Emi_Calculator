package com.allureinfosystems.emi_calculator;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Context;
import android.widget.DatePicker;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;
import java.util.Date;

public class GetDate extends AppCompatActivity {

    String selected_value;

    public void getdate(final TextView dateOfinvestmentText, DatePickerDialog picker, Context context)
    {
        Calendar cldr ;
        int day ;
        int month ;
        int year ;

        cldr = Calendar.getInstance();
        day = cldr.get(Calendar.DAY_OF_MONTH);
        month = cldr.get(Calendar.MONTH);
        year = cldr.get(Calendar.YEAR);
        // date picker dialog

        picker = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                selected_value = dayOfMonth + "-" + (month + 1) + "-" + year;
                dateOfinvestmentText.setText(selected_value);

            }
        } , year, month, day );
        picker.show();


    }






}

package com.allureinfosystems.emi_calculator;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;


import java.util.List;

public class SpinnerData extends AppCompatActivity {


    public void initspinnerfooter(android.widget.Spinner spinner_Deposit_Frequency, Context context, List items) {


        ArrayAdapter<String> adapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, items);
        spinner_Deposit_Frequency.setAdapter(adapter);
        spinner_Deposit_Frequency.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                String selected_frequency = (String) parent.getItemAtPosition(position);
                Log.v("item", (String) parent.getItemAtPosition(position));
               // ((TextView) parent.getChildAt(0)).setTextColor(Color.WHITE);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub
            }
        });
    }


}

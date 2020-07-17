package com.allureinfosystems.emi_calculator;

import android.app.DatePickerDialog;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;


/**
 * A simple {@link Fragment} subclass.
 */
public class compoundInterestFragment extends Fragment {

    private android.widget.Spinner spinner_Deposit_Frequency;
    private DatePickerDialog picker;
    private Button buttonGet;
    private TextView select_Date;
    private TextView principle_text;
    private TextView interest_rate_text;
    private TextView term_text;
    private double principle;
    private double interest;
    private double term;
    private String deposit_interval;
    private String currentDate;
    private String maturityDate;
    private TextView Maturity_Text;
    private TextView investment_value_text;
    private double Amount;
    private TextView investment_date;
    private TextView maturity_date;
    private Button buttonCalculate;

    private double investment_amount_value;
    String[] items = new String[]{
            "Select",
            "Yearly", "Monthly", "Quarterly", "Half Yearly","Bi-Monthly","Thrice-Yearly"
    };

    GetDate getdate = new GetDate();
    SpinnerData spinnerData = new SpinnerData();

    public compoundInterestFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_compound_interest, container, false);

        spinner_Deposit_Frequency = rootView.findViewById(R.id.compound_spinner_deposit_frequency);

        select_Date = rootView.findViewById(R.id.compound_date_of_investment_value);
        buttonGet = rootView.findViewById(R.id.compound_buttongetdate);
        principle_text = rootView.findViewById(R.id.compound_interset_principle_value);
        interest_rate_text = rootView.findViewById(R.id.compound_interset_interesst_rate_value);
        term_text = rootView.findViewById(R.id.compound_saving_period_value);
        investment_value_text = rootView.findViewById(R.id.compound_investment_amount);
        Maturity_Text = rootView.findViewById(R.id.compound_maturity_value);
        investment_date = rootView.findViewById(R.id.compound_investment_date);
        maturity_date = rootView.findViewById(R.id.compound_maturity_date);
        buttonCalculate = rootView.findViewById(R.id.compound_button_Interest_Calc_Calculate);
        String currentDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
        select_Date.setText(currentDate);
        final MessageComment messageComment = new MessageComment();

        buttonGet.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                getdate.getdate(select_Date,picker, getActivity());
            }
        });

        buttonCalculate.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {

                Double  CIinterestValue = ParseDouble(String.valueOf(interest_rate_text.getText()));
                Double   CIterm = ParseDouble(String.valueOf(term_text.getText()));
                if(CIinterestValue <= 50) {
                    if(CIterm <=40) {
                        calculate_simple_interest_Amount();
                    }
                    else
                    {
                        Toast.makeText(getActivity(), messageComment.messageYearComment, Toast.LENGTH_SHORT).show();

                    }
                }
                else
                {
                    Toast.makeText(getActivity(), messageComment.messageInterestRateComment, Toast.LENGTH_SHORT).show();

                }
            }
        });



        spinnerData.initspinnerfooter(spinner_Deposit_Frequency,getActivity(), Arrays.asList(items));

        return rootView;
    }

    public void calculate_simple_interest_Amount()
    {

        principle = ParseDouble(String.valueOf(principle_text.getText()));
        interest = ParseDouble(String.valueOf(interest_rate_text.getText()));
        term =  ParseDouble(String.valueOf(term_text.getText()));
        deposit_interval =  spinner_Deposit_Frequency.getSelectedItem().toString();

        String Current = (String) select_Date.getText();
        Log.d("Current Date  *****",Current);

        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");

        Calendar c = Calendar.getInstance();

        try {
            c.setTime(sdf.parse(Current));

        } catch (ParseException e) {

            e.printStackTrace();
        }
        c.add(Calendar.YEAR, 10);
        SimpleDateFormat sdf1 = new SimpleDateFormat("MM-dd-yyyy");
        String maturity = sdf1.format(c.getTime());

        Log.d("Maturiy Date ", maturity);
        currentDate = (String) select_Date.getText();


        switch (deposit_interval)
        {
            case "Yearly":  Amount = principle * (1 + interest * term / 100);
                investment_amount_value = principle * term;
                break;
            case  "Monthly": Amount = principle * (1 + interest * term / (12 * 100));
                investment_amount_value = principle * 12 * term;
                break;
            case  "Quarterly":  Amount = principle * (1 + interest * term / (4 * 100));
                investment_amount_value = principle * 4 * term;
                break;
            case  "Half Yearly": Amount = principle * (1 + interest * term / (2 * 100));
                investment_amount_value = principle * 2 * term;
                break;
            case  "Bi-Monthly":  Amount = principle * (1 + interest * term / (6 * 100));
                investment_amount_value = principle * 6 * term;
                break;
            case  "Thrice-Yearly": Amount = principle * (1 + interest * term / (3 * 100));
                investment_amount_value = principle * 3 * term;
                break;
            default:System.out.println("nothing");

        }

        investment_value_text.setText(String.valueOf(Amount));
        Maturity_Text.setText(String.valueOf(investment_amount_value));
        investment_date.setText(Current);
        maturity_date.setText(maturity);



    }

    double ParseDouble(String strNumber) {
        if (strNumber != null && strNumber.length() > 0) {
            try {
                return Double.parseDouble(strNumber);
            } catch(Exception e) {
                return -1;
            }
        }
        else return 0;
    }



}

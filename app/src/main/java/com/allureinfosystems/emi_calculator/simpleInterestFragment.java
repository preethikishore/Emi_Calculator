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

import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
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
public class simpleInterestFragment extends Fragment {


    private Spinner spinnerDepositFrequency;
    private RadioGroup siRadioGroup;
    private RadioButton siRadiButton;
    private DatePickerDialog datePickerDialog;
    private Button buttonGet;
    private  Button buttonCalculate;
    private TextView selectDate;
    private EditText siPrincipleAmount;
    private EditText siInterestValue;
    private EditText siSavingTerm;
    private TextView siInvestmentAmount;
    private TextView siMaturityDate;
    private TextView siInvestmentDate;
    private double principleAmount;
    private double interestValue;
    private double term;
    private double Amount;
    private String currentDate;
    String maturityDate;
    private String depositInterval;
    private TextView maturityText;
    private double investmentAmountValue;
    private Button buttonReset;
    Boolean status;
    String selectedTenureMode;
    MessageComment messageComment = new MessageComment();
    ButtonAnimationActivity animationActivity = new ButtonAnimationActivity();

    SimpleDateFormat sdf = new SimpleDateFormat("MM/DD/YYYY");


    GetDate getdate = new GetDate();
    SpinnerData spinnerData = new SpinnerData();
    String[] items = new String[]{
            "Yearly", "Monthly", "Quarterly", "Half Yearly","Bi-Monthly","Thrice-Yearly"
    };


    public simpleInterestFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        final View rootView = inflater.inflate(R.layout.fragment_simple_interest, container, false);
        spinnerDepositFrequency = rootView.findViewById(R.id.simple_spinner_deposit_frequency);
        selectDate = rootView.findViewById(R.id.simple_date_of_investment_value);
        buttonGet = rootView.findViewById(R.id.simple_buttongetdate);
        siPrincipleAmount = rootView.findViewById(R.id.simple_interest_principle_value);
        siInterestValue = rootView.findViewById(R.id.simple_interest_rate_value);
        siSavingTerm = rootView.findViewById(R.id.simple_saving_period_value);
        buttonCalculate = rootView.findViewById(R.id.simple_button_Interest_Calc_Calculate);
        siInvestmentAmount = rootView.findViewById(R.id.simple_interest_calculator_investment_amount);
        maturityText = rootView.findViewById(R.id.simple_interest_calculator_maturity_value);
        siInvestmentDate = rootView.findViewById(R.id.simple_interest_calculator_investment_date);
        siMaturityDate = rootView.findViewById(R.id.simple_interest_calculator_maturity_date);
        String currentDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
        selectDate.setText(currentDate);
        siRadioGroup = rootView.findViewById(R.id.siRadioButtonGroup);

        buttonReset = rootView.findViewById(R.id.buttonSIReset);

        siRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                int radioid = siRadioGroup.getCheckedRadioButtonId();
                siRadiButton = rootView.findViewById(radioid);
                status = false;

                if (siRadiButton.isChecked()) {

                    selectedTenureMode = (String) siRadiButton.getText();
                    System.out.println("Selected tenure  mode :"  +selectedTenureMode);

                    if (selectedTenureMode.equals("Year"))
                    {
                        status = true;

                    }else
                    {
                        status = false;


                    }

                }

            }
        });


        buttonReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                animationActivity.animation(v);
                clear(v);
            }
        });
        buttonCalculate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                animationActivity.animation(v);

              Double  SIinterestValue = ParseDouble(String.valueOf(siInterestValue.getText()));
              Double   SIterm = ParseDouble(String.valueOf(siSavingTerm.getText()));
              principleAmount = ParseDouble(String.valueOf(siPrincipleAmount.getText()));
              if(principleAmount > 0 && SIinterestValue >0 && SIterm>0) {
                  if (SIinterestValue <= 50) {

                      if (SIterm <= 40) {
                          calculate_simple_interest_Amount();
                      } else {
                          Toast.makeText(getActivity(), messageComment.messageYearComment, Toast.LENGTH_SHORT).show();

                      }
                  } else {
                      Toast.makeText(getActivity(), messageComment.messageInterestRateComment, Toast.LENGTH_SHORT).show();

                  }
              }
              else
              {
                  Toast.makeText(getActivity(), messageComment.messageFillFeild, Toast.LENGTH_SHORT).show();

              }

            }
        });


        buttonGet.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {

                getdate.getdate(selectDate, datePickerDialog, getActivity());
            }
        });

        spinnerData.initspinnerfooter(spinnerDepositFrequency,getActivity(), Arrays.asList(items));

        return  rootView;
    }



  public void calculate_simple_interest_Amount()
  {

      siMaturityDate.setVisibility(View.VISIBLE);
      siInvestmentDate.setVisibility(View.VISIBLE);
      principleAmount = ParseDouble(String.valueOf(siPrincipleAmount.getText()));
      interestValue = ParseDouble(String.valueOf(siInterestValue.getText()));
      term = ParseDouble(String.valueOf(siSavingTerm.getText()));
      int termValue = (int)ParseDouble(String.valueOf(siSavingTerm.getText()));
      depositInterval =  spinnerDepositFrequency.getSelectedItem().toString();
      if(status)
      {
          term = term * 12;
      }

      String Current = (String) selectDate.getText();

      SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");

      Calendar c = Calendar.getInstance();

      try {
          c.setTime(sdf.parse(Current));

      } catch (ParseException e) {

          e.printStackTrace();
      }
      if(status) {
          c.add(Calendar.YEAR, termValue);
      }
      else
      {
          c.add(Calendar.MONTH, termValue);
      }
      SimpleDateFormat sdf1 = new SimpleDateFormat("dd-MM-yyyy");
      String maturity = sdf1.format(c.getTime());

      Log.d("Maturiy Date ", maturity);
      currentDate = (String) selectDate.getText();


      switch (depositInterval)
      {
           case "Yearly":  Amount = principleAmount * (1 + interestValue * term / 100);
                          investmentAmountValue = principleAmount * term;
                          break;
          case  "Monthly": Amount = principleAmount * (1 + interestValue * term / (12 * 100));
                           investmentAmountValue = principleAmount * 12 * term;
                           break;
          case  "Quarterly":  Amount = principleAmount * (1 + interestValue * term / (4 * 100));
                              investmentAmountValue = principleAmount * 4 * term;
                              break;
          case  "Half Yearly": Amount = principleAmount * (1 + interestValue * term / (2 * 100));
                               investmentAmountValue = principleAmount * 2 * term;
                               break;
          case  "Bi-Monthly":  Amount = principleAmount * (1 + interestValue * term / (6 * 100));
                               investmentAmountValue = principleAmount * 6 * term;
                               break;
          case  "Thrice-Yearly": Amount = principleAmount * (1 + interestValue * term / (3 * 100));
                                 investmentAmountValue = principleAmount * 3 * term;
                                 break;
          default:System.out.println("nothing");

      }

      siInvestmentAmount.setText(String.valueOf(Amount));
      maturityText.setText(String.valueOf(investmentAmountValue));
      siInvestmentDate.setText(Current);
      siMaturityDate.setText(maturity);


  }
    double ParseDouble(String strNumber) {
        if (strNumber != null && strNumber.length() > 0) {
            try {
                return Double.parseDouble(strNumber);
            } catch(Exception e) {
                return -1;   // or some value to mark this field is wrong. or make a function validates field first ...
            }
        }
        else return 0;
    }
    public void clear(View v) {
        siPrincipleAmount.setText("");
        siInterestValue.setText("");
        siSavingTerm.setText("");
        siInvestmentAmount.setText("0");
        maturityText.setText("0");
        siMaturityDate.setVisibility(View.INVISIBLE);
        siInvestmentDate.setVisibility(View.INVISIBLE);
    }


}

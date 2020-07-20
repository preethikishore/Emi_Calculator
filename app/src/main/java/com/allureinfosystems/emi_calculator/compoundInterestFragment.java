package com.allureinfosystems.emi_calculator;

import android.app.DatePickerDialog;

import android.os.Build;
import android.os.Bundle;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;


/**
 * A simple {@link Fragment} subclass.
 */
 public class compoundInterestFragment extends Fragment {


    private android.widget.Spinner spinnerCompoundingFrequency;
    private RadioGroup ciRadioGroup;
    private RadioButton ciRadiButton;
    private RadioGroup ciDepoistWithdrawRadioGroup;
    private RadioButton ciDepoistWithdrawRadioButton;
    private DatePickerDialog picker;
    private Button buttonGet;
    private TextView selectDate;
    private EditText principleText;
    private EditText interestRateText;
    private EditText termText;
    private double principle;
    private double annualInterest;
    private double term;
    private String depositInterval;
    private String currentDate;
    private TextView maturityText;
    private TextView investmentValueText;
    private TextView investmentDate;
    private TextView maturityDateValue;
    private Button buttonCalculate;
    private Button buttonReset;
    private TextView ciTotalInterest;
    private EditText depositWithdrawAmount;
    private Double monthlyAmount;
    private Boolean status = false;
    private Boolean statusDeposit = false;
    private String selectedTenureMode;
    private String selectedOptionWD;
    private double totalInterest;
    private final DecimalFormat df = new DecimalFormat("####0.0");
    private ButtonAnimationActivity animationActivity = new ButtonAnimationActivity();

    private String[] items = new String[]{
            "Yearly", "Monthly", "Quarterly", "Half Yearly","Bi-Monthly","Thrice-Yearly"
    };

    private GetDate getdate  = new GetDate();
    private SpinnerData spinnerData = new SpinnerData();

    public compoundInterestFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View rootView = inflater.inflate(R.layout.fragment_compound_interest, container, false);
        spinnerCompoundingFrequency =rootView.findViewById(R.id.compound_spinner_compounding_frequency);
        selectDate = rootView.findViewById(R.id.compound_date_of_investment_value);
        buttonGet = rootView.findViewById(R.id.compound_buttongetdate);
        principleText = rootView.findViewById(R.id.compound_interset_principle_value);
        interestRateText = rootView.findViewById(R.id.compound_interset_interesst_rate_value);
        termText = rootView.findViewById(R.id.compound_saving_period_value);
        investmentValueText = rootView.findViewById(R.id.compound_investment_amount);
        maturityText = rootView.findViewById(R.id.compound_maturity_value);
        investmentDate = rootView.findViewById(R.id.compound_investment_date);
        maturityDateValue = rootView.findViewById(R.id.compound_maturity_date);
        buttonCalculate = rootView.findViewById(R.id.compound_button_Interest_Calc_Calculate);
        String currentDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
        selectDate.setText(currentDate);
        ciRadioGroup = rootView.findViewById(R.id.ciRadioButtonGroup);
        ciDepoistWithdrawRadioGroup = rootView.findViewById(R.id.deposit_withdraw_RadioButtonGroup);
        ciTotalInterest =rootView.findViewById(R.id.compound_total_interest_value);
        depositWithdrawAmount= rootView.findViewById(R.id.deposit_withraw_amount_value);
        final MessageComment messageComment = new MessageComment();

        buttonReset = rootView.findViewById(R.id.buttonCIReset);
        ciRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                int radioid = ciRadioGroup.getCheckedRadioButtonId();
                ciRadiButton = rootView.findViewById(radioid);
                //status = false;

                if (ciRadiButton.isChecked()) {

                    selectedTenureMode = (String) ciRadiButton.getText();

                    status = selectedTenureMode.equals("Year");

                }

            }
        });

        ciDepoistWithdrawRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                int radioid = ciDepoistWithdrawRadioGroup.getCheckedRadioButtonId();
                ciDepoistWithdrawRadioButton = rootView.findViewById(radioid);
                statusDeposit = false;

                if (ciDepoistWithdrawRadioButton.isChecked()) {

                    selectedOptionWD = (String) ciDepoistWithdrawRadioButton.getText();
                    System.out.println("Selected Deposit  mode :"  +selectedOptionWD);

                    statusDeposit = selectedOptionWD.equals("Deposit");

                }

            }
        });

        buttonReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                animationActivity.animation(v);
                clear();
            }
        });


        buttonGet.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                getdate.getdate(selectDate,picker, getActivity());
            }
        });

        buttonCalculate.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                animationActivity.animation(v);
                investmentDate.setVisibility(View.VISIBLE);
                maturityDateValue.setVisibility(View.VISIBLE);
                double principleTextValue = ParseDouble(String.valueOf(principleText.getText()));
                double CIinterestValue = ParseDouble(String.valueOf(interestRateText.getText()));
                double CIterm = ParseDouble(String.valueOf(termText.getText()));
                double monthlyAmount = ParseDouble(String.valueOf(depositWithdrawAmount.getText()));
                if (principleTextValue > 0 && CIinterestValue > 0 && CIterm > 0 && monthlyAmount > 0) {

                    if (CIinterestValue <= 50) {

                        if(status) {

                            if (CIterm <= 40) {
                                calculateCompoudInterest();
                            } else {
                                Toast.makeText(getActivity(), messageComment.messageYearComment, Toast.LENGTH_SHORT).show();

                            }

                        }else
                        {
                            if (CIterm <= 480) {
                                calculateCompoudInterest();
                            } else {
                                Toast.makeText(getActivity(), messageComment.messagePeroidMonth, Toast.LENGTH_SHORT).show();

                            }

                        }


                    } else {
                        Toast.makeText(getActivity(), messageComment.messageInterestRateComment, Toast.LENGTH_SHORT).show();

                    }
                }else
                {
                    Toast.makeText(getActivity(), messageComment.messageFillFeild, Toast.LENGTH_SHORT).show();

                }
            }

        });



        spinnerData.initspinnerfooter(spinnerCompoundingFrequency,getActivity(), Arrays.asList(items));

        return rootView;
    }

    private void calculateCompoudInterest()
    {

        principle = ParseDouble(String.valueOf(principleText.getText()));
        annualInterest = ParseDouble(String.valueOf(interestRateText.getText()));
        term =  ParseDouble(String.valueOf(termText.getText()));
        int termValue = (int)ParseDouble(String.valueOf(termText.getText()));
        depositInterval =  spinnerCompoundingFrequency.getSelectedItem().toString();
        monthlyAmount = ParseDouble(String.valueOf(depositWithdrawAmount.getText()));

        String Current = (String) selectDate.getText();
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy",Locale.UK);
        Calendar c = Calendar.getInstance();
        double termInMonths = 0;
        double monthlyDeposit = 0;
        double interestRate = annualInterest/1200;
        double depositAmount = principle;
        double futureValue = principle;
        double interestCalc = 0;
        totalInterest = 0;
        double capitalizedInterest = 0;

        if (statusDeposit)
            monthlyDeposit = monthlyAmount;
        else
            monthlyDeposit = monthlyAmount * -1;


        if(status)
            termInMonths = term * 12 ;
        else
            termInMonths = term;

        try {
            c.setTime(Objects.requireNonNull(sdf.parse(Current)));

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
        SimpleDateFormat sdf1 = new SimpleDateFormat("dd-MM-yyyy",Locale.UK);
        String maturity = sdf1.format(c.getTime());

        currentDate = (String) selectDate.getText();
        for(int i =1; i <= termInMonths; i++)
        {
            futureValue+=monthlyDeposit;
            System.out.println("futureValue: "+futureValue);
            if (futureValue > 0) {
                depositAmount +=monthlyDeposit;
                System.out.println("depositAmount: "+depositAmount);
                interestCalc = futureValue * interestRate;
                System.out.println("interestCalc:"+interestCalc);
                totalInterest += interestCalc;
                System.out.println("totalInterest: "+totalInterest);
                capitalizedInterest += interestCalc;
                System.out.println("capitalizedInterest: "+capitalizedInterest);

                if (depositInterval.equals("Yearly")) {
                    if (i % 12 == 0) {
                        futureValue += capitalizedInterest;
                        capitalizedInterest = 0;
                    }

                } else if (depositInterval.equals("Monthly")) {
                    futureValue += capitalizedInterest;
                    capitalizedInterest = 0;
                } else if (depositInterval.equals("Quarterly")) {
                    if (i % 3 == 0) {
                        futureValue += capitalizedInterest;
                        capitalizedInterest = 0;
                    }

                } else if (depositInterval.equals("Half Yearly")) {
                    if (i % 6 == 0) {
                        futureValue += capitalizedInterest;
                        capitalizedInterest = 0;
                    }
                } else if (depositInterval.equals("Bi-Monthly")) {
                    if (i % 2 == 0) {
                        futureValue += capitalizedInterest;
                        capitalizedInterest = 0;
                    }

                } else if (depositInterval.equals("Thrice-Yearly")) {
                    if (i % 4 == 0) {
                        futureValue += capitalizedInterest;
                        capitalizedInterest = 0;
                    }
                }
            }
            else
            {
                futureValue = 0;
                depositAmount = 0;
            }
        }

        investmentValueText.setText(df.format(depositAmount));
        maturityText.setText(df.format(futureValue));
        ciTotalInterest.setText(df.format(totalInterest));
        investmentDate.setText(Current);
        maturityDateValue.setText(maturity);

    }

    private double ParseDouble(String strNumber) {
        if (strNumber != null && strNumber.length() > 0) {
            try {
                return Double.parseDouble(strNumber);
            } catch(Exception e) {
                return -1;
            }
        }
        else return 0;
    }
    private void clear() {
        principleText.setText("");
        interestRateText.setText("");
        termText.setText("");
        depositWithdrawAmount.setText("");
        investmentValueText.setText("0");
        maturityText.setText("0");
        investmentValueText.setText("0");
        investmentDate.setVisibility(View.INVISIBLE);
        maturityDateValue.setVisibility(View.INVISIBLE);
    }


}

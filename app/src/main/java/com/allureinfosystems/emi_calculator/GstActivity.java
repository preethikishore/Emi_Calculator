package com.allureinfosystems.emi_calculator;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

import java.util.Arrays;

public class GstActivity extends AppCompatActivity {

    RadioGroup radio_group;
    RadioButton radio_button;
    Button Calculate;
    private EditText amount_text;
    private Spinner gst_Spinner_Data;
    private EditText textOthersOption;
    private TextView orginal_cost;
    private TextView net_gst_value;
    private TextView gst_price;
    private double net_gst_calculated_value;
    private double input_amount;
    private double gst_value;
    double selectedvalue =  25.0;
    String selectedOption;
    boolean status = false;
    private DrawerLayout drawer;
    ButtonAnimationActivity animationActivity = new ButtonAnimationActivity();
    MessageComment messageComment = new MessageComment();

    String[] items = new String[]{
            "ADD GST","REMOVE GST"
    };
    SpinnerData spinnerData = new SpinnerData();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gst);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawer = findViewById(R.id.gst_drawer_layout);
        NavigationView navigationView = findViewById(R.id.gst_nav_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch(item.getItemId())
                {
                    case R.id.help:
                        startActivity(new Intent(GstActivity.this, HelpMenu.class));
                        break;

                    case R.id.setting:
                        startActivity(new Intent(GstActivity.this, SettingsMenu.class));
                        break;

                }
                drawer.closeDrawer(GravityCompat.START);
                return true;
            }
        });
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar
                , R.string.navigation_drawer_open, R.string.navigation_drawer_open);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        amount_text = findViewById(R.id.gst_calculation_amount_value);
        orginal_cost = findViewById(R.id.gst_orginal_cost_value);
        net_gst_value = findViewById(R.id.gst_net_price_value);
        gst_price = findViewById(R.id.gst_price_value);
        radio_group = (RadioGroup)findViewById(R.id.gstradioButtonGroup);
        Calculate = findViewById(R.id.gst_button_Interest_Calc_Calculate);
        gst_Spinner_Data = findViewById(R.id.add_gst);
        textOthersOption = findViewById(R.id.gst_radio_Others_text);

        spinnerData.initspinnerfooter(gst_Spinner_Data, GstActivity.this, Arrays.asList(items));

        radio_group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {


            public void onCheckedChanged(RadioGroup group, int checkedId)
            {

                int radioid = radio_group.getCheckedRadioButtonId();
                radio_button = findViewById(radioid);


                if(radio_button.isChecked())
                {
                    selectedOption = (String) radio_button.getText();

                    if(selectedOption.equals("Others"))
                    {
                        textOthersOption.setVisibility(View.VISIBLE);

                    }else

                        {

                        textOthersOption.setVisibility(View.INVISIBLE);

                         }

                    switch(selectedOption)
                    {
                        case "5%": selectedvalue = 5.0;
                            status = false;
                            break;
                        case "10%": selectedvalue = 10.0;
                              status = false;
                             break;
                        case "15%": selectedvalue = 15.0;
                              status = false;
                             break;
                        case "20%": selectedvalue = 20.0;
                             status = false;
                             break;
                        case "25%": selectedvalue = 25.0;
                             status = false;
                             break;
                        case "Others":
                            status = true;
                             break;

                    }

                }

            }
        });

        Calculate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                String spinnerValue = gst_Spinner_Data.getSelectedItem().toString();
                animationActivity.animation(v);
                input_amount = ParseDouble(String.valueOf(amount_text.getText()));

                if (input_amount > 0) {

                    if (spinnerValue.equals("ADD GST")) {


                        if (status == true) {

                            selectedvalue = Double.valueOf((String.valueOf(textOthersOption.getText())));
                            gst_value = input_amount * selectedvalue / 100;
                            net_gst_calculated_value = input_amount + gst_value;


                        } else {
                            gst_value = input_amount * selectedvalue / 100;
                            net_gst_calculated_value = input_amount + gst_value;

                        }
                    } else {

                        if (status == true) {

                            selectedvalue = Double.valueOf((String.valueOf(textOthersOption.getText())));
                            gst_value = input_amount * selectedvalue / 100;
                            net_gst_calculated_value = input_amount - gst_value;


                        } else {
                            gst_value = input_amount * selectedvalue / 100;
                            net_gst_calculated_value = input_amount - gst_value;

                        }


                    }
                    orginal_cost.setText(String.valueOf(input_amount));
                    net_gst_value.setText(String.valueOf(net_gst_calculated_value));
                    gst_price.setText(String.valueOf(gst_value));


                }
                else
                {

                    Toast.makeText(GstActivity.this, messageComment.messageFillFeild, Toast.LENGTH_SHORT).show();

                }
            }

        });



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

}





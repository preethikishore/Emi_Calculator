package com.allureinfosystems.emi_calculator;

import android.view.View;

public class ButtonAnimationActivity {

    public void animation(final View v)
    {

        v.animate().scaleX(1.1f).scaleY(1.1f).setDuration(200).withEndAction(new Runnable() {
            @Override
            public void run() {
                v.animate().scaleX(1.0f).scaleY(1.0f).setDuration(200);
            }
        });

    }


}

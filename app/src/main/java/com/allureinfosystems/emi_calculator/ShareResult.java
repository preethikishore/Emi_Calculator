package com.allureinfosystems.emi_calculator;

import android.content.Context;
import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;

import static androidx.core.content.ContextCompat.startActivity;

public class ShareResult extends AppCompatActivity {
    public void shareResult(String shareData,String subjectTitle)
    {
        String contentShare = new String(shareData);
        Intent share = new Intent(android.content.Intent.ACTION_SEND);
        share.setType("text/plain");
        share.putExtra(Intent.EXTRA_SUBJECT, subjectTitle);
        share.putExtra(Intent.EXTRA_TEXT, contentShare);
        startActivity(Intent.createChooser(share, "Share via"));
    }
}

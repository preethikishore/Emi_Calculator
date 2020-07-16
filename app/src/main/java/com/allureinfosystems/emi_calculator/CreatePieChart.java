package com.allureinfosystems.emi_calculator;

import android.graphics.Color;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

public class CreatePieChart  {

    public void  CreatePieChartOne(PieChart pieChartOne)
    {

        pieChartOne.setUsePercentValues(true);
        pieChartOne.getDescription().setEnabled(false);
        pieChartOne.setExtraOffsets(2,5,2,2);
        pieChartOne.setDragDecelerationFrictionCoef(0.95f);
        pieChartOne.setDrawHoleEnabled(true);
        pieChartOne.setHoleColor(Color.WHITE);
        pieChartOne.setTransparentCircleRadius(20f);

        ArrayList<PieEntry> values = new ArrayList<>();
        values.add(new PieEntry(30f,"Party A"));
        values.add(new PieEntry(30f,"Party B"));
        values.add(new PieEntry(40f,"Party c"));
        PieDataSet dataset = new PieDataSet(values,"Countries");
        dataset.setColors(ColorTemplate.JOYFUL_COLORS);
        PieData data = new PieData(dataset);
        data.setValueTextSize(10f);
        data.setValueTextColor(Color.YELLOW);
        pieChartOne.setData(data);




    }


}

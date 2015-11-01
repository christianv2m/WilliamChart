package com.db.williamchartdemo.barchart;

import android.animation.PropertyValuesHolder;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.os.Build;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.TextView;

import com.db.chart.Tools;
import com.db.chart.listener.OnEntryClickListener;
import com.db.chart.model.Bar;
import com.db.chart.model.BarSet;
import com.db.chart.view.HorizontalBarChartView;
import com.db.chart.view.Tooltip;
import com.db.chart.view.XController;
import com.db.chart.view.animation.Animation;
import com.db.williamchartdemo.CardController;
import com.db.williamchartdemo.R;


public class BarCardTwo extends CardController {


    private final Context mContext;


    private TextView mTextViewValue;
    private TextView mTextViewMetric;


    private final HorizontalBarChartView mChart;

    private final String[] mLabels= {"Mon", "Tue", "Wed", "Thu", "Fri"};
    private final float [][] mValues = {{23f, 34f, 55f, 71f, 98f}, {17f, 26f, 48f, 63f, 94f}};


    public BarCardTwo(CardView card, Context context){
        super(card);

        mContext = context;
        mChart = (HorizontalBarChartView) card.findViewById(R.id.chart2);
        mTextViewValue = (TextView) card.findViewById(R.id.value);
        mTextViewMetric = (TextView) card.findViewById(R.id.metric);

        Typeface typeface = Typeface.createFromAsset(context.getAssets(),"Ponsi-Regular.otf");
        mTextViewValue.setTypeface(typeface);
        mTextViewMetric.setTypeface(typeface);

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR1) {
            mTextViewValue.animate().alpha(0).setDuration(100);
            mTextViewMetric.animate().alpha(0).setDuration(100);
        }else{
            mTextViewValue.setVisibility(View.INVISIBLE);
            mTextViewMetric.setVisibility(View.INVISIBLE);
        }
    }


    @Override
    public void show(Runnable action) {
        super.show(action);

        Tooltip tip = new Tooltip(mContext, R.layout.barchart_two_tooltip);

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
            tip.setEnterAnimation(PropertyValuesHolder.ofFloat(View.ALPHA, 1)).setDuration(150);
            tip.setExitAnimation(PropertyValuesHolder.ofFloat(View.ALPHA,0)).setDuration(150);
        }

        mChart.setTooltips(tip);

        mChart.setOnEntryClickListener(new OnEntryClickListener() {
            @Override
            public void onClick(int setIndex, int entryIndex, Rect rect) {
                mTextViewValue.setText(Integer.toString((int) mValues[0][entryIndex]));
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR1) {
                    mTextViewValue.animate().alpha(1).setDuration(200);
                    mTextViewMetric.animate().alpha(1).setDuration(200);
                } else {
                    mTextViewValue.setVisibility(View.VISIBLE);
                    mTextViewMetric.setVisibility(View.VISIBLE);
                }
            }
        });

        mChart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR1) {
                    mTextViewValue.animate().alpha(0).setDuration(100);
                    mTextViewMetric.animate().alpha(0).setDuration(100);
                } else {
                    mTextViewValue.setVisibility(View.INVISIBLE);
                    mTextViewMetric.setVisibility(View.INVISIBLE);
                }
            }
        });


        BarSet barSet = new BarSet();
        Bar bar;
        for(int i = 0; i < mLabels.length; i++){
            bar = new Bar(mLabels[i], mValues[0][i]);
            if(i == mLabels.length - 1 )
                bar.setColor(Color.parseColor("#b26657"));
            else if (i == 0)
                bar.setColor(Color.parseColor("#998d6e"));
            else
                bar.setColor(Color.parseColor("#506a6e"));
            barSet.addBar(bar);
        }
        mChart.addData(barSet);
        mChart.setBarSpacing(Tools.fromDpToPx(5));

        mChart.setBorderSpacing(0)
                .setAxisBorderValues(0, 100, 5)
                .setXAxis(false)
                .setYAxis(false)
                .setLabelsColor(Color.parseColor("#FF8E8A84"))
                .setXLabels(XController.LabelPosition.NONE);

        int[] order = {4, 3, 2, 1, 0};
        mChart.show(new Animation()
                .setOverlap(.5f, order)
                .setEndAction(action))
        ;
    }


    @Override
    public void update() {
        super.update();

        mChart.dismissAllTooltips();
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR1) {
            mTextViewValue.animate().alpha(0).setDuration(100);
            mTextViewMetric.animate().alpha(0).setDuration(100);
        }else{
            mTextViewValue.setVisibility(View.INVISIBLE);
            mTextViewMetric.setVisibility(View.INVISIBLE);
        }

        if(firstStage)
            mChart.updateValues(0, mValues[1]);
        else
            mChart.updateValues(0, mValues[0]);
        mChart.notifyDataUpdate();
    }


    @Override
    public void dismiss(Runnable action) {
        super.dismiss(action);

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR1) {
            mTextViewValue.animate().alpha(0).setDuration(100);
            mTextViewMetric.animate().alpha(0).setDuration(100);
        }else{
            mTextViewValue.setVisibility(View.INVISIBLE);
            mTextViewMetric.setVisibility(View.INVISIBLE);
        }

        mChart.dismissAllTooltips();

        int[] order = {0, 1, 2, 3, 4};
        mChart.dismiss(new Animation()
                .setOverlap(.5f, order)
                .setEndAction(action));
    }

}
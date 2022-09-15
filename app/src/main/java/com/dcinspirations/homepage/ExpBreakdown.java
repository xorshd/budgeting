package com.dcinspirations.homepage;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class ExpBreakdown extends AppCompatActivity {
TextView month;
Toolbar tb;
RelativeLayout f2,t2,s2,sh2,m2,h2;
RelativeLayout rl;
View v;
ArrayList<RelativeLayout> views;
double[] percents = {20,30,50,70,15,39};
    String[] name = {"Food","Transport","Shopping","Health","School","Miscellaneous"};
    String[] amount = {"1300","1500","20000","5400","4500","3200"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exp_breakdown);
        views = new ArrayList<>();

        month = findViewById(R.id.month);
        tb = findViewById(R.id.toolbar);
        tb.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        String m = getIntent().getExtras().getString("month");
        String d = getIntent().getExtras().getString("date");

        month.setText(m);


        v = findViewById(R.id.t1);

        f2 = findViewById(R.id.f);
        t2 = findViewById(R.id.t);
        sh2 = findViewById(R.id.sh);
        h2 = findViewById(R.id.h);
        s2 = findViewById(R.id.s);
        m2 = findViewById(R.id.m);
        views.add(f2);
        views.add(t2);
        views.add(sh2);
        views.add(h2);
        views.add(s2);
        views.add(m2);
        getData(d);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        getthem();
    }

    private void getthem(){
        for(int i=0;i<percents.length;i++){
            TextView t= (TextView)views.get(i).getChildAt(1);
            TextView t1= (TextView)views.get(i).getChildAt(2);
            DecimalFormat df = new DecimalFormat("####0.00");

            View view = ((RelativeLayout)views.get(i).getChildAt(3)).getChildAt(1);
            double d1 = v.getWidth();
            double d = (percents[i]/100)*d1;
            int d2 = (int)d;
            t.setText(name[i]+" "+"₦"+amount[i]);
            t1.setText(String.valueOf(df.format(percents[i]))+"%");
            RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(d2,v.getHeight());
            view.setLayoutParams(lp);
        }
    }

    private void getData(String date){
        double result[] = new DatabaseClass(getApplicationContext()).resolveParticularAmount(date);
        for(int i=1;i<result.length;i++){
            double d1 = result[i]/result[0];
            double d = d1*100;
            percents[i-1]=d;
            amount[i-1]=String.valueOf(result[i]);
        }

        getthem();
    }
}

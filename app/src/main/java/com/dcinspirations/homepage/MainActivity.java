package com.dcinspirations.homepage;

import android.app.Dialog;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.dcinspirations.homepage.ui.main.SectionsPagerAdapter;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
    Toolbar toolbar;
    TextView expenses,income;
    Dialog dialog;
    EditText amount;
    Button save;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar =  findViewById(R.id.toolbar);
        toolbar.setOverflowIcon(getDrawable(R.drawable.more));
        setSupportActionBar(toolbar);
        dialog = new Dialog(this);

//        new DatabaseClass(this).insertIntoIncome("08/2019","50000");
        String m = new Sp(this).getMonth();
        if(!m.equalsIgnoreCase(getDate())){
            new Sp(this).setMonth(getDate());
        }

        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setOffscreenPageLimit(0);
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);
        expenses = findViewById(R.id.expenses);
        income = findViewById(R.id.income);
//        FloatingActionButton fab = findViewById(R.id.fab);
//
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
        populateBalance();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.dash, menu);
        menu.getItem(0).setIcon(R.drawable.edit);
        menu.getItem(1).setIcon(R.drawable.ref);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.ei) {
            show();
            return true;
        }
        if (id == R.id.rf) {
            populateBalance();
            return true;
        }
        if (id == R.id.log) {
            new Sp(getApplicationContext()).setLoggedIn(false);
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void show() {
        dialog.setContentView(R.layout.addincome);
        amount = dialog.findViewById(R.id.amount);
        dialog.show();
        save = dialog.findViewById(R.id.save);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String at = amount.getText().toString();
                if(!at.isEmpty()){

                    new DatabaseClass(getApplicationContext()).deleteIncomeData(getDate());
                        Boolean b =new DatabaseClass(getApplicationContext()).insertIntoIncome(getDate(),at);
                        if(!b){
                            dialog.cancel();
                            populateBalance();
                        }

                }

            }
        });
    }
    public void populateBalance(){
        int count = new DatabaseClass(this).resolveAmount(new Sp(this).getMonth());
        int count1 = new DatabaseClass(this).resolveIncomeAmount(new Sp(this).getMonth());
        expenses.setText("₦"+General.editAmount(String.valueOf(count)));
        income.setText("₦"+General.editAmount(String.valueOf(count1)));
        expenses.setTextColor(count>count1?getResources().getColor(R.color.aux15):getResources().getColor(R.color.aux14));
        double a = count;
        double b = count1;
        double c = ((double)50/(double)100)*b;
        double d = ((double)75/(double)100)*b;
//        Toast.makeText(this, Double.toString(d), Toast.LENGTH_LONG).show();
        if(a>b) {
            showOverSpend("Hey buddy, you have exceeded your budget.");
        }else if(a>d){
            showOverSpend("You passed 75% of your budget.");
        }else if(a>c){
            showOverSpend("You passed 50% of your budget.");
        }
    }
    public String getDate(){
        SimpleDateFormat tf = new SimpleDateFormat("MM/yyyy");
        String date = tf.format( Calendar.getInstance().getTime());
        return date;
    }
    public void showOverSpend(String message){
        final Dialog osdialog = new Dialog(this);
        osdialog.setContentView(R.layout.overspend);
        TextView t = osdialog.findViewById(R.id.okay);
        t.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                osdialog.cancel();
            }
        });
        TextView t2 = osdialog.findViewById(R.id.title);
        t2.setText(message);
        Window window = osdialog.getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();
        wlp.gravity = Gravity.BOTTOM;
        wlp.width = WindowManager.LayoutParams.MATCH_PARENT;
        wlp.flags &= WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        window.setAttributes(wlp);
        osdialog.show();
    }
}
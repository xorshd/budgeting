package com.dcinspirations.homepage;

import android.app.Dialog;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.dcinspirations.homepage.models.dailyexp;
import com.dcinspirations.homepage.models.monthexp;
import com.dcinspirations.homepage.ui.main.daily_adapter;
import com.dcinspirations.homepage.ui.main.monthly_adapter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;


public class Monthly extends Fragment {

    private OnFragmentInteractionListener mListener;

    public Monthly() {
        // Required empty public constructor
    }


    DatabaseClass db;
    RecyclerView rv;
    ArrayList<monthexp> datalist;
    monthly_adapter ma;
    LinearLayout ll;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_daily, container, false);
        db = new DatabaseClass(v.getContext());
        ll = v.findViewById(R.id.empty);

        datalist = new ArrayList<>();
        ma = new monthly_adapter(v.getContext(),datalist);
        rv =  v.findViewById(R.id.rv);
        LinearLayoutManager llm = new LinearLayoutManager(v.getContext());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        rv.setLayoutManager(llm);
        rv.setItemAnimator(new DefaultItemAnimator());
        rv.setAdapter(ma);

        return v;
    }


    public String getDate(){
        SimpleDateFormat tf = new SimpleDateFormat("MM/yyyy");
        String date = tf.format( Calendar.getInstance().getTime());
        return String.valueOf(Calendar.getInstance().get(Calendar.DAY_OF_MONTH))+"/"+date;
    }
    public void getExpenses(){
        datalist.clear();
        Cursor cs = new DatabaseClass(getView().getContext()).getIncomeData();
        while (cs.moveToNext()){
            String date = cs.getString(cs.getColumnIndex("date"));
            String amount = cs.getString(cs.getColumnIndex("amount"));
            monthexp me = new monthexp(date,amount);
            datalist.add(0,me);
            ma.notifyDataSetChanged();
        }
        if(datalist.isEmpty()){
            ll.setVisibility(View.VISIBLE);
        }else{
            ll.setVisibility(View.GONE);
        }

    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }

    @Override
    public void onResume() {
        super.onResume();
        getExpenses();
    }
}

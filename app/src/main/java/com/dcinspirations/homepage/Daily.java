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
import android.widget.Toast;

import com.dcinspirations.homepage.models.dailyexp;
import com.dcinspirations.homepage.models.todayexp;
import com.dcinspirations.homepage.ui.main.daily_adapter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;


public class Daily extends Fragment {

    private OnFragmentInteractionListener mListener;

    public Daily() {
        // Required empty public constructor
    }

    FloatingActionButton fab;
    EditText name,amount;
    Spinner cat;
    String[] Datalist = {"Category","Food","Transport","Shopping","Health","School","Miscellaneous"};
    ArrayAdapter<String> adapter;
    Button save;
    Dialog dialog;
    DatabaseClass db;
    RecyclerView rv;
    ArrayList<dailyexp> datalist;
    ArrayList<String> datelist;
    daily_adapter ma;
    LinearLayout ll;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_daily, container, false);
        db = new DatabaseClass(v.getContext());
        ll = v.findViewById(R.id.empty);

        datelist = new ArrayList<>();
        datalist = new ArrayList<>();
        ma = new daily_adapter(v.getContext(),datalist);
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
    public String getTime(){
        SimpleDateFormat tf = new SimpleDateFormat("HH:mma");
        String date = tf.format( Calendar.getInstance().getTime());
        return date;
    }

    public void getExpenses(){
        datalist.clear();
        Cursor cs = new DatabaseClass(getView().getContext()).getData();
        while (cs.moveToNext()){
            String date = cs.getString(cs.getColumnIndex("date"));
            checkDate(date);
        }

        for(String r: datelist){
            Cursor cs1 = new DatabaseClass(getView().getContext()).getAmount();
            int count = 0;
            while(cs1.moveToNext()) {
                String date = cs1.getString(cs1.getColumnIndex("date"));
                if (date.equalsIgnoreCase(r)) {
                    int am = cs1.getInt(cs1.getColumnIndex("amount"));
                    count = count + am;
                }
            }
           dailyexp dexp = new dailyexp(String.valueOf(count),r);
            datalist.add(0,dexp);
            ma.notifyDataSetChanged();

        }
        if(datalist.isEmpty()){
            ll.setVisibility(View.VISIBLE);
        }else{
            ll.setVisibility(View.GONE);
        }
//        ((MainActivity) getActivity()).populateBalance();
    }

    public void checkDate(String date){
        for(String r: datelist){
            if(r.equalsIgnoreCase(date)){
                return;
            }
        }
        datelist.add(date);
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

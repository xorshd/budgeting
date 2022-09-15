package com.dcinspirations.homepage;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.res.ColorStateList;
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

import com.dcinspirations.homepage.models.todayexp;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;


public class Today extends Fragment {

    private OnFragmentInteractionListener mListener;

    public Today() {
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
    ArrayList<todayexp> datalist;
    disp_adapter ma;
    LinearLayout ll;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_today, container, false);
        dialog = new Dialog(v.getContext());
        db = new DatabaseClass(v.getContext());
        ll = v.findViewById(R.id.empty);
        fab = v.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                show();
            }
        });
        fab.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.aux7)));
        fab.setRippleColor(getResources().getColor(R.color.colorAccent));
        datalist = new ArrayList<>();
        ma = new disp_adapter(v.getContext(),datalist);
        rv =  v.findViewById(R.id.rv);
        LinearLayoutManager llm = new LinearLayoutManager(v.getContext());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        rv.setLayoutManager(llm);
        rv.setItemAnimator(new DefaultItemAnimator());
        rv.setAdapter(ma);

        getExpenses(v.getContext());
        return v;
    }

    private void show() {
        dialog.setContentView(R.layout.addexpense);
        name = dialog.findViewById(R.id.name);
        amount = dialog.findViewById(R.id.amount);
        cat = dialog.findViewById(R.id.cat);
        adapter = new ArrayAdapter<>(dialog.getContext(),R.layout.spinner_layout,Datalist);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        cat.setAdapter(adapter);
        dialog.show();
        save = dialog.findViewById(R.id.save);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                add();
            }
        });
    }

    private void add(){
        String nt = name.getText().toString().trim();
        String ct = cat.getSelectedItem().toString().trim();
        String at = amount.getText().toString().trim();
        if(!(nt.isEmpty()||ct.equalsIgnoreCase("Category")||at.isEmpty())){

            at = at.replaceAll("/.","").replaceAll(",","");
            Boolean insert = db.insertIntoMeds(nt,ct,Integer.parseInt(at),getTime(),getDate());
            if(!insert){
                dialog.cancel();
                getExpenses(getView().getContext());
                ((MainActivity) getActivity()).populateBalance();
            }
        }else{
            Toast.makeText(getView().getContext(), "Fill all fields", Toast.LENGTH_LONG).show();
        }
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

    public void getExpenses(Context context){
        datalist.clear();
        Cursor cs = db.getToday(getDate());

        while(cs.moveToNext()){
            todayexp tde = new todayexp(cs.getInt(0),cs.getString(1),cs.getString(2),cs.getString(3),cs.getString(4),cs.getString(5));
            datalist.add(0,tde);
            ma.notifyDataSetChanged();
        }
        if(datalist.isEmpty()){
            ll.setVisibility(View.VISIBLE);
        }else{
//            Toast.makeText(context, datalist.get(0).name, Toast.LENGTH_LONG).show();
            ll.setVisibility(View.GONE);
        }

    }
    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }
}

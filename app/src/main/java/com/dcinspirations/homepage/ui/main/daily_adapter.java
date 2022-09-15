package com.dcinspirations.homepage.ui.main;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dcinspirations.homepage.General;
import com.dcinspirations.homepage.R;
import com.dcinspirations.homepage.models.dailyexp;

import java.text.DateFormat;
import java.text.DateFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by pc on 2/18/2018.
 */

public class daily_adapter extends RecyclerView.Adapter<daily_adapter.viewHolder>{

    private List<dailyexp> objectlist;
    private LayoutInflater inflater;
    private Context context;

    public daily_adapter(Context context, List<dailyexp> objectlist) {
        inflater = LayoutInflater.from(context);
        this.objectlist = objectlist;
        this.context=context;
    }


    @Override
    public viewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.spendingdaily,parent,false);
        viewHolder vholder = new viewHolder(view);
        return vholder;
    }

    @Override
    public void onBindViewHolder(viewHolder holder, int position) {
        dailyexp current = objectlist.get(position);
        holder.setData(current,position);

    }

    @Override
    public int getItemCount() {
        return objectlist.size();
    }

    public void refreshEvents() {
        notifyDataSetChanged();
    }


    public class viewHolder extends RecyclerView.ViewHolder{
        private TextView amount,date,date2,day;
        private int position;
        private dailyexp currentObject;

        public void setPosition(int position) {
            this.position = position;
        }

        public viewHolder(final View itemView){
            super(itemView);
//            itemView.setOnLongClickListener(new View.OnLongClickListener() {
//                @Override
//                public boolean onLongClick(View v) {
//                    PopupMenu pm = new PopupMenu(v.getContext(),itemView, Gravity.END);
//                    pm.getMenuInflater().inflate(R.menu.dash2,pm.getMenu());
//                    pm.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
//                        @Override
//                        public boolean onMenuItemClick(MenuItem item) {
//                            switch (item.getItemId()){
//                                case R.id.del:
//                                    int remove = new DatabaseClass(context).deleteData(objectlist.get(position).id);
//                                    objectlist.remove(position);
//                                    refreshEvents();
//                                    return true;
//                            }
//                            return false;
//                        }
//                    });
//                    pm.show();
//                    return false;
//                }
//            });
            amount = itemView.findViewById(R.id.amount);
            date = itemView.findViewById(R.id.date);
            date2 = itemView.findViewById(R.id.date2);
            day = itemView.findViewById(R.id.day);
        }



        public void setData(dailyexp current, int position) {
            String d[]= current.date.split("/");
            if(d[0].length()==1){
                d[0]="0"+d[0];
            }
            this.date.setText(d[0]);
            this.amount.setText("₦"+ General.editAmount(current.amount));
            this.date2.setText(d[2]+"-"+d[1]);
            this.day.setText(getDay(current.date));
            this.position = position;
            this.currentObject=current;
        }
        String gm(int m) {
            m = m-1;
            String month = "invalid";
            DateFormatSymbols dfs = new DateFormatSymbols();
            String[] months = dfs.getMonths();
            if (m >= 0 && m <= 11 ) {
                month = months[m];
            }
            return month;
        }
        String getDay(String date){
            SimpleDateFormat format1=new SimpleDateFormat("dd/MM/yyyy");
            Date dt1= null;
            try {
                dt1 = format1.parse(date);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            DateFormat format2=new SimpleDateFormat("EEEE");
            String finalDay=format2.format(dt1);
            return finalDay;
        }


    }
}

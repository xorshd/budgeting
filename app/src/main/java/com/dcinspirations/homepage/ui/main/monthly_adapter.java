package com.dcinspirations.homepage.ui.main;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dcinspirations.homepage.DatabaseClass;
import com.dcinspirations.homepage.ExpBreakdown;
import com.dcinspirations.homepage.General;
import com.dcinspirations.homepage.R;
import com.dcinspirations.homepage.models.monthexp;

import java.text.DateFormatSymbols;
import java.util.List;

/**
 * Created by pc on 2/18/2018.
 */

public class monthly_adapter extends RecyclerView.Adapter<monthly_adapter.viewHolder> {

    private List<monthexp> objectlist;
    private LayoutInflater inflater;
    private Context context;

    public monthly_adapter(Context context, List<monthexp> objectlist) {
        inflater = LayoutInflater.from(context);
        this.objectlist = objectlist;
        this.context = context;
    }


    @Override
    public viewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.spendingmonthly, parent, false);
        viewHolder vholder = new viewHolder(view);
        return vholder;
    }

    @Override
    public void onBindViewHolder(viewHolder holder, int position) {
        monthexp current = objectlist.get(position);
        holder.setData(current, position);

    }

    @Override
    public int getItemCount() {
        return objectlist.size();
    }

    public void refreshEvents() {
        notifyDataSetChanged();
    }


    public class viewHolder extends RecyclerView.ViewHolder {
        private TextView month, income, spend;
        private int position;
        private monthexp currentObject;

        public void setPosition(int position) {
            this.position = position;
        }

        public viewHolder(final View itemView) {
            super(itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String d[] = currentObject.date.split("/");
                    context.startActivity(
                            new Intent(context,
                                    ExpBreakdown.class)
                                    .putExtra("month", gm(Integer.parseInt(d[0])) + " " + d[1])
                                    .putExtra("date",currentObject.date));
                }
            });
            month = itemView.findViewById(R.id.month);
            income = itemView.findViewById(R.id.income);
            spend = itemView.findViewById(R.id.spend);
        }


        public void setData(monthexp current, int position) {
            String d[] = current.date.split("/");
            this.month.setText(gm(Integer.parseInt(d[0])) + " " + d[1]);
            this.income.setText("₦" + General.editAmount(current.amount));
            this.spend.setText("₦" + General.editAmount(String.valueOf(new DatabaseClass(context).resolveAmount(current.date))));
            this.position = position;
            this.currentObject = current;
        }

        String gm(int m) {
            m = m - 1;
            String month = "invalid";
            DateFormatSymbols dfs = new DateFormatSymbols();
            String[] months = dfs.getMonths();
            if (m >= 0 && m <= 11) {
                month = months[m];
            }
            return month;
        }


    }
}

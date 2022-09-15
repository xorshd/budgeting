package com.dcinspirations.homepage;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.dcinspirations.homepage.models.todayexp;

import java.util.Calendar;
import java.util.List;

/**
 * Created by pc on 2/18/2018.
 */

public class disp_adapter extends RecyclerView.Adapter<disp_adapter.viewHolder>{

    private List<todayexp> objectlist;
    private LayoutInflater inflater;
    private Context context;

    public disp_adapter(Context context, List<todayexp> objectlist) {
        inflater = LayoutInflater.from(context);
        this.objectlist = objectlist;
        this.context=context;
    }


    @Override
    public viewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.spending,parent,false);
        viewHolder vholder = new viewHolder(view);
        return vholder;
    }

    @Override
    public void onBindViewHolder(viewHolder holder, int position) {
        todayexp current = objectlist.get(position);
        holder.setData(current,position);

    }

    @Override
    public int getItemCount() {
        return objectlist.size();
    }

    public void refreshEvents() {
        notifyDataSetChanged();
    }


    public class viewHolder extends RecyclerView.ViewHolder {
        private TextView name, cat, amount, time;
        private ImageView aimg;
        private int position;
        private todayexp currentObject;

        public void setPosition(int position) {
            this.position = position;
        }

        public viewHolder(final View itemView) {
            super(itemView);
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    PopupMenu pm = new PopupMenu(v.getContext(), itemView, Gravity.END);
                    pm.getMenuInflater().inflate(R.menu.dash2, pm.getMenu());
                    pm.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem item) {
                            switch (item.getItemId()) {
                                case R.id.del:
                                    int remove = new DatabaseClass(context).deleteData(objectlist.get(position).id);
                                    objectlist.remove(position);
                                    refreshEvents();
                                    return true;
                            }
                            return false;
                        }
                    });
                    pm.show();
                    return false;
                }
            });
            name = itemView.findViewById(R.id.name);
            cat = itemView.findViewById(R.id.category);
            amount = itemView.findViewById(R.id.amount);
            aimg = itemView.findViewById(R.id.img);
            time = itemView.findViewById(R.id.time);
        }


        public void setData(todayexp current, int position) {
            if (current.category.equalsIgnoreCase("health")) {
                this.aimg.setImageDrawable(context.getDrawable(R.drawable.hea));
            } else if (current.category.equalsIgnoreCase("school")) {
                this.aimg.setImageDrawable(context.getDrawable(R.drawable.sch));
            } else if (current.category.equalsIgnoreCase("food")) {
                this.aimg.setImageDrawable(context.getDrawable(R.drawable.food));
            } else if (current.category.equalsIgnoreCase("miscellaneous")) {
                this.aimg.setImageDrawable(context.getDrawable(R.drawable.misc));
            } else if (current.category.equalsIgnoreCase("shopping")) {
                this.aimg.setImageDrawable(context.getDrawable(R.drawable.shop));
            } else if (current.category.equalsIgnoreCase("transport")) {
                this.aimg.setImageDrawable(context.getDrawable(R.drawable.car));

            }
                this.name.setText(current.name);
                this.cat.setText(current.category);
                this.time.setText(current.time);
                this.amount.setText("₦" + General.editAmount(current.amount.trim()));


                this.position = position;
                this.currentObject = current;
            }



    }
}

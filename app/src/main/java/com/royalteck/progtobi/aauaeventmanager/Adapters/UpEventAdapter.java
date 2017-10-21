package com.royalteck.progtobi.aauaeventmanager.Adapters;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;

import com.royalteck.progtobi.aauaeventmanager.Model.EventModel;
import com.royalteck.progtobi.aauaeventmanager.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class UpEventAdapter extends RecyclerView.Adapter<UpEventAdapter.MyViewHolder> {

    private List<EventModel> eventslist;
    //private List<EventModel> mFilteredDeveloperList;
    Activity context;
    Filter filter;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView eventtitle, eventday, eventmonth, eventloca;
        ImageView eventimg;

        public MyViewHolder(View view) {
            super(view);
            eventtitle = (TextView) view.findViewById(R.id.eventtitletxtview);
            eventday = (TextView) view.findViewById(R.id.datetxtview);
            eventmonth = (TextView) view.findViewById(R.id.monthtxtview);
            eventloca = (TextView) view.findViewById(R.id.eventlocationtxtview);
            eventimg = (ImageView) view.findViewById(R.id.eventimg);
        }
    }


    public UpEventAdapter(List<EventModel> developerList, Activity context) {
        this.eventslist = developerList;
        //this.mFilteredDeveloperList = developerList;
        this.context = context;
        //setFilter();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.event, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        EventModel event = eventslist.get(position);
        holder.eventtitle.setText(event.getTitle());
        holder.eventday.setText(event.getDaynum());
        holder.eventmonth.setText(event.getMonth());
        holder.eventloca.setText(event.getLocation());
        //handle image loading using picasso
        Picasso.with(context).load(event.getImadeUrl()).placeholder(R.drawable.eventimg).into(holder.eventimg);
    }

    @Override
    public int getItemCount() {
        try {
            return eventslist.size();
        } catch (Exception e) {
            return 0;
        }

    }


    public void filterList(String text) {
        filter.filter(text);
    }

    /*private void setFilter() {
        filter = new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                ArrayList<Developer> newFilters = new ArrayList<>();
                FilterResults results = new FilterResults();
                for (Developer developer : mDeveloperList) {
                    if (developer.getUserName().toLowerCase().trim().contains(constraint)) {
                        newFilters.add(developer);
                    }
                }
                results.values = newFilters;
                results.count = newFilters.size();
                return results;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                mFilteredDeveloperList = (ArrayList<Developer>) results.values;
                DevelopersAdapter.this.notifyDataSetChanged();
            }
        };
    }*/

}

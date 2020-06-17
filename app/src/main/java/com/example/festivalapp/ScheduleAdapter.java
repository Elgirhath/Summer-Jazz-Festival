package com.example.festivalapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class ScheduleAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    public enum ViewType {
        DAY_LABEL,
        CONCERT_CARD,
        BOTTOM_SPACE
    }

    private List<String[]> concertsData;
    private List<ViewType> viewTypes;

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public RelativeLayout layout;
        public MyViewHolder(RelativeLayout v) {
            super(v);
            layout = v;
        }
    }

    public ScheduleAdapter(List<String[]> concertsData) {
        this.concertsData = concertsData;

        assignViewTypes();
    }

    private void assignViewTypes() {
        viewTypes = new ArrayList<>();
        String previousDate = null;
        for (String[] concertData : concertsData) {
            if (concertData[0] != previousDate){
                previousDate = concertData[0];
                viewTypes.add(ViewType.DAY_LABEL);
            }
            viewTypes.add(ViewType.CONCERT_CARD);
        }
        viewTypes.add(ViewType.BOTTOM_SPACE);
    }

    private int getConcertDataPosition(int recyclerViewPosition) {
        int concertPosition = 0;
        for (int i = 0; i < recyclerViewPosition; i++) {
            if (viewTypes.get(i) == ViewType.CONCERT_CARD) {
                concertPosition++;
            }
        }
        return concertPosition;
    }

    @Override
    public int getItemViewType(int position) {
        return viewTypes.get(position).ordinal();
    }

    @Override
    public ScheduleAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RelativeLayout relativeLayout;
        if (viewType == ViewType.DAY_LABEL.ordinal()) {
            relativeLayout = (RelativeLayout) LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.schedule_day_label, parent, false);
        }
        else if (viewType == ViewType.CONCERT_CARD.ordinal()) {
            relativeLayout = (RelativeLayout) LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.schedule_card, parent, false);
        }
        else {
            relativeLayout = (RelativeLayout) LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.schedule_card_space, parent, false);
        }
        return new MyViewHolder(relativeLayout);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        MyViewHolder mHolder = (MyViewHolder) holder;

        ViewType viewType = viewTypes.get(position);

        if (viewType == ViewType.DAY_LABEL) {
            String[] nextConcertData = concertsData.get(getConcertDataPosition(position + 1));
            TextView label = mHolder.layout.findViewById(R.id.dayLabel);
            label.setText(nextConcertData[0]);
        }
        else if (viewType == ViewType.CONCERT_CARD){
            String[] concertData = concertsData.get(getConcertDataPosition(position));
            TextView concertTitle = mHolder.layout.findViewById(R.id.concert_title);
            concertTitle.setText(concertData[1]);
        }
    }

    @Override
    public int getItemCount() {
        return viewTypes.size();
    }
}

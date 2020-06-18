package com.example.festivalapp;

import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class ScheduleAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private final DBmanager dBmanager;
    private String searchQuery = "";

    public enum ViewType {
        DAY_LABEL,
        CONCERT_CARD,
        BOTTOM_SPACE
    }

    private List<ViewType> viewTypes;

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public RelativeLayout layout;
        public MyViewHolder(RelativeLayout v) {
            super(v);
            layout = v;
        }
    }

    public ScheduleAdapter(DBmanager dBmanager) {
        this.dBmanager = dBmanager;
        assignViewTypes();
    }

    public void applyQuery(String search) {
        searchQuery = search;
        assignViewTypes();
        notifyDataSetChanged();
    }

    private void assignViewTypes() {
        viewTypes = new ArrayList<>();
        String previousDate = null;

        Cursor cursor = getConcertsOrderedByDate();
        cursor.moveToPrevious();
        int dateColumnIndex = cursor.getColumnIndex(DataBaseHelper.DATE);
        while (cursor.moveToNext()) {
            String date = cursor.getString(dateColumnIndex);
            if (!date.equals(previousDate)) {
                previousDate = date;
                viewTypes.add(ViewType.DAY_LABEL);
            }
            viewTypes.add(ViewType.CONCERT_CARD);
        }
        viewTypes.add(ViewType.BOTTOM_SPACE);
    }

    private Cursor getConcertCursorAtPosition(int position) {
        Cursor cursor = dBmanager.getDatabase().rawQuery(
                "select * from " + DataBaseHelper.PROGRAM +
                    " where " + DataBaseHelper.ARTIST + " like \"%" + searchQuery + "%\" " +
                    " order by " + DataBaseHelper.DATE +
                    " limit 1 offset " + position
                , null);
        cursor.moveToFirst();
        return cursor;
    }

    private Cursor getConcertsOrderedByDate() {
        Cursor cursor = dBmanager.getDatabase().rawQuery(
                "select * from " + DataBaseHelper.PROGRAM +
                        " where " + DataBaseHelper.ARTIST + " like \"%" + searchQuery + "%\" " +
                        " order by " + DataBaseHelper.DATE
                , null);
        cursor.moveToFirst();
        return cursor;
    }

    private String convertDate(String dateAndTime) {
        String date = dateAndTime.substring(0, dateAndTime.indexOf(" "));
        String[] parts = date.split("-");
        String[] months = {"stycznia", "lutego", "marca", "kwietnia", "maja", "czerwca", "lipca", "sierpnia", "września", "października", "listopada", "grudnia"};
        String month = months[Integer.parseInt(parts[1]) - 1];
        return parts[2] + " " + month + " " + parts[0];
    }

    private String convertTime(String time) {
        return time.substring(0,time.indexOf(":", time.indexOf(":") + 1));
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
            Cursor nextConcertDataCursor = getConcertCursorAtPosition(getConcertDataPosition(position + 1));
            TextView label = mHolder.layout.findViewById(R.id.dayLabel);
            String date = nextConcertDataCursor.getString(nextConcertDataCursor.getColumnIndex(DataBaseHelper.DATE));
            label.setText(convertDate(date));
        }
        else if (viewType == ViewType.CONCERT_CARD){
            Cursor concertDataCursor = getConcertCursorAtPosition(getConcertDataPosition(position));
            TextView concertTitle = mHolder.layout.findViewById(R.id.concert_title);
            concertTitle.setText(concertDataCursor.getString(concertDataCursor.getColumnIndex(DataBaseHelper.ARTIST)));

            TextView concertPlace = mHolder.layout.findViewById(R.id.schedule_place);
            concertPlace.setText(concertDataCursor.getString(concertDataCursor.getColumnIndex(DataBaseHelper.PLACE)));

            TextView concertTime = mHolder.layout.findViewById(R.id.schedule_time);
            String time = concertDataCursor.getString(concertDataCursor.getColumnIndex(DataBaseHelper.HOUR));
            concertTime.setText(convertTime(time));
        }
    }

    @Override
    public int getItemCount() {
        return viewTypes.size();
    }
}

package com.example.festivalapp.schedule;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.festivalapp.database.entity.ConcertEntity;
import com.example.festivalapp.database.DBmanager;
import com.example.festivalapp.database.DataBaseHelper;
import com.example.festivalapp.R;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class ScheduleAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private final DBmanager dBmanager;
    private String searchQuery = "";
    private Context context;

    public enum ViewType {
        DAY_LABEL,
        CONCERT_CARD,
        BOTTOM_SPACE
    }

    private List<ViewType> viewTypes;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public RelativeLayout layout;
        public ViewHolder(RelativeLayout v) {
            super(v);
            layout = v;
        }
    }

    public ScheduleAdapter(Context context, DBmanager dBmanager) {
        this.dBmanager = dBmanager;
        assignViewTypes();
        this.context = context;
    }

    public void applyQuery(String search) {
        searchQuery = search;
        assignViewTypes();
        notifyDataSetChanged();
    }

    private void assignViewTypes() {
        viewTypes = new ArrayList<>();
        String previousDate = null;

        ConcertEntity[] concertEntities = getConcertsOrderedByDate();
        for (ConcertEntity concertEntity : concertEntities) {
            String date = concertEntity.DATE;
            if (!date.equals(previousDate)) {
                previousDate = date;
                viewTypes.add(ViewType.DAY_LABEL);
            }
            viewTypes.add(ViewType.CONCERT_CARD);
        }
        viewTypes.add(ViewType.BOTTOM_SPACE);
    }

    private ConcertEntity[] getConcertsOrderedByDate() {
        ConcertEntity[] concertEntities = dBmanager.executeQuery(
                "select * from " + DataBaseHelper.TABLE_NAME +
                        " where " + DataBaseHelper.ARTIST + " like \"%" + searchQuery + "%\" " +
                        " order by " + DataBaseHelper.DATE);
        return concertEntities;
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

    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NotNull ViewGroup parent, int viewType) {
        int view;

        if (viewType == ViewType.DAY_LABEL.ordinal()) view = R.layout.schedule_day_label;
        else if (viewType == ViewType.CONCERT_CARD.ordinal()) view = R.layout.schedule_card;
        else view = R.layout.schedule_card_space;

        RelativeLayout relativeLayout = (RelativeLayout) LayoutInflater.from(parent.getContext())
                    .inflate(view, parent, false);

        return new ViewHolder(relativeLayout);
    }

    @Override
    public void onBindViewHolder(@NotNull RecyclerView.ViewHolder holder, int position) {
        ViewHolder viewHolder = (ViewHolder) holder;

        ViewType viewType = viewTypes.get(position);

        if (viewType == ViewType.DAY_LABEL) {
            ConcertEntity nextConcertData = getConcertsOrderedByDate()[getConcertDataPosition(position + 1)];
            TextView label = viewHolder.layout.findViewById(R.id.dayLabel);
            String date = nextConcertData.DATE;
            label.setText(DateConverter.convert(date));
        }
        else if (viewType == ViewType.CONCERT_CARD){
            ConcertEntity concertEntity = getConcertsOrderedByDate()[getConcertDataPosition(position)];
            new ScheduleCardGenerator(context).generate(viewHolder.layout, concertEntity);
        }
    }

    @Override
    public int getItemCount() {
        return viewTypes.size();
    }
}

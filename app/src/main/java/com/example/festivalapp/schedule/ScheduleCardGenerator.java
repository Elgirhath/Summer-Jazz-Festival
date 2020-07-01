package com.example.festivalapp.schedule;

import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.content.ContextCompat;

import com.example.festivalapp.App;
import com.example.festivalapp.R;
import com.example.festivalapp.database.entity.ConcertEntity;

public class ScheduleCardGenerator {
    public void generate(View layout, final ConcertEntity concertEntity) {
        TextView concertTitle = layout.findViewById(R.id.concert_title);
        concertTitle.setText(concertEntity.ARTIST);

        TextView concertPlace = layout.findViewById(R.id.schedule_place);
        concertPlace.setText(concertEntity.PLACE);

        TextView concertTime = layout.findViewById(R.id.schedule_time);
        String time = concertEntity.HOUR;
        concertTime.setText(time);

        ImageView ticketButton = layout.findViewById(R.id.buyTicketButton);
        if (concertEntity.LINK.isEmpty()) {
            ticketButton.setImageDrawable(ContextCompat.getDrawable(App.getContext(), R.drawable.ic_ticket_grayed));

            ticketButton.setOnClickListener(null);
        }
        else {
            ticketButton.setImageDrawable(ContextCompat.getDrawable(App.getContext(), R.drawable.ic_ticket));

            ticketButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try {
                        Uri uri = Uri.parse(concertEntity.LINK);
                        Intent intent = new Intent();
                        intent.setAction(Intent.ACTION_VIEW);
                        intent.addCategory(Intent.CATEGORY_BROWSABLE);
                        intent.setData(uri);
                        view.getContext().startActivity(intent);
                    }
                    catch (Exception ex) {
                        Toast.makeText(App.getContext(), App.getContext().getString(R.string.buy_ticket_error), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
}

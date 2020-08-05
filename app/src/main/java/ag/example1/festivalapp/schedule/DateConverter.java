package ag.example1.festivalapp.schedule;

import ag.example1.festivalapp.App;
import ag.example1.festivalapp.R;

public class DateConverter {
    public static String convert(String date) {
        String[] parts = date.split("-");
        String[] months = App.getContext().getResources().getStringArray(R.array.months_declensioned);
        String month = months[Integer.parseInt(parts[1]) - 1];
        return parts[2] + " " + month + " " + parts[0];
    }
}

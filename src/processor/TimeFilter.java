package processor;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import input.Event;

public class TimeFilter implements FilterComponent{
    private final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");

    public ArrayList<Event> filter(List<Event>events,String timeStamp) {
        Date standard = parseStringYMdToDate(timeStamp);
        ArrayList<Event> filtEvents = new ArrayList<Event>();
        for (Event i : events) {
            if (i.gettimeStamp() == null) {
                filtEvents.add(i);
                i.stringOutput();
            }

            Date eventDate = parseStringYMdToDate(i.gettimeStamp());
            long bias = eventDate.getTime() / 86400000 - standard.getTime()
                    / 86400000;
            if (bias <= 7 && bias >= 0) {
                filtEvents.add(i);
                i.stringOutput();
            }
        }
        return filtEvents;
    }

    private Date parseStringYMdToDate(String date) {
        Date output = null;
        try {
            output = simpleDateFormat.parse(date);
        } catch (ParseException e) {
            System.err.println("Cannot parse date "+date+" into year-month-date format"); //TODO: EXCEPTION
            e.printStackTrace();
            return null;
        }
        return output;
    }
}

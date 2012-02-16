package filters;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.jdom.Document;
import org.jdom.JDOMException;

import util.CalendarUtil;
import util.Event;
import java.util.Date;

public class TimeFilter extends FilterComponent{

    public ArrayList<Event> filter(List<Event>events,String timeStamp) {

        Date standard =   parseStringYMdToDate(timeStamp);
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


    public static Date parseStringYMdToDate(String date) {
        Date output = null;
        try {
            output = CalendarUtil.simpleDateFormat.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
        return output;
    }
}

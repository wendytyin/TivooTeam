package processor;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import input.Event;
import java.util.*;

public class TimeFilter implements FilterComponent{
    private final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddhhmm");

    public ArrayList<Event> filter(List<Event>events,String[] timeStamps) {
		ArrayList<Event> filtEvents = new ArrayList<Event>();
    	for(String timeStamp : timeStamps){
    		Date standard = parseStringYMdhmToDate(timeStamp);
    		for (Event i : events) {
    			if (i.getStartTime() == null) {
    				filtEvents.add(i);
    				i.stringOutput();
    			}

    			Date eventDate = parseStringYMdhmToDate(i.getStartTime());
    			long bias = eventDate.getTime() / 86400000 - standard.getTime()
    					/ 86400000;
    			if (bias <= 7 && bias >= 0) {
    				filtEvents.add(i);
    				i.stringOutput();
    			}
    		}
    	}
        return filtEvents;
    }

    private Date parseStringYMdhmToDate(String date) {
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
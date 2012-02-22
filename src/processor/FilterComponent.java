package processor;

import input.Event;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public abstract class FilterComponent {
    

	private static final SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
			"yyyyMMdd");


	public List<Event> filter(List<Event> events, String keyWord) {
		List<Event> filtEvents = new ArrayList<Event>();
		for (Event i : events) {
		    if (filterSpecificEvent(i,keyWord)){
		        filtEvents.add(i);
                i.stringOutput();
		    }
		}

		return filtEvents;
	}

	abstract boolean filterSpecificEvent(Event i, String keyWord);
	
	protected Date parseStringYMdToDate(String date) {
        Date output = null;
        try {
            output = simpleDateFormat.parse(date);
        } catch (ParseException e) {
            System.err.println("Cannot parse date "+date+" into year-month-date format");
            e.printStackTrace();
            return null;
        }
        return output;
	}

}

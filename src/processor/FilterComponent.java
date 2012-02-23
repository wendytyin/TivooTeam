package processor;

import input.Event;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public abstract class FilterComponent {

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

}

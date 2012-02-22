import input.*;
import processor.*;
import output.*;

import java.io.IOException;
import java.util.List;

import org.jdom.JDOMException;
import org.w3c.dom.DOMException;

public class Main {

	CalParser a;

	public static void main(String[] args) {

		// input 
		CalendarUtil util = new CalendarUtil();
		CalParser s = util.chooseParser("resources/googlecal.xml");
		List<Event> events = s.parser();
		
		// processor 
		FilterComponent filter = new KeywordFilter();
		events = filter.filter(events, "Meet");
		 filter=new TimeFilter();
		 events=filter.filter(events, "20110923");
		
		// output
		HtmlFunctions writer = new HtmlFunctions();
		writer.writeListOfEvents(events);

	}
}

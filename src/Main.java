import input.*;
import processor.*;
import output.*;

import java.io.IOException;
import java.util.*;

import javax.xml.parsers.ParserConfigurationException;

import org.jdom.JDOMException;
import org.w3c.dom.DOMException;



public class Main {

    CalParser a;

    public static void main(String[] args) throws JDOMException, IOException{
        
    	String[] keyWords= {"Meet", "Apple"};
    	String[] timeStamps = { "201108161130", "201109140300"};
    	String[] details = {"Ben Affleck", "Amy Ryan"}; 
        CalendarUtil util = new CalendarUtil();
        CalParser s = util.chooseParser("resources/googlecal.xml");
        CalParser s2 = util.chooseParser("resources/dukecal.xml");
        s.parser(); // Elements in all kind of XML will be extracted.
        s2.parser();
        List<Event> events = s.mergeparser(s2);
        FilterComponent filter = new KeywordFilter();
        events = filter.filter(events,keyWords);
        filter = new TimeFilter();
        events = filter.filter(events,timeStamps);
        filter = new DetailFilter();
        events = filter.filter(events,details); 
        Sorters sorter = new SortByStartDate();
        events = sorter.sort(events);
        sorter = new SortByEndDate();
        events = sorter.sort(events);
        sorter = new SortByTitle();
        events = sorter.sort(events);
      //  HtmlFunctions writer=new HtmlFunctions();
       // writer.writeListOfEvents(events);
        
    }
}
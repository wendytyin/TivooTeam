import java.io.IOException;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

import org.jdom.JDOMException;
import org.w3c.dom.DOMException;

import util.CalendarUtil;
import util.Event;
import util.HtmlFunctions;
import xmlparsing.*;

import com.hp.gagawa.java.Document;

import filters.*;

public class Main {

    CalParser a;

    //TODO: CATCH THOSE EXCEPTIONS
    public static void main(String[] args){
        
        CalendarUtil util = new CalendarUtil();
        CalParser s = util.chooseParser("resources/googlecal.xml");
        List<Event>events=s.parser();
        FilterComponent filter=new KeywordFilter();

        events=filter.filter(events,"Meet");
//        filter=new TimeFilter();
//        events=filter.filter(events, "20110930");
        HtmlFunctions.writeListOfEvents(events);
        
    }
}

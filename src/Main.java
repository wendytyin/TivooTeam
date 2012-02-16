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

    public static void main(String[] args) throws DOMException,
            IOException, JDOMException {
        // Tivoo s = new Tivoo();
        // s.loadFile("resources/dukecal.xml");
        // s.filterWordbyJDOM("ball");
        CalendarUtil util = new CalendarUtil();
        CalParser s = util.chooseParser("resources/dukecal.xml");
        List<Event>events=s.parser();
        FilterComponent filter=new KeywordFilter();

        events=filter.filter(events,"ball");
//        filter=new TimeFilter();
//        events=filter.filter(events, "20111001");
        HtmlFunctions.writeListOfEvents(events);
        
        
        
    }
}

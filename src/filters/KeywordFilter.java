package filters;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jdom.JDOMException;

import util.Event;

public class KeywordFilter extends FilterComponent{


    public List<Event> filter(List<Event>events, String keyWord) {
        ArrayList<Event>filtEvents=new ArrayList<Event>();
        for (Event i : events) {
            if (i.getTitle().contains(keyWord)) {
                filtEvents.add(i);
                i.stringOutput();
            }
        }
        return filtEvents;
    }

}

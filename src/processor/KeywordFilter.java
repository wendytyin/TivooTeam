package processor;

import input.Event;

import java.util.ArrayList;
import java.util.List;

import org.jdom.JDOMException;

public class KeywordFilter implements FilterComponent{

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

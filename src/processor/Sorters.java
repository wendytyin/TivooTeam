package processor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import input.Event;

public abstract class Sorters {

    protected static final Event emptyOuter=new Event(null,null,null,null,null);

    public abstract List<Event> sort(List<Event>events);
    
    public List<Event> reverseSort(List<Event>events){
        List<Event>events2=new ArrayList<Event>();
        events2=sort(events);
        Collections.reverse(events2);
        return events2;
    }
}

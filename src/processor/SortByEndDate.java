package processor;

import input.Event;
import input.Event.StartTimeComparator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SortByEndDate extends Sorters{
    
    @Override
    public List<Event> sort(List<Event> events) {
        List<Event>events2=new ArrayList<Event>();
        Collections.copy(events2, events);
        Event.EndTimeComparator emptyInner=emptyOuter.new EndTimeComparator();
        Collections.sort(events2,emptyInner);
        return events2;
    }
}

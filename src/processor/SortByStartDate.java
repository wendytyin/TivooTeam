package processor;

import input.Event;
import input.Event.StartTimeComparator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SortByStartDate extends Sorters{
    @Override
    public List<Event> sort(List<Event> events) {
        List<Event>events2=new ArrayList<Event>();
        Collections.copy(events2, events);
        Event.StartTimeComparator emptyInner=emptyOuter.new StartTimeComparator();
        Collections.sort(events2,emptyInner);
        return events2;
    }
}

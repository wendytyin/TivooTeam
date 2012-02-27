package processor;

import input.Event;
import input.Event.StartTimeComparator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SortByTitle extends Sorters{
    @Override
    public List<Event> sort(List<Event> events) {
        List<Event>events2=new ArrayList<Event>();
        events2.addAll(events);
        Event.TitleComparator emptyInner=emptyOuter.new TitleComparator();
        Collections.sort(events2,emptyInner);
        return events2;
    }
}

package filters;

import java.util.List;

import util.Event;

public abstract class FilterComponent {

    public abstract List<Event> filter(List<Event> events, String string);
    
}

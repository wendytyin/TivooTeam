package processor;

import input.Event;

import java.util.List;

public interface FilterComponent {

    public abstract List<Event> filter(List<Event> events, String string);
    
}

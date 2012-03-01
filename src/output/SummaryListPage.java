package output;

import input.Event;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.hp.gagawa.java.Node;
import com.hp.gagawa.java.elements.*;

public class SummaryListPage extends HtmlPageWriters {

    private int day = 8;

    public SummaryListPage(){
        super("Summary_List",new Body(), new Ul());
    }
    
    // page containing all events, links to detailed pages
    protected void attachEvent(Event e, Node o) {
        if (e.getDayOfWeek() != day) { // days of the week headers
            day = e.getDayOfWeek();
            ((Ul) o).appendChild(writeDayOfWeek(day));
        }

        ((Ul) o).appendChild(writeEventSummary(e));
    }

    private Node writeEventSummary(Event e) {
        Li li = new Li();
        A a = new A();
        a.setHref(e.getNameForFile() + ".html");
        a.appendText(e.getTitle());
        li.appendChild(a);
        li.appendChild(new Br());
        li.appendText(e.getFormattedStartTime() + " | " + e.getFormattedEndTime());
        return li;
    }

    @Override
    protected String getFileName() {
        return "output/summary_List.html";
    }

    @Override
    protected List<Event> additionalFilter(List<Event> events) {
        return events;  //no filter applied
    }
}
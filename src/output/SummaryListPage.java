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
    private Html start;
    private Body body;
    private Ul ul;

    public SummaryListPage(){
        start=writeHeader("Summary_List");
        body = new Body();
        ul = new Ul();
    }
    public void write(List<Event> events) {
        super.write(
        sortByStartDate(events));
    }
    
    // page containing all events, links to detailed pages
    public void writeEvent(Event e) {
        if (e.getDayOfWeek() != day) { // days of the week headers
            day = e.getDayOfWeek();
            ul.appendChild(writeDayOfWeek(day));
        }

        ul.appendChild(writeEventSummary(e));
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
    protected void closePages() {
        body.appendChild(ul);
        start.appendChild(body);

        File filename = new File("output/summary_List.html");
        writeToFile(filename,start);
    }
}
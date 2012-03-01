package output;

import input.Event;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.hp.gagawa.java.Node;
import com.hp.gagawa.java.elements.*;

public class ConflictsPage extends HtmlPageWriters {

    public ConflictsPage() {
        super("Conflicts",new Body(),new Ul());
    }

    // page containing all events, links to detailed pages
    protected void attachEvent(Event e,Node o) {
        ((Ul)o).appendChild(writeEventSummary(e));
    }

    private Node writeEventSummary(Event e) {
        Li li = new Li();
        if (e.getTitle().equals("<br />Next Conflict:")){
            li.appendText(e.getTitle());
            return li;
        }
        A a = new A();
        a.setHref(e.getNameForFile() + ".html");
        a.appendText(e.getTitle());
        li.appendChild(a);
        li.appendChild(new Br());
        li.appendText(e.getFormattedStartTime() + " | " + e.getFormattedEndTime());
        return li;
    }

    @Override
    protected List<Event> additionalFilter(List<Event> events) {
        return findConflicts(events);
    }


    // removes all events without conflict in the list
    // This should really go into the processor area
    private List<Event> findConflicts(List<Event> events) {
        Event emptyOuter = new Event(null, null, null, null, null);

        List<Event> conflicting = new ArrayList<Event>();
        Event previous = null;
        for (Event e : events) {
            if (previous == null) {
                previous = e;
                continue;
            }
            // conflicts when: same starttime, same endtime,
            // starttime less than other endtime and greater than other
            // starttime
            int starttime = emptyOuter.toCompare(e.getStartTime(),
                    previous.getStartTime());
            int endtime = emptyOuter.toCompare(e.getEndTime(),
                    previous.getEndTime());
            int startendtime = emptyOuter.toCompare(e.getStartTime(),
                    previous.getEndTime());
            int endstarttime = emptyOuter.toCompare(e.getEndTime(),
                    previous.getStartTime());
            if (starttime == 0 || endtime == 0
                    || (starttime < 0 && startendtime > 0) 
                    || (starttime > 0 && endstarttime < 0)) {
                conflicting.add(previous);
                conflicting.add(e);
                conflicting.add(new Event("<br />Next Conflict:","000000000000","000000000000",null,null));
            }
            previous=e;
        }
        return conflicting;
    }
    
    @Override
    protected String getFileName() {
        return "output/ConflictsPage.html";
    }
    
//    public static void main(String[]args){
//        List<Event>tester=new ArrayList<Event>();
//        tester.add(new Event("title1","201101011100","201101011300","www.google.com","descrp1"));
//        tester.add(new Event("title2","201101011200","201101011600","www.yahoo.com","descrp2"));
//        ConflictsPage something=new ConflictsPage();
//        something.write(tester);
//    }
}
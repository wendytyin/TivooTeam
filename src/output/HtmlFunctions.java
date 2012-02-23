package output;

import input.Event;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import com.hp.gagawa.java.Node;
import com.hp.gagawa.java.elements.*;

public class HtmlFunctions {
    private Html start;
    private final HashMap<Integer, String> daysOfWeek = new HashMap<Integer, String>();
    {
        daysOfWeek.put(1, "Sunday");
        daysOfWeek.put(2, "Monday");
        daysOfWeek.put(3, "Tuesday");
        daysOfWeek.put(4, "Wednesday");
        daysOfWeek.put(5, "Thursday");
        daysOfWeek.put(6, "Friday");
        daysOfWeek.put(7, "Saturday");
    }

    public void writeListOfEvents(List<Event> events) {
        sortDatesIntoWeek(events);
        writeDetailPages(events);
        writeSummaryPage(events);

        System.out.println("finished writing to file");
    }

    // page containing all events, links to detailed pages
    private void writeSummaryPage(List<Event> events) {
        writeHeader();
        Body body = new Body();
        Ul ul = new Ul();
        int day = 8;
        for (Event e : events) {
            if (e.getDayOfWeek() != day) { // days of the week headers
                day = e.getDayOfWeek();
                ul.appendChild(writeDayOfWeek(day));
            }

            ul.appendChild(writeEventSummary(e));
        }
        body.appendChild(ul);
        start.appendChild(body);

        File filename = new File("output/summary.html");
        writeToFile(filename);
    }

    private void writeDetailPages(List<Event> events) {
        for (Event e : events) {
            writeHeader();
            Body body = new Body();
            body.appendChild(writeAllInEvent(e));
            start.appendChild(body);

            File filename = new File("output/" + e.gettimeStamp() + ".html");
            writeToFile(filename);
        }
    }

    private void writeHeader() {
        start = new Html();
        Head head = new Head();
        start.appendChild(head);

        Title title = new Title();
        title.appendChild(new Text("output"));
        head.appendChild(title);
    }

    private Node writeEventSummary(Event e) {
        Li li = new Li();
        A a = new A();
        a.setHref(e.gettimeStamp() + ".html"); // TODO: MAKE MORE SAFE for
                                               // overlapping time stamps
        a.appendText(e.getTitle());
        li.appendChild(a);
        li.appendChild(new Br());
        li.appendText(e.getStartTime() + " | " + e.getEndTime());
        return li;
    }

    private Node writeAllInEvent(Event e) {
        P p = new P();
        A a = new A();
        a.setHref(e.getLink());
        a.appendText("Title : " + e.getTitle());
        p.appendChild(a);
        p.appendChild(new Br());
        p.appendText("Start Time : " + e.getStartTime());
        p.appendChild(new Br());
        p.appendText("End Time : " + e.getEndTime());
        p.appendChild(new Br());
        p.appendText("Time Stamp: " + e.gettimeStamp());
        p.appendChild(new Br());
        p.appendText("Link : " + e.getLink());
        p.appendChild(new Br());

        return p;
    }

    private Node writeDayOfWeek(int day) {
        H1 h1 = new H1();
        h1.appendText(daysOfWeek.get(day));
        return h1;
    }

    private void writeToFile(File filename) {
        BufferedWriter out = null;
        try {
            out = openFile(filename);
            out.write(start.write());
        } catch (IOException e) {
            System.err.println("unable to write to file " + filename.getName());
            e.printStackTrace();
        } finally {
            if (out != null) {
                System.out.println("Closing Buffered Writer in HtmlFunctions");
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    System.err
                            .println("Buffered Writer file didn't close properly");
                }
            } else {
                System.out
                        .println("Buffered Writer in HtmlFunctions never opened");
            }
        }
    }

    private BufferedWriter openFile(File filename) throws IOException {
        FileWriter fstream = new FileWriter(filename);
        BufferedWriter out = new BufferedWriter(fstream);
        return out;
    }

    private void sortDatesIntoWeek(List<Event> events) {
        Collections.sort(events);
    }

}

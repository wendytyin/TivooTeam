package output;

import input.Event;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.text.ParseException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import com.hp.gagawa.java.Node;
import com.hp.gagawa.java.elements.*;

public class HtmlFunctions {
    private Html start;
    private static final HashMap<Integer, String> DAYS_OF_WK = new HashMap<Integer, String>();
    static{
        DAYS_OF_WK.put(1, "Sunday");
        DAYS_OF_WK.put(2, "Monday");
        DAYS_OF_WK.put(3, "Tuesday");
        DAYS_OF_WK.put(4, "Wednesday");
        DAYS_OF_WK.put(5, "Thursday");
        DAYS_OF_WK.put(6, "Friday");
        DAYS_OF_WK.put(7, "Saturday");
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
        h1.appendText(DAYS_OF_WK.get(day));
        return h1;
    }

    private void writeToFile(File filename) {
        try {
            BufferedWriter out = openFile(filename);
            out.write(start.write());
            closeFile(out);
        } catch (IOException e) {
            System.err.println("unable to write to file " + filename.getName()); // TODO:EXCEPTIONS
            e.printStackTrace();
        }
    }

    private BufferedWriter openFile(File filename) throws IOException {
        FileWriter fstream = new FileWriter(filename);
        BufferedWriter out = new BufferedWriter(fstream);
        return out;
    }

    private void closeFile(Writer out) {
        try {
            out.close();
        } catch (IOException e) {
            System.err.println("unable to close file"); // TODO: print which
                                                        // file it is too
            e.printStackTrace();
        }
    }

    private void sortDatesIntoWeek(List<Event> events) {
        Collections.sort(events);
    }

}

package output;

import input.Event;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import processor.SortByStartDate;

import com.hp.gagawa.java.Node;
import com.hp.gagawa.java.elements.*;

public abstract class HtmlPageWriters {
    private SortByStartDate startsort=new SortByStartDate();
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

    public void write(List<Event> events) {
        DetailPage details=new DetailPage();
        for (Event e:events){
            details.writeEvent(e); //automatically writes detailed pages
            writeEvent(e);
        }
        closePages();
        System.out.println("finished all writing to file");
    }

    private BufferedWriter openFile(File filename) throws IOException {
        FileWriter fstream = new FileWriter(filename);
        BufferedWriter out = new BufferedWriter(fstream);
        return out;
    }
    
    protected abstract void closePages();

    protected void writeToFile(File filename, Html start) {
        BufferedWriter out = null;
        try {
            out = openFile(filename);
            out.write(start.write());
        } catch (IOException e) {
            System.err.println("unable to write to file " + filename.getName());
            e.printStackTrace();
        } finally {
            if (out != null) {
//                System.out.println("Closing Buffered Writer in HtmlFunctions");
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

    protected abstract void writeEvent(Event e);

    protected Html writeHeader(String text) {
        Html start = new Html();
        Head head = new Head();
        start.appendChild(head);

        Title title = new Title();
        title.appendText(text);
        head.appendChild(title);
        return start;
    }
    
    protected Node writeDayOfWeek(int day) {
        H1 h1 = new H1();
        h1.appendText(daysOfWeek.get(day));
        return h1;
    }


    protected List<Event> sortByStartDate(List<Event> events) {
        return startsort.sort(events);
    }

}
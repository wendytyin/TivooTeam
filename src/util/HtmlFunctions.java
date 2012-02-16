package util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.List;

import com.hp.gagawa.java.elements.*;

public class HtmlFunctions {
    private static Html start;

    private static void writeHeader() {
        start = new Html();
        Head head = new Head();
        start.appendChild(head);

        Title title = new Title();
        title.appendChild(new Text("output"));
        head.appendChild(title);
    }

    public static void writeListOfEvents(List<Event> events) {
         writeDetailPages(events);
        writeSummaryPage(events);
        System.out.println("finished writing to file");
    }

    // unordered list
    private static void writeSummaryPage(List<Event> events) {
        writeHeader();
        Body body = new Body();
        Ul ul = new Ul();
        for (Event e : events) {
            Li li = new Li();
            A a = new A();
            a.setHref(e.getTitle()+".html");
            a.appendText(e.getTitle());
            li.appendChild(a);
            li.appendChild(new Br());
            li.appendText(" | " + e.getStartTime() + " | " + e.getEndTime());
            ul.appendChild(li);
        }
        body.appendChild(ul);
        start.appendChild(body);
        FileWriter fstream;
        try {
            fstream = new FileWriter("output/summary.html");
            BufferedWriter out = new BufferedWriter(fstream);
            writeFile(out);
        } catch (IOException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
    }

    private static void writeDetailPages(List<Event> events) {
        for (Event e : events) {
            writeHeader();
            Body body = new Body();
            P p = new P();
            p=writeAllInEvent(p,e);
            body.appendChild(p);
            start.appendChild(body);
            
            File f=new File("output/"+e.getTitle()+".html");

            try {
                FileWriter fstream = new FileWriter(f);
                BufferedWriter out = new BufferedWriter(fstream);
                writeFile(out);
            } catch (IOException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
        }
    }

    private static void writeFile(BufferedWriter out) {
        try {
            out.write(start.write());
            out.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    private static P writeAllInEvent(P p,Event e) {
        A a = new A();
        a.setHref(e.getLink());
        a.appendText("Title : "+e.getTitle());
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
}

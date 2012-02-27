package output;

import input.Event;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.hp.gagawa.java.Node;
import com.hp.gagawa.java.elements.*;

public class CalendarMonthPage extends HtmlPageWriters {

    private Html start;
    private Body body;
    private Table table;
    private Tr currentRow;
    private Event previous;

    public CalendarMonthPage() {
        start = writeHeader("Calendar_Month");
        body = new Body();
        table = new Table();
        table.setBorder("1px solid black");
        table.appendChild(writeDaysOfWeekHeader());
        currentRow=new Tr();
    }

    private Node writeDaysOfWeekHeader() {
        Tr tr = new Tr();
        tr.appendChild(new Td().appendText("Sunday"));
        tr.appendChild(new Td().appendText("Monday"));
        tr.appendChild(new Td().appendText("Tuesday"));
        tr.appendChild(new Td().appendText("Wednesday"));
        tr.appendChild(new Td().appendText("Thursday"));
        tr.appendChild(new Td().appendText("Friday"));
        tr.appendChild(new Td().appendText("Saturday"));
        return tr;
    }

    //if this is not called, then no filtering happens
    public void write(List<Event> events, String month) {
        List<Event> filtered = filterByDate(events, month);
        sortByStartDate(filtered);
        super.write(filtered);
    }
    
    public void writeEvent(Event e) {
        if (previous == null) { //first one, start a new row
            currentRow = new Tr();
            int column = e.getDayOfWeek();
            while (column > 0) {
                currentRow.appendChild(new Td().appendText("&nbsp;"));
                column -= 1;
            }
            previous = e;
        } else {
            String estart = e.getStartTime().substring(0, 8);
            String pstart = previous.getStartTime().substring(0, 8); //chop the date
            int difference = Integer.parseInt(estart)
                    - Integer.parseInt(pstart);
            difference = difference % 100; // only the days are left now
            if ((e.getDayOfWeek() != previous.getDayOfWeek()) 
                    || (difference > 0)) {
                int column = previous.getDayOfWeek();
                while (difference > 0) {
                    if (column >= 7) { // start a new row when reaches Saturday
                        table.appendChild(currentRow);
                        currentRow = new Tr();
                        column = 1;
                    }
                    currentRow.appendChild(new Td().appendText("&nbsp;"));
                    column+=1; //move the pointer to the appropriate day
                    difference -= 1;
                }
            }
        }
        ArrayList<Node> nodesInWk = currentRow.getChildren();
        currentRow.appendChild(writeEventSummary(e,
                (Td) nodesInWk.remove(nodesInWk.size() - 1)));
    }

    private Node writeEventSummary(Event e, Td td) {
        td.appendChild(new Hr());
        A a = new A();
        a.setHref(e.getNameForFile() + ".html");
        a.appendText(e.getTitle());
        td.appendChild(a);
        td.appendChild(new Br());
        td.appendText(e.getFormattedStartTime() + " | " + e.getFormattedEndTime());
        return td;
    }

    private List<Event> filterByDate(List<Event> events, String week) {
        if (week.length()<6){
            throw new Error("week must be of format yyyymm");
        }
        return filterByMonth(events, week);
    }
    
    private List<Event> filterByMonth(List<Event> events, String month) {
        List<Event> filt = new ArrayList<Event>();
        for (Event e : events) {
            if (e.getStartTime().substring(0,6).equals(month)) {
                filt.add(e);
            }
        }
        return filt;
    }

    @Override
    protected void closePages() {
        table.appendChild(currentRow);
        body.appendChild(table);
        start.appendChild(body);

        File filename = new File("output/Calendar_Month.html");
        writeToFile(filename, start);
    }

//    public static void main(String[] args) {
//        List<Event> tester = new ArrayList<Event>();
//        tester.add(new Event("title1", "201201011100", "201201011300",
//                "www.google.com", "descrp1"));
//        tester.add(new Event("title3", "201201091100", "201201101300",
//                "www.msn.com", "descrp1"));
//        tester.add(new Event("title2", "201201011400", "201201011600",
//                "www.yahoo.com", "descrp2"));
//        CalendarMonthPage something = new CalendarMonthPage();
//        something.write(tester, "201201");
//    }
}
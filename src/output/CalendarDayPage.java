package output;

import input.Event;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import com.hp.gagawa.java.Node;
import com.hp.gagawa.java.elements.*;

public class CalendarDayPage extends CalendarPage {
    
    public CalendarDayPage() {
        super("Calendar_Day",new Body(),new Ul());
    }

    // page containing all events, links to detailed pages
    protected void attachEvent(Event e, Node other) {
        ((Ul) other).appendChild(writeEventSummary(e));
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

    // day will be a string of format yyyymmdd
    protected List<Event> filterByDate(List<Event> events, String day) {
        List<Event> filt = null;
        try {
            Date iweek = simpleDateFormat.parse(day);
            filt = new ArrayList<Event>();
            for (Event e : events) {
                Date eweek = simpleDateFormat.parse(e.getStartTime().substring(
                        0, 8));
                if (eweek.equals(iweek)) {
                    filt.add(e);
                }
            }
        } catch (ParseException e) {
            System.err
                    .println("Day not recognized, must be a number formatted yyyymmdd");
        }
        return filt;
    }
    
    public String getFileName(){
        return "output/Calendar_Day.html";
    }

//    public static void main(String[] args) {
//        List<Event> tester = new ArrayList<Event>();
//        tester.add(new Event("title1", "201201011100", "201201011300",
//                "www.google.com", "descrp1"));
//        tester.add(new Event("title3", "201201061100", "201201061300",
//                "www.msn.com", "descrp1"));
//        tester.add(new Event("title2", "201201011400", "201201011600",
//                "www.yahoo.com", "descrp2"));
//        CalendarDayPage something = new CalendarDayPage();
//        something.write(tester, "20120101");
//    }
}
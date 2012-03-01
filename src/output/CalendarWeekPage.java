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

import com.hp.gagawa.java.FertileNode;
import com.hp.gagawa.java.Node;
import com.hp.gagawa.java.elements.*;

//TODO: make sure it doesn't show events in another month that happen to be the same week of the month
public class CalendarWeekPage extends CalendarPage {
    

    public CalendarWeekPage() {
        super("Calendar_Week",new Body(),new Table().setBorder("1px solid black"));
    }
    
    // page containing all events, links to detailed pages
    protected void attachEvent(Event e, Node o) {
        if (((Table)o).children.size()==0){ //week table hasn't been initialized
            createEmptyWeek((Table) o);
        }
        Tr row=(Tr) ((Table)o).getChild(1);
        Td currentSpot = (Td) row.getChild(e.getDayOfWeek() - 1);
        writeEventSummary(e, currentSpot);
    }

    private void createEmptyWeek(Table o) {
        o.appendChild(writeDaysOfWeekHeader());
        Tr tr=new Tr();
        for (int i=0;i<7;i++){
            tr.appendChild(new Td().setWidth("100px").setHeight("50px").appendText("&nbsp;"));
        }
        o.appendChild(tr);
    }

    private void writeEventSummary(Event e, Td td) {
        Div div = new Div().setStyle("display:block;margin:3px;background:cyan;");
        A a = new A();
        a.setHref(e.getNameForFile() + ".html");
        a.appendText(e.getTitle());
        div.appendChild(a);
        div.appendChild(new Br());
        div.appendText(e.getFormattedStartTime() + " | "
                + e.getFormattedEndTime());
        td.appendChild(div);
    }

    protected List<Event> filterByDate(List<Event> events, String week) {
        if (week.length()<6){
            throw new Error("week must be of format yyyy## where ## is week of year");
        }
        List<Event> sameyear = filterByYear(events, week.substring(0, 4));
        return filterByWeek(sameyear, week.substring(4, 6));
    }

    private List<Event> filterByYear(List<Event> events, String substring) {
        List<Event> filt = new ArrayList<Event>();
        for (Event e : events) {
            if (e.getStartTime().substring(0, 4).equals(substring)) {
                filt.add(e);
            }
        }
        return filt;
    }

    private List<Event> filterByWeek(List<Event> events, String week) {
        List<Event> filt = null;
        try {
            int iweek = Integer.parseInt(week);
            filt = new ArrayList<Event>();
            for (Event e : events) {
                if (getWeek(e.getStartTime()) == iweek) {
                    filt.add(e);
                }
            }
        } catch (NumberFormatException e) {
            System.err
                    .println("Week not recognized, must be a number formatted yyyy## where ## corresponds to week number of the year");
        }
        return filt;
    }

    private int getWeek(String time) {
        Calendar weeks = Calendar.getInstance();
        String year = time.substring(0, 4);
        String month = time.substring(4, 6);
        String day = time.substring(6, 8);
        weeks.set(Integer.parseInt(year), Integer.parseInt(month) - 1,
                Integer.parseInt(day));

        int weekday = weeks.get(Calendar.WEEK_OF_YEAR);
        return weekday;
    }

    @Override
    protected String getFileName() {
        return "output/Calendar_Week.html";
    }
//    public static void main(String[] args) {
//        List<Event> tester = new ArrayList<Event>();
//        tester.add(new Event("title1", "201201011100", "201201011300",
//                "www.google.com", "descrp1"));
//        tester.add(new Event("title3", "201201081100", "201201081300",
//                "www.msn.com", "descrp1"));
//        tester.add(new Event("title2", "201201011400", "201201011600",
//                "www.yahoo.com", "descrp2"));
//        CalendarWeekPage something = new CalendarWeekPage();
//        something.write(tester, "201201");
//    }
}
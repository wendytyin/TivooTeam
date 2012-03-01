package output;

import input.Event;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

import com.hp.gagawa.java.Node;
import com.hp.gagawa.java.elements.*;

public class CalendarMonthPage extends CalendarPage {

    public CalendarMonthPage() {
        super("Calendar_Month", new Body(), new Table()
                .setBorder("1px solid black"));
    }
    private Td blockTd(String color){
        return new Td().setBgcolor(color).setWidth("100px").setHeight("50px");
    }
    
    private void createEmptyMonth(Table o){
        o.appendChild(writeDaysOfWeekHeader());
        
        Calendar cal=Calendar.getInstance();
        cal.set(Integer.parseInt(myDate.substring(0,4)),
                Integer.parseInt(myDate.substring(4,6))-1,
                1);
        int totaldays=cal.getActualMaximum(Calendar.DAY_OF_MONTH); //gets last day of month
        int firstday=cal.get(Calendar.DAY_OF_WEEK); //gets first day of month
        int j=firstday;
        
        Tr tr=new Tr();
        while (j>1){ //block off previous month days
            tr.appendChild(blockTd("gray"));
            j-=1;
        }
        for (int i=1;i<=totaldays;i++){
            if ((((i+firstday-2)%7)==0)&& (tr.children.size()!=0)){ //reached end of week, start new row
                o.appendChild(tr);
                tr=new Tr();
            }
            tr.appendChild(blockTd("white").appendText(""+i));
        }
        while (((totaldays+firstday-1)%7)!=0){ //block off next month days
            tr.appendChild(blockTd("gray"));
            totaldays+=1;
        }
        o.appendChild(tr);
        
    }

    protected void attachEvent(Event e, Node o) {
        if (((Table)o).children.size()==0){ //month table hasn't been initialized
            createEmptyMonth((Table) o);
        }
        String time=e.getStartTime();

        Calendar cal=Calendar.getInstance();
        cal.set(Integer.parseInt(time.substring(0,4)),
                Integer.parseInt(time.substring(4,6))-1,
                Integer.parseInt(time.substring(6,8)));
        int row=cal.get(Calendar.WEEK_OF_MONTH);
        Node tr = ((Table)o).getChild(row);
        int column=cal.get(Calendar.DAY_OF_WEEK);
        Node td = ((Tr)tr).getChild(column-1);

        writeEventSummary(e,(Td) td);
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

    @Override
    protected List<Event> filterByDate(List<Event> events, String date) {
        if (date.length() < 6) {
            throw new Error("week must be of format yyyymm");
        }
        return filterByMonth(events, date);
    }

    private List<Event> filterByMonth(List<Event> events, String month) {
        List<Event> filt = new ArrayList<Event>();
        for (Event e : events) {
            if (e.getStartTime().substring(0, 6).equals(month)) {
                filt.add(e);
            }
        }
        return filt;
    }

    @Override
    protected String getFileName() {
        return "output/Calendar_Month.html";
    }

//     public static void main(String[] args) {
//     List<Event> tester = new ArrayList<Event>();
//     tester.add(new Event("title1", "201202011100", "201202011300",
//     "www.google.com", "descrp1"));
//     tester.add(new Event("title3", "201201091100", "201201101300",
//     "www.msn.com", "descrp1"));
//     tester.add(new Event("title2", "201202011400", "201202011600",
//     "www.yahoo.com", "descrp2"));
//     CalendarMonthPage something = new CalendarMonthPage();
//     something.write(tester, "201201");
//     }
}
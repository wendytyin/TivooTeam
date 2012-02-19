package input;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

//TODO: Make sure timestamps are formatted correctly, throw exception if not?
public class Event implements Comparable<Event> {
    private String title;
    private String starttime;
    private String endtime;
    private String timeStamp; // Note: a timeStamp is the start date, formatted
                              // yyyymmdd
    private String link;

    public Event() {
    }

    public void stringOutput() {
        System.out.println("Title : " + title);

        System.out.println("Start Time : " + starttime);

        System.out.println("EndTime : " + endtime);

        System.out.println("Time Stamp: " + timeStamp);

        System.out.println("Link : " + link);

        System.out
                .println("---------------------------------------------------------------------------------");
    }

    public String getTitle() {
        return title;
    }

    public String getStartTime() {
        return starttime;
    }

    public String getEndTime() {
        return endtime;
    }

    public String getLink() {
        return link;
    }

    public String gettimeStamp() {
        return timeStamp;
    }

    public void setTitle(String mytitle) {
        this.title = mytitle;
    }

    public void settimeStamp(String myTimeStamp) {
        this.timeStamp = myTimeStamp;
    }

    public void setStartTime(String myStartTime) {
        this.starttime = myStartTime;
    }

    public void setEndTime(String myEndTime) {
        this.endtime = myEndTime;
    }

    public void setLink(String myLink) {
        this.link = myLink;
    }

    public int getDayOfWeek() {
        Calendar weeks = Calendar.getInstance();

        String rawdate = gettimeStamp();
        String year = rawdate.substring(0, 4);
        String month = rawdate.substring(4, 6);
        String day = rawdate.substring(6, 8);
        weeks.set(Integer.parseInt(year), Integer.parseInt(month) - 1,
                Integer.parseInt(day));

        int weekday = weeks.get(Calendar.DAY_OF_WEEK);
        return weekday;
    }

    @Override
    public int compareTo(Event o) {
        Calendar week1 = Calendar.getInstance();
        Calendar week2 = Calendar.getInstance();

        // this is you
        String rawdate = o.gettimeStamp();
        String year = rawdate.substring(0, 4);
        String month = rawdate.substring(4, 6);
        String day = rawdate.substring(6, 8);
        week1.set(Integer.parseInt(year), Integer.parseInt(month) - 1,
                Integer.parseInt(day));

        // this is me
        String rawdate2 = gettimeStamp();
        String year2 = rawdate2.substring(0, 4);
        String month2 = rawdate2.substring(4, 6);
        String day2 = rawdate2.substring(6, 8);
        week2.set(Integer.parseInt(year2), Integer.parseInt(month2) - 1,
                Integer.parseInt(day2));

        return week2.compareTo(week1);
    }

}

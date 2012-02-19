package input;

import java.util.ArrayList;

public class CalendarUtil {

    private  ArrayList<CalParser> calendarList;
    String fileName;


    public CalParser chooseParser(String name) {
        fileName = name;
        loadCalendarParser();
        for (CalParser calParser : calendarList) {
            if (calParser.isThisKindof())
                return calParser;
        }
        throw new Error("This calendar type "+name+" is not recognized.");
    }

    private void loadCalendarParser() {
        calendarList = new ArrayList<CalParser>();
        calendarList.add(new GoogleCalendarParser().loadFile(fileName));
        calendarList.add(new DukeCalenderParser().loadFile(fileName));
        calendarList.add(new DukeBasketBallParser().loadFile(fileName));

    }
}
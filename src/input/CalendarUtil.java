package input;

import java.util.ArrayList;
import java.util.List;

public class CalendarUtil {
    
    public CalendarUtil() {
        parserList = new ArrayList<CalParser>();
    }

    private ArrayList<CalParser> calendarList;
    String fileName;

    private static List<CalParser> parserList;

    public void chooseParsers(String[] names) {
        for (String name : names) {
            fileName = name;
            loadCalendarParser();
            for (CalParser calParser : calendarList) {
                if (calParser.isThisKindof()) {
                    parserList.add(calParser);
                }
            }
//            throw new Error("This calendar type " + name
//                    + " is not recognized.");
        }
    }

    public List<Event> parser() {
        List<Event> events = new ArrayList<Event>();
        for (CalParser calParser : parserList) {
            events.addAll(calParser.parser());
        }
        return events;
    }

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
        calendarList.add(new DukeCalendarParser().loadFile(fileName));
        calendarList.add(new DukeBasketBallParser().loadFile(fileName));
        calendarList.add(new NFLParser().loadFile(fileName));
        calendarList.add(new TvParser().loadFile(fileName));

    }
}
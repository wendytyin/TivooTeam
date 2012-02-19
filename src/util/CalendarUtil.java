package util;



import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import xmlparsing.CalParser;
import xmlparsing.DukeBasketBallParser;
import xmlparsing.DukeCalenderParser;
import xmlparsing.GoogleCalendarParser;
import xmlparsing.ParserException;

public class CalendarUtil {

    public static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
    private static ArrayList<CalParser> calendarList;
    String fileName;

    public static Date parseStringYMdToDate(String date) {
        Date output = null;
        try {
            output = simpleDateFormat.parse(date);
        } catch (ParseException e) {
            e.printStackTrace(); //TODO: MODIFY EXCEPTION, DO NOT RETURN NULL
            return null;
        }
        return output;
    }

    public CalParser chooseParser(String name) {
        fileName = name;
        loadCalendarParser();
        for (CalParser calParser : calendarList) {
            if (calParser.isThisKindof())
                return calParser;
        }

        throw new ParserException("Calendar type not recognized.",ParserException.Type.CALENDAR_TYPE);

    }

    private void loadCalendarParser() {
        calendarList = new ArrayList<CalParser>();
        calendarList.add(new GoogleCalendarParser().loadFile(fileName));
        calendarList.add(new DukeCalenderParser().loadFile(fileName));
        calendarList.add(new DukeBasketBallParser().loadFile(fileName));

    }
}

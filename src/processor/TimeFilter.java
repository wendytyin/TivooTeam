package processor;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import input.Event;

public class TimeFilter extends FilterComponent{
    private static final SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
            "yyyyMMdd");

    @Override
    boolean filterSpecificEvent(Event i, String keyWord) {
        if (i.gettimeStamp() == null) {
            return true; 
        }

        Date standard = parseStringYMdToDate(keyWord);
        Date eventDate = parseStringYMdToDate(i.gettimeStamp());
        long bias = eventDate.getTime() / 86400000 - standard.getTime()
                / 86400000;
        if (bias <= 7 && bias >= 0) {
            return true;
        }
        return false;
    }

    protected Date parseStringYMdToDate(String date) {
        Date output = null;
        try {
            output = simpleDateFormat.parse(date);
        } catch (ParseException e) {
            System.err.println("Cannot parse date "+date+" into year-month-date format");
            e.printStackTrace();
            return null;
        }
        return output;
    }
}
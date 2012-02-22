package processor;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import input.Event;

public class TimeFilter extends FilterComponent{

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
    
}
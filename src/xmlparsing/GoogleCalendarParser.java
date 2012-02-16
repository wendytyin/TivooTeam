package xmlparsing;

import java.util.ArrayList;
import java.util.List;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;

import util.Event;

public class GoogleCalendarParser extends CalParser {

    @Override
    public boolean isThisKindof() {
        if (fileName.equals("resources/googlecal.xml"))
            return true;

        else

            return false;
    }

    public ArrayList<Event> parseEvent(Element root) {
        List<Element> events = root.getChildren("entry");
        ArrayList<Event> filterEvents = new ArrayList<Event>();

        for (int i = 0; i < events.size(); i++) {

            Event event = new Event();
            String[] date = events.get(i).getChildText("content").split(" ");

            StringBuilder str = new StringBuilder();

            if (date[0].equals("When:")) {
                date[3] = date[3].substring(0, date[3].length() - 1);
                str.append(date[4].substring(0, 4)).append(parseMonth(date[2]))
                        .append(parseDay(date[3]));
            }

            event.setTitle(events.get(i).getChildText("title"));
            event.setStartTime(events.get(i).getChildText("content"));
            event.setEndTime(null);
            event.setLink(events.get(i).getChildText("id"));
            event.settimeStamp(str.toString());
            filterEvents.add(event);

        }
        return filterEvents;
    }

    public String parseMonth(String month) {
        String mon = null;
        String[] MONTHS = { "Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul",
                "Aug", "Sep", "Oct", "Nov", "Dec" };
        for (int k = 0; k < 12; k++) {
            if (month.equals(MONTHS[k])) {
                mon = String.valueOf(k + 1);
                if (k > 0 && k < 9)
                    mon = "0" + mon;
            }
        }
        return mon;

    }

    public String parseDay(String day) {
        String we = day;
        int k = Integer.parseInt(day);
        if (k > 0 && k < 10)
            we = "0" + we;
        return we;

    }

}

package input;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import org.jdom.Element;

public class DukeCalendarParser extends CalParser {

    public boolean isThisKindof() {
        if (fileName.equals("resources/dukecal.xml"))
            return true;

        else

            return false;
    }

    public ArrayList<Event> parseEvent(Element root) {
        List<Element> events = root.getChildren("event");
        ArrayList<Event> filterEvents = new ArrayList<Event>();

        for (int i = 0; i < events.size(); i++) {

            String title = events.get(i).getChildText("summary");
            String endtime = events.get(i).getChild("end").getChildText("time")
                    + " "
                    + events.get(i).getChild("end").getChildText("shortdate");
            String starttime = events.get(i).getChild("start")
                    .getChildText("time")
                    + " "
                    + events.get(i).getChild("start").getChildText("shortdate");
            String timeStamp = events.get(i).getChild("start")
                    .getChildText("unformatted").substring(0, 8);
            String link = events.get(i).getChildText("link");
            
            Event event = new Event(title,starttime,endtime,timeStamp,link);
            filterEvents.add(event);

        }

        return filterEvents;
    }

}

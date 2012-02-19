package input;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;

public class DukeCalenderParser extends CalParser {

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

            Event event = new Event();
            event.setTitle(events.get(i).getChildText("summary"));
            event.setEndTime(events.get(i).getChild("end").getChildText("time")
                    + " "
                    + events.get(i).getChild("end").getChildText("shortdate"));
            event.setStartTime(events.get(i).getChild("start")
                    .getChildText("time")
                    + " "
                    + events.get(i).getChild("start").getChildText("shortdate"));
            event.settimeStamp(events.get(i).getChild("start")
                    .getChildText("unformatted").substring(0, 8));
            event.setLink(events.get(i).getChildText("link"));
            filterEvents.add(event);
        }

        return filterEvents;
    }

}

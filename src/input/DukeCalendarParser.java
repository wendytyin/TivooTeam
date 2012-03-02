package input;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import org.jdom.Element;

public class DukeCalendarParser extends CalParser {

    public boolean isThisKindof() {
        return (fileName.equals("resources/dukecal.xml"));
    }

    public ArrayList<Event> parseEvent(Element root) {
        List<Element> events = root.getChildren("event");
        ArrayList<Event> filterEvents = new ArrayList<Event>();

        for (int i = 0; i < events.size(); i++) {

            String title = events.get(i).getChildText("summary");
            String temp = events.get(i).getChild("start").getChildText("unformatted");
            String starttime = null;
            String endtime = null;
            if(temp.length()==8)
            {
            	starttime = temp.substring(0, 8)+"0000";
            }
            else
            	starttime = temp.substring(0, 8)+temp.substring(9,13);
            temp = events.get(i).getChild("end").getChildText("unformatted");
            if(temp.length()==8)
            {
            	endtime = temp.substring(0, 8)+"0000";
            }
            else
            	endtime = temp.substring(0, 8)+temp.substring(9,13);
           
            String link = events.get(i).getChildText("link");
            String description = events.get(i).getChildText("description");
            Event event = new Event(title,starttime,endtime,link,description);
            filterEvents.add(event);

        }

        return filterEvents;
    }

}
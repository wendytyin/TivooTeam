package input;

import java.util.ArrayList;
import java.util.List;

import org.jdom.Element;

public class DukeBasketBallParser extends CalParser {
    public boolean isThisKindof() {

        if (fileName.equals("resources/DukeBasketBall.xml"))
            return true;

        else

            return false;
    }

    public ArrayList<Event> parseEvent(Element root) {
        List<Element> events = root.getChildren("Calendar");
        ArrayList<Event> filterEvents = new ArrayList<Event>();
        for (int i = 0; i < events.size(); i++) {
            
            String title = events.get(i).getChildText("Subject");
            String time[] = events.get(i).getChildText("StartDate").split("/");
            for (int j = 0; j < 2; j++) {
                if (time[j].length() == 1) {
                    time[j] = "0" + time[j];
                }
            }

            String timeStamp = time[2] + time[0] + time[1];
            String starttime = events.get(i).getChildText("StartTime") + " "
                    + events.get(i).getChildText("StartDate");
            String endtime = events.get(i).getChildText("EndTime") + " "
                    + events.get(i).getChildText("EndDate");
            String link = events.get(i).getChildText("Description");
            link = link.substring(link.indexOf("http"));

            Event event = new Event(title,starttime,endtime,timeStamp,link);
            filterEvents.add(event);
        }
        return filterEvents;
    }

}
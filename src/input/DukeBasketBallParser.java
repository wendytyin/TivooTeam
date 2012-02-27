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
            String starttime = genTimeStamp(events.get(i).getChildText("StartDate"),events.get(i).getChildText("StartTime"));
            String endtime = genTimeStamp(events.get(i).getChildText("EndDate"),events.get(i).getChildText("EndTime"));
            String link = events.get(i).getChildText("Description");
            link = link.substring(link.indexOf("http"));
            Event event = new Event(title,starttime,endtime,link,"");
            filterEvents.add(event);
        }
        return filterEvents;
    }
    public String genTimeStamp(String date, String detail)
    {   System.out.println(date+" "+detail);
    	String time[] = date.split("/");
        for (int j = 0; j < 2; j++) {
            if (time[j].length() == 1) {
                time[j] = "0" + time[j];
            }
        }
       String[]  temp = detail.split(":");
        if(temp[2].contains("PM"))
        {
        	int hour = Integer.parseInt(temp[0]);
        	hour+=12;
        	temp[0] = Integer.toString(hour);
        }
        if(temp[0].length()==1)
        	temp[0]="0"+temp[0];
        System.out.println(time[2]+time[0]+time[1]+temp[0]+temp[1]);
         return time[2]+time[0]+time[1]+temp[0]+temp[1];
    }
}
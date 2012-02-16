package xmlparsing;

import java.util.ArrayList;
import java.util.List;

import org.jdom.Element;
import util.Event;


public class DukeBasketBallParser extends CalParser{
	public  boolean isThisKindof()
	{ 
		
		if(fileName.equals("resources/DukeBasketBall.xml"))
		return true;
	
		else
			
		return false;
	}

	public ArrayList<Event> parseEvent(Element root){
		List<Element> events = root.getChildren("Calendar"); 
		ArrayList<Event> filterEvents  = new ArrayList<Event>();
		for(int i = 0; i <events.size(); i++){
			Event event = new Event();
			String title = events.get(i).getChildText("Subject");
			event.setTitle(title);
			
            String time[] = events.get(i).getChildText("StartDate").split("/");
            for(int j = 0 ; j < 2 ; j++){
				if(time[j].length() == 1){
					time[j] = "0" + time[j];
				}
			}
			
			String timeStamp = time[2]+time[0]+time[1];
			
			event.settimeStamp(timeStamp);
			
			String starttime = events.get(i).getChildText("StartTime") +
					           " " + events.get(i).getChildText("StartDate");
			event.setStartTime(starttime);
			
			String endtime = events.get(i).getChildText("EndTime") +
			                 " " + events.get(i).getChildText("EndDate");
			event.setEndTime(endtime);
			
			String link = events.get(i).getChildText("Description");
			link = link.substring(link.indexOf("http"));
			event.setLink(link);
			
			filterEvents.add(event);
		}
		return filterEvents;
	}


}

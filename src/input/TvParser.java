package input;

import java.util.ArrayList;
import java.util.List;

import org.jdom.Element;

public class TvParser extends CalParser{

	@Override
	public boolean isThisKindof() {
		 if (fileName.equals("resources/tv.xml"))
	            return true;

	        else

	            return false;
	}

	@Override
	public ArrayList<Event> parseEvent(Element root) {
	 
		    List<Element> events = root.getChildren("prgramme");
	  
	        ArrayList<Event> filterEvents = new ArrayList<Event>();

	        for (int i = 0; i < events.size(); i++) {

	            String title = events.get(i).getChildText("title");
	            String starttime = events.get(i).getAttributeValue("start").substring(0,12);
	            String endtime = events.get(i).getAttributeValue("end").substring(0,12);
	            String link = null;
	            StringBuilder str = new StringBuilder();
	            str.append(events.get(i).getChildText("descr")+" ActorList: ");
	            ArrayList<Element>actorlist = (ArrayList<Element>) events.get(i).getChildren("actor");
	            for(int j = 0; j< actorlist.size(); j++)
	            {
	            	if(j==actorlist.size()-1)
	            		str.append(actorlist.get(i).getText()+".");
	            		
	            	else
	            	
	            		str.append(actorlist.get(i).getText()+" ");
	            }
	            Event event = new Event(title,starttime,endtime,link,str.toString());
	            filterEvents.add(event);

	        }
		return null;
	}

}

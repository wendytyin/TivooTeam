package input;


import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;

public class NFLParser extends CalParser{
	
	public boolean isThisKindof() {
		return (fileName.equals("resources/NFL.xml"));
	}

		public ArrayList<Event> parseEvent(Element root){
			List<Element> events = root.getChildren("row"); 
			ArrayList<Event> filterEvents  = new ArrayList<Event>();
			for(int i = 0; i <events.size(); i++){
				String title = events.get(i).getChildText("Col1");			

				String link = events.get(i).getChildText("Col2");
				String starttime = genTimeStamp(events.get(i).getChildText("Col8"));
				String endtime = genTimeStamp(events.get(i).getChildText("Col9"));
                Event event = new Event(title,starttime,endtime,link,"");
                filterEvents.add(event);
			}
			return filterEvents;
		}
	
      private String genTimeStamp(String time)
      {
    	  String[] temp = time.split(" ");
    	  String date = temp[0].replace("-","");
    	  temp = temp[1].split(":");
    	  temp[0] = Integer.toString(Integer.parseInt(temp[0])+12);
    	  return date+temp[0]+temp[1];
      }



}



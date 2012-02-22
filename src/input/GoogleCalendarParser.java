package input;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jdom.Element;

public class GoogleCalendarParser extends CalParser {

    @Override
    public boolean isThisKindof() {
        if (fileName.equals("resources/googlecal.xml"))
            return true;

        else

            return false;
    }

	public ArrayList<Event> parseEvent(Element root) {
//        CalendarUtil cal = new CalendarUtil();
  
    	List<Element> events = root.getChildren();
        
        List<Element> entry = forEntry(events); 
        
        ArrayList<Event> filterEvents = new ArrayList<Event>();

        for (int i = 0; i < entry.size(); i++) {

        	List<String> eventComponent = new ArrayList<String>();
            Map<String, Element> componentMap = forComponent(entry.get(i));

            String[] date = componentMap.get("content").getText().split(" ");

            StringBuilder str = new StringBuilder();

            if (date[0].equals("When:")) {
                date[3] = date[3].substring(0, date[3].length() - 1);
                str.append(date[4].substring(0, 4)).append(parseMonth(date[2]))
                        .append(parseDay(date[3]));
            }
            
            if(date[0].equals("Recurring"))
            {
            	String firstDateStamp = date[4].replaceAll("-", "");
                str = str.append(firstDateStamp);
            }
           
            String content = componentMap.get("content").getText();
            String startTime = null;
            if(content.indexOf("EDT")!=-1)
            {  startTime = content.substring(0, content.indexOf("EDT"));}
            else 
            	{startTime = content.substring(0, content.indexOf("<"));}

            eventComponent.add(componentMap.get("title").getText());
            eventComponent.add(startTime);
            eventComponent.add(null);
            eventComponent.add(str.toString());
            eventComponent.add(componentMap.get("id").getText());
            Event event = new Event(eventComponent);
            filterEvents.add(event);

        }
        return filterEvents;
    }
    
    private Map forComponent(Element entry)
    {
    	Map<String, Element> componentMap = new HashMap<String, Element>();
    	List<Element> entryChildren = entry.getChildren();
		for(Element e : entryChildren)
		{    
			if(e.getName().equals("content")){
				componentMap.put("content",e);
			}
			if(e.getName().equals("id"))componentMap.put("id",e);
			if(e.getName().equals("title"))componentMap.put("title",e);
				
		}
		return componentMap;
    }
 
	private List<Element> forEntry(List<Element> events) {
    	List<Element> entry = new ArrayList<Element>();
    	for(Element e : events)
        {
        	if(e.getName().equals("entry"))
        		entry.add(e);
        }
		return entry;
	}
	 
	private String parseMonth(String month) {
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

    private String parseDay(String day) {
        String we = day;
        int k = Integer.parseInt(day);
        if (k > 0 && k < 10)
            we = "0" + we;
        return we;

    }
//    
//    public int getweekday(String timeStamp)
//    {
//    	Calendar weeks=Calendar.getInstance();
//    	String year=timeStamp.substring(0, 4);
//        String month=timeStamp.substring(4, 6);
//        String day=timeStamp.substring(6, 8);
//        weeks.set(Integer.parseInt(year), Integer.parseInt(month)-1 ,Integer.parseInt(day));
//        return  weeks.get(Calendar.DAY_OF_WEEK);
//    }
    
}
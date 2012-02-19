package xmlparsing;

import java.util.ArrayList;
import java.util.List;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;

import util.Event;

/**
 * Note: new version. Old one didn't work at all
 * @author Shiyuan
 *
 */
public class GoogleCalendarParser extends CalParser {

    @Override
    public boolean isThisKindof() {
        if (fileName.equals("resources/googlecal.xml"))
            return true;

        else

            return false;
    }

    public ArrayList<Event> parseEvent(Element root) {
        List<Element> events = root.getChildren();
        
        List<Element> entry = forEntry(events); 
        
        ArrayList<Event> filterEvents = new ArrayList<Event>();

        for (int i = 0; i < entry.size(); i++) {

            Event event = new Event();
            Element specificElement=entry.get(i);
            String[] date = forContent(specificElement).getText().split(" ");

            StringBuilder str = new StringBuilder();

            //getting the date
            if (date[0].equals("When:")) { 
                date[3] = date[3].substring(0, date[3].length() - 1);
                str.append(date[4].substring(0, 4)).append(parseMonth(date[2]))
                        .append(parseDay(date[3]));
            }
            else if (date[0].equals("Recurring")){ //pasted in to take care of recurrence - will need to fix later --Wendy
                String[]newdate=date[3].split("-");
                date[3] = newdate[2].substring(0, newdate[2].length() - 1);
                str.append(newdate[0].substring(0, 4)).append(newdate[1])
                        .append(parseDay(date[3]));
            }

            event.setTitle(forTitle(specificElement).getText());
            //System.out.println(forTitle(entry.get(i)).getText());
            String content = forContent(specificElement).getText();
            String startTime = null;
            if(content.indexOf("EDT")!=-1)
            {  startTime = content.substring(0, content.indexOf("EDT"));}
            else 
            	{startTime = content.substring(0, content.indexOf("Event"));}
      
            event.setStartTime(startTime);
            event.setEndTime(null);
            event.setLink(forId(specificElement).getText());
            event.settimeStamp(str.toString());
            filterEvents.add(event);

        }
        return filterEvents;
    }

    private Element forContent(Element entry) {
		List<Element> entryChildren = entry.getChildren();
		for(Element e : entryChildren)
		{
			if(e.getName().equals("content"))
				return e;
		}
		return null;
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
	
	 private Element forTitle(Element entry) {
			List<Element> entryChildren = entry.getChildren();
			for(Element e : entryChildren)
			{
				if(e.getName().equals("title"))
					return e;
			}
			return null;
		}
	 
	 private Element forId(Element entry) {
			List<Element> entryChildren = entry.getChildren();
			for(Element e : entryChildren)
			{
				if(e.getName().equals("id"))
					return e;
			}
			return null;
		}
	 
	 //TODO: fix so that if month is already in format of a number, it doesn't crash
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

	//adds a zero to the beginning if a one-digit day
    public String parseDay(String day) {
        String we = day;
        int k = Integer.parseInt(day);
        if (k > 0 && k < 10)
            we = "0" + we;
        return we;

    }

}
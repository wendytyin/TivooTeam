package input;

import java.util.Set;

public class DukeCalEvent extends Event{

	public DukeCalEvent(String otitle, String start, String end, String url,
			String descri,Set<String> tagSet) {
		super(otitle, start, end, url, descri,tagSet);
	}
	public boolean isThisKindOfEvent(String type)
	{
		
		
		return type.equals("DukeCalEvent");
	}
    
}
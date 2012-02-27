package processor;

import input.Event; 
import java.util.*;

import org.jdom.JDOMException;

public class DetailFilter implements FilterComponent{

    public List<Event> filter(List<Event>events, String[] details) {
        ArrayList<Event>filtEvents=new ArrayList<Event>();
        for (Event i : events) {
        	for(String detail : details){
        		if(i.getDetail().contains(detail)){
        			filtEvents.add(i); 
        		}        		
        	}
        }
        return filtEvents;
    }
}
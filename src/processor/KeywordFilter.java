package processor;

import input.Event; 
import java.util.ArrayList;
import java.util.List;
import java.util.*;

import org.jdom.JDOMException;

public class KeywordFilter implements FilterComponent{

    public List<Event> filter(List<Event>events, String[] keyWords) {
        ArrayList<Event>filtEvents=new ArrayList<Event>();
        for (Event i : events) {
        	for( String keyWord : keyWords){
        		if (keyWord.startsWith("-")){ // for not a keyword, we can enter keyword in main with - first. EX. -Ball = not Ball
        			filtEvents.add(i);
        			if(i.getTitle().contains(keyWord)){
        				filtEvents.remove(i);
        			}
        		}
        		else if (i.getTitle().contains(keyWord)) {
        			filtEvents.add(i);
        			i.stringOutput();
        		}
        	}
        }
        return filtEvents;
    }
    

}
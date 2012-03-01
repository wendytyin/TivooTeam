package processor;

import input.Event; 
import java.util.*;

import org.jdom.JDOMException;

public class DetailFilter extends FilterComponent{

	 @Override
	    boolean filterSpecificEvent(Event i, String keyWord) {
	        return(i.getDetail().contains(keyWord));
	    }
	}
package output;

import input.Event;

import java.text.SimpleDateFormat;
import java.util.List;

import com.hp.gagawa.java.Node;
import com.hp.gagawa.java.elements.Body;
import com.hp.gagawa.java.elements.Html;
import com.hp.gagawa.java.elements.Td;
import com.hp.gagawa.java.elements.Tr;

public abstract class CalendarPage extends HtmlPageWriters{

    protected String myDate=null;
    protected final SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
            "yyyyMMdd");
    
    public CalendarPage(String title, Body body,Node o) {
        super(title, body,o);
    }
    public void write(List<Event>events,String date){
        myDate=date;
        super.write(events);
    }
    
    protected List<Event> additionalFilter(List<Event>events){
        return filterByDate(events,myDate);
    }
    
    protected abstract List<Event> filterByDate(List<Event> events, String date);

    protected Node writeDaysOfWeekHeader() {
        Tr tr = new Tr();
        tr.appendChild(new Td().appendText("Sunday"));
        tr.appendChild(new Td().appendText("Monday"));
        tr.appendChild(new Td().appendText("Tuesday"));
        tr.appendChild(new Td().appendText("Wednesday"));
        tr.appendChild(new Td().appendText("Thursday"));
        tr.appendChild(new Td().appendText("Friday"));
        tr.appendChild(new Td().appendText("Saturday"));
        return tr;
    }
}

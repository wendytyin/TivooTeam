package gui;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import output.*;

import processor.FilterComponent;
import processor.Sorters;

import input.CalParser;
import input.CalendarUtil;
import input.Event;

public class XMLModel {
    private static final HashMap<String, HtmlPageWriters> HTML = new HashMap<String, HtmlPageWriters>();
    static {
        HTML.put("Calendar Day", new CalendarDayPage());
        HTML.put("Calendar Day", new CalendarMonthPage());
        HTML.put("Calendar Day", new CalendarWeekPage());
        HTML.put("Calendar Day", new ConflictsPage());
        HTML.put("Calendar Day", new SummaryListPage());
    }

    private List<Event> origEvents;
    private List<Event> events;
    private CalendarUtil util;
    private GuiView guo;

    public XMLModel() {
        util = new CalendarUtil();
        origEvents = new ArrayList<Event>();
        guo=new GuiView();
    }

    public void parseFiles(File[] filenames) {
        CalParser prev = null;
        CalParser s;
        for (File file : filenames) {
            s = util.chooseParser(file.getName());
            if (prev != null) {
                events = s.mergeparser(prev);
            } else {
                events = s.parser();
            }
            prev = s;
        }
        origEvents.addAll(events); // make a copy of the original list
    }

    public void applyFilter(FilterComponent f, String[] keyword) {
        events = f.filter(events, keyword);
    }

    public void applySort(Sorters s) {
        events = s.sort(events);
    }

    public void applyReverseSort(Sorters s) {
        events = s.reverseSort(events);
    }

    public void resetAllFilters() {
        events.clear();
        events.addAll(origEvents);
    }

    public void showFileOfType(String type, String opt) {
        HtmlPageWriters w = findHtmlWriter(type);
        writeToFileOfType(w, opt);
        //TODO: somehow get the file pathway that it was written to
        updateGui(uri);
    }

    private void writeToFileOfType(HtmlPageWriters w, String opt) {
        w.write(events, opt);
    }

    private HtmlPageWriters findHtmlWriter(String type) {
        return HTML.get(type);
    }
    public void updateGui(String uri){
        guo.updateView(uri);
    }
}

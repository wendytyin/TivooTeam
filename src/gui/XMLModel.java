package gui;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import output.*;
import processor.*;

import processor.FilterComponent;
import processor.Sorters;

import input.CalParser;
import input.CalendarUtil;
import input.Event;

public class XMLModel {
    private final HashMap<String, HtmlPageWriters> HTML = new HashMap<String, HtmlPageWriters>();
    private final HashMap<String, FilterComponent> FILTERS = new HashMap<String, FilterComponent>();
    private final HashMap<String, Sorters> SORTS = new HashMap<String, Sorters>();
    {
        HTML.put("Calendar Day", new CalendarDayPage());
        HTML.put("Month View", new CalendarMonthPage());
        HTML.put("Calendar Week", new CalendarWeekPage());
        HTML.put("Conflicts", new ConflictsPage());
        HTML.put("Summary", new SummaryListPage());

        FILTERS.put("Filter By Title", new KeywordFilter());
        FILTERS.put("Filter By Details", new DetailFilter());
        FILTERS.put("Filter By Timestamp", new TimeFilter());
        FILTERS.put("Exclude Keyword", new ExcludeFilter());
        FILTERS.put("Filter By Range of Time", new RangeOfDatesTimeFilter());
        FILTERS.put("Filter By Tags", new TagFilter());
        FILTERS.put("Filter By XML File, Keyword in specific tag",
                new SpecificXMLFilter());

        SORTS.put("Sort by start time", new SortByStartDate());
        SORTS.put("Sort by end time", new SortByEndDate());
        SORTS.put("Sort by title", new SortByTitle());
    }

    private List<Event> origEvents;
    private List<Event> events;
    private CalendarUtil util;
    private GuiView guiObserver;

    public XMLModel() {
        util = new CalendarUtil();
        origEvents = new ArrayList<Event>();
        guiObserver = new GuiView(this);
    }

    public void parseFiles(File[] filenames) {
        origEvents.clear();
        String[] files = new String[filenames.length];
        for (int i = 0; i < filenames.length; i++) {
            files[i] = "resources/" + filenames[i].getName();
        }
        events = util.parseFiles(files);
        origEvents.addAll(events); // make a copy of the original list
    }

    public void applyFilter(String s, String[] keyword) {
        FilterComponent f=FILTERS.get(s);
        events = f.filter(events, keyword);
    }

    public void applySort(String s, String keyword) {
        Sorters sort=SORTS.get(s);
        if (keyword.equals("descending")){
            events = sort.reverseSort(events);
        }
        events = sort.sort(events);
    }

    public void resetAllFilters() {
        events.clear();
        events.addAll(origEvents);
    }

    public void showFileOfType(String type) throws Exception {
        HtmlPageWriters w = findHtmlWriter(type);
        writeToFileOfType(w);

        File f = new File(w.getFileName());
        if (f.exists()) {
            String uri = f.toURI().toString();
            notifyGui(uri);
        } else {
            throw new Exception("wrote to file " + w.getFileName()
                    + " yet that file doesn't exist");
        }
    }

    private void writeToFileOfType(HtmlPageWriters w) {
        w.write(events);
    }

    private HtmlPageWriters findHtmlWriter(String type) {
        return HTML.get(type);
    }

    public void notifyGui(String uri) {
        guiObserver.updateView(uri);
    }
}

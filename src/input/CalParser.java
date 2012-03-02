package input;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;

public abstract class CalParser {

	protected String fileName;
	private List<Event> events;

	public CalParser loadFile(String name) {
		fileName = name;
		return this;
	}

	public abstract boolean isThisKindof();

	public abstract ArrayList<Event> parseEvent(Element root);

	public List<Event> parser() {
		File inputXml = new File(fileName);
		SAXBuilder saxBuilder = new SAXBuilder();
		events = new ArrayList<Event>();
		try {
			Document document = saxBuilder.build(inputXml);
			events.addAll(parseEvent(document.getRootElement()));
		} catch (JDOMException e) {
			System.err.println("Problem building parsed document");
			e.printStackTrace();
		} catch (IOException e) {
			System.err.println("Problem opening file " + fileName);
			e.printStackTrace();
		}
		return events;
	}
    
	protected Set getTags(Element i, Set<String> tagSet)
	{
		if(i.getChildren().size()==0)
			tagSet.add(i.getName());
		else
		{   
			tagSet.add(i.getName());
			List<Element> children = i.getChildren();
			for(Element child: children)
			{
				tagSet = getTags(child,tagSet);
			}
		}
	return tagSet;
	}
	// public List<Event> mergeparser(CalParser other)
	// {
	// List<Event> newList = getEvents();
	// newList.addAll(other.getEvents());
	// return newList;
	// }
	//
	// private List<Event> getEvents()
	// {
	// return events;
	// }

	/*
	 * public static List<Event> mergeparser() { loadParser(); ArrayList<Event>
	 * events = new ArrayList<Event>(); for(CalParser parser: parserList) {
	 * events.addAll(parser.parser()); } return events; } public static void
	 * loadParser() { parserList = new ArrayList<CalParser>();
	 * parserList.add(new
	 * DukeCalendarParser().loadFile("resources/dukecal.xml"));
	 * parserList.add(new
	 * GoogleCalendarParser().loadFile("resources/googlecal.xml"));
	 * parserList.add(new
	 * DukeBasketBallParser().loadFile("resources/DukeBasketBall.xml"));
	 * parserList.add(new NFLParser().loadFile("resources/NFL.xml")); //
	 * parserList.add(new TvParser().loadFile("resources/tv.xml")); // Tv.xml
	 * can not be loaded currently }
	 */

}
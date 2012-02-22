package input;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;

public abstract class CalParser {

	protected String fileName;
	private String keyWord; // What are these?
	private Stack<Element> refinedEvents; // What are these?

	public CalParser loadFile(String name) {
		fileName = name;
		return this;
	}

	public abstract boolean isThisKindof();

	public abstract ArrayList<Event> parseEvent(Element root);

	// WHAT ABOUT ATTRIBUTES? -Wendy

	public List<Event> parser() {
		File inputXml = new File(fileName);
		SAXBuilder saxBuilder = new SAXBuilder();
		ArrayList<Event> events = new ArrayList<Event>();
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
}

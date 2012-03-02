package processor;

import java.util.Map;

import input.Event;

import org.jdom.JDOMException;

/**
 * Only filters events coming from specific xml file, will return others with no
 * filtering.
 * 
 * @author Wendy
 * 
 */
public class SpecificXMLFilter extends FilterComponent {
    @Override
    boolean filterSpecificEvent(Event i, String keyWord) {
        String[] keys = keyWord.split("-");
        System.out.println(keys.toString());
        if (keys.length != 3) {
            throw new Error(
                    "Did not pass in proper details for filterSpecificEvent. Must be of format XML-TAG-TERM");
        }
        Map<String, String> subTags = i.getSubTags();
        return (checkXML(i, keys[0])) && (checkTag(subTags, keys[1]))
                && (checkKeyword(subTags, keys[1], keys[2]));

    }

    private boolean checkXML(Event i, String key) {
        return (i.isThisKindOfEvent(key)) || (key.equals("*"));
    }

    private boolean checkTag(Map<String, String> subTags, String key) {
        return (subTags.containsKey(key)) || (key.equals("*"));
    }

    private boolean checkKeyword(Map<String, String> subTags, String key,
            String val) {
        return (subTags.get(key).contains(val)) || (val.equals("*"));
    }
}
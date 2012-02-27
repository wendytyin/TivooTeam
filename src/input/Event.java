package input;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;


public class Event {
	private String title;
	private String starttime;
	private String endtime;
	private String link;
	private String detail;
	private ArrayList<Integer> compareInfo;

	public Event(String otitle, String start, String end, String url,
			String descri) {
		title = otitle;
		starttime = start;
		endtime = end;
		link = url;
		detail = descri;
	}

	public void stringOutput() {
		System.out.println("Title : " + title);

		System.out.println("Start Time : " + starttime);

		System.out.println("EndTime : " + endtime);

		System.out.println("Description : " + detail);

		System.out.println("Link : " + link);

		System.out
				.println("---------------------------------------------------------------------------------");
	}

	public String getNameForFile(){
	    String[]words=title.split("\\W");
	    return starttime+"_"+words[0];
	}
	public String getTitle() {
		return title;
	}

	public String getStartTime() {
		return starttime;
	}

	public String getEndTime() {
		return endtime;
	}
    
    public String getFormattedStartTime() {
        String year = starttime.substring(0, 4);
        String month = starttime.substring(4, 6);
        String day = starttime.substring(6, 8);
        String hour = starttime.substring(8, 10);
        String min = starttime.substring(10, 12);
        return year+"/"+month+"/"+day+" "+hour+":"+min;
    }

    public String getFormattedEndTime() {
        String year = endtime.substring(0, 4);
        String month = endtime.substring(4, 6);
        String day = endtime.substring(6, 8);
        String hour = endtime.substring(8, 10);
        String min = endtime.substring(10, 12);
        return year+"/"+month+"/"+day+" "+hour+":"+min;
    }
    
	public String getLink() {
		return link;
	}

	public String getDetail() {
		return detail;
	}

	public int getDayOfWeek() {
		Calendar weeks = Calendar.getInstance();
		String time1 = getStartTime();
		String year = time1.substring(0, 4);
		String month = time1.substring(4, 6);
		String day = time1.substring(6, 8);
		weeks.set(Integer.parseInt(year), Integer.parseInt(month) - 1,
				Integer.parseInt(day));

		int weekday = weeks.get(Calendar.DAY_OF_WEEK);
		return weekday;
	}

	/**
	 * sets compareInfo to values corresponding to the string comparisons for 
	 * [title,start time, end time]
	 */
	public void getCompareInfo(Event o1, Event o2) {
		compareInfo = new ArrayList<Integer>();
		compareInfo.add(((Event) o1).getTitle().compareTo(
				(((Event) o2).getTitle())));
		compareInfo.add(((Event) o1).getStartTime().compareTo(
				(((Event) o2).getStartTime())));
		compareInfo.add(((Event) o1).getEndTime().compareTo(
				(((Event) o2).getEndTime())));
	}

	public class StartTimeComparator implements Comparator {
		@Override
		// StartTime is primary concern, then title, then endtime
		public int compare(Object o1, Object o2) {
			getCompareInfo((Event) o1, (Event) o2);
			return getCompareResult(compareInfo.get(1), compareInfo.get(0),
					compareInfo.get(2));

		}
	}

	public class EndTimeComparator implements Comparator {

		@Override
		// EndTime is primary concern, then title, then start time
		public int compare(Object o1, Object o2) {
			getCompareInfo((Event) o1, (Event) o2);
			return getCompareResult(compareInfo.get(2), compareInfo.get(0),
					compareInfo.get(1));
		}
	}

	public class TitleComparator implements Comparator {
		@Override
		// Title is primary concern, then StartTime, then End time
		public int compare(Object o1, Object o2) {
			getCompareInfo((Event) o1, (Event) o2);
			return getCompareResult(compareInfo.get(0), compareInfo.get(1),
					compareInfo.get(2));
		}

	}

    public int getCompareResult(int firstConcern, int secondConcern,
            int thirdConcern) {
        if (firstConcern==0){
            if (secondConcern == 0) {
                return thirdConcern;
            } else {
                return secondConcern;
            }
        }
        return firstConcern;
    }


	/**
	 * @return -1 if time2 less than time1, 0 if =, 1 if time2 greater than time1
	 */
	public int toCompare(String time1, String time2) {
		Calendar week1 = Calendar.getInstance();
		Calendar week2 = Calendar.getInstance();
		String year = time1.substring(0, 4);
		String month = time1.substring(4, 6);
		String day = time1.substring(6, 8);
        String hour = time1.substring(8, 10);
        String min = time1.substring(10,12);
		week1.set(Integer.parseInt(year), Integer.parseInt(month) - 1,
				Integer.parseInt(day),Integer.parseInt(hour),Integer.parseInt(min));
		String year2 = time2.substring(0, 4);
		String month2 = time2.substring(4, 6);
		String day2 = time2.substring(6, 8);
        String hour2 = time2.substring(8, 10);
        String min2 = time2.substring(10,12);
		week2.set(Integer.parseInt(year2), Integer.parseInt(month2) - 1,
				Integer.parseInt(day2),Integer.parseInt(hour2),Integer.parseInt(min2));
		return week2.compareTo(week1);
	}
}

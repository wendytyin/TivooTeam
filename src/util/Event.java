package util;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

//from shawn, beta
public class Event implements Comparable<Event>{
     private String title;
     private String starttime;
     private String endtime;
     private String timeStamp;
     private String link;
     
     public Event() {

	}

	public void stringOutput(){
    	 System.out.println("Title : " + title);

    	 System.out.println("Start Time : " + starttime);

    	 System.out.println("EndTime : " + endtime);
    	 
    	 System.out.println("Time Stamp: " + timeStamp);

    	 System.out.println("Link : " + link);
    	 
    	 System.out.println("---------------------------------------------------------------------------------");
     }
     
     public String getTitle(){
    	 return title;
     }
 
     
     public String getStartTime(){
    	 return starttime;
     }
     
     public String getEndTime(){
    	 return endtime;
     }
     
     public String getLink(){
    	 return link;
     }
     
     public String gettimeStamp(){
    	 return timeStamp;
     }
     
     public void setTitle(String mytitle){
    	 this.title = mytitle;
     }
     
     public void settimeStamp(String myTimeStamp){
    	 this.timeStamp= myTimeStamp;
     }
     
    
     public void setStartTime(String myStartTime){
    	 this.starttime = myStartTime;
     }
     
     public void setEndTime(String myEndTime){
    	 this.endtime = myEndTime;
     }
   
     
     public void setLink(String myLink){
    	 this.link = myLink;
     }

    public int getDayOfWeek() {
        Calendar weeks=Calendar.getInstance();
        
        String rawdate = getStartTime();
        String[] mdy = rawdate.split("/");
        int date = Integer.parseInt(mdy[0].substring(8));
        weeks.set(Integer.parseInt(mdy[2]), Integer.parseInt(mdy[1]), date);
        
        int weekday = weeks.get(Calendar.DAY_OF_WEEK);
        return weekday;
    }

    @Override
    public int compareTo(Event o) {
        Calendar week1=Calendar.getInstance();
        Calendar week2=Calendar.getInstance();
        
        //this is you
        String rawdate = getStartTime();
        String[] mdy = rawdate.split("/");
        int date = Integer.parseInt(mdy[0].substring(8));
        week1.set(Integer.parseInt(mdy[2]), Integer.parseInt(mdy[1]), date);
        
        //this is me
        String rawdate2 = getStartTime();
        String[] mdy2 = rawdate2.split("/");
        int date2 = Integer.parseInt(mdy2[0].substring(8));
        week2.set(Integer.parseInt(mdy2[2]), Integer.parseInt(mdy2[1]), date2);
        
        return week1.compareTo(week2);
    }

    private static Date parseStringYMdToDate(String date) {
        Date output = null;
        try {
            output = CalendarUtil.simpleDateFormat.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
        return output;
    }
    
    
     
     
}


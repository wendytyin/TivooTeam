package util;

//from shawn, beta
public class Event {
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
     
     
}


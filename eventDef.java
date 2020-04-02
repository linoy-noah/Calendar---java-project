package il.ac.shenkar.final_project.calender;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**eventDef
 * a class that present our event item
 * @author Linoy Noah and Ido Kilker
 */
 public class eventDef{
     int id;
    String eventName;
    Date startDate;
    Date endDate;
    double time;
    String description;

    /**
     * set id by int
     * @param t id
     */
    public void setId(int t){
    this.id=t;
}

    /**
     *
     * @return id value(int)
     */
     public int getId() {
         return id;
     }

     public eventDef(String event) {
        String[] eventDetails= event.split(",",6);
        setId(Integer.parseInt(eventDetails[0]));
        setEventName(eventDetails[1]);
        setStartDate(eventDetails[2]);
        setEndDate(eventDetails[3]);
        setTime(eventDetails[4]);
        setDescription(eventDetails[5]);
    }
     public eventDef(String event,int i) {
         String[] eventDetails= event.split(",",5);
         setEventName(eventDetails[0]);
         setStartDate(eventDetails[1]);
         setEndDate(eventDetails[2]);
         setTime(eventDetails[3]);
         setDescription(eventDetails[4]);
     }


    public eventDef(String eventName, Date startDate, Date endDate, double time, String description) {
        setEventName(eventName);
        setStartDate(startDate);
        setEndDate(endDate);
        setTime(time);
        setDescription(description);
    }

    /**
     * get event name
     * @return name of the event
     */
    public String getEventName() {
        return eventName;
    }

    /**
     * set event name
     * @param eventName
     */
    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    /**
     *
     * @return start date of the event
     */
    public Date getStartDate() {
        return startDate;
    }

    /**
     * set start date by date
     * @param startDate
     */
    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    /**
     * set start date by string
     * @param sDate
     */
    public void setStartDate(String sDate)  {
        Date t=StringToDate(sDate);
        if(t!=null){
        setStartDate(t);
    }
}

    /**
     * convert string to date
     * @param sDate
     * @return
     */
    private Date StringToDate(String sDate){
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-mm-dd");
        Date t;
        try{
            t=formatter.parse(sDate);
        }catch(ParseException e){
            return null;
        }
        return t;
    }

    /**
     * convert date to string in the format yyyy-mm-dd
     * @param e date
     * @return string of date in format yyyy-mm-dd
     */
    public String DateToString(Date e){
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-mm-dd");
        String strDate = formatter.format(e);
        return  strDate;
    }

    /**
     * set end date by string
     * @param eDate
     */
    public void setEndDate(String eDate)  {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-mm-dd");
        Date t;
        try{
            t=formatter.parse(eDate);
        }catch(ParseException e){
            return;
        }
        setEndDate(t);
    }

    /**
     *
     * @return end date
     */
    public Date getEndDate() {
        return endDate;
    }

    /**
     * set end date by date
     * @param endDate
     */
    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    /**
     *
     * @return string that present the event time in format hh:mm
     */
    public String getTimeS() {
        String t;
        if(this.time%1==0){t=(int)this.time+":00";}
        else{t=(int)this.time+":30";}
        return t;
    }
    public double getTimeD(){
        return this.time;
    }

    /**
     * set time by double
     * @param time
     */
    public void setTime(double time) {
        if( time%1!=0){
            time= time-(time%1) +0.5;
        }
        if(time>23.5||time<0){return;}
        else {this.time = time;}
    }

    /**
     * set time by string
     * @param time
     */
    public void setTime(String time) {
        setTime(stringToTime(time));
    }

    /**
     * convert string to double
     * @param time string that present the time in format hh:mm
     * @return double value that present the time in the database
     */
    private double stringToTime(String time){
        String[] t =time.split(":");
        double i= Integer.parseInt(t[0]);
        if(t[1]=="30")
        { i=i+0.5;}
        return i;
    }

    /**
     *
     * @return Description
     */
    public String getDescription() {
        return description;
    }

    /**
     * set Description
     * @param description of event
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * convert event to string[]
     * @return string[] that present the event
     */
    public String[] toStringA() {
         String i=String.valueOf(getId());
        String[] t= {i ,eventName,DateToString(startDate),DateToString(endDate),getTimeS(),description};
        return t;
    }

    /**
     * convert the event to string that fit the data insert
     * @return string that fit the data insert that present the event
     */
     public String insert() {
         return "'" + eventName +
                 "', '" + DateToString(startDate) +
                 "', '" +DateToString(endDate) +
                 "', "  + time +
                 ", '" + description + "'";
     }

    /**
     * get the primary key of the event
     * @return id (int) primary key in database
     */
    public int toKey() {
        return id;
    }

}

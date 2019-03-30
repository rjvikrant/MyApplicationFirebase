package vikrant.com.myapplicationfirebase;

import android.support.annotation.NonNull;

public  class EventPOJO implements Comparable<EventPOJO> {
    String EventAgenda;
    long Date;
    String Time;
    String Emails;

    public EventPOJO() {
    }

    public EventPOJO(String eventName, long date, String time, String emails) {
        EventAgenda = eventName;
        Date = date;
        Time = time;
        Emails = emails;
    }

    public String getEventAgenda() {
        return EventAgenda;
    }

    public void setEventAgenda(String eventAgenda) {
        EventAgenda = eventAgenda;
    }

    public long getDate() {
        return Date;
    }

    public void setDate(long date) {
        Date = date;
    }

    public String getTime() {
        return Time;
    }

    public void setTime(String time) {
        Time = time;
    }

    public String getEmails() {
        return Emails;
    }

    public void setEmails(String emails) {
        Emails = emails;
    }


    @Override
    public int compareTo(@NonNull EventPOJO o) {

        long compareage=((EventPOJO)o).getDate();
        /* For Ascending order*/
        return (int) (this.getDate()-compareage);

    }
}

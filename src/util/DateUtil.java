package util;

import edu.utdallas.csmc.web.model.session.SessionTimeSlot;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;

import static java.util.Calendar.*;

public class DateUtil {

    public static Date trimDate(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(HOUR_OF_DAY, 0);
        cal.set(MINUTE, 0);
        cal.set(SECOND, 0);
        cal.set(MILLISECOND, 0);
        return cal.getTime();
    }

    public static int compareTimeWithCurrentTime(Date date1) {
        if (date1.before(new Date())) {
            return -1;
        }
        return 1;
    }

    public static boolean isStartTimeBeforeCurrentTime(Date startTime){
        Date currTime = new  Date();
        if(startTime.before(currTime) || startTime.compareTo(currTime)==0){
            return true;
        }else {
            return false;
        }
    }

    public static DateFormat dateFormatTo12Hours = new SimpleDateFormat("MM/dd/yy hh:mm a");
}

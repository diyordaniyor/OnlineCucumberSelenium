package com.vytrack.utilities;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public class DateTimeUtilities {


    public static String getCurrentDate(String format) {
        return LocalDate.now().format(DateTimeFormatter.ofPattern(format));
    }

    /**
     * This metod will return difference between end and start time
     * @param start
     * @param end
     * @param format
     * @return
     */ //without static it was not visible in my test
    public static long getTimeDifference(String start, String end, String format){
        LocalTime startTime = LocalTime.parse(start, DateTimeFormatter.ofPattern(format));
        //we created an object of LocalTime
        //convert string into local time object
        //and the parse it
        //to parse we have to know the format

        LocalTime endTime = LocalTime.parse(end, DateTimeFormatter.ofPattern(format));
        return ChronoUnit.HOURS.between(startTime, endTime);
        //ChronoUnit class will provide the difference between time and date
    }


}

package ua.sumdu.j2se.innapolushkina.tasks;

import javafx.scene.Node;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.regex.Pattern;

/**
 * This is the class which contains helping methods.
 * */
public class Utill {

    public static final String dateFormate = "yyyy-MM-dd HH:mm:ss.SSS";
    public static final String dateCellFormate = "yyyy-MM-dd HH:mm";
    public static final String regexpRepetative = "^\"(.*)\" from \\[(\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2}\\.\\d{3})] to \\[(\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2}\\.\\d{3})] every \\[(.*)](.*)[.;]$";
    public static final String regexpNotRepetative = "^\"(.*)\" at \\[(\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2}\\.\\d{3})](.*)[.;]$";
    public static final SimpleDateFormat dateCellFormater = new SimpleDateFormat(dateCellFormate);
    public static final Pattern patternRepetative = Pattern.compile(regexpRepetative);
    public static final Pattern patternNotRepetative = Pattern.compile(regexpNotRepetative);
    public static final Pattern integerString = Pattern.compile("(^0$)|(^[1-9]{1}\\d*$)");


    public static int secondsToIntegerDays(int seconds) {
        if(seconds < 0){
            System.err.println(new StringBuilder("Variable seconds must not be less than 0. seconds = ").append(seconds));
            throw new IllegalArgumentException(new StringBuilder("Variable seconds must not be less than 0. seconds = ").append(seconds).toString());
        }
        return seconds / 86400;
    }


    public static int secondsToIntegerHours(int seconds) {
        if(seconds < 0){
            System.err.println(new StringBuilder("Variable seconds must not be less than 0. seconds = ").append(seconds));
            throw new IllegalArgumentException(new StringBuilder("Variable seconds must not be less than 0. seconds = ").append(seconds).toString());
        }
        return (seconds - secondsToIntegerDays(seconds) * 86400) / 3600;
    }


    public static int secondsToIntegerMinutes(int seconds) {
        if(seconds < 0){
            System.err.println(new StringBuilder("Variable seconds must not be less than 0. seconds = ").append(seconds));
            throw new IllegalArgumentException(new StringBuilder("Variable seconds must not be less than 0. seconds = ").append(seconds).toString());
        }
        return (seconds - secondsToIntegerDays(seconds) * 86400 - secondsToIntegerHours(seconds) * 3600) / 60;
    }


    public static void hideNodes(Node...nodes) {
        for (Node node : nodes){
            node.setVisible(false);
        }
    }


    public static void showNodes(Node...nodes) {
        for (Node node : nodes){
            node.setVisible(true);
        }
    }

    public static LocalDate dateToLocalDateTime(Date date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(dateFormate);
        SimpleDateFormat dateFormat = new SimpleDateFormat(dateFormate);
        return LocalDate.parse(dateFormat.format(date),formatter);
    }
    public static LocalTime dateToLocalTime(Date date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(dateFormate);
        SimpleDateFormat dateFormat = new SimpleDateFormat(dateFormate);
        return LocalTime.parse(dateFormat.format(date),formatter);
    }

    public static Date localDateTimeToDate(LocalDateTime localDateTime) {
        ZonedDateTime zonedDateTime = localDateTime.atZone(ZoneId.systemDefault());
        return Date.from(zonedDateTime.toInstant());
    }


    public static int getIntervalFromStrings(String days, String hours, String minutes) {
        return Integer.parseInt(days) * 86400 + Integer.parseInt(hours) * 3600 + Integer.parseInt(minutes) * 60;
    }

}
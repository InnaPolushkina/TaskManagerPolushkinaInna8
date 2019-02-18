package ua.sumdu.j2se.innapolushkina.tasks;

import javafx.scene.Node;
import org.apache.log4j.Logger;

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

    /**
     * the method convert integer seconds to integer days
     * @param seconds have seconds for converting
     * @return integer days
     */
    public static int secondsToIntegerDays(int seconds) {
        if(seconds < 0){
            System.err.println(new StringBuilder("Variable seconds must not be less than 0. seconds = ").append(seconds));
            throw new IllegalArgumentException(new StringBuilder("Variable seconds must not be less than 0. seconds = ").append(seconds).toString());
        }
        return seconds / 86400;
    }

    /**
     * the method convert integer seconds to integer hours
     * @param seconds have seconds for converting
     * @return integer hours
     */
    public static int secondsToIntegerHours(int seconds) {
        if(seconds < 0){
            System.err.println(new StringBuilder("Variable seconds must not be less than 0. seconds = ").append(seconds));
            throw new IllegalArgumentException(new StringBuilder("Variable seconds must not be less than 0. seconds = ").append(seconds).toString());
        }
        return (seconds - secondsToIntegerDays(seconds) * 86400) / 3600;
    }

    /**
     * the method convert integer seconds to integer minutes
     * @param seconds have integer seconds for converting
     * @return integer minutes
     */
    public static int secondsToIntegerMinutes(int seconds) {
        if(seconds < 0){
            System.err.println(new StringBuilder("Variable seconds must not be less than 0. seconds = ").append(seconds));
            throw new IllegalArgumentException(new StringBuilder("Variable seconds must not be less than 0. seconds = ").append(seconds).toString());
        }
        return (seconds - secondsToIntegerDays(seconds) * 86400 - secondsToIntegerHours(seconds) * 3600) / 60;
    }

    /**
     *the method hide nodes from view
     * @param nodes have nodes for hiding from view
     */
    public static void hideNodes(Node...nodes) {
        for (Node node : nodes){
            node.setVisible(false);
        }
    }

    /**
     * the method show nodes from view
     * @param nodes have nodes for showing from view
     */
    public static void showNodes(Node...nodes) {
        for (Node node : nodes){
            node.setVisible(true);
        }
    }

    /**
     * the method convert LocalDate value to Date value
     * @param date have date for converting
     * @return LocalDate date
     */
    public static LocalDate dateToLocalDateTime(Date date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(dateFormate);
        SimpleDateFormat dateFormat = new SimpleDateFormat(dateFormate);
        return LocalDate.parse(dateFormat.format(date),formatter);
    }

    /**
     * the method for convert LocalTime value to Date value
     * @param date have date for converting
     * @return LocalTime
     */
    public static LocalTime dateToLocalTime(Date date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(dateFormate);
        SimpleDateFormat dateFormat = new SimpleDateFormat(dateFormate);
        return LocalTime.parse(dateFormat.format(date),formatter);
    }

    /**
     * the method convert interval from String values to integer values
     * @param days
     * @param hours
     * @param minutes
     * @return integer interval values
     */
    public static int getIntervalFromStrings(String days, String hours, String minutes) {
        return Integer.parseInt(days) * 86400 + Integer.parseInt(hours) * 3600 + Integer.parseInt(minutes) * 60;
    }

}
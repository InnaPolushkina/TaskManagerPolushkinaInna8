package ua.sumdu.j2se.innapolushkina.tasks.model;

import java.util.Date;
import java.io.Serializable;
import java.text.ParseException;
import java.io.*;
import java.text.SimpleDateFormat;
import java.io.IOException;
import java.lang.String;
import java.util.regex.*;

/**
 * class TaskIO realizes methods for writing/reading tasks list to/from file of stream
 */
public class TaskIO implements Serializable,  Cloneable {
    public static final String dateFormate = "yyyy-MM-dd HH:mm:ss.SSS";
    public static final String regexpRepetative = "^\"(.*)\" from \\[(\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2}\\.\\d{3})] to \\[(\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2}\\.\\d{3})] every \\[(.*)](.*)[.;]$";
    public static final String regexpNotRepetative = "^\"(.*)\" at \\[(\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2}\\.\\d{3})](.*)[.;]$";
    public static final Pattern patternRepetative = Pattern.compile(regexpRepetative);
    public static final Pattern patternNotRepetative = Pattern.compile(regexpNotRepetative);

    /**
     * the method convert String value into integer interval
     * @param string have string for converting
     * @return integer interval
     */
    public static int parseInterval(String string){
        if (string == null){
            throw new IllegalArgumentException("The string must not be null");
        }
        int seconds = 0;
        Pattern pattern = Pattern.compile("(\\d*) day");
        Matcher matcher = pattern.matcher(string);
        if(matcher.find()){
            seconds += Integer.parseInt(matcher.group(1)) * 86400;
        }
        pattern = Pattern.compile("(\\d*) hour");
        matcher = pattern.matcher(string);
        if(matcher.find()){
            seconds += Integer.parseInt(matcher.group(1)) * 3600;
        }
        pattern = Pattern.compile("(\\d*) minute");
        matcher = pattern.matcher(string);
        if(matcher.find()){
            seconds += Integer.parseInt(matcher.group(1)) * 60;
        }
        pattern = Pattern.compile("(\\d*) second");
        matcher = pattern.matcher(string);
        if(matcher.find()){
            seconds += Integer.parseInt(matcher.group(1));
        }
        return seconds;
    }

    /**
     * the method write tasks from list into OutputStream in binary format
     * @param tasks list for writing
     * @param out stream for writing
     * @throws IOException if method can't write task into stream
     */
    public static void write(TaskList tasks, OutputStream out) throws IOException {
        DataOutputStream dataOutputStream = null;
        try{
            dataOutputStream = new DataOutputStream(out);
            dataOutputStream.writeInt(tasks.size());
            for(Task task : tasks){
                dataOutputStream.writeInt(task.getTitle().length());
                for(int i = 0; i < task.getTitle().length(); i++){
                    dataOutputStream.writeChar(task.getTitle().charAt(i));
                }
                dataOutputStream.writeBoolean(task.isActive());
                dataOutputStream.writeInt(task.getRepeatInterval());
                if(task.isRepeated()){
                    dataOutputStream.writeLong(task.getStartTime().getTime());
                    dataOutputStream.writeLong(task.getEndTime().getTime());
                }else{
                    dataOutputStream.writeLong(task.getTime().getTime());
                }
            }
        }finally {
            if(dataOutputStream != null){
                dataOutputStream.close();
            }
        }
    }

    /**
     * the method read tasks from InputStream in binary format
     * @param tasks list for reading
     * @param in stream for reading
     * @throws IOException if can't read tasks from stream
     */
    public static void read(TaskList tasks, InputStream in) throws IOException {
        DataInputStream dataInputStream = null;
        try {
            dataInputStream = new DataInputStream(in);
            int amOfTasks = dataInputStream.readInt();
            for (int i = 0; i < amOfTasks; i++) {
                int detailsLength = dataInputStream.readInt();
                StringBuilder taskDetails = new StringBuilder();
                for (int j = 0; j < detailsLength; j++) {
                    taskDetails.append(dataInputStream.readChar());
                }
                boolean isActive = dataInputStream.readBoolean();
                int interval = dataInputStream.readInt();
                if (interval != 0) {
                    long startTime = dataInputStream.readLong();
                    long endTime = dataInputStream.readLong();
                    Task task = new Task(taskDetails.toString(), new Date(startTime), new Date(endTime), interval);
                    task.setActive(isActive);
                    tasks.add(task);
                } else {
                    long time = dataInputStream.readLong();
                    Task task = new Task(taskDetails.toString(), new Date(time));
                    task.setActive(isActive);
                    tasks.add(task);
                }
            }
        } finally {
            if (dataInputStream != null) {
                dataInputStream.close();
            }
            in.close();
        }
    }
    /**
     * the method write tasks from list into File in binary format
     * @param tasks list for writing
     * @param file for writing
     * @throws IOException if method can't write task into file
     */
    public static void writeBinary(TaskList tasks, File file) throws IOException{
        BufferedOutputStream bufferedOutputStream = null;
        try{
            bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(file));
            write(tasks,bufferedOutputStream);
        }finally {
            if(bufferedOutputStream != null){
                bufferedOutputStream.close();
            }
        }
    }
    /**
     * the method read tasks from File in binary format
     * @param tasks list for reading
     * @param file file for reading
     * @throws IOException if can't read tasks from file
     */
    public static void readBinary(TaskList tasks, File file) throws IOException{
        FileInputStream fileInputStream = null;
        try{
            fileInputStream = new FileInputStream(file);
            read(tasks,fileInputStream);
        }finally {
            if(fileInputStream != null){
                fileInputStream.close();
            }
        }
    }
    /**
     * the method write tasks from list into Writer in text format
     * @param tasks list for writing
     * @param out writer for writing
     * @throws IOException if method can't write task into stream
     */
    public static void write(TaskList tasks, Writer out) throws IOException{
        PrintWriter printWriter = null;
        try {
            printWriter = new PrintWriter(out);
            printWriter.write(tasks.toString());
        }finally {
            if(printWriter != null){
                printWriter.close();
            }
        }
    }
    /**
     * the method read tasks from Reader in text format
     * @param tasks list for reading
     * @param in reader for reading
     * @throws IOException if can't read tasks from stream
     */
    public static void read(TaskList tasks, Reader in) throws IOException, ParseException{
        BufferedReader bufferedReader = null;
        try{
            bufferedReader = new BufferedReader(in);

            SimpleDateFormat dateFormat = new SimpleDateFormat(dateFormate);
            Task task;
            String stringToParse = bufferedReader.readLine();
            while (stringToParse != null){
                Matcher matcher = patternNotRepetative.matcher(stringToParse);
                if (matcher.find()){
                    String details = matcher.group(1).replace("\"\"", "\"");
                    Date time = dateFormat.parse(matcher.group(2));
                    boolean isActive = matcher.group(3).length() == 0;
                    task = new Task(details,time);
                    task.setActive(isActive);
                    tasks.add(task);
                }else{
                    matcher = patternRepetative.matcher(stringToParse);
                    if(matcher.find()) {
                        String details = matcher.group(1).replace("\"\"", "\"");
                        Date start = dateFormat.parse(matcher.group(2));
                        Date end = dateFormat.parse(matcher.group(3));
                        int interval = parseInterval(matcher.group(4));
                        boolean isActive = matcher.group(5).length() == 0;
                        task = new Task(details, start, end, interval);
                        task.setActive(isActive);
                        tasks.add(task);
                    } else if(stringToParse.length() == 0){
                        return; 
                    } else {
                        throw new IOException(new StringBuilder("Wrong stringToParse value : ").append(stringToParse).toString());
                    }
                }
                stringToParse = bufferedReader.readLine();
            }
        }finally {
            if(bufferedReader != null){
                bufferedReader.close();
            }
        }
    }

    /**
     * the method write tasks list into file in text format
     * @param tasks list for reading
     * @param file for writing
     * @throws IOException
     */
     public static void writeText(TaskList tasks, File file) throws IOException{
        BufferedWriter bufferedWriter = null;
        try {
            bufferedWriter = new BufferedWriter(new FileWriter(file));
            write(tasks, bufferedWriter);
        } finally {
            bufferedWriter.close();
        }
    }
    /**
     * the method read tasks list from file in text format
     * @param tasks list for writing
     * @param file for reading
     * @throws IOException
     */
    public static void readText(TaskList tasks, File file) throws IOException{
        BufferedReader bufferedReader =  null;
        try {
            bufferedReader = new BufferedReader(new FileReader(file));
            read(tasks, bufferedReader);
        } catch (ParseException e) {
            throw new IOException(e);
        }finally {
            if(bufferedReader != null){
                bufferedReader.close();
            }
        }
    }

}
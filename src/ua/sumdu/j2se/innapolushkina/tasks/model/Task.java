package ua.sumdu.j2se.innapolushkina.tasks.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.util.Date;
import java.io.Serializable;

import java.text.SimpleDateFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static ua.sumdu.j2se.innapolushkina.tasks.Utill.dateCellFormater;

/**
 * class Task is the main class of app model
 * class uses StringProperty uses for quick load data into view after editions without reloading the app
 * variables for StringProperty have getters and setters, which uses in standard methods of class
 */
public class Task  implements Cloneable,  Serializable{
        public static final String dateFormate = "yyyy-MM-dd HH:mm:ss.SSS";
        private String title;
        private Date time;
        private Date start;
        private Date end;
        private int interval;
        private boolean active;
        private boolean repeat;

        private StringProperty observableTitle;
        private StringProperty observableTime;
        private StringProperty observableStart;
        private StringProperty observableEnd;
        private StringProperty observableInterval;
        private StringProperty observableActive;
        private StringProperty observableRepeat;

    /**
     * the method get String value from observableTitle
     * @return
     */
        public String getObservableTitle() {
            observableTitle.set(title);
            return observableTitle.get();
        }

    /**
     * the method get StringProperty observableTitle
     * @return observableTitle
     */
        public StringProperty observableTitleProperty() {
            return observableTitle;
        }

    /**
     * the method set String value into ObservableTitle
     * @param observableTitle have string with title of task
     */
        public void setObservableTitle(String observableTitle) {
            this.observableTitle.set(observableTitle);
        }

    /**
     * the method get String value from observableTime
     * @return string with data about time, if task is not repeated, else data about start
     */
        public String getObservableTime() {
            if (repeat) {
                setObservableTime(dateCellFormater.format(start));
            }
            else {
                setObservableTime(dateCellFormater.format(time));
            }
            return observableTime.get();
        }

    /**
     * the method get StringProperty from observableTime
     * @return observableTime
     */
        public StringProperty observableTimeProperty() {
            return observableTime;
        }

    /**
     * the method set observableTime
     * @param observableTime have string value with data about time of task
     */
        public void setObservableTime(String observableTime) {
            this.observableTime.set(observableTime);
        }


    /**
     * the method get StringProperty from observableInterval
     * @return observableInterval
     */
    public StringProperty getObservableIntervalProperty(){
        return observableInterval;
    }
    /**
     * the method get StringProperty from observableEnd
     * @return observableEnd
     */
    public StringProperty getObservableEndProperty(){
        return observableEnd;
    }

    /**
     * the method get StringProperty from observableStart
     * @return observableStart
     */
    public StringProperty getObservableStartProperty(){
        return observableStart;
    }

    /**
     * the method get observableInterval
     * @return string value about interval os task
     */
    public String getObservableInterval() {
        String interval;
        if(repeat){
            Pattern pattern = Pattern.compile("every \\[(.*)]");
            Matcher matcher = pattern.matcher(toString());
            matcher.find();
            interval =  new StringBuilder("Every ").append(matcher.group(1)).toString();
        }else{
            interval = "";
        }
        setObservableInterval(interval);
        return observableInterval.get();
    }

    /**
     * the method get StringProperty observableInterval
     * @return observableInterval
     */
    public StringProperty observableIntervalProperty() {
        return observableInterval;
    }

    /**
     * the method set ObservableInterval
     * @param observableInterval have string value with data about interval
     */
    public void setObservableInterval(String observableInterval) {
        this.observableInterval.set(observableInterval);
    }

    /**
     * the method get observableStart
     * @return observableStart if task is repeated, else return empty string
     */
    public String getObservableStart() {
        if(repeat){
            setObservableStart(dateCellFormater.format(start));
        }else{
            setObservableStart("");
        }
        return observableStart.get();
    }

    /**
     * the method get StringProperty observableStart
     * @return observableStart
     */
    public StringProperty observableStartProperty() {
        return observableStart;
    }

    /**
     * the method set ObservableStart
     * @param observableStart have string with value of start of task
     */
    private void setObservableStart(String observableStart) {
        this.observableStart.set(observableStart);
    }

    /**
     * the method get String observableEnd
     * @return string with value of end time of task, if task is repeated, else empty string
     */
    public String getObservableEnd() {
        if(repeat){
            setObservableEnd(dateCellFormater.format(end));
        }else{
            setObservableEnd("");
        }
        return observableEnd.get();
    }

    /**
     * the method get StringProperty  of end task
     * @return observableEnd
     */
    public StringProperty observableEndProperty() {
        return observableEnd;
    }

    /**
     * the method set string value into observableEnd
     * @param observableEnd have string for setting
     */
    public void setObservableEnd(String observableEnd) {
        this.observableEnd.set(observableEnd);
    }

    /**
     * the method get String observableIsActive
     * @return Active if task is active, else return Disabled
     */
    public String getObservableIsActive() {
        if(active){
            setObservableIsActive("Active");
        }else{
            setObservableIsActive("Disabled");
        }
        return observableActive.get();
    }

    /**
     * the method get value from StringProperty observableActive
     * @return
     */
    public StringProperty observableIsActiveProperty() {
        return observableActive;
    }

    /**
     * the method set value into observableActive
     * @param observableIsActive have boolean value
     */
    private void setObservableIsActive(String observableIsActive) {
        this.observableActive.set(observableIsActive);
    }

    /**
     * empty constructor for class Task
     */
        public Task() {
            super();
        }

    /**
     * the constructor for class task
     * @param title have name for task
     * @param time date for task
     */
        public Task(String title,Date time) {

            active = false;
            repeat = false;
            this.title = title;
            this.time = this.start = this.end = time;
            observableTitle = new SimpleStringProperty();
            observableTitle.set(title);
            observableTime = new SimpleStringProperty();
            observableStart = new SimpleStringProperty();
            observableEnd = new SimpleStringProperty();
            observableInterval = new SimpleStringProperty();
            observableActive = new SimpleStringProperty();
            getObservableTitle();
            getObservableTime();
            getObservableStart();
            getObservableEnd();
            getObservableInterval();
            getObservableIsActive();

        }

    /**
     * the constructor  for class Task, if task is repeated
     * @param title have name for task
     * @param start have date of start
     * @param end have date of end
     * @param interval have repeated interval
     */
        public Task(String title,Date start,Date end,int interval) {
            active = false;
            this.time = start;
            this.start = start;
            this.end = end;
            this.interval = interval;
            this.title = title;
            repeat = true;
            observableTitle = new SimpleStringProperty();
            observableTime = new SimpleStringProperty();
            observableStart = new SimpleStringProperty();
            observableEnd = new SimpleStringProperty();
            observableInterval = new SimpleStringProperty();
            observableActive = new SimpleStringProperty();
            getObservableTitle();
            getObservableTime();
            getObservableStart();
            getObservableEnd();
            getObservableInterval();
            getObservableIsActive();
        }

    /**
     * the method get value of title
     * @return
     */
    public String getTitle() {
            return title;
    }

    /**
     * the method set title for task
     * @param title
     */
    public void setTitle(String title) {
            this.title = title;
            observableTitleProperty().set(title);
    }

    /**
     * the method get activity of task
     * @return boolean value
     */
    public boolean isActive() {
            return active;
    }

    /**
     * the method set activity of task
     * @param active boolean value
     */
    public void setActive(boolean active) {
            this.active = active;
            getObservableIsActive();
        }

    /**
     * the method get date of task
     * if task is repeated return date os start, else return time of task
     * @return
     */
    public Date getTime() {
            if (isRepeated()) {
                return start;
            }
            return time;
        }

    /**
     * the method set time into task
     * if task is repeated set repeated false
     * @param time have date for setting
     */
    public void setTime(Date time) {
        if(isRepeated()){
            repeat = false;
        }
        this.time = time;
        getObservableTime();
        getObservableStart();
        getObservableEnd();
        getObservableInterval();
    }

    /**
     * the method get start of task
     * if task is not repeated return time
     * @return date of start
     */
        public Date getStartTime(){
            if(!isRepeated()){
                return time;
            }
            return start;
        }

    /**
     * the method get end of task
      * @return date of end
     * if task is not repeated return time
     */
        public Date getEndTime(){
            if(!isRepeated()){
                return time;
            }
            return end;
        }

    /**
     * the method get repeated interval
      * @return integer interval value
     * if task is not repeated return 0
     */
        public int getRepeatInterval(){
            if(!isRepeated()){
                return 0;
            }
            return interval;
        }

    /**
     * the method set time into task
     * if task is not repeated set repeated true
      * @param start have start time
     * @param end have end time
     * @param interval have repeated interval
     * @throws IllegalArgumentException if interval/start/end less then 0
     */
        public void setTime(Date start,Date end,int interval) throws IllegalArgumentException{
            if(interval <= 0 || start.getTime() < 0 || end.getTime() < 0) {
                    throw new IllegalArgumentException("Incorrectly interval, must be above zero");
            }
            else{
                if(!isRepeated()){
                    repeat = true;
                }
                this.interval = interval;
                this.start = start;
                this.end = end;
            }
            getObservableTime();
            getObservableStart();
            getObservableEnd();
            getObservableInterval();
        }

    /**
     * the method get repeated of task
      * @return boolean value
     */
        public boolean isRepeated(){
            return repeat;
        }

    /**
     * the method get next repeated time after current date
      * @param current have date for comparison
     * @return next time after current, if task is repeated, else return time
     */
        public Date nextTimeAfter(Date current){
            if(active) {
                if(repeat) {
                    if(current.after(end)) {
                        return null;
                    }
                    Date res = new Date(start.getTime());
                    for(long i = start.getTime(); res.compareTo(end) <= 0; i += interval*1000, res.setTime(i)) {
                        if(i > current.getTime()) {
                            return new Date(i);
                        }
                    }
                }
                else {
                    if(time.after(current)) {
                        return time;
                    }
                }


            }
            return time;

        }

    /**
     * the method compare any object with this
     * @param o have any object for comparison
     * @return true if o equals this, else false
     */
    @Override
        public boolean equals(Object o){
            if(o == null){
                return false;
            }
            if(o == this){
                return true;
            }
            if(getClass() != o.getClass()){
                return false;
            }
            Task t = (Task)o;
            if(title.equals(t.title) && time.getTime() == t.time.getTime() && active == t.active && repeat == t.repeat) return true;
            else return false;
        }

    /**
     * the method generate hash code for task
     * @return integer values
     */
    @Override
        public int hashCode(){
            int hash = 37;
            hash = hash*17 + title.hashCode();
            hash = hash*17 + time.hashCode();
            return hash;
        }

    /**
     * the method parse task to string values
     * @return string values
     */
        @Override
        public String toString() {
            StringBuilder taskInfo = new StringBuilder("\"");
            taskInfo.append(title.replace("\"", "\"\"")).append('"');
            SimpleDateFormat dateFormat = new SimpleDateFormat(new StringBuilder("[").append(dateFormate).append("]").toString());
            if(repeat){
                taskInfo.append(" from ").append(dateFormat.format(start)).append(" to ").append(dateFormat.format(end)).append(" every [");
                //int interval = this.interval; // in seconds
                int days = interval / 86400;
                int hours = (interval - days * 86400) / 3600;
                int minutes = (interval - days * 86400 - hours * 3600) / 60;
                int seconds = interval - days * 86400 - hours * 3600 - minutes * 60;
                StringBuilder intervalFormat = new StringBuilder();
                if (days != 0) {
                    intervalFormat.append(days).append((days > 1 ? " days " : " day "));
                }
                if (hours != 0) {
                    intervalFormat.append(hours).append((hours > 1 ? " hours " : " hour "));
                }
                if (minutes != 0) {
                    intervalFormat.append(minutes).append((minutes > 1 ? " minutes " : " minute "));
                }
                if (seconds != 0) {
                    intervalFormat.append(seconds).append((seconds > 1 ? " seconds " : " second "));
                }
                taskInfo.append(intervalFormat.substring(0, intervalFormat.length() - 1)).append(']');
            } else {
                taskInfo.append(" at ").append(dateFormat.format(time));
            }
            if (!active) {
                taskInfo.append(" inactive");
            }
            return taskInfo.toString();
        }

    /**
     * the method clone task
      * @return clone of task
     * @throws CloneNotSupportedException if method can't clone task
     */
        @Override
        protected Task clone() throws CloneNotSupportedException {
            return (Task)super.clone();
        }
    }

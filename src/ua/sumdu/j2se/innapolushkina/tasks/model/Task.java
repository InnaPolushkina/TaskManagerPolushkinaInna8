package ua.sumdu.j2se.innapolushkina.tasks.model;

import java.util.Date;
import java.io.Serializable;

import java.text.SimpleDateFormat;


    public class Task  implements Cloneable,  Serializable{
        public static final String dateFormate = "yyyy-MM-dd HH:mm:ss.SSS";
        private String title;
        private Date time;
        private Date start;
        private Date end;
        private int interval;
        private boolean active;
        private boolean repeat;
        public Task() {
            super();
        }
        public Task(String title,Date time) {
            active = false;
            repeat = false;
            this.title = title;
            setTime(time);
        }
        public Task(String title,Date start,Date end,int interval) {
            active = false;
            setTime(start,end,interval);
            time = start;
            this.title = title;
            repeat = true;
        }
        public String getTitle() {
            return title;
        }
        public void setTitle(String title) {
            this.title = title;
        }
        public boolean isActive() {
            return active;
        }
        public void setActive(boolean active) {
            this.active = active;
        }
        public Date getTime() {
            if (isRepeated()) {
                return start;
            }
            return time;
        }
        public void setTime(Date time) {
            if(time.getTime() < 0) {
                try {
                    throw new IllegalArgumentException("Incorrected time");
                }
                catch(IllegalArgumentException ex) {
                    System.out.println(ex);
                }
            }
            else{
                if(isRepeated()){
                    repeat = false;
                }
                this.time = time;
            }

        }
        public Date getStartTime(){
            if(!isRepeated()){
                return time;
            }
            return start;
        }
        public Date getEndTime(){
            if(!isRepeated()){
                return time;
            }
            return end;
        }
        public int getRepeatInterval(){
            if(!isRepeated()){
                return 0;
            }
            return interval;
        }
        public void setTime(Date start,Date end,int interval){
            if(interval <= 0 || start.getTime() < 0 || end.getTime() < 0) {
                try {
                    throw new IllegalArgumentException("Incorrected interval, must be above zero");
                }
                catch(IllegalArgumentException ex) {
                    System.out.println(ex);
                }
            }
            else{
                if(!isRepeated()){
                    repeat = true;
                }
                this.interval = interval;
                this.start = start;
                this.end = end;
            }
        }
        public boolean isRepeated(){
            return repeat;
        }
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
            return null;
         /*if (!repeat && time.after(current) && active)
            return time;
        if (repeat && start.after(current) && active)
            return start;
        if (isRepeated()) {
            Date k = start;
            while (current.after(k)) {
                //k.add((GregorianCalendar.MONTH), current.getMonth());
                //k += interval;
                k = current;
            }
            if (k.after(end)) {
                return null;
            }
            return k;
        }
        return null;*/
        }
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
        @Override
        public int hashCode(){
            int hash = 37;
            hash = hash*17 + title.hashCode();
            hash = hash*17 + time.hashCode();
            return hash;
        }

    /* @Override
    public String toString() {
        return "Task{" +
                "title='" + title + '\'' +
                ", time=" + time +
                ", active=" + active +
                ", start=" + start +
                ", end=" + end +
                ", interval=" + interval +
                ", repeat=" + repeat +
                '}';
    }*/

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

        @Override
        protected Task clone() throws CloneNotSupportedException {
            return (Task)super.clone();
        }
    }

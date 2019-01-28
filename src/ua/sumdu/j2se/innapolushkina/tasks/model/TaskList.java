package ua.sumdu.j2se.innapolushkina.tasks.model;
import java.io.Serializable;
import java.util.Iterator;



public abstract class TaskList implements Cloneable, Serializable, Iterable<Task>{
    abstract public void add(Task task);
    abstract public boolean remove(Task task);
    abstract public int size();
    abstract public Task getTask(int index);
    
     @Override
    public String toString() {
        Iterator<Task> iter = this.iterator();
        StringBuilder taskListInfo = new StringBuilder();
        while (iter.hasNext()) {
            Task task = iter.next();
            taskListInfo.append(task.toString());
            if(iter.hasNext()){
                taskListInfo.append(";\n");
            }else{
                taskListInfo.append('.');
            }
        }
        return taskListInfo.toString();
    }
    
    
}
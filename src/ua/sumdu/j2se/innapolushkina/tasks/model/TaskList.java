package ua.sumdu.j2se.innapolushkina.tasks.model;
import java.io.Serializable;
import java.util.Iterator;

/**
 * class TaskList is for work with collection of Task
 */

public abstract class TaskList implements Cloneable, Serializable, Iterable<Task>{
    /**
     * the method must add object of class Task into collection
     * @param task for adding
     */
    abstract public void add(Task task);
    /**
     * the method must remove object of class Task from collection
     * @param task for removing
     */
    abstract public boolean remove(Task task);
    /**
     * the method must return size of Task collection
     * @return integer size
     */
    abstract public int size();

    /**
     * the method must get element of collections dependents on param
     * @param index number element in collection
     * @return task with this index
     */
    abstract public Task getTask(int index);

    /**
     * the method generate String value from task
     * @return string
     */
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
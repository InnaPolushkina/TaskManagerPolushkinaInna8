package ua.sumdu.j2se.innapolushkina.tasks.model;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Date;

/**
 * class ArrayTaskList realizes collection array for class Task
 */
public class ArrayTaskList extends TaskList implements Cloneable{
    private Task[]array;
    private int index;

    /**
     * constructor for class ArrayTasList
     * generate array for 10 elements
     */
    public ArrayTaskList() {
        array = new Task[10];
        index = 0;
    }

    /**
     * the method add new task in array
     * @param task for adding
     */
    public void add(Task task) {
        if(task == null) {
            throw new IllegalArgumentException("Empty task can not be at List");
        }
        
            if(index == array.length) {
            Task[] array2 = array;
                array = new Task[array.length*2 + 1];
                System.arraycopy(array2,0,array,0,index);
                array[index] = task;               
            }
            else {
                array[index] = task;
            }
             index++;
    }

    /**
     * the method remove task from array
     * @param task for removing
     * @return true if task was removed, else false
     */
    public boolean remove(Task task){
        boolean res = false;
        int indexRemove = 0;
        
        Task[]arrayAfter = new Task[this.size() - 1];
        for(int i=0; i < this.size(); i++) {
            if(array[i].equals(task)) {
                res = true;
                indexRemove = i;
                break;
            }
            if(i<arrayAfter.length)
                arrayAfter[i] = array[i];
        }
        if(res){
            for(int i=indexRemove;i<arrayAfter.length;i++){
                arrayAfter[i] = array[i+1];
            }
            array = arrayAfter;
            index--;
            return true;   
            }
        else {
            try {
                throw new IllegalArgumentException("Task for deleting was not found");
            } catch (IllegalArgumentException ex) {
                System.out.println(ex);
            }
            return false;
        }
    }

    /**
     * the method get size of array
     * @return integer values
     */
    public int size(){
        return index;
    }

    /**
     * the method get task on index
     * @param index have index for getting task
     * @return element of array dependents on index
     */
    public Task getTask(int index){
        if(index < 0 || index >= this.size()) {
            try {
                throw new IllegalArgumentException("Index is not correct!!!");
            }
            catch(IllegalArgumentException ex) {
                System.out.println(ex);
            }
            return null;
        }
        else{
          return array[index];  
        }
    }

    /**
     * the method get subarray from main array where dates between from and to
     * @param from start time
     * @param to end time
     * @return array
     */
     public ArrayTaskList incoming(Date from, Date to){
        ArrayTaskList result = new ArrayTaskList();
        for(Task  elem: array){
           if(elem.nextTimeAfter(from)!= null) {
                result.add(elem);
           }
        }
        return result;
    }

    /**
     * constructor for iterator
     * @return new Iterator for object of this class
     */
    public Iterator<Task> iterator(){
        return new ArrayTaskListIterator();
    }

    /**
     * private class for ArrayTaskListIterator
     * realises standard methods of iterator
     */
    private class ArrayTaskListIterator implements Iterator<Task>{
        private boolean wasMoved;
        private int currentIndex = -1;

        /**
         * the method get next object from collections
         * @return next element of array
         */
        public Task next(){
            if(currentIndex + 1 > index - 1){
                throw new NoSuchElementException();
            }else{
                wasMoved = true;
                return array[++currentIndex];
            }
        }

        /**
         * the method verifiable if array has next element
         * @return return true if has, else return false
         */
        public boolean hasNext(){
            return currentIndex + 1 <= index - 1;
        }

        /**
         * the method for removing element of array
         */
        public void remove(){
            if(!wasMoved){
                throw new IllegalStateException();
            }
            else{
                for(int i = currentIndex; i < index - 1; i++ ){
                    array[i] = array[i + 1];
                }
                array[index - 1] = null;
                wasMoved = false;
                index--;
                currentIndex--;
            }
        }      
    }
    /**
     * the method generate hash code for object of this class
     * @return integer values
     */
       @Override
        public int hashCode(){
            int hash = 37;
            for(int i = 0; i < index; i++){
                hash += array[i].hashCode();
            }
            return hash;
        }
    /**
     * the method compare any object with this
     * @param ob have any object for comparison
     * @return true if o equals this, else false
     */
        @Override
        public boolean equals(Object ob){
            if(this == ob){
                return true;
            }
            if(ob == null || getClass() != ob.getClass() ){
                return false;
            }
            ArrayTaskList t = (ArrayTaskList)ob;
            if(index != t.index){
                return false;
            }
            Iterator<Task> ir = this.iterator();
            Iterator<Task> it = t.iterator();
            while(ir.hasNext()){
                if(!ir.next().equals(it.next())){
                return false;                
                }
            }
            
            return true;
        }

    /**
     * the method clone object of ArrayTaskList
     * @return clone of this
     * @throws CloneNotSupportedException if can't clone object
     */
    @Override
    public ArrayTaskList clone() throws CloneNotSupportedException{
        ArrayTaskList clone = (ArrayTaskList)super.clone();
        clone.array = new Task[index];
        for(int i = 0; i < index; i++){
            clone.array[i] = array[i].clone();
        }
        return clone;
    }
    
}

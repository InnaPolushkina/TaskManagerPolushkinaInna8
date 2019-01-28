package ua.sumdu.j2se.innapolushkina.tasks.model;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Date;

public class ArrayTaskList extends TaskList implements Cloneable{
    private Task[]array;
    private int index;

    public ArrayTaskList() {
        array = new Task[10];
        index = 0;
    }

    public void add(Task task) {
        if(task == null) {
            throw new IllegalArgumentException("Empty task can not be at List");
        }
        
            if(index == array.length) {
            Task[] array2 = array;
                array = new Task[array.length*2 + 1];
                System.arraycopy(array2,0,array,0,index);
                //System.out.println(array.length);
                array[index] = task;               
            }
            else {
                array[index] = task;
            }
             index++;            
        

    }
    
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
    public int size(){
        return index;
    }
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
     public ArrayTaskList incoming(Date from, Date to){
        ArrayTaskList result = new ArrayTaskList();
        for(Task  elem: array){
           if(elem.nextTimeAfter(from)!= null) {
                result.add(elem);
           }
                /*if(elem.getEndTime() == to && elem.getStartTime() < from && !elem.isRepeated()){
                        result.add(elem);   
                }
                else if(elem.nextTimeAfter(from)>from && elem.nextTimeAfter(from)<=to && elem.isActive()){
                        result.add(elem);
                }     */    
            
        }
        return result;
    }
    
    /*private boolean equally(Task task1, Task task2){
        if(task1.getTitle() == task2.getTitle() && task1.getTime() == task2.getTime())
            return true;
        else return false;
    }*/
    
    public Iterator<Task> iterator(){
        return new ArrayTaskListIterator();
    }
    private class ArrayTaskListIterator implements Iterator<Task>{
        private boolean wasMoved;
        private int currentIndex = -1;
        
        public Task next(){
            if(currentIndex + 1 > index - 1){
                throw new NoSuchElementException();
            }else{
                wasMoved = true;
                return array[++currentIndex];
            }
        }
        
        public boolean hasNext(){
            return currentIndex + 1 <= index - 1;
        }
        
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

       @Override
        public int hashCode(){
            int hash = 37;
            for(int i = 0; i < index; i++){
                hash += array[i].hashCode();
            }            
            //System.out.println(++i);
            
            return hash;
        }
        
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
        
    /*@Override
    public String toString() {
        return "ArrayTaskList{" +
                "array=" + Arrays.toString(array) +
                '}';
    }*/
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

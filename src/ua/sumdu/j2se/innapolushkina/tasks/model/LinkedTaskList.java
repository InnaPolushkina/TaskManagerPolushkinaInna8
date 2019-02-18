package ua.sumdu.j2se.innapolushkina.tasks.model;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;

/**
 * class LinkedTaskList realizes LinkedList collections for class Task
 */
public class LinkedTaskList extends TaskList {
    private Node head;
    private Node tail;
    private int n;

    /**
     * the constructor for this class
     */
    public LinkedTaskList(){
        super();
        n = 0;
    }

    /**
     * the method add new task into linked list collection
     * @param task for adding
     */
    public void add(Task task){
        if(task == null){
            throw new IllegalArgumentException("Empty task can not be at LinkedList!");
        }
        Node node = new Node(task);
        if(head == null){
            head = node;
            tail = node;
            n = 1;
            return;
        }
            tail.setNext(node);
            node.setPrev(tail);
            tail = node;
            n++;
        
    }

    /**
     * the method get size of liked list
     * @return integer values
     */
    public int size(){
        return n;
    }

    /**
     * the method remove element of linked list
     * @param task have task for removing
     * @return true if task was removing, else return false
     */
    public boolean remove(Task task){
        if(task == null){
            throw new IllegalArgumentException("Can not remove empty task");
        }
        Node t = head;
        while(t != null && !t.getTask().equals(task)){
            t = t.getNext();
        }
        if(t != null && t.getTask().equals(task)){
            if(t.getPrev() != null){
               t.getPrev().setNext(t.getNext());
                if(tail == t){
                    tail = t.getPrev();
                } 
            }
            if(t.getNext() != null){
                t.getNext().setPrev(t.getPrev());
                if(head == t){
                    head = t.getNext();
                }
            }
            if(t.getNext() == null && t.getPrev() == null) {
                head = null;
                tail = null;
            }
            n--;
            return true;
        }
        return false;
    }

    /**
     * the method get elements of linked list dependents on its index
     * @param index of element in linked list
     * @return task with index
     */
    public Task getTask(int index) {
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
            Node t = head;
            for(int i = 0; i < index; i++) {
                t = t.getNext();
            }
            return t.getTask();
        }
    }
    /**
     * constructor for iterator
     * @return new Iterator for object of this class
     */
    public Iterator<Task> iterator(){
        return new LinkedTaskListIterator();
    }
    /**
     * private class for LinkedTaskListIterator
     * realises standard methods of iterator
     */
    private class LinkedTaskListIterator implements Iterator<Task>{
        private Node t = new Node();
        private int currentIndex = -1;
        private boolean wasMoved;

        /**
         * constructor for this class
         */
        {
            t.setNext(head);
        }
        /**
         * the method get next object from collections
         * @return next element of linked list
         */
        public Task next(){
            t = t.getNext();
            wasMoved = true;
            currentIndex++;
            if(t == null){
                  throw new NoSuchElementException();
            }else{
                return t.getTask();
            }
        }
        /**
         * the method verifiable if linked list has next element
         * @return return true if has, else return false
         */
        public boolean hasNext(){
            return t.getNext()!=null;
        }
        /**
         * the method for removing element of linked list
         */
        public void remove(){
            if(!wasMoved){
                throw new IllegalStateException();
            }
            else{
                if(t.getPrev() == null){
                    head = t.getNext();
                    t.getNext().setPrev(null);
                }
                else if(t.getNext() == null){
                    tail = t.getPrev();
                    t.getPrev().setNext(null);                        
                }else{
                    t.getPrev().setNext(t.getNext()); 
                    t.getNext().setPrev(t.getPrev());
                }
                wasMoved = false;
                n--;
            }
        }
    }
    /**
     * the method generate hash code for object of this class
     * @return integer values
     */
        @Override
        public int hashCode(){
            return Objects.hash(head, tail, n);
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
            LinkedTaskList t = (LinkedTaskList)ob;
            if(n != t.n){
                return false;
            }else{
            Iterator<Task> ir = iterator();
            Iterator<Task> it = t.iterator();
            while(ir.hasNext()){
                if(!ir.next().equals(it.next())){
                return false;                
                }
            }
            
            }
            return true;
        }

    /**
     * the method clone object of the class
     *@return clone of this
     *@throws CloneNotSupportedException if can't clone object
     */
    @Override
    public LinkedTaskList clone() throws CloneNotSupportedException {
        LinkedTaskList clone = (LinkedTaskList) super.clone();
        Iterator<Task> iterator = iterator();
        clone.head = clone.tail = null;
        while(iterator.hasNext()){
            clone.add(iterator.next().clone());
        }
        return clone;
    }
    
}

package ua.sumdu.j2se.innapolushkina.tasks.model;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;

public class LinkedTaskList extends TaskList {
    private Node head;
    private Node tail;
    private int n;
    
    public LinkedTaskList(){
        super();
        n = 0;
    }
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
    public int size(){
        return n;
    }
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
    public Iterator<Task> iterator(){
        return new LinkedTaskListIterator();
    } 
    private class LinkedTaskListIterator implements Iterator<Task>{
        private Node t = new Node();
        private int currentIndex = -1;
        private boolean wasMoved;
        
        {
            t.setNext(head);
        }
        
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
        public boolean hasNext(){
            return t.getNext()!=null;
        }
        
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
        @Override
        public int hashCode(){
            return Objects.hash(head, tail, n);
        }
        
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
        
        /*@Override
        public String toString(){
            StringBuilder temp = new StringBuilder();
            Node k = head;
            while(k != null){
                temp.append(k.getTask().toString());
                k = k.getNext();
            }
            return "LinkedTaskList{" +
                "length=" + n +
                ", array=" + temp +
                '}';
        }*/
        
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

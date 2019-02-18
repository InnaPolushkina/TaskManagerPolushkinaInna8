package ua.sumdu.j2se.innapolushkina.tasks.model;

/**
 * class Node realizes element of LinkedList collections for class Task
 */
public class Node implements Cloneable{
    private Task task;
    private Node next;
    private Node prev;

    /**
     * the empty constructor for Node of likedList
     */
    public Node(){
    }

    /**
     * the constructor with value of task
     * @param task
     */
    public Node(Task task){
        this.task = task;
    }

    /**
     * the method get task from Node
     * @return task
     */
    public Task getTask(){
        return task;
    }

    /**
     * the method get next element
     * @return next element after task
     */
    public Node getNext(){
        return next;
    }
    /**
     * the method get previous element
     * @return previous element before task
     */
    public Node getPrev(){
        return prev;
    }

    /**
     * the method set next element
     * @return next element after task
     */
    public void setNext(Node node){
        next = node;
    }
    /**
     * the method set previous element
     * @return previous element before task
     */
    public void setPrev(Node node){
        prev = node;
    }

    /**
     * the method compare any object with this
     * @param o have any object for comparison
     * @return true if o equals this, else false
     */
    @Override
    public boolean equals(Object o){
        if(this == o){
            return true;
        }
        if(this == null || o.getClass() != getClass()){
            return false;
        }
        Node node = (Node) o;        
        if(task.equals(node.task)) return true;            
        else return false;
    }

    /**
     * the method generate hashcode for task from likedList
     * @return integer hash code of task
     */
   @Override
    public int hashCode(){
        int coef1 = (next == null ? 31 : 53);
        int coef2 =  (prev == null ? 7 : 11);
        int coef3 = coef1 + coef2;
        return task.hashCode() * coef1 - coef2 + coef3 * coef1;
    }

    /**
     * the method generate string value about task
     * @return string
     */
    @Override
    public String toString(){
        return new StringBuilder().append("\n Previous : ").append(prev.getTask().toString()).append("\n Next : ").append(next.getTask().toString()).toString();
    }

    /**
     * the method clone task from likedList
     * @return clone of task
     * @throws CloneNotSupportedException if method can't clone task
     */
    @Override
    public Node clone() throws CloneNotSupportedException {
        Node clone = (Node)super.clone();
        clone.task = task.clone();
        return clone;
    }
    
}

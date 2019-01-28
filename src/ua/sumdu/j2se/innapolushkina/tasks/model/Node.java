package ua.sumdu.j2se.innapolushkina.tasks.model;

public class Node implements Cloneable{
    private Task task;
    private Node next;
    private Node prev;
    
    public Node(){

    }

    public Node(Task task){
        this.task = task;
    }

    public Task getTask(){
        return task;
    }

    public Node getNext(){
        return next;
    }
    
    public Node getPrev(){
        return prev;
    }
    
    public void setNext(Node node){
        next = node;
    }
    
    public void setPrev(Node node){
        prev = node;
    }
    
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
    
   @Override
    public int hashCode(){
        int coef1 = (next == null ? 31 : 53);
        int coef2 =  (prev == null ? 7 : 11);
        int coef3 = coef1 + coef2;
        return task.hashCode() * coef1 - coef2 + coef3 * coef1;
    }
    
    @Override
    public String toString(){
        return new StringBuilder().append("\n Previous : ").append(prev.getTask().toString()).append("\n Next : ").append(next.getTask().toString()).toString();
    }
    
      @Override
    public Node clone() throws CloneNotSupportedException {
        Node clone = (Node)super.clone();
        clone.task = task.clone();
        return clone;
    }
    
}

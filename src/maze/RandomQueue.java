package maze;
import java.util.LinkedList;

/**
 * Implement a new data structure to randomly add and remove from head or tail
 * We can use this data structure to implement both depth first search and breath first search
 * @param <E>
 */
public class RandomQueue <E> {

    // initialize my randomly queue
    private LinkedList<E> queue;

    public RandomQueue(){
        queue = new LinkedList<E>();
    }

    // randomly take out one element from head or tail
    public void add(E e){
        if(Math.random() < 0.5){
            queue.addLast(e);
        }else{
            queue.addFirst(e);
        }
    }

    // randomly remove one element from head or tail
    public E remove(){
        if(queue.isEmpty()){
            throw new ArrayIndexOutOfBoundsException("empty array");
        }
        E element;
        if(Math.random() < 0.5){
            element = queue.removeLast();
        }else{
            element = queue.removeFirst();
        }
        return element;
    }
    // return if queue is empty or not
    public boolean isEmpty(){
        return queue.isEmpty();
    }
}

// Name:        Matt Nwachukwu
// Class:       CS 3305/Section 04
// Term:        Spring 2026
// Instructor:  Professor Bradley
// Assignment:  7
// IDE Name:    VS Code

/**
 * A generic max-priority queue backed by a Heap object.
 * The element with the greatest value is always at the front. All priority
 * queue operations delegate directly to the underlying Heap, keeping this
 * class lean and the logic concentrated in one place.
 * Capacity is bounded by Heap.CAPACITY.
 */
public class PQ_heap<E extends Comparable<E>> {

    // The heap that does the real heavy lifting for this priority queue.
    private Heap<E> heap = new Heap<E>();

    /** Create an empty priority queue. */
    PQ_heap() {
    }

    /**
     * Return true if the priority queue contains no elements.
     */
    public boolean is_empty() {
        return heap.getSize() == 0;
    }

    /**
     * Return true if the priority queue has reached its capacity limit.
     * Capacity is defined by the constant Heap.CAPACITY.
     */
    public boolean is_full() {
        return heap.isFull();
    }

    /**
     * Return the front element (the maximum) without removing it.
     * Precondition: the priority queue is not empty.
     */
    public E front() {
        return heap.peek();
    }

    /**
     * Return the current number of elements in the priority queue.
     */
    public int size() {
        return heap.getSize();
    }

    /**
     * Remove and return the largest element from the priority queue.
     * Precondition: the priority queue is not empty.
     */
    public E dequeue() {
        return heap.remove();
    }

    /**
     * Insert the given value into the priority queue.
     * Precondition: the priority queue is not full.
     */
    public void enqueue(E value) {
        heap.add(value);
    }

    /**
     * Return a reference to the underlying heap.
     * Intended for use by the test program when it needs to display the
     * internal tree structure.
     */
    public Heap<E> getHeap() {
        return heap;
    }
}

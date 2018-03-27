package src.dataStructures;

import java.util.ArrayList;

public class BinaryHeap <E extends Comparable<? super E>> {
    public BinaryHeap() {
        this(DEFAULT_CAPACITY);
    }
    public BinaryHeap(int capacity) {
        currentSize = 0;
        array = (E[])new Comparable[capacity];
    }
    public BinaryHeap(E[] items) {
        currentSize = items.length;
        array = (E[])new Comparable[(currentSize + 2) * 11 / 10];

        int i = 1;
        for(E item: items)
            array[i++] = item;
        buildHeap();
    }

    public void insert(E x) {
        if(currentSize == array.length - 1)
            enLargeArray(array.length * 2 + 1);

        int hole = ++currentSize;
        for(; hole > 0 && array[hole].compareTo(array[hole / 2]) < 0; hole /= 2)
            array[hole] = array[hole / 2];
        array[hole] = x;
    }
    public E findMin() {
        return array[1];
    }
    public E deleteMin() {
        if (isEmpty()) {

        }

        E minItem = findMin();
        array[1] = array[currentSize--];
        percolateDown(1);

        return minItem;
    }
    public boolean isEmpty() {
        return currentSize==0 ? true : false;
    }
    public void makeEmpty() {

    }

    private static final int DEFAULT_CAPACITY = 10;

    private int currentSize;
    private E[] array;

    private void percolateDown(int hole) {
        int child;
        E tmp = array[hole];

        for(; hole * 2 <= currentSize; hole = child) {
            child = hole * 2;
            if (child != currentSize && array[child].compareTo(array[child + 1]) > 0) {
                child++;
            }
            if (array[hole].compareTo(array[child]) > 0) {
                array[hole] = array[child];
            } else {
                break;
            }
        }
    }
    private void buildHeap() {
        for(int i = currentSize / 2; i > 0; i--) {
            percolateDown(i);
        }
    }
    private void enLargeArray(int newSize) {
        ArrayList<E> newArray = new ArrayList<E>(newSize);
        for(int i = 0; i < currentSize; i++)
            newArray.add(array[i]);
        array = (E[]) newArray.toArray();
    }
}

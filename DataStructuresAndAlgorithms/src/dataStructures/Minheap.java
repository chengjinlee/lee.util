package src.dataStructures;

import java.util.Iterator;
import java.util.Map;

public class Minheap <E extends Object> {

    //维持一个大小为k的最小堆
    public Map.Entry[] getTopKRecord(Map<E, E> map, int k){
        int i=0;
        Iterator iter=map.entrySet().iterator();
        Map.Entry[] elements=new Map.Entry[k];
        while(iter.hasNext()){
            Map.Entry e=(Map.Entry) iter.next();
            if(i<=k-1){
                elements[i]=e;
                if(i==k-1){
                    buildMinHeap(elements);
                }
                i++;
            }
            else{
                insertHeap(e,elements);
            }
        }
        return elements;
    }
    private static void insertHeap(Map.Entry n, Map.Entry[] heap) {
        if((int)n.getValue()>(int)heap[0].getValue()){
            heap[0]=n;
            minHeap(heap,0,heap.length);
        }

    }
    private static void buildMinHeap(Map.Entry[] heap) {
        int i=heap.length/2-1;
        for(;i>=0;i--){
            minHeap(heap,i,heap.length);
        }
    }
    private static void minHeap(Map.Entry[] heap, int i, int length) {
        int left ,right,min;
        Map.Entry temp;
        left=2*i+1;
        right=2*i+2;
        min=i;
        if(left<=length-1&&(int)heap[left].getValue()<(int)heap[i].getValue()){
            min=left;
        }
        if(right<=length-1&&(int)heap[right].getValue()<(int)heap[min].getValue()){
            min=right;
        }
        if(min!=i){
            temp=heap[i];
            heap[i]=heap[min];
            heap[min]=temp;
            minHeap(heap,min,length);
        }
    }
}

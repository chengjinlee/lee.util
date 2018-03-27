package src.test;

import src.dataStructures.Minheap;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/*
 *
 * 关键字：TopK,url统计,最小堆
 *
 */
public class TopK {
    //每行封装成一个记录，便于以后处理其他信息，如时间等等
    static class Record{
        private String searchKey;
        public String getSearchKey() {
            return searchKey;
        }
        public void setSearchKey(String s) {
            this.searchKey = s;
        }
        public Record() {

        }
    }

    public static void main(String[] args) {
        Map<String,Integer> map=new HashMap<String,Integer>();
        Minheap minheap = new Minheap();
        File f=new File("D://TopKTest.txt");
        BufferedReader reader=null;
        int k=3;//测试用例记录较少，这里指定为3
        Record record=new Record();
        try {
            reader=new BufferedReader(new FileReader(f));
            String tmp="";
            while((tmp=reader.readLine())!=null){
                record.setSearchKey(tmp);
                insert(record,map);
            }
            Map.Entry[] result= minheap.getTopKRecord(map,k);
            for(Map.Entry<String,Integer> e:result){
                System.out.println(e.getKey()+" "+e.getValue());
            }
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }
    //Hash表统计次数
    private static void insert(Record record, Map<String, Integer> map) {
        String url=record.getSearchKey();
        if(map.containsKey(url)){
            map.put(url, map.get(url)+1);
        }
        else
            map.put(url, 1);
    }

}

package com.example.hodujjajko;

import java.util.List;

public class Iterator {
    private int cursor;
    List<Timer> list;

    public Iterator(List<Timer> list){
        this.list = list;
        cursor = 0;
    }

    public boolean hasNext(){
        return cursor != list.size();
    }

    public Timer next(){
        return list.get(cursor++);
    }

    public void reset(){
        cursor = 0;
    }
}

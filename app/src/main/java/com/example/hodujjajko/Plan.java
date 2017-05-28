package com.example.hodujjajko;



public class Plan {
    public int id;
    public String name;
    public boolean isOnce;
    public String day;
    public String timeStart;
    public String timeEnd;
    public int dayOfWeek;

    public int getDay(){
        if (isOnce) {
            String result = day.substring(0, 2);
            if (result.charAt(0) == '0') {
                result.substring(1);
            }
            return Integer.parseInt(result);
        }
        return 0;
    }

    public int getMonth(){
        if(isOnce) {
            String result = day.substring(3, 5);
            if (result.charAt(0) == '0') {
                result.substring(1);
            }
            return Integer.parseInt(result);
        }
        return 0;

    }
    public int getYear(){
        if(isOnce) {
            String result = day.substring(6);
            return Integer.parseInt(result);
        }
        return 0;
    }

}


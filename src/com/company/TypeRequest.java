package com.company;

import java.util.ArrayList;
import java.util.Collections;

public class TypeRequest {
    private String typeOfRequests;
    private ArrayList<Request> list= new ArrayList<>();

    private Integer average;
    private Integer median;
    private Integer percentile90;
    private Integer percentile99;
    private Integer maximum;

    public TypeRequest(String type){
        typeOfRequests = type;
    }

    public ArrayList<Request> getList() {
        return list;
    }

    /**
     * calculation statistics
     */

    public void Calculation(){
        ArrayList<Integer> listStat = new ArrayList<>();
        for (Request req: list) {
            listStat.add(req.getRetention());
        }
        Collections.sort(listStat);
        if(list.size()>0) {
            //average
            int sum = 0;
            for (int req : listStat) {
                sum += req;
            }
            average = sum / listStat.size();
            //median
            if (listStat.size() > 1) {
                if ((listStat.size() % 2) == 1) {
                    median = listStat.get(listStat.size() / 2);
                } else {
                    median = (listStat.get(listStat.size() / 2) + listStat.get(listStat.size() / 2 - 1)) / 2;
                }
            } else median = listStat.get(0);
            //percentile90
            percentile90 = listStat.get((int) Math.round(listStat.size() * 0.9) - 1);
            //percentile99
            percentile99 = listStat.get((int) Math.round(listStat.size() * 0.99) - 1);
            //maximum
            maximum = listStat.get(listStat.size()-1);
        }
    }

    public Integer getAverage() {
        return average;
    }

    public Integer getMaximum() {
        return maximum;
    }

    public Integer getMedian() {
        return median;
    }

    public Integer getPercentile90() {
        return percentile90;
    }

    public Integer getPercentile99() {
        return percentile99;
    }
}

package com.company;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DataAnalysis {
    private ArrayList<Request> list = new ArrayList<>();
    private String DataTime;
    private HashMap<String,TypeRequest> types = new HashMap<>();
    public DataAnalysis(String time){
        DataTime = time;
    }

    public ArrayList<Request> getList() {
        return list;
    }

    /**
     * calculation statistics after filling the class with requests
     */
    public void Work(){
        for (Request req: list) {
            if(!types.containsKey(req.getType())){
                TypeRequest newTypeReq = new TypeRequest(req.getType());
                newTypeReq.getList().add(req);
                types.put(req.getType(),newTypeReq);
            }else{
                types.get(req.getType()).getList().add(req);
            }
        }
        for (Map.Entry<String, TypeRequest> t:types.entrySet()) {
                t.getValue().Calculation();
        }
    }

    /**
     * this function return statistics after work
     */

    public ArrayList<ArrayList<String>> getStatistics(){
        ArrayList<ArrayList<String>> statistics = new ArrayList<>();
        for(Map.Entry<String, TypeRequest> t:types.entrySet()){
            ArrayList<String> statisticReq = new ArrayList<>();
            statisticReq.add(DataTime);
            statisticReq.add(t.getKey());
            statisticReq.add(String.valueOf(t.getValue().getList().size()));
            statisticReq.add(String.valueOf(t.getValue().getAverage()));
            statisticReq.add(String.valueOf(t.getValue().getMedian()));
            statisticReq.add(String.valueOf(t.getValue().getPercentile90()));
            statisticReq.add(String.valueOf(t.getValue().getPercentile99()));
            statisticReq.add(String.valueOf(t.getValue().getMaximum()));
            statistics.add(statisticReq);
        }
        return statistics;
    }
}

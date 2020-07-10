package com.company;

public class Request {
    private String idRequest;
    private String type;
    private Integer retention;

    public Request(String id, String timeOutput,String timeInput, String typeReq) {
        idRequest = id;
        type = typeReq;
        int out = Integer.parseInt(timeOutput.substring(9));
        int in = Integer.parseInt(timeInput.substring(9));
        if (out - in> 0)
            retention = out-in;
        else
            retention = out+1000000-in;
    }

    public String getType() {
        return type;
    }

    public Integer getRetention() {
        return retention;
    }

    @Override
    public String toString() {
        return "Request{" +
                "idRequest='" + idRequest + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}

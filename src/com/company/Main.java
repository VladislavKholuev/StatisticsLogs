package com.company;

public class Main {

    public static void main(String[] args) {
        if(args.length>0){
            WorkWithLogs w = new WorkWithLogs(args[0],"fileOut.csv");
            w.WorkWithFile();
        }
    }
}

package com.company;

import java.io.*;
import java.nio.file.Paths;
import java.util.*;

public class WorkWithLogs  {
    private String workFile;
    private File outFile;
    private HashMap<String,String> mapReq = new HashMap<>();
    private ArrayList<DataAnalysis> ListDataAnalysisToWrite = new ArrayList<>();
    public WorkWithLogs(String file, String fileOut){
        workFile = file;
        outFile = new File(fileOut);
    }

    public void WorkWithFile(){
        try {
            Scanner scanner = new Scanner(Paths.get(workFile));
            String date = "";
            String timeInlog;
            String Minute = "";
            DataAnalysis newDataAnalysis = null;
            String s;
            while (scanner.hasNextLine()) {
                s = scanner.nextLine();
                timeInlog = s.substring(0,8);
                if(!timeInlog.equals(date)) {
                    if(newDataAnalysis != null) {
                        ListDataAnalysisToWrite.add(newDataAnalysis);
                    }
                    newDataAnalysis = new DataAnalysis((timeInlog));
                    date = timeInlog;
                }
                Request req = ParseString(s);
                if(req != null)
                    newDataAnalysis.getList().add(req);

                if(!Minute.equals(s.substring(0,5))) {
                    for (DataAnalysis dat:ListDataAnalysisToWrite) {
                        dat.Work();
                    }
                    WriteInFile();
                    ListDataAnalysisToWrite.clear();
                    Minute = s.substring(0,5);
                }
            }

            //Work with last
            ListDataAnalysisToWrite.add(newDataAnalysis);
            for (DataAnalysis dar: ListDataAnalysisToWrite) {
              dar.Work();
            }
            WriteInFile();
            ListDataAnalysisToWrite.clear();
            mapReq.clear();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    private Request ParseString(String s){
        Request req = null;
        if (!s.contains("SQLProxy") && !s.contains("P2_COD") && !s.contains("Error") && s.contains("type")) {
            StringTokenizer strT = new StringTokenizer(s, ";");
            String time = strT.nextToken();
            strT.nextToken("id");
            String id = strT.nextToken(",").substring(3);
            strT.nextToken();
            strT.nextToken();
            strT.nextToken();
            String typeReq = strT.nextToken().substring(6);

            if (s.contains("input")) {
                mapReq.put(id, time);
            }

            if (s.contains("reply") && mapReq.containsKey(id)) {
                req = new Request(id, time, mapReq.get(id), typeReq);
                mapReq.remove(id);
            }
        }
        return req;
    }
    /**
     * write in csv file
     */
    private void WriteInFile() throws IOException {

        FileWriter writer  = new FileWriter(outFile.getAbsoluteFile(), true);
        BufferedWriter bufferWriter = new BufferedWriter(writer);

        for (DataAnalysis dat:ListDataAnalysisToWrite) {
            for (ArrayList<String> ar: dat.getStatistics()) {
                StringBuilder builder = new StringBuilder();
                for (String str: ar) {
                    builder.append(str).append(",");
                }
                builder.deleteCharAt(builder.length()-1);
                builder.append("\n");
                bufferWriter.write(builder.toString());
            }
        }
        bufferWriter.close();
    }
}

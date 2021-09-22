package edu.jsu.mcis;

import java.io.*;
import java.util.*;
import com.opencsv.*;
import org.json.simple.*;
import org.json.simple.parser.*;

public class Converter {
    
    
    
        /*Consider the following CSV data:
        
        "ID","Total","Assignment 1","Assignment 2","Exam 1"
        "111278","611","146","128","337"
        "111352","867","227","228","412"
        "111373","461","96","90","275"
        "111305","835","220","217","398"
        "111399","898","226","229","443"
        "111160","454","77","125","252"
        "111276","579","130","111","338"
        "111241","973","236","237","500"
        
        /*The corresponding JSON data would be similar to the following (tabs and
        other whitespace have been added for clarity).  Note the curly braces,
        square brackets, and double-quotes!  These indicate which values should
        be encoded as strings, and which values should be encoded as integers!
        
        {
            "colHeaders":["ID","Total","Assignment 1","Assignment 2","Exam 1"],
            "rowHeaders":["111278","111352","111373","111305","111399","111160",
            "111276","111241"],
            "data":[[611,146,128,337],
                    [867,227,228,412],
                    [461,96,90,275],
                    [835,220,217,398],
                    [898,226,229,443],
                    [454,77,125,252],
                    [579,130,111,338],
                    [973,236,237,500]
            ]
        }
    
        Your task for this program is to complete the two conversion methods in
        this class, "csvToJson()" and "jsonToCsv()", so that the CSV data shown
        above can be converted to JSON format, and vice-versa.  Both methods
        should return the converted data as strings, but the strings do not need
        to include the newlines and whitespace shown in the examples; again,
        this whitespace has been added only for clarity.
    
        NOTE: YOU SHOULD NOT WRITE ANY CODE WHICH MANUALLY COMPOSES THE OUTPUT
        STRINGS!!!  Leave ALL string conversion to the two data conversion
        libraries we have discussed, OpenCSV and json-simple.  See the "Data
        Exchange" lecture notes for more details, including example code.
    
    */
    
    @SuppressWarnings("unchecked")
    public static String csvToJson(String csvString) {
        
        String results = "";
        
        try {
            
            CSVReader reader = new CSVReader(new StringReader(csvString));
            List<String[]> full = reader.readAll();
            Iterator<String[]> iterator = full.iterator();
            
            
            
            // new empty containers
            JSONObject json = new JSONObject();         
            JSONArray rowHeaders = new JSONArray();
            JSONArray colHeaders = new JSONArray();
            JSONArray data = new JSONArray();
            JSONArray holder;
            String[] record = iterator.next();
            
            // loop to go through columns
           for(int i = 0; i < record.length; i++){
                colHeaders.add(record[i]);
            }
            
            // while loop go through all data until false or condition is no 
            //longer true
            
            while(iterator.hasNext()){
                holder = new JSONArray();
                record = iterator.next();
                rowHeaders.add(record[0]);
                for(int i = 1; i < record.length; i++){
                    int stringHolder = Integer.parseInt(record[i]);
                    holder.add(stringHolder);
            }
                // adds all revised data to holder container
                    data.add(holder);
                 
                    
            }

         
            json.put("colHeaders", colHeaders); // add column headers to JSON object
            json.put("rowHeaders", rowHeaders); // add row headers to JSON object
            json.put("data", data); // add "big array" of data to JSON object
            results = JSONValue.toJSONString(json); // "flatten" JSON object to a JSON string
     
        }
        catch(Exception e) { e.printStackTrace(); }
        
        return results.trim(); // return JSON string
        
    }
    
    public static String jsonToCsv(String jsonString) {
         
        String results = "";
        
        try {

            StringWriter writer = new StringWriter();
            CSVWriter csvWriter = new CSVWriter(writer, ',', '"', '\\', "\n");
            
            
            //utilization of variables for JSonArray to put data in
            JSONParser parser = new JSONParser();
            JSONObject json = (JSONObject) parser.parse(jsonString);
            
            JSONArray colHeaders = (JSONArray) json.get("colHeaders");
            JSONArray rowHeaders = (JSONArray) json.get("rowHeaders");
            
            JSONArray data = (JSONArray) json.get("data");
            JSONArray holder;
            
            String [] record = new String[colHeaders.size()];
            
            // for loop for the amount of columns 
            for(int i = 0; i < colHeaders.size(); i++){
                record[i] = (String) colHeaders.get(i);
            
           }                   
           csvWriter.writeNext(record);
           
           // for loop puts data in rows
           for(int i = 0; i < data.size(); i++){
               
               // stores grades into overall data container and inserts it into JSONARRAY
                holder = (JSONArray) data.get(i);
                //adds rows 
                record = new String[holder.size() + 1];
                // initializes first column to  hold id's , assignments, etc
                record[0] =(String) rowHeaders.get(i);
                
                // for loop for grades in each row excepts the inital row wit id's
                for(int j = 0; j < holder.size(); j++){
                    record[j + 1] = Long.toString((long)holder.get(j));
                          
           }
                 // adds following rows to records
                 csvWriter.writeNext(record);
                          
            
        } 
           
           results = writer.toString();
        }
        
        catch(Exception e) { e.printStackTrace(); }
        
        return results.trim();
        
    }
   

}
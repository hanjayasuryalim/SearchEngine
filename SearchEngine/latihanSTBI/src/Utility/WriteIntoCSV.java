/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utility;

import Model.NoDocValue;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;


/**
 *
 * @author user
 */
public class WriteIntoCSV {
    public static void save(HashMap<String, ArrayList<NoDocValue>> dict, String target){
        String data = "";
        Set<String> keySort = dict.keySet();
        List<String> keys =  StringSorter.sortStringList(new ArrayList<String>(keySort));
        data = "";
        
        for (int j = 0; j < keys.size();j++) {
            String key = keys.get(j);
            if (!key.equals("")){
              data += key + "= [";

              ArrayList<NoDocValue> values = dict.get(key);
              for (int i = 0; i < values.size(); i++) {
                  data += String.valueOf(values.get(i).getValue()) + ", ";
              }

              data = data.substring(0, data.length()-2);
              data +="]\n";
              
              WriteFile.writeData(data, target);
              data = "";
              System.out.println("Row "+j+" Write Suceed");
            }
        }
    }
    
    public static void saveIDF(HashMap<String,Double> dict, String target){
        String data = "";
        Set<String> keySort = dict.keySet();
        List<String> keys =  StringSorter.sortStringList(new ArrayList<String>(keySort));
        data = "";
        
        for (int j = 0; j < keys.size();j++) {
            String key = keys.get(j);
            if (!key.equals("")){
              data += key + "= ";

              Double values = dict.get(key);
             

              data+=values+"\n";
              
              WriteFile.writeData(data, target);
              data = "";
              System.out.println("Row "+j+" Write Suceed");
            }
        }
    }
}

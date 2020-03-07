/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utility;

import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author adit
 */
public class LoadDict {
    public static HashMap<String,ArrayList<Integer>> loadTitle(String path){
        HashMap<String,ArrayList<Integer>> hm = new HashMap<>();
        ArrayList<String> rows = ReadFile.inputFile(path);
        for (int i = 0; i < rows.size(); i++) {
            String row = rows.get(i);
            String token = row.split("\t")[0];
            if (token.equals("") == false){
                String [] index = row.split("\t");
                ArrayList<Integer> idDokumen = new ArrayList<>();
                for (int j = 1; j < index.length; j++) {
                    String intString = index[j];
                    idDokumen.add(Integer.valueOf(intString));
                }
                hm.put(token, idDokumen);
            }
            
        }
        
        return hm;
    }
}

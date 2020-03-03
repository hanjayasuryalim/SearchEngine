/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utility;


import java.util.*;
public class test {
    
  
    public static ArrayList sortbykey(HashMap<String,ArrayList<Integer>> hm){
        TreeMap<String,ArrayList<Integer>> sorted = new TreeMap<>();
        ArrayList<String> sortedKey = new ArrayList<>(); 
        sorted.putAll(hm);
        
        for (Map.Entry<String,ArrayList<Integer>> entry : sorted.entrySet()) {
            ArrayList<Integer> v = entry.getValue();
            String number = "";
            for (Integer integer : v) {
                number+=integer+" ";
            }
            sortedKey.add(entry.getKey());
        }
        return sortedKey; 
    }
    
}

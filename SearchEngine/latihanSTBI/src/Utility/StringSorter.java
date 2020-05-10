/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utility;

import java.util.ArrayList;

/**
 *
 * @author USER
 */
public class StringSorter {
    public static ArrayList<String> sortStringList(ArrayList<String> stringList){
        int size = stringList.size();

        for(int i = 0; i<size-1; i++) {
           for (int j = i+1; j<stringList.size(); j++) {
              if(stringList.get(i).compareTo(stringList.get(j))>0) {
                 String temp = stringList.get(i);
                 stringList.set(i,stringList.get(j));
                 stringList.set(j,temp);
              }
           }
        }
        /*
        for (int i = 0;  i<size-1; i++) {
            System.out.println(stringList.get(i));
            
        }
        */
        return stringList;
    }
}


/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vektorruang;

import Utility.ReadCSV;
import java.util.HashMap;
import java.util.ArrayList;
import Model.NoDocValue;
import java.lang.Math;
import static Utility.WriteIntoCSV.save;
import static Utility.WriteIntoCSV.saveIDF;

/**
 *
 * @author user
 */
public class TFIDF {

    public static void main(String[] args) {
        String pathBody = "XML/FrequencyBody.csv";
        String pathTitle = "XML/FrequencyTitle.csv";
        String pathDate = "XML/FrequencyDate.csv";
        HashMap<String, ArrayList<NoDocValue>> hmDate = new HashMap<>();
        HashMap<String, ArrayList<NoDocValue>> hmTitle = new HashMap<>();
        HashMap<String, ArrayList<NoDocValue>> hmBody = new HashMap<>();

        hmDate = ReadCSV.getDataCSV(pathDate);
        hmBody = ReadCSV.getDataCSV(pathBody);
        hmTitle = ReadCSV.getDataCSV(pathTitle);

        // ===============================================================CREATE TF HASH MAP
//        HashMap<String,ArrayList<NoDocValue>> hmDateTF = new HashMap<>();
//        HashMap<String,ArrayList<NoDocValue>> hmTitleTF = new HashMap<>();
//        HashMap<String,ArrayList<NoDocValue>> hmBodyTF = new HashMap<>();
//        
//        hmDateTF = getHashMapTF(hmDate);
//        hmTitleTF = getHashMapTF(hmTitle);
//        hmBodyTF = getHashMapTF(hmBody);
        
        //CHECKING
//        printCheckHashMap(hmDateTF,"Date");
//        printCheckHashMap(hmBodyTF,"Body");
//        printCheckHashMap(hmTitleTF,"Title");
        //SAVING INTO CSV
//        save(hmDateTF,"XML/TFDate.csv");
//        save(hmBodyTF,"XML/TFBody.csv");
//        save(hmTitleTF,"XML/TFTitle.csv");
        //==============================================================- CREATE IDF HASH MAP
        HashMap<String,Double> hmDateIDF = new HashMap<>();
        HashMap<String,Double> hmTitleIDF = new HashMap<>();
        HashMap<String,Double> hmBodyIDF = new HashMap<>();
//        
        hmDateIDF = getIDF(hmDate);
        hmTitleIDF = getIDF(hmTitle);
        hmBodyIDF = getIDF(hmBody);
        //SAVING INTO CSV
          saveIDF(hmDateIDF,"XML/IDFDate.csv");
          saveIDF(hmBodyIDF,"XML/IDFBody.csv");
          saveIDF(hmTitleIDF,"XML/IDFTitle.csv");
//                
    }

    //TF
    public static double getTF(double value) {
        if (value == 0) {
            return 0;
        } else {
            return 1 + Math.log10(value);
        }
    }

    public static ArrayList<NoDocValue> getArrayListTF(ArrayList<NoDocValue> arrayListInput) {
        ArrayList<NoDocValue> newList = new ArrayList<>();
        int index = 0;

        for (NoDocValue inputObj : arrayListInput) {
            double x = getTF(inputObj.getValue());
            NoDocValue obj = new NoDocValue(index, x);
            newList.add(obj);
            index++;
        }

        return newList;
    }

    public static HashMap<String, ArrayList<NoDocValue>> getHashMapTF(HashMap<String, ArrayList<NoDocValue>> hm) {
        HashMap<String, ArrayList<NoDocValue>> returnHm = new HashMap<>();

        for (String key : hm.keySet()) {
            ArrayList<NoDocValue> returnArrayList = getArrayListTF(hm.get(key));
            returnHm.put(key, returnArrayList);
        }

        return returnHm;
    }

    public static void printCheckHashMap(HashMap<String, ArrayList<NoDocValue>> hm, String category) {
        System.out.println("===============================================================================\n");
        System.out.println("\t\t\t\t" + category);
        System.out.println("\n===============================================================================");
        for (String key : hm.keySet()) {
            System.out.println(key);
            for (NoDocValue item : hm.get(key)) {
                System.out.print(item.getValue() + " ");
            }
            System.out.println("\n==========================================\n\n");
        }
    }

    //IDF
    public static HashMap<String, Double> getIDF(HashMap<String, ArrayList<NoDocValue>> hm) {
        HashMap<String, Double> returnHm = new HashMap<>();

        for (String key : hm.keySet()) {
            //Karena termasuk query counter (ni) mulai dari 1
            int counter = 1;
            for (NoDocValue obj : hm.get(key)) {
                if (obj.getValue() != 0) {
                    counter++;
                }
            }
            //Document ada 500 + 1 query => N = 501
            double idf = Math.log10(1 + (501 / counter));
            returnHm.put(key, idf);
        }

        return returnHm;

    }

}

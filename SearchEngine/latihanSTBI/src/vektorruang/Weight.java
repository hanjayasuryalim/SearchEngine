/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vektorruang;

import Model.NoDocValue;
import Utility.ReadCSV;
import Utility.ReadCSVWithoutZero;
import static Utility.WriteIntoCSV.saveWeight;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author user
 */
public class Weight {
    
    public static long startTime, endTime, timeElapsed;
    public static void main(String[] args) {
        startTime = System.nanoTime();
        String pathBodyIDF = "XML/IDFBody.csv";
        String pathTitleIDF = "XML/IDFTitle.csv";
        String pathDateIDF = "XML/IDFDate.csv";
        String pathBodyTF = "XML/TFBody.csv";
        String pathTitleTF = "XML/TFTitle.csv";
        String pathDateTF = "XML/TFDate.csv";
        HashMap<String, ArrayList<NoDocValue>> hmDateTF = new HashMap<>();
        HashMap<String, ArrayList<NoDocValue>> hmTitleTF = new HashMap<>();
        HashMap<String, ArrayList<NoDocValue>> hmBodyTF = new HashMap<>();

        //GET TF WITHOUT ZERO
        hmDateTF = ReadCSVWithoutZero.getDataCSV(pathDateTF);
        hmBodyTF = ReadCSVWithoutZero.getDataCSV(pathBodyTF);
        hmTitleTF = ReadCSVWithoutZero.getDataCSV(pathTitleTF);

        //GET IDF
        HashMap<String, Double> hmDateIDF = new HashMap<>();
        HashMap<String, Double> hmTitleIDF = new HashMap<>();
        HashMap<String, Double> hmBodyIDF = new HashMap<>();

        hmDateIDF = ReadCSV.getDataCSVIDF(pathDateIDF);
        hmBodyIDF = ReadCSV.getDataCSVIDF(pathBodyIDF);
        hmTitleIDF = ReadCSV.getDataCSVIDF(pathTitleIDF);

        //CREATE WEIGHT 
        HashMap<String, ArrayList<NoDocValue>> hmDateWeight = new HashMap<>();
        HashMap<String, ArrayList<NoDocValue>> hmTitleWeight = new HashMap<>();
        HashMap<String, ArrayList<NoDocValue>> hmBodyWeight = new HashMap<>();

        hmDateWeight = calculateWeightHashMap(hmDateTF, hmDateIDF);
        hmTitleWeight = calculateWeightHashMap(hmTitleTF, hmTitleIDF);
        hmBodyWeight = calculateWeightHashMap(hmBodyTF, hmBodyIDF);

        //SAVE WEIGHT
        saveWeight(hmDateWeight,"XML/WeightDate.csv");
        saveWeight(hmBodyWeight,"XML/WeightBody.csv");
        saveWeight(hmTitleWeight,"XML/WeightTitle.csv");
        endTime = System.nanoTime();
        timeElapsed = endTime-startTime;
        System.out.println(timeElapsed);
        //checking
//        printCheckHashMap(hmDateWeight,"Date");
//        printCheckHashMap(hmTitleWeight,"Title");
//        printCheckHashMap(hmBodyWeight,"Body");
    }

    public static void printCheckHashMap(HashMap<String, ArrayList<NoDocValue>> hm, String category) {
        System.out.println("===============================================================================\n");
        System.out.println("\t\t\t\t" + category);
        System.out.println("\n===============================================================================");
        for (String key : hm.keySet()) {
            System.out.println(key);
            for (NoDocValue item : hm.get(key)) {
                System.out.print(item.getNoDoc() + " : " + item.getValue() + " --- ");
            }
            System.out.println("\n==========================================\n\n");
        }
    }

    public static void printCheckHashMapIDF(HashMap<String, Double> hm, String category) {
        System.out.println("===============================================================================\n");
        System.out.println("\t\t\t\t" + category);
        System.out.println("\n===============================================================================");
        for (String key : hm.keySet()) {
            System.out.println(key + " = " + hm.get(key));
        }
    }

    public static HashMap<String, ArrayList<NoDocValue>> calculateWeightHashMap(HashMap<String, ArrayList<NoDocValue>> hmTF, HashMap<String, Double> hmIDF) {
        HashMap<String, ArrayList<NoDocValue>> returnHm = new HashMap<>();
        double value;

        for (String string : hmTF.keySet()) {
            ArrayList<NoDocValue> al = null;

            if (hmIDF.get(string) == 0.0) {
                returnHm.put(string, al);
            } else {
                al = new ArrayList<>();
                for (NoDocValue obj : hmTF.get(string)) {
                    value = obj.getValue() * hmIDF.get(string);

                    al.add(new NoDocValue(obj.getNoDoc(), value));
                }

                returnHm.put(string, al);
            }
        }
        return returnHm;
    }
}

package vektorruang;

import Model.NoDocValue;
import Utility.ReadCSV;
import java.util.ArrayList;
import java.util.HashMap;

public class SearchTFIDF {
    
    //Path untuk ke CSV Weight
    private static final String PATHWEIGHTTITLE = "XML/WeightTitle.csv";
    private static final String PATHWEIGHTBODY = "XML/WeightBody.csv";
    private static final String PATHWEIGHTDATE = "XML/WeightDate.csv";
    
    //Path untuk ke CSV IDF
    private static final String PATHIDFTITLE = "XML/IDFTitle";
    private static final String PATHIDFBODY = "XML/IDFBody";
    private static final String PATHIDFDATE = "XML/IDFDate";
    
    private static HashMap<String, ArrayList<NoDocValue>> dictWeight;
    private static HashMap<String, Double> dictIDF;
    
    //HashMap ini akan menyimpan data no document dan array list yang isinya weight (index ke-0) dan index lainnya weight kuadrat dari term
    private static HashMap<Integer, ArrayList<Double>> arrDocWeight;
    
    //Query yang dimasukkan adalah query yang sudah dipreprocessing
    public static void getRanking(String query, String type)  {
        String[] arrQuery = query.split(" ");
        
        if (type.toLowerCase().equals("title")) {
            dictWeight = ReadCSV.getDataCSVWeight(PATHWEIGHTTITLE);
            dictIDF = ReadCSV.getDataCSVIDF(PATHIDFTITLE);
        } else if (type.toLowerCase().equals("date")) {
            dictWeight = ReadCSV.getDataCSVWeight(PATHWEIGHTDATE);
            dictIDF = ReadCSV.getDataCSVIDF(PATHIDFDATE);
        } else {
            dictWeight = ReadCSV.getDataCSVWeight(PATHWEIGHTBODY);
            dictIDF = ReadCSV.getDataCSVIDF(PATHIDFBODY);
        }
        
        //Getting query frequency
        HashMap<String, NoDocValue> queryFrequency = Frequency.getQueryFrequency(query);
        
        for (String term : arrQuery) {
            //Get frequency and idf
            double frequency = queryFrequency.get(term).getValue();
            double idf = dictIDF.get(term);
            
            NoDocValue queryWeight = new NoDocValue(0, getQueryWeight(frequency, idf));
            
            dictWeight.get(idf).add(queryWeight);
        }
    }
    
    private static double getQueryWeight(double frequency, double idf) {
        double tfQuery;
        if (frequency == 0.0) {
            tfQuery = 0;
        } else {
            tfQuery = Math.log10(frequency);
        }
        
        double weightQuery = tfQuery * idf;
        return weightQuery;
    }
    
    public static double squareWeight(double weight) {
        return (weight * weight);
    }
    
    public static void main(String[] args) {
        SearchTFIDF.getRanking("Hello", "Title");
    }
}

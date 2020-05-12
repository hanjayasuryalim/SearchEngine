package vektorruang;

import LanguageModel.RankedItem;
import Model.NoDocValue;
import Utility.ReadCSV;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class SearchTFIDF {
    //variable to combine all ranking
    private static ArrayList<RankedItem> TotalListRanking = new ArrayList<>();
    private static ArrayList<Integer> noDocumentInRanking = new ArrayList<>();
    //Path untuk ke CSV Weight
    private static final String PATHWEIGHTTITLE = "XML/WeightTitle.csv";
    private static final String PATHWEIGHTBODY = "XML/WeightBody.csv";
    private static final String PATHWEIGHTDATE = "XML/WeightDate.csv";
    
    //Path untuk ke CSV IDF
    private static final String PATHIDFTITLE = "XML/IDFTitle.csv";
    private static final String PATHIDFBODY = "XML/IDFBody.csv";
    private static final String PATHIDFDATE = "XML/IDFDate.csv";
    
    private static HashMap<String, ArrayList<NoDocValue>> dictWeight;
    private static HashMap<String, Double> dictIDF;
    
    public static long startTime, endTime, timeElapsed;
    
    //HashMap ini akan menyimpan data no document dan ArrayList 
    /*ArrayList ini akan menyimpan: 
        pada index ke-0: jumlah dari weight term i pada dokumen j dikalikan weight query term i (w i,j * w i,q)
        pada index ke-1: jumlah dari weight term i pada dokuemn j dikuadaratkan (w i,j ^ 2)
        pada index ke-2: |dj| => akar dari jumlah weight term i pada dokumen j dikuadratkan
        Sehingga panjang ArrayList ini akan banyak term unik pada query dikalikan 2 (i * 2)
    */
    private static HashMap<Integer, ArrayList<Double>> arrDocWeight = new HashMap<>();
    private static ArrayList<Integer> relatedNoDoc = new ArrayList<>();
   
    private static HashMap<Double, ArrayList<Integer>> arrSim = new HashMap<>();
    private static ArrayList<Double> keySetOfArrSim = new ArrayList<>();
    
    //Query yang dimasukkan adalah query yang sudah dipreprocessing
    public static void getRanking(String query, String type)  {
        String[] arrQuery = query.split(" ");
        arrDocWeight = new HashMap<>();
        relatedNoDoc = new ArrayList<>();
        
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
        
        //System.out.println("Document weight, weight ^ 2");
        for (String term : arrQuery) {
            //Get frequency and idf
            double frequency = queryFrequency.get(term).getValue();
            double idf;
            if(dictIDF.get(term)!=null){
                idf = dictIDF.get(term);
            }else{
                idf = 0;
            }
            
            
            NoDocValue queryWeight = new NoDocValue(0, getQueryWeight(frequency, idf));
            
            if(dictWeight.containsKey(term)){
                dictWeight.get(term).add(queryWeight);
                //For filling arrDocWeight
                fillArrDocWeight(dictWeight.get(term), queryWeight.getValue());
            }
        }
        //System.out.println("\n\n");
        
        //Iterate through arrDocWeight to get |dj|
        //System.out.println("Document |dj| : ");
        for (int noDoc : relatedNoDoc) {
            arrDocWeight.get(noDoc).add(2, squareRootDouble(arrDocWeight.get(noDoc).get(1)));
            //System.out.println("No Doc: " + noDoc + " - " + arrDocWeight.get(noDoc).get(2)+" -- "+arrDocWeight.get(noDoc).get(0)+" -- ");          
        }
        //System.out.println("\n\n");
        
        //Iterate to get similarity of document j and query ( sim(dj,q) )
        for (int noDoc : relatedNoDoc) {
            if (noDoc != 0) {
                double sim = arrDocWeight.get(noDoc).get(0) / (arrDocWeight.get(noDoc).get(2) * arrDocWeight.get(0).get(2));
                
                if (arrSim.containsKey(sim)) {
                    arrSim.get(sim).add(noDoc);
                } else {
                    ArrayList<Integer> newNoDoc = new ArrayList<>();
                    newNoDoc.add(noDoc);
                    
                    arrSim.put(sim, newNoDoc);
                    
                    keySetOfArrSim.add(sim);
                }
            }
        }
        
        Collections.sort(keySetOfArrSim, Collections.reverseOrder());
        
        ArrayList<Integer> listRanking = printRanking();
    }
    
    private static void fillArrDocWeight(ArrayList<NoDocValue> termWeight, double queryTermWeight) {
        int noDoc;
        double weight;
        double squareWeight;
        
        for (NoDocValue newWeight: termWeight) {
            noDoc = newWeight.getNoDoc();
            weight = newWeight.getValue();
            squareWeight = squareDouble(weight);
            
            if (arrDocWeight.containsKey(noDoc)) {
                //If no document already in arrDocWeight, update the weight at index 0 and 1
                arrDocWeight.get(noDoc).set(0, arrDocWeight.get(noDoc).get(0) + (weight * queryTermWeight));
                arrDocWeight.get(noDoc).set(1, arrDocWeight.get(noDoc).get(1) + squareWeight);
            } else {
                ArrayList<Double> newArrayList = new ArrayList<>();
                newArrayList.add(0, weight * queryTermWeight);
                newArrayList.add(1, squareWeight);

                arrDocWeight.put(noDoc, newArrayList);
                
                //Add no document to ArrayList relatedNoDoc
                relatedNoDoc.add(noDoc);
               // System.out.println("No Doc : " + noDoc + " - " + weight + ", " + squareWeight);
            }
        }
    }
    
    private static double getQueryWeight(double frequency, double idf) {
        double tfQuery;
        if (frequency == 0.0) {
            tfQuery = 0;
        } else {
            tfQuery = 1 + Math.log10(frequency);
        }
        
        double weightQuery = tfQuery * idf;
        return weightQuery;
    }
    
    private static ArrayList<Integer> printRanking() {
        ArrayList<Integer> listRanking = new ArrayList<>();
        
        //System.out.println("Ranking : ");
        //System.out.println("-------------------------------------------------");
        for (int i = 0; i < keySetOfArrSim.size(); i++) {
            String documents = "";
            for (int noDoc : arrSim.get( keySetOfArrSim.get(i) )) {
                if (!listRanking.contains(noDoc)) {
                    documents += noDoc + ", ";
                }
                if(!noDocumentInRanking.contains(noDoc)){
                    noDocumentInRanking.add(noDoc);
                    
                    RankedItem newItem = new RankedItem();
                    newItem.setDocId(noDoc);
                    newItem.setScore(keySetOfArrSim.get(i));
                    
                    TotalListRanking.add(newItem);
                }
            }
            
            //System.out.println("No. " + (i+1) + " : " + documents.substring(0, documents.length()-2) + " with similarity = " + keySetOfArrSim.get(i));
        }
        
        return listRanking;
    }
    
    public static double squareRootDouble(double sumOfSquareWeight) {
        return Math.sqrt(sumOfSquareWeight);
    }
    
    public static double squareDouble(double weight) {
        return (weight * weight);
    }
    
    public static ArrayList<RankedItem> getTFIDFRanking(String query) {
        TotalListRanking.clear();
        noDocumentInRanking.clear();
        arrDocWeight.clear();
        relatedNoDoc.clear();
        arrSim.clear();
        keySetOfArrSim.clear();
        
        startTime = System.nanoTime();
        String[] arr = query.split(" ");
        String preprocessed = "";
        for (String string : arr) {
            preprocessed += preprocessing.PreProcess.singleWordPreprocess(string)+" ";
        }
        preprocessed = preprocessed.substring(0,preprocessed.length()-1);

        SearchTFIDF.getRanking(preprocessed, "Body");
        SearchTFIDF.getRanking(preprocessed, "Title");
        SearchTFIDF.getRanking(preprocessed, "Date");
        //System.out.println("\n\n\n");
        /*
        for (RankedItem rank : TotalListRanking) {
            System.out.println(rank.getDocId());
        }
        */
        endTime = System.nanoTime();
        return TotalListRanking;
    }
    
    public static long getTimeElapsed(){
        timeElapsed = endTime-startTime;
        return timeElapsed;
    }
    
//    public static void main(String[] args) {
//        String query="Analyst appeared";
//        String[]arr=query.split(" ");
//        String preprocessed="";
//        for (String string : arr) {
//            preprocessed += preprocessing.PreProcess.singleWordPreprocess(string)+" ";
//        }
//        preprocessed = preprocessed.substring(0,preprocessed.length()-1);
//
//        SearchTFIDF.getRanking(preprocessed, "Body");
//        SearchTFIDF.getRanking(preprocessed, "Title");
//        SearchTFIDF.getRanking(preprocessed, "Date");
//        //System.out.println("\n\n\n");
////        for (Integer rank : TotalListRanking) {
////            //System.out.println(rank);
////        }
//    }
}

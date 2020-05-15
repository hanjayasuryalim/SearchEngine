package LanguageModel;

import Search.Search;
import Utility.ReadCSV;
import Utility.ReadFile;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import preprocessing.PreProcess;

public class LM{
    private String query;
    private ArrayList<Integer> listDocsId;
    private static String path = "XML/";
    private int totalWord;
    private ArrayList<Integer> totalWordPerDoc;
    private HashMap<String, ArrayList<Integer>> termFrequency;
    private HashMap<String, Integer> termTotalFrequency;
    private double lambda = 0.5;
    private ArrayList<Integer> rankedDocNo;
    private String [] cleanTokens;
    public static long startTime, endTime, timeElapsed;
    private static HashMap<String,ArrayList<Integer>> hmDate = ReadCSV.getIntegerCSV(path + "FrequencyDate.csv");
    private static HashMap<String,ArrayList<Integer>> hmTitle = ReadCSV.getIntegerCSV(path + "FrequencyBody.csv");
    private static HashMap<String,ArrayList<Integer>> hmBody = ReadCSV.getIntegerCSV(path + "FrequencyTitle.csv");
        
    
    
    public LM(String query){
        // START
        startTime = System.nanoTime();
        this.query = query;
        
        
        //Search query dengan mode OR
        
        
        listDocsId = new ArrayList<>();
        
        
        String [] words = query.split(" ");
        
        for (int i = 0; i < words.length; i++) {
            words[i] = PreProcess.singleWordPreprocess(words[i]);
            
        }
        
        for (int i = 0; i < words.length; i++) {
            //System.out.println(words[i]);
            ArrayList<Integer> dateArray = hmDate.get(words[i]);
            if (dateArray != null){
                for (int j = 0; j < dateArray.size(); j++) {
                    if (dateArray.get(j) > 0){    
                        listDocsId.add(j+1);
                    }
                }
            }
            
            ArrayList<Integer> titleArray = hmTitle.get(words[i]);
            if (titleArray != null){
                for (int j = 0; j < titleArray.size(); j++) {
                    if (titleArray.get(j) > 0){    
                        listDocsId.add(j+1);
                    }
                }
            }
            ArrayList<Integer> bodyArray = hmBody.get(words[i]);
            if (bodyArray != null){
                for (int j = 0; j < bodyArray.size(); j++) {
                    if (bodyArray.get(j) > 0){    
                        listDocsId.add(j+1);
                    }
                }
            }
        }
        
        Collections.sort(listDocsId);
        
        for(int i = 1; i < listDocsId.size(); i++) {
            while (i < listDocsId.size() && listDocsId.get(i).equals(listDocsId.get(i-1))){
                listDocsId.remove(i);
            }
            
        }

        //System.out.println("Founded = " + listDocs.size());
        
        // Membuka file yang isinya jumlah word per dokumen yang dipecah jadi 3 (untuk pengembangan)
        String [] bodyCount = ReadFile.inputSingleLineFile(path + "BodyCount.csv").split(",");
        String [] dateCount = ReadFile.inputSingleLineFile(path + "DateCount.csv").split(",");
        String [] titleCount = ReadFile.inputSingleLineFile(path + "TitleCount.csv").split(",");
        
        
        
        
        
        //System.out.println("");
        
        
        // Menyimpan nilai total word dan jumlah word per dokumen yang ditemukan
        totalWordPerDoc = new ArrayList<>();
        totalWord = 0;
        for (int i = 0; i < listDocsId.size(); i++) {
            int index = listDocsId.get(i)-1;
            int temp = Integer.valueOf(bodyCount[index]);
            temp += Integer.valueOf(dateCount[index]);
            temp += Integer.valueOf(titleCount[index]);
            totalWord += temp;
            totalWordPerDoc.add(temp);
            //System.out.print(String.valueOf(temp) + ","); 
        }
        //System.out.println("");
        
        
        
       
            
        query = String.join(" ", words);
        while (query.contains("  ")){
            query = query.replace("  "," ");
        }
        words = query.split(" ");
                
        termTotalFrequency = new HashMap<>();
        termFrequency = new HashMap<>();
        for (int i = 0; i < words.length; i++) {
            ArrayList<Integer> fullFreqBody = hmBody.get(words[i]);
            ArrayList<Integer> fullFreqDate = hmDate.get(words[i]);
            ArrayList<Integer> fullFreqTitle = hmTitle.get(words[i]);
            
            //System.out.println(words[i]);
            
            ArrayList<Integer> wordFreq = new ArrayList<>();
            
            int total = 0;
            for (int j = 0; j < listDocsId.size(); j++) {
                int index = listDocsId.get(j)-1;
                int temp = 0;
                if (fullFreqBody != null){
                    temp += fullFreqBody.get(index);
                }
                
                if (fullFreqDate != null){
                    temp += fullFreqDate.get(index);
                }
                
                if (fullFreqTitle != null){
                    temp += fullFreqTitle.get(index);
                }
                wordFreq.add(temp);
                total += temp;
            }
            termFrequency.put(words[i], wordFreq);
            termTotalFrequency.put(words[i], total);
        }
        this.cleanTokens = words;
    }

    //P(q|d) = 
    public ArrayList<RankedItem> calculateProbability(){
        String [] tokens = this.cleanTokens;
        ArrayList<RankedItem> rankedList = new ArrayList<>();
        ArrayList<Double> probs = new ArrayList<>();
        for (int i = 0; i < listDocsId.size(); i++) {
            
            RankedItem tempRankedItem = new RankedItem();
            tempRankedItem.docId = listDocsId.get(i);
            
            double probability = 1;
            for (int j = 0; j < tokens.length; j++) {
                String token = tokens[j];
                double fWordInDoc = Double.valueOf(termFrequency.get(token).get(i));
                double nWordInDoc = Double.valueOf(totalWordPerDoc.get(i));
                double fWordTotal = Double.valueOf(termTotalFrequency.get(token));
                double nWordTotal = Double.valueOf(totalWord);
                double temp = ((fWordInDoc/nWordInDoc)*lambda) + ((fWordTotal/nWordTotal)*(1.0-lambda));
                //System.out.println("((" + fWordInDoc + "/" + nWordInDoc + ")*" + lambda + ") + ((" + fWordTotal + "/" + nWordTotal + ")*(1.0-" + lambda + "))");
                probability = probability * temp;
            }
            
            tempRankedItem.score = probability;
            rankedList.add(tempRankedItem);
            
            probs.add(probability);
            //System.out.println(String.valueOf(probability));
        }
        
        Collections.sort(rankedList);        
        
        //System.out.println("RANKING : ");
        //System.out.println("Total Item = " + String.valueOf(rankedList.size()));
        for (int i = 0; i < rankedList.size(); i++) {
            String a = String.valueOf(rankedList.get(i).score) + " => " + String.valueOf(rankedList.get(i).docId);
            //System.out.println(a);
        }
        
        rankedDocNo = new ArrayList<>();
        //System.out.println("");
        for (int i = 0; i < listDocsId.size(); i++) {
            double p = 0.0;
            int index = 0;
            for (int j = 0; j < probs.size(); j++) {
                if (probs.get(j) > p){
                    p = probs.get(j);
                    index = j;
                }
            }
            //System.out.print(listDocsId.get(index) + ", ");
            //rankedDocNo.add(listDocsId.get(index));
            probs.set(index , 0.0);
        }
        
        // END
        endTime = System.nanoTime();
        return rankedList;
    }
    
    public long getTimeElapsed(){
        timeElapsed = endTime - startTime;
        return timeElapsed;
    }
    
    public static void main(String[] args) {
        LM langModel = new LM("Chairman closed the last");
        langModel.calculateProbability();
    }
}

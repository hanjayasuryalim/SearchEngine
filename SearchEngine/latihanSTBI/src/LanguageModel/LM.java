package LanguageModel;

import Search.Search;
import Utility.ReadCSV;
import Utility.ReadFile;
import java.util.ArrayList;
import java.util.HashMap;
import preprocessing.PreProcess;

public class LM{
    private String query;
    private ArrayList<Integer> listDocsId;
    private String path = "XML/";
    private int totalWord;
    private ArrayList<Integer> totalWordPerDoc;
    private HashMap<String, ArrayList<Integer>> termFrequency;
    private HashMap<String, Integer> termTotalFrequency;
    private double lambda = 0.5;
    private ArrayList<Integer> rankedDocNo;
    
    
    LM(String query){
        this.query = query;
        
        
        //Search query dengan mode OR
        ArrayList<String> listDocs = Search.search(query, 0);
        System.out.println("Founded = " + listDocs.size());
        listDocsId = new ArrayList<>();
        listDocs.forEach(docName -> listDocsId.add(Integer.valueOf(docName)));
        
        // Membuka file yang isinya jumlah word per dokumen yang dipecah jadi 3 (untuk pengembangan)
        String [] bodyCount = ReadFile.inputSingleLineFile(path + "BodyCount.csv").split(",");
        String [] dateCount = ReadFile.inputSingleLineFile(path + "DateCount.csv").split(",");
        String [] titleCount = ReadFile.inputSingleLineFile(path + "TitleCount.csv").split(",");
        
        
        
        
        
        System.out.println("");
        
        
        // Menyimpan nilai total word dan jumlah word per dokumen yang ditemukan
        totalWordPerDoc = new ArrayList<>();
        totalWord = 0;
        for (int i = 0; i < listDocs.size(); i++) {
            int index = listDocsId.get(i)-1;
            int temp = Integer.valueOf(bodyCount[index]);
            temp += Integer.valueOf(dateCount[index]);
            temp += Integer.valueOf(titleCount[index]);
            totalWord += temp;
            totalWordPerDoc.add(temp);
            System.out.print(String.valueOf(temp) + ","); 
        }
        System.out.println("");
        
        HashMap<String,ArrayList<Integer>> hmDate = new HashMap<>();
        HashMap<String,ArrayList<Integer>> hmTitle = new HashMap<>();
        HashMap<String,ArrayList<Integer>> hmBody = new HashMap<>();
        
        hmDate = ReadCSV.getIntegerCSV(path + "FrequencyDate.csv");
        hmBody = ReadCSV.getIntegerCSV(path + "FrequencyBody.csv");
        hmTitle = ReadCSV.getIntegerCSV(path + "FrequencyTitle.csv");
        
        
        
        String [] words = query.split(" ");
        termTotalFrequency = new HashMap<>();
        termFrequency = new HashMap<>();
        for (int i = 0; i < words.length; i++) {
            words[i] = PreProcess.singleWordPreprocess(words[i]);
            ArrayList<Integer> fullFreqBody = hmBody.get(words[i]);
            ArrayList<Integer> fullFreqDate = hmDate.get(words[i]);
            ArrayList<Integer> fullFreqTitle = hmTitle.get(words[i]);
            
            System.out.println(words[i]);
            
            ArrayList<Integer> wordFreq = new ArrayList<>();
            
            int total = 0;
            for (int j = 0; j < listDocs.size(); j++) {
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
        
        calculateProbability(words);
        
    }

    //P(q|d) = 
    void calculateProbability(String [] tokens){
        ArrayList<Double> probs = new ArrayList<>();
        for (int i = 0; i < listDocsId.size(); i++) {
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
            probs.add(probability);
            System.out.println(String.valueOf(probability));
        }
        
        System.out.println("RANKING : ");
        
        rankedDocNo = new ArrayList<>();
        
        for (int i = 0; i < listDocsId.size(); i++) {
            double p = 0.0;
            int index = 0;
            for (int j = 0; j < probs.size(); j++) {
                if (probs.get(j) > p){
                    p = probs.get(j);
                    index = j;
                }
            }
            System.out.println(listDocsId.get(index));
            rankedDocNo.add(listDocsId.get(index));
            probs.set(index , 0.0);
        }
    }
    
    
    
    public static void main(String[] args) {
        LM langModel = new LM("9-Mar-87");
    }
}

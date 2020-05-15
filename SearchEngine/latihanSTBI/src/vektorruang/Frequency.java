package vektorruang;


import Model.NoDocValue;
import Utility.WriteFile;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Set;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import Utility.StringSorter;
import java.util.List;

public class Frequency {

    private ArrayList<String> termDate;
    private ArrayList<String> termTitle;
    private ArrayList<String> termBody;
    public static long startTime, endTime, timeElapsed;
    private HashMap<String, ArrayList<NoDocFrequency>> dictDate;
    private HashMap<String, ArrayList<NoDocFrequency>> dictTitle;
    private HashMap<String, ArrayList<NoDocFrequency>> dictBody;

    class NoDocFrequency {

        int noDoc;
        int frequency;

        public NoDocFrequency(int noDoc, int frequency) {
            this.noDoc = noDoc;
            this.frequency = frequency;
        }
    }

    public void countTotalWord(){
      
        ArrayList<Integer> dateCount = new ArrayList<>();
        ArrayList<Integer> bodyCount = new ArrayList<>();
        ArrayList<Integer> titleCount = new ArrayList<>();
        
        int n = 500;

        for(int i = 1; i <= n; i++){
            String from = "XML/Doc (" + i + ").xml";
            File f = new File(from);

            try {
                DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
                DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
                // Document doc = dBuilder.parse(is);
                Document doc = dBuilder.parse(f);

                String date = (doc.getElementsByTagName("date").getLength() <= 0) ? ""
                        : doc.getElementsByTagName("date").item(0).getTextContent();
                //System.out.println(date);
                while (date.contains("  ")) {
                    date = date.replace("  ", " ");
                }
                String[] arrDate = date.split(" ");
                //System.out.println(date);
                dateCount.add(arrDate.length);

                String title = (doc.getElementsByTagName("title").getLength() <= 0) ? ""
                        : doc.getElementsByTagName("title").item(0).getTextContent();

                while (title.contains("  ")) {
                    title = title.replace("  ", " ");
                }
                String[] arrTitle = title.split(" ");
                titleCount.add(arrTitle.length);

                String body = (doc.getElementsByTagName("body").getLength() <= 0) ? ""
                        : doc.getElementsByTagName("body").item(0).getTextContent();

                while (body.contains("  ")) {
                    body = body.replace("  ", " ");
                }
                String[] arrBody = body.split(" ");
                bodyCount.add(arrBody.length);
            } catch (Exception e) {
                e.printStackTrace();
            }
            
        }
        saveWordCount(titleCount, "XML/TitleCount.csv");
        saveWordCount(dateCount, "XML/DateCount.csv");
        saveWordCount(bodyCount, "XML/BodyCount.csv");
    }

    public void saveWordCount(ArrayList<Integer> list, String target){
        String s = list.get(0).toString();
        for (int i = 1; i < list.size(); i++) {
            s = s + "," + list.get(i).toString();
            WriteFile.writeData(s, target);
            s = "";
        }
        
    }


    public void countTermFrequency() {
        //To store key
        termDate = new ArrayList<>();
        termTitle = new ArrayList<>();
        termBody = new ArrayList<>();

        //To get all terms
        getAllTermsInDocuments();

        //To store frequency
        dictDate = new HashMap<>();
        dictTitle = new HashMap<>();
        dictBody = new HashMap<>();

        //To initialize all dictionary
        initializeDictionary();

        //For frequency
        getFrequencyFromDocuments();

        //Saving the frequency
        //System.out.println("Frequency of Date : ");
        csvDict(dictDate, "Date", "XML/FrequencyDate.csv");

        //System.out.println("Frequency of Title : ");
        csvDict(dictTitle, "Title", "XML/FrequencyTitle.csv");

        //System.out.println("Frequency of Body : ");
        csvDict(dictBody, "Body", "XML/FrequencyBody.csv");
    }

    private void getAllTermsInDocuments() {
        int n = 500;

        //To get all term in documents
        for (int i = 1; i <= n; i++) {
            String from = "XML/Doc (" + i + ").xml";

            // String text = ReadFile.inputBasicFile(from);
            // InputSource is = new InputSource();
            // is.setCharacterStream(new StringReader(text));
            File f = new File(from);

            try {
                DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
                DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
                // Document doc = dBuilder.parse(is);
                Document doc = dBuilder.parse(f);

                String date = (doc.getElementsByTagName("date").getLength() <= 0) ? ""
                        : doc.getElementsByTagName("date").item(0).getTextContent();
                //System.out.println(date);
                while (date.contains("  ")) {
                    date = date.replace("  ", " ");
                }
                String[] arrDate = date.split(" ");
                //System.out.println(date);
                for (String key : arrDate) {
                    if (!" ".equals(key) && !termDate.contains(key)) {
                        termDate.add(key);
                    }
                }

                String title = (doc.getElementsByTagName("title").getLength() <= 0) ? ""
                        : doc.getElementsByTagName("title").item(0).getTextContent();

                while (title.contains("  ")) {
                    title = title.replace("  ", " ");
                }
                String[] arrTitle = title.split(" ");
                for (String key : arrTitle) {
                    if (!" ".equals(key) && !termTitle.contains(key)) {
                        termTitle.add(key);
                    }
                }

                String body = (doc.getElementsByTagName("body").getLength() <= 0) ? ""
                        : doc.getElementsByTagName("body").item(0).getTextContent();

                while (body.contains("  ")) {
                    body = body.replace("  ", " ");
                }
                String[] arrBody = body.split(" ");
                for (String key : arrBody) {
                    if (!" ".equals(key) && !termBody.contains(key)) {
                        termBody.add(key);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void initializeDictionary() {
        //Initialize dictDate
        Collections.sort(termDate);
        for (int i = 0; i < termDate.size(); i++) {
            String key = termDate.get(i);
            ArrayList<NoDocFrequency> value = new ArrayList<>();
            dictDate.put(key, value);
        }

        //Initialize dictTitle
        Collections.sort(termTitle);
        for (int i = 0; i < termTitle.size(); i++) {
            String key = termTitle.get(i);
            ArrayList<NoDocFrequency> value = new ArrayList<>();
            dictTitle.put(key, value);
        }

        //Initialize dictBody
        Collections.sort(termBody);
        for (int i = 0; i < termBody.size(); i++) {
            String key = termBody.get(i);
            ArrayList<NoDocFrequency> value = new ArrayList<>();
            dictBody.put(key, value);
        }
    }

    private void getFrequencyFromDocuments() {
        int n = 500;

        //To count frequency
        for (int i = 1; i <= n; i++) {
            String from = "XML/Doc (" + i + ").xml";

            //String text = ReadFile.inputBasicFile(from);
            //InputSource is = new InputSource();
            //is.setCharacterStream(new StringReader(text));
            File f = new File(from);

            try {
                DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
                DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
                //Document doc = dBuilder.parse(is);
                Document doc = dBuilder.parse(f);

                String date = (doc.getElementsByTagName("date").getLength() <= 0) ? "" : doc.getElementsByTagName("date").item(0).getTextContent();
                //System.out.println(date);
                while (date.contains("  ")) {
                    date = date.replace("  ", " ");
                }

                List<String> arrDate = Arrays.asList(date.split(" "));
                //System.out.println(date);
                for (String key : termDate) {
                    if (arrDate.contains(key)) {
                        int noDoc = i;
                        int frequency = Collections.frequency(arrDate, key);

                        NoDocFrequency newValue = new NoDocFrequency(noDoc, frequency);
                        dictDate.get(key).add(i - 1, newValue);
                    } else {
                        NoDocFrequency newValue = new NoDocFrequency(i, 0);
                        dictDate.get(key).add(i - 1, newValue);
                    }
                }

                String title = (doc.getElementsByTagName("title").getLength() <= 0) ? "" : doc.getElementsByTagName("title").item(0).getTextContent();

                while (title.contains("  ")) {
                    title = title.replace("  ", " ");
                }
                List<String> arrTitle = Arrays.asList(title.split(" "));
                for (String key : termTitle) {
                    if (arrTitle.contains(key)) {
                        int noDoc = i;
                        int frequency = Collections.frequency(arrTitle, key);

                        NoDocFrequency newValue = new NoDocFrequency(noDoc, frequency);
                        dictTitle.get(key).add(i - 1, newValue);
                    } else {
                        NoDocFrequency newValue = new NoDocFrequency(i, 0);
                        dictTitle.get(key).add(i - 1, newValue);
                    }
                }

                String body = (doc.getElementsByTagName("body").getLength() <= 0) ? "" : doc.getElementsByTagName("body").item(0).getTextContent();

                while (body.contains("  ")) {
                    body = body.replace("  ", " ");
                }
                List<String> arrBody = Arrays.asList(body.split(" "));
                for (String key : termBody) {
                    if (arrBody.contains(key)) {
                        int noDoc = i;
                        int frequency = Collections.frequency(arrBody, key);

                        NoDocFrequency newValue = new NoDocFrequency(noDoc, frequency);
                        dictBody.get(key).add(i - 1, newValue);
                    } else {
                        NoDocFrequency newValue = new NoDocFrequency(i, 0);
                        dictBody.get(key).add(i - 1, newValue);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private static String csvDict(HashMap<String, ArrayList<NoDocFrequency>> dict, String category, String target) {
        String data = "";
        Set<String> keySort = dict.keySet();

        List<String> keys = StringSorter.sortStringList(new ArrayList<String>(keySort));

        data = "";

        for (int j = 0; j < keys.size(); j++) {
            String key = keys.get(j);
            if (!key.equals("")) {
                data += key + "= [";

                ArrayList<NoDocFrequency> values = dict.get(key);
                for (int i = 0; i < values.size(); i++) {
                    data += String.valueOf(values.get(i).frequency) + ", ";
                }

                data = data.substring(0, data.length() - 2);
                data += "]\n";

                //System.out.println(data);

                WriteFile.writeData(data, target);
                data = "";
                //System.out.println(" ");
            }
        }
        data += "\n\n";

        WriteFile.writeData(data, target);
        data = "";

        return data;
    }

    public static void main(String[] args) {
        startTime = System.nanoTime();
        Frequency f = new Frequency();
        f.countTotalWord();
        f.countTermFrequency();
        endTime = System.nanoTime();
        timeElapsed = endTime-startTime;
        System.out.println(timeElapsed);
        
        
    }

    private static ArrayList<String> getAllTermsInQuery(String query) {
        ArrayList<String> queryTerms = new ArrayList<>();

        String[] arrQuery = query.split(" ");
        for (String word : arrQuery) {
            if (!queryTerms.contains(word)) {
                queryTerms.add(word);
            }
        }

        return queryTerms;
    }

    private static int getTermsFrequency(String term, String sentence) {
        //Query to list
        List<String> arrSentences = Arrays.asList(sentence.split(" "));

        int frequency = Collections.frequency(arrSentences, term);

        return frequency;
    }

    public static HashMap<String, NoDocValue> getQueryFrequency(String query) {
        HashMap<String, NoDocValue> queryFrequency = new HashMap<>();

        //Get all terms in the query
        ArrayList<String> queryTerms = getAllTermsInQuery(query);

        //Initialize HashMap
        //System.out.println("Query Frequency : ");
        for (String term : queryTerms) {
            int frequency = getTermsFrequency(term, query);
            NoDocValue value = new NoDocValue(0, Double.valueOf(frequency));
            queryFrequency.put(term, value);
            
            //System.out.println("Term : " + term + " - " + frequency);
        }
        //System.out.println("\n\n");

        return queryFrequency;
    }
}

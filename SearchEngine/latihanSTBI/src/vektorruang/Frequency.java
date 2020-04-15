package vektorruang;

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

    class NoDocFrequency {
        int noDoc;
        int frequency;

        public NoDocFrequency(int noDoc, int frequency) {
            this.noDoc = noDoc;
            this.frequency = frequency;
        }
    }

    public void countTermFrequency() {
        //To store key
        ArrayList<String> termDate = new ArrayList<>();
        ArrayList<String> termTitle = new ArrayList<>();
        ArrayList<String> termBody = new ArrayList<>();

        int n = 500;

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
                System.out.println(date);
                while (date.contains("  ")) {
                    date = date.replace("  ", " ");
                }
                String[] arrDate = date.split(" ");
                System.out.println(date);
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

        //To store frequency
        HashMap<String, ArrayList<NoDocFrequency>> dictDate = new HashMap<>();
        HashMap<String, ArrayList<NoDocFrequency>> dictTitle = new HashMap<>();
        HashMap<String, ArrayList<NoDocFrequency>> dictBody = new HashMap<>();
        
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

        //For frequency
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
                System.out.println(date);
                while(date.contains("  ")){
                    date = date.replace("  ", " ");
                }
                
                List<String> arrDate = Arrays.asList(date.split(" "));
                System.out.println(date);
                for (String key : termDate) {
                    if (arrDate.contains(key)) {
                        int noDoc = i;
                        int frequency = Collections.frequency(arrDate, key);

                        NoDocFrequency newValue = new NoDocFrequency(noDoc, frequency);
                        dictDate.get(key).add(i-1, newValue);
                    } else {
                        NoDocFrequency newValue = new NoDocFrequency(i, 0);
                        dictDate.get(key).add(i-1, newValue);
                    }
                }

                String title = (doc.getElementsByTagName("title").getLength() <= 0) ? "" : doc.getElementsByTagName("title").item(0).getTextContent();

                while(title.contains("  ")){
                    title = title.replace("  ", " ");
                }
                List<String> arrTitle = Arrays.asList(title.split(" "));
                for (String key : termTitle) {
                    if (arrTitle.contains(key)) {
                        int noDoc = i;
                        int frequency = Collections.frequency(arrTitle, key);

                        NoDocFrequency newValue = new NoDocFrequency(noDoc, frequency);
                        dictTitle.get(key).add(i-1, newValue);
                    } else {
                        NoDocFrequency newValue = new NoDocFrequency(i, 0);
                        dictTitle.get(key).add(i-1, newValue);
                    }
                }

                String body = (doc.getElementsByTagName("body").getLength() <= 0) ? "" : doc.getElementsByTagName("body").item(0).getTextContent();

                while(body.contains("  ")){
                    body = body.replace("  ", " ");
                }
                List<String> arrBody = Arrays.asList(body.split(" "));
                for (String key : termBody) {
                    if (arrBody.contains(key)) {
                        int noDoc = i;
                        int frequency = Collections.frequency(arrBody, key);

                        NoDocFrequency newValue = new NoDocFrequency(noDoc, frequency);
                        dictBody.get(key).add(i-1, newValue);
                    } else {
                        NoDocFrequency newValue = new NoDocFrequency(i, 0);
                        dictBody.get(key).add(i-1, newValue);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        //Saving the frequency
        System.out.println("Frequency of Date : ");
        csvDict(dictDate, "Date", "XML/FrequencyDate.csv");

        System.out.println("Frequency of Title : ");
        csvDict(dictTitle, "Title", "XML/FrequencyTitle.csv");

        System.out.println("Frequency of Body : ");
        csvDict(dictBody, "Body", "XML/FrequencyBody.csv");
    }

    private static String csvDict(HashMap<String, ArrayList<NoDocFrequency>> dict, String category, String target) {
        String data = "";
        Set<String> keySort = dict.keySet();

        List<String> keys =  StringSorter.sortStringList(new ArrayList<String>(keySort));

        data = "";

        for (int j = 0; j < keys.size();j++) {
            String key = keys.get(j);
            if (!key.equals("")){
              data += key + "= [";

              ArrayList<NoDocFrequency> values = dict.get(key);
              for (int i = 0; i < values.size(); i++) {
                  data += String.valueOf(values.get(i).frequency) + ", ";
              }

              data = data.substring(0, data.length()-2);
              data +="]\n";
               
              System.out.println(data);

              WriteFile.writeData(data, target);
              data = "";
              System.out.println(" ");
            }
        }
        data += "\n\n";

        WriteFile.writeData(data, target);
        data = "";

        return data;
    }

    public static void main(String[] args) {
        Frequency f = new Frequency();
        f.countTermFrequency();
    }
}
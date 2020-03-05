package preprocessing;


import Utility.WriteFile;
import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import Utility.ReadFile;
import Utility.StringSorter;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class MakeDict {

    private static void printAllDictionary(HashMap<String, ArrayList<Integer>> dict) {
        Set<String> keys = dict.keySet();


        List<String> keySort =  StringSorter.sortStringList(new ArrayList<String>(keys));

        for (int i = 0; i < keySort.size(); i++) {
            String key = keySort.get(i);
            System.out.print(key + " : ");

            ArrayList<Integer> values = dict.get(key);
            values.forEach((value) -> System.out.print(value + " "));

            System.out.println(" ");

        }
    }

    private static String csvDict(HashMap<String, ArrayList<Integer>> dict,String category,String target) {
        String data = "";
        Set<String> keySort = dict.keySet();

        List<String> keys =  StringSorter.sortStringList(new ArrayList<String>(keySort));
        data = "";

        for (int j = 0; j < keys.size();j++) {
            String key = keys.get(j);
            if (!key.equals("")){
              data += key + "\t";

              ArrayList<Integer> values = dict.get(key);
              for (int i = 0; i < values.size(); i++) {
                  data += String.valueOf(values.get(i)) + "\t";
              }

              System.out.println(data);

              data +="\n";

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

    public static void main(String[] args) throws ParserConfigurationException, SAXException {
        HashMap<String, ArrayList<Integer>> dictDate = new HashMap<>();
        HashMap<String, ArrayList<Integer>> dictTitle = new HashMap<>();
        HashMap<String, ArrayList<Integer>> dictBody = new HashMap<>();

        int n = 500;

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
                String [] arrDate = date.split(" ");
                System.out.println(date);
                for (String key : arrDate) {
                    if (dictDate.containsKey(key) && !"".equals(key)) {
                        if (!dictDate.get(key).contains(i)) {
                            dictDate.get(key).add(i);
                        }
                    } else if (!" ".equals(key)) {
                        ArrayList<Integer> value = new ArrayList<>();
                        value.add(i);

                        dictDate.put(key, value);
                    }
                }

                String title = (doc.getElementsByTagName("title").getLength() <= 0) ? "" : doc.getElementsByTagName("title").item(0).getTextContent();

                while(title.contains("  ")){
                    title = title.replace("  ", " ");
                }
                String [] arrTitle = title.split(" ");
                for (String key : arrTitle) {
                    if (dictTitle.containsKey(key) && !"".equals(key)) {
                        if (!dictTitle.get(key).contains(i)) {
                            dictTitle.get(key).add(i);
                        }
                    } else if (!" ".equals(key)) {
                        ArrayList<Integer> value = new ArrayList<>();
                        value.add(i);

                        dictTitle.put(key, value);
                    }
                }

                String body = (doc.getElementsByTagName("body").getLength() <= 0) ? "" : doc.getElementsByTagName("body").item(0).getTextContent();

                while(body.contains("  ")){
                    body = body.replace("  ", " ");
                }
                String [] arrBody = body.split(" ");
                for (String key : arrBody) {
                    if (dictBody.containsKey(key) && !"".equals(key)) {
                        if (!dictBody.get(key).contains(i)) {
                            dictBody.get(key).add(i);
                        }
                    } else if (!" ".equals(key)) {
                        ArrayList<Integer> value = new ArrayList<>();
                        value.add(i);

                        dictBody.put(key, value);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
            String data ="";
            System.out.println("Dictionary of Date : ");
            csvDict(dictDate, "Date","XML/AfterDate.tsv");


            System.out.println("Dictionary of Title : ");
            csvDict(dictTitle, "Title","XML/AfterTitle.tsv");

            System.out.println("Dictionary of Body : ");
            csvDict(dictBody, "Body","XML/AfterBody.tsv");


    }
}

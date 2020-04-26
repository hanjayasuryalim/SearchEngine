/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utility;

import preprocessing.Lemmatize;
import preprocessing.StopWords;
import preprocessing.Stemmer;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;

public class ReadFile {

    public static ArrayList<String> inputFile(String path) {
        try {
            File fileInput = new File(path);
            FileReader fr = new FileReader(fileInput);
            BufferedReader br = new BufferedReader(fr);
            String line;
            ArrayList<String> docRows = new ArrayList<>();
            while ((line = br.readLine()) != null) {
                docRows.add(line);
            }
            return docRows;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
    
    public static String inputSingleLineFile(String path) {
        try {
            File fileInput = new File(path);
            FileReader fr = new FileReader(fileInput);
            BufferedReader br = new BufferedReader(fr);
            String text = "";
            String line;
            ArrayList<String> docRows = new ArrayList<>();
            while ((line = br.readLine()) != null) {
                text+=line;
            }
            return text;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public static String inputBasicFile(String path) {
        try {
            File fileInput = new File(path);
            FileReader fr = new FileReader(fileInput);
            BufferedReader br = new BufferedReader(fr);
            String text = "<?xml version=\"1.0\"?><root>";
            String line;
            while ((line = br.readLine()) != null) {
                line = line.replace("&lt;"," ");
                text += line + " ";

            }
            text += "</root>";
            return text;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

}

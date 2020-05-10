/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package preprocessing;

import Utility.ReadFile;
import Utility.WriteFile;
import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author USER
 */
public class PreProcess {

    private static final String FOLDER_ORIGIN_NAME = "Dataset";
    private static final String FOLDER_TARGET_NAME = "XML";

    public static void preprocess() {

        ArrayList<String> filesName = new ArrayList<>();
        Collections.addAll(filesName, getFileNames(FOLDER_ORIGIN_NAME));
        if (filesName == null) {
            return;
        }

        for (int i = 0; i < filesName.size(); i++) {
            WriteFile.writeData("<?xml version=\"1.0\"?><root>", FOLDER_TARGET_NAME + "/" + filesName.get(i).replace("txt", "xml"));

            ArrayList<String> documentRows = ReadFile.inputFile(FOLDER_ORIGIN_NAME + "/" + filesName.get(i));
            for (int j = 0; j < documentRows.size(); j++) {
                String line = documentRows.get(j);

                line = line.replace("(", " ").replace(")", " ").replace('"', ' ')
                        .replace("'", " ").replace("?", " ")
                        .replace(">", "> ").replace("<", " <");

                String dummy;

                String[] tokens = line.split(" ");
                boolean[] isRegex = Regex.isToken(tokens);
                for (int k = 0; k < tokens.length; k++) {
                    // Jika bukan regex ;
                    if (!isRegex[k]) {
                        tokens[k] = SymbolRemover.removeSymbol(tokens[k]).toLowerCase();

                    }else{
                        String dateExp[] = {"^(?:\\d{4}|\\d{1,2})[-/.](?:\\d{1,2}|J(anuary|an|AN|une|un|UN|uly|ul|UL)|F(ebruary|eb|EB)|M(arch|ar|AR|ay|AY)|A(pril|pr|PR|ugust|ug|UG)|S(eptember|ep|EP)|O(ctober|ct|CT)|N(ovember|ov|OV)|D(ecember|ec|EC))[-/.](?:\\d{4}|\\d{1,2})$"};
                        Pattern SubContentPattern = Pattern.compile(dateExp[0]);
                        Matcher matcher = SubContentPattern.matcher(tokens[k]);
                        //System.out.println("_______________DATE ??? _____________");
                        if (matcher.find()) {
                            tokens[k] = normalizeDate(tokens[k]).toLowerCase();
                        }else{
                            tokens[k] = tokens[k].toLowerCase();
                        }
                    }
                }

                dummy = String.join(" ", tokens);
                tokens = dummy.split(" ");

                for (int k = 0; k < tokens.length; k++) {
                    tokens[k] = Lemmatize.execute(tokens[k]);
                }

                tokens = StopWords.execute(tokens);
                tokens = Stemmer.execute(tokens);

                WriteFile.writeData(String.join(" ", tokens) + " ", FOLDER_TARGET_NAME + "/" + filesName.get(i).replace("txt", "xml"));
            }
            WriteFile.writeData("</root>", FOLDER_TARGET_NAME + "/" + filesName.get(i).replace("txt", "xml"));

        }

    }

    private static String[] getFileNames(String folderName) {

        try {
            File folder = new File(folderName);
            String[] files = folder.list();
            return files;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String singleWordPreprocess(String word) {
        ArrayList<String> words = new ArrayList<>();
        words.add(word);
        
        for (int j = 0; j < words.size(); j++) {
            String line = words.get(j);

            line = line.replace("(", " ").replace(")", " ").replace('"', ' ')
                    .replace("'", " ").replace("?", " ")
                    .replace(">", "> ").replace("<", " <");

            String dummy;

            String[] tokens = line.split(" ");
            boolean[] isRegex = Regex.isToken(tokens);
            for (int k = 0; k < tokens.length; k++) {
                // Jika bukan regex ;
                if (!isRegex[k]) {
                    tokens[k] = SymbolRemover.removeSymbol(tokens[k].toLowerCase());
                }else{
                    String dateExp[] = {"^(?:\\d{4}|\\d{1,2})[-/.](?:\\d{1,2}|J(anuary|an|AN|une|un|UN|uly|ul|UL)|F(ebruary|eb|EB)|M(arch|ar|AR|ay|AY)|A(pril|pr|PR|ugust|ug|UG)|S(eptember|ep|EP)|O(ctober|ct|CT)|N(ovember|ov|OV)|D(ecember|ec|EC))[-/.](?:\\d{4}|\\d{1,2})$"};
                    Pattern SubContentPattern = Pattern.compile(dateExp[0]);
                    Matcher matcher = SubContentPattern.matcher(tokens[k]);
                    //System.out.println("_______________DATE ??? _____________");
                    if (matcher.find()) {
                        tokens[k] = normalizeDate(tokens[k]).toLowerCase();
                    }else{
                        tokens[k] = tokens[k].toLowerCase();
                    }
                }
            }

            dummy = String.join(" ", tokens);
            tokens = dummy.split(" ");

            for (int k = 0; k < tokens.length; k++) {
                tokens[k] = Lemmatize.execute(tokens[k]);
            }

            tokens = StopWords.execute(tokens);
            tokens = Stemmer.execute(tokens);
            if (tokens.length>0){
                word = tokens[0];
            }else{
                word = "";
            }

        }
        return word;
    }
    
    
    private static String normalizeDate(String d) {
        Date date;
        DateFormat finalFormat = new SimpleDateFormat("dd-MMM-yy");
        try {
            DateFormat format = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);
            date = format.parse(d);
            d = finalFormat.format(date);
        } catch (Exception e) {
        }
        try {
            DateFormat format = new SimpleDateFormat("dd-MM-yy", Locale.ENGLISH);
            date = format.parse(d);
            d = finalFormat.format(date);
        } catch (Exception e) {
        }
        try {
            DateFormat format = new SimpleDateFormat("dd-MMM-yyyy", Locale.ENGLISH);
            date = format.parse(d);
            d = finalFormat.format(date);
        } catch (Exception e) {
        }
        try {
            DateFormat format = new SimpleDateFormat("dd-M-yy", Locale.ENGLISH);
            date = format.parse(d);
            d = finalFormat.format(date);
        } catch (Exception e) {
        }
        try {
            DateFormat format = new SimpleDateFormat("dd-M-yyyy", Locale.ENGLISH);
            date = format.parse(d);
            d = finalFormat.format(date);
        } catch (Exception e) {
        }
        try {
            DateFormat format = new SimpleDateFormat("dd-MMM-yy", Locale.ENGLISH);
            date = format.parse(d);
            d = finalFormat.format(date);
        } catch (Exception e) {
        }
        
        return d;
    }
    public static void main(String[] args) {
        PreProcess.preprocess();
    }
}

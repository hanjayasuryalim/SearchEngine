/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package preprocessing;

import Utility.ReadFile;
import Utility.WriteFile;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

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

                line = line.toLowerCase().replace("(", " ").replace(")", " ").replace('"', ' ')
                        .replace("'", " ").replace("?", " ")
                        .replace(">", "> ").replace("<", " <");

                String dummy;

                String[] tokens = line.split(" ");
                boolean[] isRegex = Regex.isToken(tokens);
                for (int k = 0; k < tokens.length; k++) {
                    // Jika bukan regex ;
                    if (!isRegex[k]) {
                        tokens[k] = SymbolRemover.removeSymbol(tokens[k]);

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

            line = line.toLowerCase().replace("(", " ").replace(")", " ").replace('"', ' ')
                    .replace("'", " ").replace("?", " ")
                    .replace(">", "> ").replace("<", " <");

            String dummy;

            String[] tokens = line.split(" ");
            boolean[] isRegex = Regex.isToken(tokens);
            for (int k = 0; k < tokens.length; k++) {
                // Jika bukan regex ;
                if (!isRegex[k]) {
                    tokens[k] = SymbolRemover.removeSymbol(tokens[k]);

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
}

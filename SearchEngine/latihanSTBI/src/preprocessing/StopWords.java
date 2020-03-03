/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package preprocessing;

import java.io.BufferedReader;
import java.io.File;
import java.util.ArrayList;
import java.io.FileReader;

public class StopWords {
    public static String [] execute(String [] tokens) {
        ArrayList<String>stopwords = new ArrayList<>();
        
        try {
            File f = new File("stopwords.txt");
            FileReader fr = new FileReader(f);
            BufferedReader br = new BufferedReader(fr);
            String line;
            line = br.readLine();
            while(line!=null){
                stopwords.add(line);
                line = br.readLine();
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        ArrayList<String> cleanTokens = new ArrayList();
        
        for (int i = 0; i < tokens.length; i++) {
            String token = tokens[i];
            if(!stopwords.contains(token)){
                cleanTokens.add(token);
            }
        }
        String [] finalTokens = new String[cleanTokens.size()];
        for (int i = 0; i < finalTokens.length; i++) {
            finalTokens[i] = cleanTokens.get(i);
        }
        
        
        return finalTokens;      
    }
}

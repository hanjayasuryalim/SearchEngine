/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package preprocessing;

import opennlp.tools.stemmer.PorterStemmer;

public class Stemmer {

    public static String[] execute(String[] tokens) {
        for (int i = 0; i < tokens.length; i++) {
            PorterStemmer stemmer = new PorterStemmer();
            tokens[i] = stemmer.stem(tokens[i]);
        }
        return tokens;
    }
}

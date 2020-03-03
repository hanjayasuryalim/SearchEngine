/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package preprocessing;


import Controller.Pipeline;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;

import edu.stanford.nlp.pipeline.CoreDocument;
import java.util.List;

public class Lemmatize {
    
    public static String execute(String passedText) {
        StanfordCoreNLP stanfordCoreNLP = Pipeline.getPipeline();
        CoreDocument coreDocument = new CoreDocument(passedText);
        stanfordCoreNLP.annotate(coreDocument);
        List<CoreLabel> coreLabelList = coreDocument.tokens();
        
        String returnString="";
        for (CoreLabel coreLabel : coreLabelList) {
        	returnString+=coreLabel.lemma();
        }
        
        return returnString;
    }
    
    
}

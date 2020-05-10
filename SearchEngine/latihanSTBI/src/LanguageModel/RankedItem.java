/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LanguageModel;

/**
 *
 * @author adit
 */
public class RankedItem implements Comparable {
    public int docId;
    public double score;

    @Override
    public int compareTo(Object rankedItem) {
        if (rankedItem instanceof RankedItem){
            RankedItem rItem = (RankedItem)rankedItem;
            Double compareScore = rItem.score;
            Double thisScore = this.score;
            return compareScore.compareTo(thisScore);
        }else{
            return 0;
        }
    }

    public int getDocId() {
        return docId;
    }

    public void setDocId(int docId) {
        this.docId = docId;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }
    
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Main;

import Utility.LoadDict;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Scanner;
import java.util.Set;
import preprocessing.Levensthein;
import preprocessing.PreProcess;

public class Main {

    public static HashMap<String, ArrayList<Integer>> titleDict = LoadDict.loadTitle("XML/AfterTitle.csv");
    public static HashMap<String, ArrayList<Integer>> bodyDict = LoadDict.loadTitle("XML/AfterBody.csv");
    public static HashMap<String, ArrayList<Integer>> dateDict = LoadDict.loadTitle("XML/AfterDate.csv");

    public static void main(String[] args) {

            //START
        ArrayList<String>test = new ArrayList<>();
        
        test = getDocs("fly || pay");
        
        for (String string : test) {
            System.out.println(string);
        }
    }
    
    public static ArrayList<String> getDocs(String query){
        titleDict = LoadDict.loadTitle("XML/AfterTitle.csv");
        bodyDict = LoadDict.loadTitle("XML/AfterBody.csv");
        dateDict = LoadDict.loadTitle("XML/AfterDate.csv");
        
         String[] queryPart = query.split(" ");
        ArrayList<Object> queries = new ArrayList<>();
        for (int i = 0; i < queryPart.length; i++) {
            if (queryPart[i].equals("&&") || queryPart[i].equals("||") || queryPart[i].equals("(") || queryPart[i].equals(")") ){
                queries.add(queryPart[i]);            
            }else{
                queries.add(getList(queryPart[i]));
            }
            

        }
        
        ArrayList<Integer> result = ((ArrayList)eval(queries));
        ArrayList<String> listDocument = new ArrayList<>();
        Collections.sort(result);
        for (int i = 0; i < result.size(); i++) {
            if (result.get(i) instanceof Integer){
                listDocument.add(((Integer)result.get(i)).toString());
                
            }
            
        }
        
        
        return listDocument;

    }

    public static ArrayList<Integer> getList(String word) {
        System.out.println("Word : "+word);
        word = PreProcess.singleWordPreprocess(word);
        ArrayList<Integer> myList = new ArrayList<>();
        if (word.equals("")){
            for (int i = 1; i <= 500; i++) {
                myList.add((Integer)i);
            }
        }
          
        //levensthein
        int threshold = 1;
        ArrayList<String> titleValidWordList = new ArrayList<>();
        for (String string : titleDict.keySet()) {
            if (Levensthein.getLevenshteinDistance(word, string)<=threshold){
                titleValidWordList.add(string);
            }
        }
        
        for (String string : titleValidWordList) {
            if (titleDict.get(string) != null) {
                myList.addAll(titleDict.get(string));
            }
        }
        
        ArrayList<String> bodyValidWordList = new ArrayList<>();
        for (String string : bodyDict.keySet()) {
          if(Levensthein.getLevenshteinDistance(word, string)<=threshold){
              bodyValidWordList.add(string);
          }
        }
        
        for (String string : bodyValidWordList) {
            if (bodyDict.get(string) != null) {
                myList = orOperation(myList,bodyDict.get(word));
            }
        }
        
  
//        if (titleDict.get(word) != null) {
//            myList = orOperation(myList,titleDict.get(word));
//        }
//
        if (myList.size() == 0) {
            System.out.println("nothing");
        }
        return myList;
    }

    private static ArrayList<Object> eval(ArrayList<Object> qList) {
        
        //Reverse Array List
        ArrayList<Object> queries = new ArrayList<>();
        for (int i = qList.size()-1; i >= 0; i--) {
            queries.add(qList.get(i));
        }
        
        int i = 0;
        
        
        ArrayList<Object> stack = new ArrayList<>();
        while (i < queries.size()){
            if (queries.get(i) instanceof String){
                String operator = (String)queries.get(i);
                if (operator.equals("(")){
                    boolean isLoop = true;
                    ArrayList<Object> miniStack = new ArrayList<>();
                    while(isLoop){
                        if (stack.get(stack.size()-1) instanceof String){
                            String curSymbol = (String)stack.get(stack.size()-1);
                            if (curSymbol.equals(")")){
                                stack.remove(stack.size()-1);
                                if (miniStack.size() == 1){
                                    stack.add(miniStack.get(0));
                                }else{
                                    stack.add(eval(miniStack));
                                }
                            }
                        }else{
                            miniStack.add(stack.get(stack.size()-1));
                            stack.remove(stack.size()-1);
                        }
                    }
                }else{
                    stack.add(queries.get(i));
                }
            }else{
                stack.add(queries.get(i));
            }           
            i++;
        }
   
        
        i = 0;
        
        while (i < stack.size()){
            if (stack.get(i) instanceof String){
                String symbol = (String) stack.get(i);
                if (symbol.equals("&&")){                    
                    ArrayList<Integer> result = andOperation(stack.get(i-1),stack.get(i+1));
                    stack.remove(i+1);
                    stack.remove(i);
                    stack.set(i-1, result);
                }
            }
            i++;
        }
        
        
        while (i < stack.size()){
            if (stack.get(i) instanceof String){
                String symbol = (String) stack.get(i);
                if (symbol.equals("||")){
                    ArrayList<Integer> result = orOperation(stack.get(i-1),stack.get(i+1));
                    stack.remove(i+1);
                    stack.remove(i);
                    stack.set(i-1, result);
                }
            }
            i++;
        }
        
        if (stack.get(0) instanceof ArrayList){
            ArrayList<Object> result= (ArrayList)stack.get(0);
            return result;
        }
        return null;
    }

    private static ArrayList<Integer> orOperation(Object get, Object get0) {

        if ((get instanceof ArrayList) && (get0 instanceof ArrayList)){
            ArrayList<Integer> listNumber1 = (ArrayList)get;
            ArrayList<Integer> listNumber2 = (ArrayList)get0;
            if (listNumber1.size() == 500){
                return listNumber2;
            }
            if (listNumber2.size() == 500){
                return listNumber1;
            }
            
            for (int i = 0; i < listNumber2.size(); i++) {
                boolean isDuplicate = false;
                for (int j = 0; j < listNumber1.size(); j++) {
                    if (listNumber1.get(j).equals(listNumber2.get(i)))  {
                        isDuplicate = true;
                    }                  
                }
                if(!isDuplicate){
                    listNumber1.add(listNumber2.get(i));
                }
            }
            
            
            return listNumber1;
        } 
        return null;
    }

    private static ArrayList<Integer> andOperation(Object get, Object getTwo) {

        if ((get instanceof ArrayList) && (getTwo instanceof ArrayList)){
            ArrayList<Integer> listNumber1 = (ArrayList)get;
            ArrayList<Integer> listNumber2 = (ArrayList)getTwo;
            ArrayList<Integer> listNumberFinal = new ArrayList<>();
            for (int i = 0; i < listNumber2.size(); i++) {
                boolean isDuplicate = false;
                for (int j = 0; j < listNumber1.size(); j++) {
                    if (listNumber1.get(j).equals(listNumber2.get(i)))  {
                        isDuplicate = true;
                    }                  
                }
                if(isDuplicate){
                    listNumberFinal.add(listNumber2.get(i));
                }
            }
            return listNumberFinal;
        } 
        return null;
    }
}

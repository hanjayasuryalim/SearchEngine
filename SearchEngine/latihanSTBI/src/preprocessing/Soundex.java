/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package preprocessing;

import java.util.ArrayList;



public class Soundex {
    
     private static ArrayList<Character> condition0 = new ArrayList<>();
     private static ArrayList<Character> condition1 = new ArrayList<>();
     private static ArrayList<Character> condition2 = new ArrayList<>();
     private static ArrayList<Character> condition3 = new ArrayList<>();
     private static ArrayList<Character> condition4 = new ArrayList<>();
     private static ArrayList<Character> condition5 = new ArrayList<>();
     private static ArrayList<Character> condition6 = new ArrayList<>();
     private static ArrayList<Character> listAngka = new ArrayList<>();
     
    public static void initiate(){
        condition0.add('A');
        condition0.add('E');
        condition0.add('I');
        condition0.add('O');
        condition0.add('U');
        condition0.add('H');
        condition0.add('W');
        condition0.add('Y');
        
        condition1.add('B');
        condition1.add('F');
        condition1.add('P');
        condition1.add('V');
        
        condition2.add('C');
        condition2.add('G');
        condition2.add('J');
        condition2.add('K');
        condition2.add('Q');
        condition2.add('S');
        condition2.add('X');
        condition2.add('Z');
        
        condition3.add('D');
        condition3.add('T');
        
        condition4.add('L');
        
        condition5.add('M');
        condition5.add('N');
        
        condition6.add('R');
        
        listAngka.add('0');
        listAngka.add('1');
        listAngka.add('2');
        listAngka.add('3');
        listAngka.add('4');
        listAngka.add('5');
        listAngka.add('6');
        listAngka.add('7');
        listAngka.add('8');
        listAngka.add('9');
    }
    
    public static String getTerm(String s){
        
        String temp = s.toUpperCase();
        String term ="";
        
        if(temp.length()==1){
            term = temp;
        }else{
            term+=temp.charAt(0);
        }
        
        
        
        String returnTerm = "";
       
        for (int i = 1; i < temp.length(); i++) {
            if(condition0.contains(temp.charAt(i))){
               term+='0';
           } else if(condition1.contains(temp.charAt(i))){
               term+='1';
           }else if(condition2.contains(temp.charAt(i))){
               term+='2';
           }else if(condition3.contains(temp.charAt(i))){
               term+='3';
           }else if(condition4.contains(temp.charAt(i))){
               term+='4';
           }else if(condition5.contains(temp.charAt(i))){
               term+='5';
           }else if(condition6.contains(temp.charAt(i))){
               term+='6';
           }
        }
        
       int counter = 0;
       
       
       while(counter < term.length()){
    
            if(!listAngka.contains(term.charAt(counter))){
                returnTerm += term.charAt(counter);
                counter++;
            }else{
                if(term.charAt(counter)=='0'){
                    counter++;
                }else{
                    if(counter < term.length()-1){
                        if(term.charAt(counter) == term.charAt(counter+1)){
                            returnTerm+=term.charAt(counter);
                            counter+=2;
                        }else{
                            returnTerm+=term.charAt(counter);
                            counter++;
                        }
                    }else{
                        returnTerm+=term.charAt(counter);
                        counter++;
                    }
                }
            }
           
       }
        
        return returnTerm;
    }
    public static boolean execute(String s, String t){
       
        boolean soundex = false;
        
        String term1 = getTerm(s);
        String term2 = getTerm(t);
        
        if(term1.equals(term2)){
            soundex = true;
        }
        return soundex;
    }
    
   
}

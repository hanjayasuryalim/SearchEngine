/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package preprocessing;

/**
 *
 * @author USER
 */
public class SymbolRemover {
    public static String removeSymbol(String token){
        token = token.replace("-"," ");
        token = token.replace("..."," ");
        
        for (int i = 0; i < token.length()-1; i++) {
            if (token.charAt(i) == '.'){
                if (token.charAt(token.length()-1) == '.'){
                    return token;
                }
            }
        }
        
        if ((token.contains(">")||token.contains("&lt;") || token.contains("/")) && !token.contains("date") && !token.contains("body") && !token.contains("title")  ){
            System.out.println("Masuk sini");
            token = token.replace("<", " ");
            token = token.replace("&lt;"," ");
            token = token.replace(">"," ");
            token = token.replace("/"," ");
        }
        
        
        token = token.replace("-"," ");
        while (token.endsWith(".") || token.endsWith(",")|| token.endsWith(":")|| token.endsWith("+") ){
            token = token.substring(0,token.length()-1);
        }
        while (token.startsWith(".") || token.startsWith(",") || token.startsWith("+") ){
            token = token.substring(1,token.length());
        }
        
        
                
        return token;
    }
}

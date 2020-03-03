/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utility;


public class ParseIntoXML {
    public static void parsing(String readPath,String writePath,String targetPath,String text){
        try {
            CreateDirectory.createDir(targetPath);
            //String text = ReadFile.inputFile(readPath);
            WriteFile.writeData(text,writePath);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

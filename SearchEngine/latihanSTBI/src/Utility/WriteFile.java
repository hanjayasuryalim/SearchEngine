/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utility;

import java.io.FileWriter;

import Controller.*;

public class WriteFile {
    public static void writeData(String data, String path){
        try {
            data = data.toLowerCase();
            data.replaceAll(" ", "");
            FileWriter writer = new FileWriter(path,true);
            writer.write(data);
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static void writeDatasetXML(String data,String path){
        try{
            FileWriter writer = new FileWriter(path,true);
            writer.write(data);
            writer.close();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}

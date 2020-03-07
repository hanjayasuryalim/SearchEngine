/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Utility.ParseIntoXML;
import java.io.File;

public class Parser {
    public static void parsingThisFolderIntoXML(String originPath,String targetPath){
         try {
            File folder = new File(originPath); 
            String [] files = folder.list();
            String xmlFiles;
            
            for (String file : files) {
                xmlFiles = file.replace("txt", "xml");
                //ParseIntoXML.parsing("Dataset/"+file, "XML/"+xmlFiles,targetPath);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static void parsingDatasetIntoXML(String originPath,String targetPath){
         try {
            File folder = new File(originPath); 
            String [] files = folder.list();
            String xmlFiles;
            
            for (String file : files) {
                xmlFiles = file.replace("txt", "xml");
                ParseIntoXML.parsingDataset("Dataset/"+file,"DatasetXML/"+xmlFiles, targetPath);
                
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

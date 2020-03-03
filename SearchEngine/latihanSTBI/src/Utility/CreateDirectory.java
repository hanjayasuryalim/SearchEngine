/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utility;

import java.io.File;

public class CreateDirectory {
    public static void createDir(String path){
        try {
            File f = new File("XML");
            if(f.mkdir()){
                
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

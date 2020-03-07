package Model;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import org.w3c.dom.*;
import javax.xml.parsers.*;
import java.io.*;

/**
 *
 * @author specter2k11
 */
public class XMLDoc {
    
    String title, body, date;
    
    public XMLDoc(int targetDoc) {
        String path = "DatasetXML/Doc (" + targetDoc + ").xml";
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(new File(path));
            document.getDocumentElement().normalize();
            title = document.getElementsByTagName("TITLE").item(0).getTextContent();
            body = document.getElementsByTagName("BODY").item(0).getTextContent();
            date = document.getElementsByTagName("DATE").item(0).getTextContent();
        } catch(Exception e) {
            System.out.println(e);
        }
    }    

    public String getTitle() {
        return title;
    }

    public String getBody() {
        return body;
    }

    public String getDate() {
        return date;
    }
}

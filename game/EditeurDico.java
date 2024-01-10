/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package game;
import java.util.ArrayList;
import org.w3c.dom.*;
import javax.xml.parsers.*;
import java.io.*;
import util.XMLUtil;
/**
 *
 * @author ding
 */
public class EditeurDico {
    private Document doc;
    private ArrayList<String> mots;
    
    public EditeurDico(String fichier){
        mots=new ArrayList<String>();
        lireDOM(fichier);
    }
    
    public void lireDOM(String fichier){
        try{
            DocumentBuilderFactory dbf=DocumentBuilderFactory.newInstance();
            DocumentBuilder db=dbf.newDocumentBuilder();
            doc=db.parse(new File(fichier));
        }catch(Exception e){
            System.out.println("Erreur: "+e);
        }
    }
    
    public void ecrireDOM(String fichier){
        try{
            XMLUtil.DocumentTransform.writeDoc(doc, fichier);
        }catch(Exception e){
            System.out.println("Erreur: "+e);
        }
    }
    
    public void ajouterMot(String mot,int niveau){
        Element mota=doc.createElement("tux:mot");
        mota.appendChild(doc.createTextNode(mot.substring(0).toLowerCase()));
        mota.setAttribute("niveau", String.valueOf(niveau));
        doc.getDocumentElement().appendChild(mota);
    }
}

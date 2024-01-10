/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package game;
import java.util.ArrayList;
import javax.xml.parsers.*;
import org.w3c.dom.*;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import java.io.File;
/**
 *
 * @author ding
 */
public class Dico extends DefaultHandler{
    private ArrayList<String> listeNiveau1;
    private ArrayList<String> listeNiveau2;
    private ArrayList<String> listeNiveau3;
    private ArrayList<String> listeNiveau4;
    private ArrayList<String> listeNiveau5;
    private String cheminFichierDico;
    private StringBuffer buffer;
    
    public Dico(String cheminFichierDico){
        this.cheminFichierDico = cheminFichierDico;
        listeNiveau1 = new ArrayList<>();
        listeNiveau2 = new ArrayList<>();
        listeNiveau3 = new ArrayList<>();
        listeNiveau4 = new ArrayList<>();
        listeNiveau5 = new ArrayList<>();
        //lireDictionnaireDOM(this.cheminFichierDico,"dico.xml");
        lireDictionnaireSAX();
    }
    
    public String getMotDepuisListe(ArrayList<String> list){
        int i=(int)(Math.random()*(list.size()));
        return list.get(i);
    }
    
    public String getMotDepuisListeNiveau(int niveau){
        String mot="";
        switch(vérifieNiveau(niveau)){
            case 1:
              mot = getMotDepuisListe(listeNiveau1);
              break;
            case 2:
              mot = getMotDepuisListe(listeNiveau2);
              break;
            case 3:
              mot = getMotDepuisListe(listeNiveau3);
              break;
            case 4:
              mot = getMotDepuisListe(listeNiveau4);
              break;
            case 5:
              mot = getMotDepuisListe(listeNiveau5);
              break;
            default:
              System.out.println("Erreur du niveau");
        }
        return mot;
    }
    
    private int vérifieNiveau(int niveau){
        if(niveau>5){
            niveau=5;
        }else if(niveau<1){
            niveau=1;
        }
        return niveau;
    }
    
    public void ajouteMotADico(int niveau,String mot){
        switch(vérifieNiveau(niveau)){
            case 1:
              listeNiveau1.add(mot);
              break;
            case 2:
              listeNiveau2.add(mot);
              break;
            case 3:
              listeNiveau3.add(mot);
              break;
            case 4:
              listeNiveau4.add(mot);
              break;
            case 5:
              listeNiveau5.add(mot);
              break;
            default:
              System.out.println("Erreur du niveau");
        }
    }
    
    public void lireDictionnaireDOM(String path,String filename){
        String filepath = path +filename;
        try{
            DocumentBuilderFactory dbf=DocumentBuilderFactory.newInstance();
            DocumentBuilder db=dbf.newDocumentBuilder();
            Document doc=db.parse(filepath);
            NodeList mots=doc.getElementsByTagName("tux:mot");
            for(int i=0;i<mots.getLength();i++){
                Element mot=(Element)mots.item(i);
                String texte=mot.getTextContent();
                int n=Integer.parseInt(mot.getAttribute("niveau"));
                ajouteMotADico(n,texte);
            }
        }catch(Exception e){
            System.out.println("Erreur: "+e);
        }
        
    }
    
    public void lireDictionnaireSAX(){
        try{
            SAXParserFactory pf=SAXParserFactory.newInstance();
            SAXParser p=pf.newSAXParser(); 
            File f=new File("src/xml/dico.xml");
            DefaultHandler dh=new DefaultHandler(){
                int niveau;
                @Override
                public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
                    if(qName.equals("tux:mot")){
                        niveau=Integer.parseInt(attributes.getValue(0));           
                    }else{
                        buffer=new StringBuffer();
                    }
                    buffer.setLength(0);
                }
                @Override
                public void endElement(String uri, String localName, String qName) throws SAXException {
                    if(qName.equals("tux:mot")){
                        switch(vérifieNiveau(niveau)){
                        case 1:
                          listeNiveau1.add(buffer.toString());
                          break;
                        case 2:
                          listeNiveau2.add(buffer.toString());
                          break;
                        case 3:
                          listeNiveau3.add(buffer.toString());
                          break;
                        case 4:
                          listeNiveau4.add(buffer.toString());
                          break;
                        case 5:
                          listeNiveau5.add(buffer.toString());
                          break;
                        default:
                          System.out.println("Erreur du niveau");
                        }
                    }
                }

                @Override
                public void characters(char[] ch, int start, int length) throws SAXException {
                    if(buffer!=null){
                        buffer.append(ch, start, length);
                    }
                }
            };
            p.parse(f, dh);
        }catch(Exception e){
            System.out.println("Erreur: "+e);
        }
        
    }
}

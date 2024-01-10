/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package game;

import java.util.ArrayList;
import org.w3c.dom.*;
import java.util.logging.*;
import util.XMLUtil;
import java.io.File;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

/**
 *
 * @author ding
 */
public class Profil {
    private String nom;
    private String dateNaissance;
    private String avatar;
    private ArrayList<Partie> parties;
    public Document doc;
    
    public Profil(){
        nom = "j";
        dateNaissance = "1926/08/17";
        avatar = "player1.svg";
        parties = new ArrayList<>();
    }
    
    /*public Profil(String nom){
        this.nom=nom;
        dateNaissance = "1926/08/17";
        avatar = "player1.svg";
        parties = new ArrayList<>();
    }*/
    
    public Profil(String nom,String dateNaissance){
        this.nom=nom;
        this.dateNaissance=dateNaissance;
        avatar = "player1.svg";
        parties = new ArrayList<>();
    }
    
    public Profil(String nomFichier) {
        try{
           DocumentBuilderFactory domFactory = DocumentBuilderFactory.newInstance();
           domFactory.setNamespaceAware(true); // never forget this! // XXXXXX
           DocumentBuilder builder = domFactory.newDocumentBuilder();
           doc = builder.parse("src/xml/"+nomFichier+".xml"); 
        }catch(Exception e){
            
        }
        
        //doc = fromXML("src/xml/"+nomFichier+".xml");
        this.nom=doc.getElementsByTagName("nom").item(0).getTextContent();
        this.avatar=doc.getElementsByTagName("avatar").item(0).getTextContent();
        this.dateNaissance=xmlDateToProfileDate(doc.getElementsByTagName("anniversaire").item(0).getTextContent());
        parties = new ArrayList<>();
        NodeList partiex=doc.getElementsByTagName("partie");
        for(int i=0;i<partiex.getLength();i++){
            Element partie=(Element)partiex.item(i);
            ajouterPartie(new Partie(partie));
            //System.out.print(i);
        }
        // parsing à compléter
        // ?!#?!
    }
    
    public Document fromXML(String nomFichier) {
        try {
            return XMLUtil.DocumentFactory.fromFile(nomFichier);
        } catch (Exception ex) {
            Logger.getLogger(Profil.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    public void toXML(String nomFichier) {
        try {
            XMLUtil.DocumentTransform.writeDoc(doc, nomFichier);
        } catch (Exception ex) {
            Logger.getLogger(Profil.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static String xmlDateToProfileDate(String xmlDate) {
        String date;
        // récupérer le jour
        date = xmlDate.substring(xmlDate.lastIndexOf("-") + 1, xmlDate.length());
        date += "/";
        // récupérer le mois
        date += xmlDate.substring(xmlDate.indexOf("-") + 1, xmlDate.lastIndexOf("-"));
        date += "/";
        // récupérer l'année
        date += xmlDate.substring(0, xmlDate.indexOf("-"));

        return date;
    }
    
    public static String profileDateToXmlDate(String profileDate) {
        String date;
        // Récupérer l'année
        date = profileDate.substring(profileDate.lastIndexOf("/") + 1, profileDate.length());
        date += "-";
        // Récupérer  le mois
        date += profileDate.substring(profileDate.indexOf("/") + 1, profileDate.lastIndexOf("/"));
        date += "-";
        // Récupérer le jour
        date += profileDate.substring(0, profileDate.indexOf("/"));

        return date;
    }
    
    public void ajouterPartie(Partie p) {
        this.parties.add(p);
    }
    
    public int getDernierNiveau(){
        Partie dernier=parties.get(parties.size()-1);
        return dernier.getNiveau();
    }
    
    @Override
    public String toString(){
        String s="nom:"+nom+"\n"+"dateNaissance:"+dateNaissance+"\n";
        for(int i=0;i<parties.size();i++){
            s=s+parties.get(i).toString()+"\n";
        }
        return s;
    }
    
    public void sauvegarder(String filename){
        String path="src/xml/";
        File f = new File(path);
        if (!f.exists()) {
            f.mkdir();
        }
        try{
           DocumentBuilderFactory domFactory = DocumentBuilderFactory.newInstance();
           domFactory.setNamespaceAware(true); // never forget this! // XXXXXX
           DocumentBuilder builder = domFactory.newDocumentBuilder();
           doc = builder.parse(new File("src/xml/new.xml"));
            Element profil = (Element) doc.getElementsByTagName("profil").item(0);
            Element nom = doc.createElement("nom");
            Element avatar = doc.createElement("avatar");
            Element anniversaire = doc.createElement("anniversaire");
            Element parties = doc.createElement("parties");
            nom.setTextContent(this.nom);
            avatar.setTextContent(this.avatar);
            anniversaire.setTextContent(this.dateNaissance);
            profil.appendChild(nom);
            profil.appendChild(avatar);
            profil.appendChild(anniversaire);
            profil.appendChild(parties);
           for (int i = 0; i < this.parties.size(); i++) {
                    Element partie = doc.createElement("partie");
                    Element mot = doc.createElement("mot");
                    Element temps = doc.createElement("temps");
                    partie.setAttribute("date", profileDateToXmlDate(this.parties.get(i).getDate()));
                    if(this.parties.get(i).getTrouve()!=100){
                        partie.setAttribute("trouvé", String.valueOf(this.parties.get(i).getTrouve())+"%");
                    }
                    mot.setAttribute("niveau", String.valueOf(this.parties.get(i).getNiveau()));
                    mot.setTextContent(this.parties.get(i).getMot());
                    temps.setTextContent(String.valueOf(this.parties.get(i).getTemps()));
                    partie.appendChild(temps);
                    partie.appendChild(mot);
                    parties.appendChild(partie);
           }
        }catch(Exception e){
            System.out.print(e);
        }
        toXML(path+filename+".xml");
    }
    
    public ArrayList<Partie> getParties(){
        return parties;
    }
    
    public String getNom(){
        return nom;
    }
    
}

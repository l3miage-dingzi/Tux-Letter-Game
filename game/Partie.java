/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package game;
import org.w3c.dom.*;
/**
 *
 * @author ding
 */
public class Partie {
    private String date;
    private String mot;
    private int niveau;
    private int trouve;
    private double temps;
    
    public Partie(String date,String mot,int niveau){
        this.date=date;
        this.mot=mot;
        this.niveau=niveau;
        trouve=0;
        temps=0;
    }
    
    public Partie(Element partie){
        this.date=xmlDateToProfileDate(partie.getAttribute("date"));
        if(partie.hasAttribute("trouvé")){
            String t=partie.getAttribute("trouvé");
            t=t.substring(0, t.length()-1);
            this.trouve=Integer.parseInt(t);
        }else{
            this.trouve=100;
        }
        Element motp=(Element)partie.getElementsByTagName("mot").item(0);
        this.mot=motp.getTextContent();
        //System.out.print("mot:"+this.mot);
        this.niveau=Integer.parseInt(motp.getAttribute("niveau"));
        NodeList temps=partie.getElementsByTagName("temps");
        if(temps.getLength()>0){
            this.temps=Double.valueOf(((Element)temps.item(0)).getTextContent());
        }else{
            this.temps=0;
        }
    }
    
    public void setTrouve(int nbLettresRestantes){
        trouve=(int)((double)(mot.length()-nbLettresRestantes)/mot.length()*100);
        //System.out.print(trouve);
    }
    
    public void setTemps(int temps){
        if(temps>3){
            this.temps=temps;
        }   
    }
    
    public int getNiveau(){
        return niveau;
    }
    
    public int getTrouve(){
        return trouve;
    }
    
    public String getMot(){
        return mot;
    }
    
    public double getTemps(){
        return temps;
    }
    
    public String getDate(){
        return date;
    }
    
    @Override
    public String toString(){
        return "date:"+date+"\n"
                +"mot:"+mot+"\n"+
                "trouve:"+trouve+"%\n"+
                "niveau:"+niveau+"\n"+
                "temps:"+temps+"\n";
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
    
    public Element getPartie(Document doc){
        Element partie=doc.createElement("partie");
        partie.setAttribute(date, profileDateToXmlDate(date));
        if(trouve!=100){
            partie.setAttribute("trouvé", trouve+"%");
        }else{
            Element temps=doc.createElement("temps");
            temps.setTextContent(String.valueOf(this.temps));
            partie.appendChild(temps);
        }
        Element mot=doc.createElement("mot");
        mot.setAttribute("niveau", String.valueOf(niveau));
        mot.setTextContent(this.mot);
        partie.appendChild(mot);
        return partie;
    }
}

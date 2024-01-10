/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package game;

/**
 *
 * @author ding
 */
public class testdico {
    public static void main(String[] args){
        Dico dico = new Dico("src/xml/");
        
        /*dico.ajouteMotADico(1, "Charlotte");
        dico.ajouteMotADico(1, "Fréminet");
        dico.ajouteMotADico(1, "Furina");
        dico.ajouteMotADico(1, "Lynette");
        
        dico.ajouteMotADico(2, "Gorou");
        dico.ajouteMotADico(2, "Kaedehara Kazuha");
        
        dico.ajouteMotADico(3, "Kamisato Ayaka");
        dico.ajouteMotADico(4, "Kamisato Ayato");
        dico.ajouteMotADico(5, "Kirara");
        
        dico.ajouteMotADico(-10, "Kujou Sara");
        dico.ajouteMotADico(8, "Kuki Shinobu");
        */
        try{
            for(int i=0; i<5; i++){
                String mot = dico.getMotDepuisListeNiveau(i);
                System.out.println("Mot de niveau "+i+1+" "+mot+"\n");
            }   
        }catch(Exception e){
            System.out.println("erreur : "+e);
        }
    } 
}

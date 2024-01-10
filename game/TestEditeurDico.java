/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package game;

/**
 *
 * @author ding
 */
public class TestEditeurDico {
        public static void main(String[] args) {
        EditeurDico t = new EditeurDico("src/xml/dico.xml");
        t.ajouterMot("Chevreuse", 5);
        t.ajouterMot("Dainsleif", 1);
        t.ecrireDOM("src/xml/newdico.xml");
    }
}

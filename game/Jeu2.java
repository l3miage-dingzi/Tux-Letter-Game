/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package game;

import env3d.Env;
import java.util.ArrayList;
/**
 *
 * @author ding
 */
public abstract class Jeu2 {
    private Env env;
    private Room room;
    private Profil profil;
    private Tux tux;
    public ArrayList<Letter> lettres;
    private Dico dico;
    private String cheminFichierDico="src/xml/";
    public Jeu2(){
 
    // Crée un nouvel environnement
    env = new Env();
    lettres = new ArrayList<>();
    // Instancie une Room
    room = new Room();
     
    // Règle la camera
    env.setCameraXYZ(50, 60, 175);
    env.setCameraPitch(-20);
 
    // Désactive les contrôles par défaut
    env.setDefaultControl(false);
 
    // Instancie un profil par défaut
    profil = new Profil();
    dico = new Dico(cheminFichierDico);
    }
    
    public void execute() {
 
        // pour l'instant, nous nous contentons d'appeler la méthode joue comme cela
        // et nous créons une partie vide, juste pour que cela fonctionne
        joue();
         
        // Détruit l'environnement et provoque la sortie du programme 
        env.exit();
    }
         
         
    public void joue() {
        //String mot="furina de fontaine";
        String mot=dico.getMotDepuisListeNiveau(1);
        char[] tab = mot.toCharArray();
        // TEMPORAIRE : on règle la room de l'environnement. Ceci sera à enlever lorsque vous ajouterez les menus.
        env.setRoom(room);
 
        // Instancie un Tux
        tux = new Tux(env,room);
        env.addObject(tux);
        for(int i=0;i<mot.length();i++){
            Letter l = new Letter(tab[i], room);
            lettres.add(l);
            env.addObject(l);
        }
        Partie partie=new Partie("2023/11/28",mot,1);
        
        
        // Ici, on peut initialiser des valeurs pour une nouvelle partie
        demarrePartie(partie);
         
        // Boucle de jeu
        Boolean finished;
        finished = false;
        while (!finished) {
            tux.déplace();
            // Contrôles globaux du jeu (sortie, ...)
            //1 is for escape key
            if (partie.getTrouve()==mot.length()) {
                finished = true;
            }
 
            // Contrôles des déplacements de Tux (gauche, droite, ...)
            // ... (sera complété plus tard) ...
 
            // Ici, on applique les regles
            appliqueRegles(partie);
 
            // Fait avancer le moteur de jeu (mise à jour de l'affichage, de l'écoute des événements clavier...)
            env.advanceOneFrame();
        }
 
        // Ici on peut calculer des valeurs lorsque la partie est terminée
        terminerPartie(partie);
 
    }
    protected double distance(Letter letter){
        double x=letter.getX();
        double y=letter.getY();
        double z=letter.getZ();
        double xt=tux.getX();
        double yt=tux.getY();
        double zt=tux.getZ();
        return(Math.sqrt(Math.pow(x-xt, 2)+Math.pow(y-yt, 2)+Math.pow(z-zt, 2)));
    }
    
    protected boolean collision(Letter letter){
        return distance(letter)<=5.5;
    }
   
    protected abstract void demarrePartie(Partie partie);
    protected abstract void appliqueRegles(Partie partie);
    protected abstract void terminerPartie(Partie partie);
}



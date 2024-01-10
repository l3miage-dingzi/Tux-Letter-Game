/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package game;

/**
 *
 * @author ding
 */
public class JeuDevineLeMotOrdre extends Jeu{
    private int nbLettresRestantes;
    private Chronometre chrono;
    
    public JeuDevineLeMotOrdre(){
        super();
    }
    
    private boolean tuxTrouveLettre(){
        if(!super.lettres.isEmpty()){
            return collision(super.lettres.get(0));
        }else{
            return false;
        }
        
    }
    
    @Override
    protected void demarrePartie(Partie partie) {
       nbLettresRestantes=super.lettres.size();
       System.out.println(nbLettresRestantes);
       chrono = new Chronometre(30);
       chrono.start();
       
    }
    
    @Override
    protected void appliqueRegles(Partie partie){
        if(tuxTrouveLettre()) {
            super.lettres.remove(0);
            nbLettresRestantes--;
            System.out.println(nbLettresRestantes);
            partie.setTrouve(getNbLettresRestantes());
        }
    }
    
    @Override
    protected void terminerPartie(Partie partie) {
       chrono.stop();
       partie.setTemps(getTemps());
       super.lettres.clear();
       System.out.print(partie.toString());
    }
    
    @Override
    protected boolean fin() {
       return !chrono.remainsTime();
    }
    
    private int getNbLettresRestantes(){
        return nbLettresRestantes;
    }
    
    private int getTemps(){
        return (int)(chrono.getTime()/1000);
    }
}

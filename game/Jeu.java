/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game;

import env3d.Env;
import org.lwjgl.input.Keyboard;
import java.util.ArrayList;

/**
 *
 * @author gladen
 */
public abstract class Jeu {

    enum MENU_VAL {
        MENU_SORTIE, MENU_CONTINUE, MENU_JOUE
    }

    private final Env env;
    private Tux tux;
    private final Room mainRoom;
    private final Room menuRoom;
    private Letter letter;
    private Profil profil;
    private final Dico dico;
    protected EnvTextMap menuText;                         //text (affichage des texte du jeu)
    private String cheminFichierDico="src/xml/";
    public ArrayList<Letter> lettres;
    EnvTextMap gameText;
    EditeurDico t;
    
    public Jeu() {

        // Crée un nouvel environnement
        env = new Env();
        t = new EditeurDico("src/xml/dico.xml");
        // Instancie une Room
        mainRoom = new Room("plateau.xml");

        // Instancie une autre Room pour les menus
        menuRoom = new Room();
        menuRoom.setTextureEast("textures/black.png");
        menuRoom.setTextureWest("textures/black.png");
        menuRoom.setTextureNorth("textures/black.png");
        menuRoom.setTextureBottom("textures/black.png");

        // Règle la camera
        env.setCameraXYZ(50, 60, 175);
        env.setCameraPitch(-20);

        // Désactive les contrôles par défaut
        env.setDefaultControl(false);

        // Instancie un profil par défaut
        profil = new Profil();
        
        // Dictionnaire
        dico = new Dico(cheminFichierDico);

        // instancie le menuText
        menuText = new EnvTextMap(env);
        
        // Textes affichés à l'écran
        menuText.addText("Voulez vous ?", "Question", 200, 300);
        menuText.addText("1. Commencer une nouvelle partie ?", "Jeu1", 250, 280);
        menuText.addText("2. Charger une partie existante ?", "Jeu2", 250, 260);
        menuText.addText("3. Sortir de ce jeu ?", "Jeu3", 250, 240);
        menuText.addText("4. Quitter le jeu ?", "Jeu4", 250, 220);
        menuText.addText("Choisissez un nom de joueur : ", "NomJoueur", 200, 300);
        //menuText.addText("Choisissez un numero : ", "Numero", 200, 300);
        menuText.addText("1. Charger un profil de joueur existant ?", "Principal1", 250, 280);
        menuText.addText("2. Créer un nouveau joueur ?", "Principal2", 250, 260);
        menuText.addText("3. Sortir du jeu ?", "Principal3", 250, 240);
        menuText.addText("4. Ajouter un nouveau mot?", "Principal4", 250, 220);
        menuText.addText("5. Enregistrer le dico?", "Principal5", 250, 200);
        menuText.addText("Le nom du fichier :", "Fichier", 200, 300);
        menuText.addText("Choisissez le niveau par tapez : ", "Niveau", 200, 300);
        menuText.addText("Ne trouve pas ce joueur\n"+"Tapez 1 pour retourner", "Retour", 200, 300);
        menuText.addText("Ne trouve pas de partie existante\n"+"Tapez 1 pour retourner", "Partie", 200, 300);
        menuText.addText("Bien sauvegardé\n"+"Tapez 1 pour retourner", "Sau", 200, 300);
        menuText.addText("Veuillez sauvegarder? \n"+"Tapez 1 = Oui ,0 = Non", "Save", 200, 300);
        lettres = new ArrayList<>();
    }

    /**
     * Gère le menu principal
     *
     */
    public void execute() {

        MENU_VAL mainLoop;
        mainLoop = MENU_VAL.MENU_SORTIE;
        do {
            mainLoop = menuPrincipal();
        } while (mainLoop != MENU_VAL.MENU_SORTIE);
        this.env.setDisplayStr("Au revoir !", 300, 30);
        env.exit();
    }


    // fourni
    private String getNomJoueur() {
        String nomJoueur = "";
        menuText.getText("NomJoueur").display();
        nomJoueur = menuText.getText("NomJoueur").lire(true);
        menuText.getText("NomJoueur").clean();
        return nomJoueur;
    }
    
    public static boolean isNumeric(String str){
        boolean c=true;
        int i=0;
        while(c&&i<str.length()){
            if(!Character.isDigit(str.charAt(i))){
                c=false;
            }
            i++;
        }
        return c;
    }
    private int getNumero(String s,int j) {
        gameText = new EnvTextMap(env);
        gameText.addText(s+"Choisissez un numero : ", "Numero", 200, 300);
        gameText.addText("Num invalide", "Numin", 100, 280);
        String num = "";
        gameText.getText("Numero").display();
        num = gameText.getText("Numero").lire(true);
        while(!numval(num,j)){
            gameText.getText("Numin").display();
            num = gameText.getText("Numero").lire(true);
        }
        int k=Integer.parseInt(num);
        gameText.getText("Numero").clean();
        gameText.getText("Numin").clean();
        return k;
    }

    private String getMot(){
        String mot="";
        int i=0;
        boolean c=true;
        gameText = new EnvTextMap(env);
        gameText.addText("Mot :", "Mot", 200, 300);
        gameText.addText("Mot invalide", "Motin", 200, 280);
        gameText.getText("Mot").display();
        mot = gameText.getText("Mot").lire(true);
        while(!motval(mot)){
            gameText.getText("Motin").display();
            mot = gameText.getText("Mot").lire(true);
        }
        gameText.getText("Motin").clean();
        return mot;
    }
    
    private boolean motval(String mot){
        boolean c=true;
        int i=0;
        while(c&&i<mot.length()){
            if(!(Character.isLetter(mot.toCharArray()[i])||Character.isSpaceChar(mot.toCharArray()[i]))){
                c=false;
            }
            i++;
        }
        return c;
    }
    
    private boolean numval(String num,int j){
        if(isNumeric(num)){
            int k=Integer.parseInt(num);
            if(k<0||k>=j){
                return false;
            }
        }else{
            return false;
        }
        return true;
    }
    
    private int getNiveau(){
        gameText = new EnvTextMap(env);
        gameText.addText("Choisissez un niveau : ", "Niveau", 200, 300);
        gameText.addText("Niveau invalide", "Nivin", 200, 280);
        String num = "";
        gameText.getText("Niveau").display();
        num = gameText.getText("Niveau").lire(true);
        while(!numval(num,6)||num.equals("0")){
            gameText.getText("Nivin").display();
            num = gameText.getText("Niveau").lire(true);
        }
        int k=Integer.parseInt(num);
        gameText.getText("Niveau").clean();
        gameText.getText("Nivin").clean();
        return k;
    }
    // fourni, à compléter
    private MENU_VAL menuJeu() {

        MENU_VAL playTheGame;
        playTheGame = MENU_VAL.MENU_JOUE;
        Partie partie;
        do {
            // restaure la room du menu
            env.setRoom(menuRoom);
            // affiche menu
            menuText.getText("Question").display();
            menuText.getText("Jeu1").display();
            menuText.getText("Jeu2").display();
            menuText.getText("Jeu3").display();
            menuText.getText("Jeu4").display();
            
            // vérifie qu'une touche 1, 2, 3 ou 4 est pressée
            int touche = 0;
            while (!(touche == Keyboard.KEY_1 || touche == Keyboard.KEY_2 || touche == Keyboard.KEY_3 || touche == Keyboard.KEY_4)) {
                touche = env.getKey();
                env.advanceOneFrame();
            }

            // nettoie l'environnement du texte
            menuText.getText("Question").clean();
            menuText.getText("Jeu1").clean();
            menuText.getText("Jeu2").clean();
            menuText.getText("Jeu3").clean();
            menuText.getText("Jeu4").clean();

            // restaure la room du jeu
            //env.setRoom(mainRoom);

            // et décide quoi faire en fonction de la touche pressée
            switch (touche) {
                // -----------------------------------------
                // Touche 1 : Commencer une nouvelle partie
                // -----------------------------------------                
                case Keyboard.KEY_1: // choisi un niveau et charge un mot depuis le dico
                    // .......... dico.******
                    // crée un nouvelle partie
                    menuText.getText("Niveau").display();
                    int n=0;
                    while (!(n == Keyboard.KEY_1 || n == Keyboard.KEY_2 || n == Keyboard.KEY_3 || n == Keyboard.KEY_4|| n == Keyboard.KEY_5)) {
                        n = env.getKey();
                        env.advanceOneFrame();
                    }
                    menuText.getText("Niveau").clean();
                    String mot=dico.getMotDepuisListeNiveau(n-1);
                    gameText = new EnvTextMap(env);
                    gameText.addText("Le mot :"+mot+"\nTapez 1 pour commencer", "Mot", 200, 300);
                    gameText.getText("Mot").display();
                    int k=0;
                    while (!(k == Keyboard.KEY_1)) {
                        k = env.getKey();
                        env.advanceOneFrame();
                    }
                    gameText.getText("Mot").clean();
                    env.setRoom(mainRoom);
                    partie = new Partie("07/08/2018", mot, n-1);
                    // joue
                    joue(partie);
                    profil.ajouterPartie(partie);
                    // enregistre la partie dans le profil --> enregistre le profil
                    // .......... profil.******
                    playTheGame = MENU_VAL.MENU_JOUE;
                    break;

                // -----------------------------------------
                // Touche 2 : Charger une partie existante
                // -----------------------------------------                
                case Keyboard.KEY_2: // charge une partie existante
                    ArrayList<Partie> parties=profil.getParties();
                    if(parties.isEmpty()){
                        menuText.getText("Partie").display();
                        int i=0;
                        while (!(i == Keyboard.KEY_1)) {
                            i = env.getKey();
                            env.advanceOneFrame();
                        }
                    }else{
                        String s="";
                        int c=-1;
                        for(int j=0;j<parties.size();j++){
                            s+=j+": "+parties.get(j).getMot()+"\n";
                        }
                        gameText = new EnvTextMap(env);
                        gameText.addText(s, "Partie", 200, 300);
                        gameText.getText("Partie").display();
                        System.out.print(s);
                        c=getNumero(s,parties.size());
                        env.setRoom(mainRoom);
                        joue(parties.get(c));
   
                    }
                    
                    // Recupère le mot de la partie existante
                    // ..........
                    // joue
                    //joue(partie);
                    // enregistre la partie dans le profil --> enregistre le profil
                    // .......... profil.******
                    playTheGame = MENU_VAL.MENU_JOUE;
                    break;

                // -----------------------------------------
                // Touche 3 : Sortie de ce jeu
                // -----------------------------------------                
                case Keyboard.KEY_3:
                    menuText.getText("Save").display();
                    int p=0;
                    while (!(p == Keyboard.KEY_1 || p == Keyboard.KEY_0)) {
                        p = env.getKey();
                        env.advanceOneFrame();
                    }
                    if(p==Keyboard.KEY_1){
                       profil.sauvegarder(profil.getNom()); 
                    }
                    playTheGame = MENU_VAL.MENU_CONTINUE;
                    break;

                // -----------------------------------------
                // Touche 4 : Quitter le jeu
                // -----------------------------------------                
                case Keyboard.KEY_4:
                    playTheGame = MENU_VAL.MENU_SORTIE;
            }
        } while (playTheGame == MENU_VAL.MENU_JOUE);
        return playTheGame;
    }

    private MENU_VAL menuPrincipal() {

        MENU_VAL choix = MENU_VAL.MENU_CONTINUE;
        String nomJoueur;

        // restaure la room du menu
        env.setRoom(menuRoom);

        menuText.getText("Question").display();
        menuText.getText("Principal1").display();
        menuText.getText("Principal2").display();
        menuText.getText("Principal3").display();
        menuText.getText("Principal4").display(); 
        menuText.getText("Principal5").display();
        // vérifie qu'une touche 1, 2 ou 3 est pressée
        int touche = 0;
        while (!(touche == Keyboard.KEY_1 || touche == Keyboard.KEY_2 || touche == Keyboard.KEY_3|| touche == Keyboard.KEY_4|| touche == Keyboard.KEY_5)) {
            touche = env.getKey();
            env.advanceOneFrame();
        }

        menuText.getText("Question").clean();
        menuText.getText("Principal1").clean();
        menuText.getText("Principal2").clean();
        menuText.getText("Principal3").clean();
        menuText.getText("Principal4").clean();
        menuText.getText("Principal5").clean();
        // et décide quoi faire en fonction de la touche pressée
        switch (touche) {
            // -------------------------------------
            // Touche 1 : Charger un profil existant
            // -------------------------------------
            case Keyboard.KEY_1:
                // demande le nom du joueur existant
                nomJoueur = getNomJoueur();
                // charge le profil de ce joueur si possible
                try{
                   profil = new Profil(nomJoueur); 
                }catch(Exception e){
                    menuText.getText("Retour").display();
                    int i=0;
                    while (!(i == Keyboard.KEY_1)) {
                        i = env.getKey();
                        env.advanceOneFrame();
                    }
                    choix = menuPrincipal();
                }
                
                //if (profil.charge(nomJoueur)) {
                    choix = menuJeu();
                //} else {
                    //choix = MENU_VAL.MENU_SORTIE;//CONTINUE;
                //}
                break;

            // -------------------------------------
            // Touche 2 : Créer un nouveau joueur
            // -------------------------------------
            case Keyboard.KEY_2:
                // demande le nom du nouveau joueur
                nomJoueur = getNomJoueur();
                // crée un profil avec le nom d'un nouveau joueur
                profil = new Profil(nomJoueur,"1926/08/17");
                choix = menuJeu();
                break;

            // -------------------------------------
            // Touche 3 : Sortir du jeu
            // -------------------------------------
            case Keyboard.KEY_3:
                choix = MENU_VAL.MENU_SORTIE;
                break;
            case Keyboard.KEY_4:
                String mot="";
                mot=getMot();
                int n;
                n=getNiveau();
                dico.ajouteMotADico(n, mot);
                t.ajouterMot(mot, n);
                choix = menuPrincipal();
                break;
            case Keyboard.KEY_5:
                //String fichier;
                //menuText.getText("Fichier").display();
                //fichier = menuText.getText("Fichier").lire(true);
                t.ecrireDOM("src/xml/dico.xml");
                menuText.getText("Sau").display();
                    int i=0;
                    while (!(i == Keyboard.KEY_1)) {
                        i = env.getKey();
                        env.advanceOneFrame();
                    }
                choix = menuPrincipal();
                break;
        }
        return choix;
    }

    public void joue(Partie partie) {
        String mot=partie.getMot();
        char[] tab = mot.toCharArray();
        // Instancie un Tux
        tux = new Tux(env, mainRoom);
        env.addObject(tux);

        for(int i=0;i<mot.length();i++){
            Letter l = new Letter(tab[i], mainRoom);
            lettres.add(l);
            env.addObject(l);
        }


        // Ici, on peut initialiser des valeurs pour une nouvelle partie
        demarrePartie(partie);
        // Boucle de jeu
        Boolean finished;
        finished = false;
        while (!finished) {

            // Contrôles globaux du jeu (sortie, ...)
            //1 is for escape key
            if (fin()||partie.getTrouve()==100) {
                finished = true;
            }

            // Contrôles des déplacements de Tux (gauche, droite, ...)
            tux.déplace();

            // Ici, on applique les regles
            appliqueRegles(partie);
            

            // Fait avancer le moteur de jeu (mise à jour de l'affichage, de l'écoute des événements clavier...)
            env.advanceOneFrame();
        }

        // Ici on peut calculer des valeurs lorsque la partie est terminée
        terminerPartie(partie);

    }

    protected abstract void demarrePartie(Partie partie);

    protected abstract void appliqueRegles(Partie partie);

    protected abstract void terminerPartie(Partie partie);
    
    protected abstract boolean fin();
    
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

}
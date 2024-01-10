/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package game;

import env3d.Env;
import env3d.advanced.EnvNode;
import org.lwjgl.input.Keyboard;

/**
 *
 * @author ding
 */
public class Tux extends EnvNode{
    private Env env;
    private Room room;
    
    public Tux(Env env, Room room){
        this.env = env;
        this.room = room;

        setScale(4.0);
        setX(this.room.getWidth()/2);// positionnement au milieu de la largeur de la room
        setY(getScale() * 1.1); // positionnement en hauteur basé sur la taille de Tux
        setZ(this.room.getDepth()/2); // positionnement au milieu de la profondeur de la room
        
        setTexture("models/tux/tux.png");
        setModel("models/tux/tux.obj");  
    }
    
    public void déplace() {
        if (env.getKeyDown(Keyboard.KEY_W) || env.getKeyDown(Keyboard.KEY_UP)) { // Fleche 'haut' ou Z
            // Haut
            if(testRoomCollision(this.room.getWidth(),this.room.getDepth(),1)){
                this.setRotateY(180);
                this.setZ(this.getZ() - 1.0);
            }
            }
        if (env.getKeyDown(Keyboard.KEY_A) || env.getKeyDown(Keyboard.KEY_LEFT)) { // Fleche 'gauche' ou Q
            // Gauche
            if(testRoomCollision(this.room.getWidth(),this.room.getDepth(),2)){
            this.setRotateY(-90);
            this.setX(this.getX() - 1.0);
            }
            }
        if (env.getKeyDown(Keyboard.KEY_S) || env.getKeyDown(Keyboard.KEY_DOWN)) { 
            // Haut
            if(testRoomCollision(this.room.getWidth(),this.room.getDepth(),3)){
            this.setRotateY(0);
            this.setZ(this.getZ() + 1.0);
            }
            }
        if (env.getKeyDown(Keyboard.KEY_D) || env.getKeyDown(Keyboard.KEY_RIGHT)) {
            // Haut
            if(testRoomCollision(this.room.getWidth(),this.room.getDepth(),4)){
            this.setRotateY(90);
            this.setX(this.getX() + 1.0);
            }
            }
   
   }
    
    private boolean testRoomCollision(double x, double z,int i){
        boolean in =false;
        int j=5;
        switch(i){
            case 1:
                if(this.getZ()>j){
                    in=true;
                }
                break;
            case 2:
                if(this.getX()>j){
                    in=true;
                }
                break;
            case 3:
                if(this.getZ()<z-j){
                    in=true;
                }
                break;
            case 4:
                if(this.getX()<x-j){
                    in=true;
                }
                break;
            default:
                break;
        }
        
        return in;
    }
}

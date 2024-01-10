/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package game;

import env3d.Env;
import env3d.advanced.EnvNode;
/**
 *
 * @author ding
 */
public class Letter extends EnvNode{
    private char letter;
    
    
    public Letter(char l, Room room){
        letter = Character.toLowerCase(l);
        setScale(4.0);
        setX(room.getWidth()*Math.random());// positionnement au milieu de la largeur de la room
        setY(getScale() * 1.1); // positionnement en hauteur basé sur la taille de Tux
        setZ(room.getDepth()*Math.random()); // positionnement au milieu de la profondeur de la room
        
        System.out.println("Letter: "+letter);
        String texturePath = "/models/letter/"+letter+".png";
        if(letter!=' '){
            setTexture(texturePath);
        }else{
            texturePath = "/models/letter/cube.png";
            setTexture(texturePath);
        }
        setModel("/models/letter/cube.obj");  
    }
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package game;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/**
 *
 * @author ding
 */
public class Room {
    private int depth;
    private int height;
    private int width;
    private String textureBottom;
    private String textureNorth;
    private String textureEast;
    private String textureWest;
    private String textureTop;
    private String textureSouth;
    
    
    public Room() {
        this.depth  = 100;
        this.width  = 100;
        this.height = 60;

        this.textureBottom = "/textures/fence1.png";
        this.textureNorth  = "/textures/fence1.png";
        this.textureEast   = "/textures/fence1.png";
        this.textureWest   = "/textures/fence1.png";
    }
    
    public Room(String filename){
        String path="src/xml/";
        String filepath = path + filename;
        try{
            DocumentBuilderFactory dbf=DocumentBuilderFactory.newInstance();
            DocumentBuilder db=dbf.newDocumentBuilder();
            Document doc=db.parse(filepath);
            Element dimensions=(Element)doc.getElementsByTagName("dimensions").item(0);
            this.depth=Integer.parseInt( ((Element) dimensions.getElementsByTagName("depth").item(0)).getTextContent() );
            this.width=Integer.parseInt( ((Element) dimensions.getElementsByTagName("width").item(0)).getTextContent() );
            this.height=Integer.parseInt( ((Element) dimensions.getElementsByTagName("height").item(0)).getTextContent() );
            Element mapping=(Element)doc.getElementsByTagName("mapping").item(0);
            this.textureBottom=((Element) mapping.getElementsByTagName("textureBottom").item(0)).getTextContent();
            this.textureNorth=((Element) mapping.getElementsByTagName("textureNorth").item(0)).getTextContent();
            this.textureEast=((Element) mapping.getElementsByTagName("textureEast").item(0)).getTextContent();
            this.textureWest=((Element) mapping.getElementsByTagName("textureWest").item(0)).getTextContent();
        }catch(Exception e){
            System.out.println("Erreur: "+e);
        }
    }
    
    public int getDepth() {
        return depth;
    }

    public void setDepth(int depth) {
        this.depth = depth;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public String getTextureBottom() {
        return textureBottom;
    }

    public void setTextureBottom(String textureBottom) {
        this.textureBottom = textureBottom;
    }

    public String getTextureNorth() {
        return textureNorth;
    }

    public void setTextureNorth(String textureNorth) {
        this.textureNorth = textureNorth;
    }

    public String getTextureEast() {
        return textureEast;
    }

    public void setTextureEast(String textureEast) {
        this.textureEast = textureEast;
    }

    public String getTextureWest() {
        return textureWest;
    }

    public void setTextureWest(String textureWest) {
        this.textureWest = textureWest;
    }

    public String getTextureTop() {
        return textureTop;
    }

    public void setTextureTop(String textureTop) {
        this.textureTop = textureTop;
    }

    public String getTextureSouth() {
        return textureSouth;
    }

    public void setTextureSouth(String textureSouth) {
        this.textureSouth = textureSouth;
    }
    
    
    
}

package com.dexafree.reversed.components;

import com.dexafree.reversed.Game;
import com.dexafree.reversed.model.GameObject;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Circle;
import org.newdawn.slick.geom.Polygon;
import org.newdawn.slick.geom.Shape;

public class ObjectView {
    
    private GameObject object;
    private Color color;
    private Shape shape;
    private boolean picked = false;
    
    public ObjectView(GameObject object){
        this.object = object;
        color = getColor();
        shape = getShape();
    }
    
    public void render(Graphics g){
        if(!picked) {
            g.setColor(color);
            g.fill(shape);
        }
    }
    
    private Color getColor(){
        String objectColor = object.getColor();

        if(objectColor.equalsIgnoreCase("black")){
            return Color.black;
        }
        else if(objectColor.equalsIgnoreCase("blue")){
            return Color.blue;
        }
        else if(objectColor.equalsIgnoreCase("cyan")){
            return Color.cyan;
        }
        else if(objectColor.equalsIgnoreCase("darkGray")){
            return Color.darkGray;
        }
        else if(objectColor.equalsIgnoreCase("gray")){
            return Color.gray;
        }
        else if(objectColor.equalsIgnoreCase("green")){
            return Color.green;
        }
        else if(objectColor.equalsIgnoreCase("lightGray")){
            return Color.lightGray;
        }
        else if(objectColor.equalsIgnoreCase("magenta")){
            return Color.magenta;
        }
        else if(objectColor.equalsIgnoreCase("orange")){
            return Color.orange;
        }
        else if(objectColor.equalsIgnoreCase("pink")){
            return Color.pink;
        }
        else if(objectColor.equalsIgnoreCase("red")){
            return Color.red;
        }
        else if(objectColor.equalsIgnoreCase("white")){
            return Color.white;
        }
        else if(objectColor.equalsIgnoreCase("yellow")){
            return Color.yellow;
        }else {
            return Color.green;
        }
    }
    
    private Shape getShape(){
        String objectShape = object.getType();
        
        if(objectShape.equalsIgnoreCase("key")){
            
            int x = (object.getX() * Game.LINES_SIZE) + (Game.LINES_SIZE/2);
            int y = (object.getY() * Game.LINES_SIZE) + (Game.LINES_SIZE/2);
            
            return new Circle(x, y, 20);
        }
        
        return new Polygon(new float[]{0, 0});
        
    }
    
    public GameObject getObject(){
        return object;
        
    }
    
    public boolean isPicked(){
        return picked;
        
    }
    
    public void pick(){
        picked = true;
    }
    
    public void init(){
        color = getColor();
        shape = getShape();
        picked = false;
    }
    
    public void invert(float gameWidth){
        float[] points = shape.getPoints();
        for(int i=0;i<points.length;i+=2){
            points[i] = (gameWidth-points[i]);
        }
        shape = new Polygon(points);
        
    }
    
    public boolean collides(Shape s){
        return shape.intersects(s) || s.contains(shape);
    }
    
}

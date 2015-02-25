package com.dexafree.reversed.components;

import com.dexafree.reversed.AssetManager;
import com.dexafree.reversed.Game;
import com.dexafree.reversed.Utils;
import com.dexafree.reversed.model.GameObject;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Circle;
import org.newdawn.slick.geom.Polygon;
import org.newdawn.slick.geom.Shape;

public class ObjectView {
    
    private GameObject object;
    private Color color;
    private Shape shape;
    private boolean picked = false;
    private Image mImage;
    
    public ObjectView(GameObject object, AssetManager manager){
        this.object = object;
        color = Utils.getColor(object.getColor());
        shape = getShape();
        mImage = manager.getKeyImage();

    }
    
    public void render(Graphics g){
        if(!picked) {
            /*g.setColor(color);
            g.fill(shape);*/
            mImage.draw(shape.getX(), shape.getY(), color);
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
        color = Utils.getColor(object.getColor());
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

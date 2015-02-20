package com.dexafree.reversed.components;

import com.dexafree.reversed.Game;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Polygon;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;

public abstract class BaseView {

    protected Shape mShape;
    
    public abstract void render(Graphics g);
    public void invert(float gameWidth){
        
        float[] points = mShape.getPoints();
        
        for(int i=0;i<points.length;i+=2){
            points[i] = (gameWidth-points[i]);
        }
        
        mShape = new Polygon(points);
    }

    public static Rectangle getPlatform(int x, int y, int width, int height){

        assert (x >= 0 && x <= Game.WIDTH_SQUARES);
        assert (y >= 0 && y <= Game.HEIGHT_SQUARES);
        assert(width > 0);
        assert(height > 0);

        float rectX = (x * Game.LINES_SIZE);
        float rectY = (y * Game.LINES_SIZE);

        float rectWidth = (width * Game.LINES_SIZE);
        float rectHeight = (height * Game.LINES_SIZE);

        return new Rectangle(rectX, rectY, rectWidth, rectHeight);
    }

    public Shape getShape(){
        return mShape;
    }
    
    public boolean collidesOrContains(Shape s){
        return mShape.intersects(s) || mShape.contains(s);
    }
    
}
